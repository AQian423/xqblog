package com.qinweizhao.blog.theme;

import com.qinweizhao.blog.framework.handler.theme.config.ThemePropertyResolver;
import com.qinweizhao.blog.framework.handler.theme.config.impl.YamlThemePropertyResolver;
import com.qinweizhao.blog.framework.handler.theme.config.support.ThemeProperty;
import com.qinweizhao.blog.util.FilenameUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.qinweizhao.blog.service.ThemeService.SETTINGS_NAMES;

/**
 * Theme property scanner.
 *
 * @author johnniang
 */
@Slf4j
public enum ThemePropertyScanner {

    /**
     * 实例
     */
    INSTANCE;

    /**
     * 主题属性文件名
     */
    private static final String[] THEME_PROPERTY_FILE_NAMES = {"theme.yaml", "theme.yml"};

    /**
     * 主题截图名称
     */
    private static final String THEME_SCREENSHOTS_NAME = "screenshot";

    private final ThemePropertyResolver propertyResolver = new YamlThemePropertyResolver();

    /**
     * 扫描主题属性 todo
     *
     * @param themePath them path must not be null
     * @return a list of them property
     */
    public ThemeProperty scan(Path themePath) {
        // 不存在就创建
        try {
            if (Files.notExists(themePath)) {
                Files.createDirectories(themePath);
            }
        } catch (IOException e) {
            log.error("Failed to create directory: " + themePath, e);
            return new ThemeProperty();
        }
        try (Stream<Path> pathStream = Files.list(themePath)) {
            // List and filter sub folders
            List<Path> themePaths = pathStream.filter(Files::isDirectory)
                    .collect(Collectors.toList());

            if (CollectionUtils.isEmpty(themePaths)) {
                return new ThemeProperty();
            }

            // Get theme properties
            ThemeProperty[] properties = themePaths.stream()
                    .map(this::fetchThemeProperty)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .peek(themeProperty -> {
                        themeProperty.setActivated(true);
                    })
                    .toArray(ThemeProperty[]::new);
            // Cache the themes
            return properties[0];
        } catch (IOException e) {
            log.error("Failed to get themes", e);
            return new ThemeProperty();
        }
    }

    /**
     * Fetch theme property
     *
     * @param themePath theme path must not be null
     * @return an optional theme property
     */

    public Optional<ThemeProperty> fetchThemeProperty(Path themePath) {
        Assert.notNull(themePath, "Theme path must not be null");

        Optional<Path> optionalPath = fetchPropertyPath(themePath);

        if (!optionalPath.isPresent()) {
            return Optional.empty();
        }

        Path propertyPath = optionalPath.get();

        try {
            // 获取属性内容
            String propertyContent = new String(Files.readAllBytes(propertyPath), StandardCharsets.UTF_8);

            // Resolve the base properties
            ThemeProperty themeProperty = propertyResolver.resolve(propertyContent);

            // Resolve additional properties
            themeProperty.setThemePath(themePath.toString());
            themeProperty.setFolderName(themePath.getFileName().toString());
            themeProperty.setHasOptions(hasOptions(themePath));
            themeProperty.setActivated(false);

            // Set screenshots
            getScreenshotsFileName(themePath).ifPresent(screenshotsName -> themeProperty.setScreenshots(StringUtils.join("/themes/",
                    FilenameUtils.getBasename(themeProperty.getThemePath()),
                    "/",
                    screenshotsName)));

            return Optional.of(themeProperty);
        } catch (Exception e) {
            log.warn("Failed to load theme property file", e);
        }
        return Optional.empty();
    }

    /**
     * 获取屏幕截图文件名
     *
     * @param themePath theme path must not be null
     * @return screenshots file name or null if the given theme path has not screenshots
     * @throws IOException throws when listing files
     */

    private Optional<String> getScreenshotsFileName(Path themePath) throws IOException {
        Assert.notNull(themePath, "Theme path must not be null");

        try (Stream<Path> pathStream = Files.list(themePath)) {
            return pathStream.filter(path -> Files.isRegularFile(path)
                            && Files.isReadable(path)
                            && FilenameUtils.getBasename(path.toString()).equalsIgnoreCase(THEME_SCREENSHOTS_NAME))
                    .findFirst()
                    .map(path -> path.getFileName().toString());
        }
    }

    /**
     * Gets property path of nullable.
     *
     * @param themePath theme path.
     * @return an optional property path
     */

    private Optional<Path> fetchPropertyPath(Path themePath) {
        Assert.notNull(themePath, "Theme path must not be null");

        for (String propertyPathName : THEME_PROPERTY_FILE_NAMES) {
            Path propertyPath = themePath.resolve(propertyPathName);

            log.debug("Attempting to find property file: [{}]", propertyPath);
            if (Files.exists(propertyPath) && Files.isReadable(propertyPath)) {
                log.debug("Found property file: [{}]", propertyPath);
                return Optional.of(propertyPath);
            }
        }

        log.warn("Property file was not found in [{}]", themePath);

        return Optional.empty();
    }

    /**
     * Check existence of the options.
     *
     * @param themePath theme path must not be null
     * @return true if it has options; false otherwise
     */
    private boolean hasOptions(Path themePath) {
        Assert.notNull(themePath, "Path must not be null");

        for (String optionsName : SETTINGS_NAMES) {
            // Resolve the options path
            Path optionsPath = themePath.resolve(optionsName);

            log.debug("Check options file for path: [{}]", optionsPath);

            if (Files.exists(optionsPath)) {
                return true;
            }
        }
        return false;
    }
}
