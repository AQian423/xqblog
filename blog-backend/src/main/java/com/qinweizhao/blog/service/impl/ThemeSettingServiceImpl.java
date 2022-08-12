package com.qinweizhao.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qinweizhao.blog.config.properties.MyBlogProperties;
import com.qinweizhao.blog.exception.ServiceException;
import com.qinweizhao.blog.framework.handler.theme.config.support.Group;
import com.qinweizhao.blog.framework.handler.theme.config.support.Item;
import com.qinweizhao.blog.mapper.ThemeSettingMapper;
import com.qinweizhao.blog.model.entity.ThemeSetting;
import com.qinweizhao.blog.service.ThemeService;
import com.qinweizhao.blog.service.ThemeSettingService;
import com.qinweizhao.blog.util.ServiceUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Theme setting service implementation.
 *
 * @author johnniang
 * @since 2019-04-08
 */
@Slf4j
@Service
@AllArgsConstructor
public class ThemeSettingServiceImpl implements ThemeSettingService {

    private final ThemeSettingMapper themeSettingMapper;

    private final ThemeService themeService;

    private final Configuration configuration;

    private final MyBlogProperties blogProperties;

    @Override
    public Map<String, Object> getSettings() {

        Map<String, Item> itemMap = getConfigItemMap();

        // Get theme setting
        List<ThemeSetting> themeSettings = themeSettingMapper.selectList(Wrappers.emptyWrapper());

        Map<String, Object> result = new HashMap<>();

        // Build settings from user-defined
        themeSettings.forEach(themeSetting -> {
            String key = themeSetting.getSettingKey();

            Item item = itemMap.get(key);

            if (item == null) {
                return;
            }

            Object convertedValue = item.getDataType().convertTo(themeSetting.getSettingValue());
            log.debug("Converted user-defined data from [{}] to [{}], type: [{}]", themeSetting.getSettingValue(), convertedValue, item.getDataType());

            result.put(key, convertedValue);
        });

        // Build settings from pre-defined
        itemMap.forEach((name, item) -> {
            log.debug("Name: [{}], item: [{}]", name, item);

            if (item.getDefaultValue() == null || result.containsKey(name)) {
                return;
            }

            // Set default value
            Object convertedDefaultValue = item.getDataType().convertTo(item.getDefaultValue());
            log.debug("Converted pre-defined data from [{}] to [{}], type: [{}]", item.getDefaultValue(), convertedDefaultValue, item.getDataType());

            result.put(name, convertedDefaultValue);
        });

        return result;
    }

    @Override
    public boolean save(Map<String, Object> settings) {

        if (CollectionUtils.isEmpty(settings)) {
            return false;
        }
        // 保存配置
        settings.forEach((key, value) -> {
            this.saveItem(key,value.toString());
        });

        try {
            configuration.setSharedVariable("settings", listAsMap());
        } catch (TemplateModelException e) {
            throw new ServiceException("主题设置保存失败", e);
        }
        return false;
    }

    /**
     * 保存配置
     * @param key key
     * @param value value
     */
    private void saveItem(String key, String value) {


    }


    @Override
    public Map<String, Object> listAsMap() {
        // Convert to item map(key: item name, value: item)
        Map<String, Item> itemMap = getConfigItemMap();

        // Get theme setting
        List<ThemeSetting> themeSettings = themeSettingMapper.selectList(Wrappers.emptyWrapper());

        Map<String, Object> result = new HashMap<>();

        // Build settings from user-defined
        themeSettings.forEach(themeSetting -> {
            String key = themeSetting.getSettingKey();

            Item item = itemMap.get(key);

            if (item == null) {
                return;
            }

            Object convertedValue = item.getDataType().convertTo(themeSetting.getSettingValue());
            log.debug("Converted user-defined data from [{}] to [{}], type: [{}]", themeSetting.getSettingValue(), convertedValue, item.getDataType());

            result.put(key, convertedValue);
        });

        // Build settings from pre-defined
        itemMap.forEach((name, item) -> {
            log.debug("Name: [{}], item: [{}]", name, item);

            if (item.getDefaultValue() == null || result.containsKey(name)) {
                return;
            }

            // Set default value
            Object convertedDefaultValue = item.getDataType().convertTo(item.getDefaultValue());
            log.debug("Converted pre-defined data from [{}] to [{}], type: [{}]", item.getDefaultValue(), convertedDefaultValue, item.getDataType());

            result.put(name, convertedDefaultValue);
        });

        return result;
    }

//
//    @Override
//    public ThemeSetting save(String key, String value, String themeId) {
//        Assert.notNull(key, "Setting key must not be null");
//        assertThemeIdHasText(themeId);
//
//        log.debug("Starting saving theme setting key: [{}], value: [{}]", key, value);
//
//        // Find setting by key
//        Optional<ThemeSetting> themeSettingOptional = themeSettingRepository.findByThemeIdAndKey(themeId, key);
//
//        if (StringUtils.isBlank(value)) {
//            // Delete it
//            return themeSettingOptional
//                    .map(setting -> {
//                        themeSettingRepository.delete(setting);
//                        log.debug("Removed theme setting: [{}]", setting);
//                        return setting;
//                    }).orElse(null);
//        }
//
//        // Get config item map
//        Map<String, Item> itemMap = getConfigItemMap(themeId);
//
//        // Get item info
//        Item item = itemMap.get(key);
//
//        // Update or create
//        ThemeSetting themeSetting = themeSettingOptional
//                .map(setting -> {
//                    log.debug("Updating theme setting: [{}]", setting);
//                    setting.setValue(value);
//                    log.debug("Updated theme setting: [{}]", setting);
//                    return setting;
//                }).orElseGet(() -> {
//                    ThemeSetting setting = new ThemeSetting();
//                    setting.setKey(key);
//                    setting.setValue(value);
//                    setting.setThemeId(themeId);
//                    log.debug("Creating theme setting: [{}]", setting);
//                    return setting;
//                });
//        // Determine whether the data already exists
//        if (themeSettingRepository.findOne(Example.of(themeSetting)).isPresent()) {
//            return null;
//        }
//        // Save the theme setting
//        return themeSettingRepository.save(themeSetting);
//    }
//
//    @Override
//    public void save(Map<String, Object> settings, String themeId) {
//        assertThemeIdHasText(themeId);
//
//        if (CollectionUtils.isEmpty(settings)) {
//            return;
//        }
//
//        // Save the settings
//        settings.forEach((key, value) -> save(key, value.toString(), themeId));
//
//        try {
//            configuration.setSharedVariable("settings", listAsMapBy(themeService.getActivatedThemeId()));
//        } catch (TemplateModelException e) {
//            throw new ServiceException("主题设置保存失败", e);
//        }
//    }
//
//    @Override
//    public List<ThemeSetting> listBy(String themeId) {
//        assertThemeIdHasText(themeId);
//
//        return themeSettingRepository.findAllByThemeId(themeId);
//    }
//
//    @Override
//    public Map<String, Object> listAsMapBy(String themeId) {
//        // Convert to item map(key: item name, value: item)
//        Map<String, Item> itemMap = getConfigItemMap(themeId);
//
//        // Get theme setting
//        List<ThemeSetting> themeSettings = listBy(themeId);
//
//        Map<String, Object> result = new HashMap<>();
//
//        // Build settings from user-defined
//        themeSettings.forEach(themeSetting -> {
//            String key = themeSetting.getKey();
//
//            Item item = itemMap.get(key);
//
//            if (item == null) {
//                return;
//            }
//
//            Object convertedValue = item.getDataType().convertTo(themeSetting.getValue());
//            log.debug("Converted user-defined data from [{}] to [{}], type: [{}]", themeSetting.getValue(), convertedValue, item.getDataType());
//
//            result.put(key, convertedValue);
//        });
//
//        // Build settings from pre-defined
//        itemMap.forEach((name, item) -> {
//            log.debug("Name: [{}], item: [{}]", name, item);
//
//            if (item.getDefaultValue() == null || result.containsKey(name)) {
//                return;
//            }
//
//            // Set default value
//            Object convertedDefaultValue = item.getDataType().convertTo(item.getDefaultValue());
//            log.debug("Converted pre-defined data from [{}] to [{}], type: [{}]", item.getDefaultValue(), convertedDefaultValue, item.getDataType());
//
//            result.put(name, convertedDefaultValue);
//        });
//
//        return result;
//    }
//
//    @Override
//    public List<ThemeSetting> replaceUrl(String oldUrl, String newUrl) {
//        List<ThemeSetting> themeSettings = listAll();
//        List<ThemeSetting> replaced = new ArrayList<>();
//        themeSettings.forEach(themeSetting -> {
//            if (StringUtils.isNotEmpty(themeSetting.getValue())) {
//                themeSetting.setValue(themeSetting.getValue().replaceAll(oldUrl, newUrl));
//            }
//            replaced.add(themeSetting);
//        });
//        return updateInBatch(replaced);
//    }
//
//    @Override
//    @Transactional
//    public void deleteInactivated() {
//        themeSettingRepository.deleteByThemeIdIsNot(themeService.getActivatedThemeId());
//    }
//

    /**
     * 获取配置项映射。 （键：项目名称，值：项目）
     *
     * @return config item map
     */
    private Map<String, Item> getConfigItemMap() {
        // Get theme configuration
        List<Group> groups = themeService.listConfig();

        // Mix all items
        Set<Item> items = new LinkedHashSet<>();
        groups.forEach(group -> items.addAll(group.getItems()));

        // Convert to item map(key: item name, value: item)
        return ServiceUtils.convertToMap(items, Item::getName);
    }


}
