package com.qinweizhao.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiniu.common.Zone;
import com.qiniu.storage.Region;
import com.qinweizhao.blog.exception.MissingPropertyException;
import com.qinweizhao.blog.model.dto.OptionDTO;
import com.qinweizhao.blog.model.entity.Option;
import com.qinweizhao.blog.model.enums.ValueEnum;
import com.qinweizhao.blog.model.properties.PropertyEnum;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Option service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-14
 */
public interface OptionService extends IService<Option> {

    int DEFAULT_POST_PAGE_SIZE = 10;

    int DEFAULT_ARCHIVES_PAGE_SIZE = 10;

    int DEFAULT_COMMENT_PAGE_SIZE = 10;

    int DEFAULT_RSS_PAGE_SIZE = 20;

    String OPTIONS_KEY = "options";


    /**
     * 构建完整路径
     *
     * @param postId postId
     * @return String
     */
    String buildFullPath(Integer postId);


    /**
     * 保存属性
     *
     * @param property property
     * @param value    value
     */
    void saveProperty(PropertyEnum property, String value);

    /**
     * Save multiple options
     *
     * @param options options
     */
    void save(Map<String, Object> options);
//
//    /**
//     * Save multiple options
//     *
//     * @param optionParams option params
//     */
//    @Transactional
//    void save(@Nullable List<OptionParam> optionParams);
//
//    /**
//     * Save single option.
//     *
//     * @param optionParam option param
//     */
//    void save(@Nullable OptionParam optionParam);
//
//    /**
//     * Update option by id.
//     *
//     * @param optionId    option id must not be null.
//     * @param optionParam option param must not be null.
//     */
//    void update( Integer optionId,  OptionParam optionParam);
//


    /**
     * Saves blog properties.
     *
     * @param properties blog properties
     */
//    @Transactional
//    void saveProperties( Map<? extends PropertyEnum, String> properties);

    /**
     * Get all options
     *
     * @return Map
     */

    @Transactional
    Map<String, Object> listOptions();

    /**
     * Lists options by key list.
     *
     * @param keys key list
     * @return a map of option
     */

    Map<String, Object> listOptions(@Nullable List<String> keys);

    /**
     * Lists all option dtos.
     *
     * @return a list of option dto
     */

    List<OptionDTO> listDtos();

//    /**
//     * Pages option output dtos.
//     *
//     * @param pageable    page info must not be null
//     * @param optionQuery optionQuery
//     * @return a page of option output dto
//     */
//    Page<OptionSimpleDTO> pageDtosBy( Pageable pageable, OptionQuery optionQuery);
//
//    /**
//     * Removes option permanently.
//     *
//     * @param id option id must not be null
//     * @return option detail deleted
//     */
//    
//    Option removePermanently( Integer id);

    /**
     * Get option by key
     *
     * @param key option key must not be blank
     * @return option value or null
     */
    @Nullable
    Object getByKeyOfNullable(String key);

    /**
     * Gets option value of non null.
     *
     * @param key option key must not be null
     * @return option value of non null
     */

    Object getByKeyOfNonNull(String key);

    /**
     * Get option by key
     *
     * @param key option key must not be blank
     * @return an optional option value
     */

    Optional<Object> getByKey(String key);

    /**
     * Gets option value by blog property.
     *
     * @param property blog property must not be null
     * @return an option value
     */
    @Nullable
    Object getByPropertyOfNullable(PropertyEnum property);

    /**
     * Gets option value by blog property.
     *
     * @param property blog property
     * @return an optiona value
     * @throws MissingPropertyException throws when property value dismisses
     */

    Object getByPropertyOfNonNull(PropertyEnum property);

    /**
     * Gets option value by blog property.
     *
     * @param property blog property must not be null
     * @return an optional option value
     */

    Optional<Object> getByProperty(PropertyEnum property);

    /**
     * Gets property value by blog property.
     *
     * @param property     blog property must not be null
     * @param propertyType property type must not be null
     * @param defaultValue default value
     * @param <T>          property type
     * @return property value
     */
    <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType, T defaultValue);

    /**
     * Gets property value by blog property.
     * <p>
     * Default value from property default value.
     *
     * @param property     blog property must not be null
     * @param propertyType property type must not be null
     * @param <T>          property type
     * @return property value
     */
    <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType);

    /**
     * Gets property value by blog property.
     *
     * @param property     blog property must not be null
     * @param propertyType property type must not be null
     * @param <T>          property type
     * @return property value
     */
    <T> Optional<T> getByProperty(PropertyEnum property, Class<T> propertyType);

    /**
     * Gets value by key.
     *
     * @param key          key must not be null
     * @param valueType    value type must not be null
     * @param defaultValue default value
     * @param <T>          property type
     * @return value
     */
    <T> T getByKeyOrDefault(String key, Class<T> valueType, T defaultValue);

    /**
     * Gets value by key.
     *
     * @param key       key must not be null
     * @param valueType value type must not be null
     * @param <T>       value type
     * @return value
     */

    <T> Optional<T> getByKey(String key, Class<T> valueType);

    /**
     * Gets enum value by property.
     *
     * @param property  property must not be blank
     * @param valueType enum value type must not be null
     * @param <T>       enum value type
     * @return an optional enum value
     */

    <T extends Enum<T>> Optional<T> getEnumByProperty(PropertyEnum property, Class<T> valueType);

    /**
     * Gets enum value by property.
     *
     * @param property     property must not be blank
     * @param valueType    enum value type must not be null
     * @param defaultValue default value
     * @param <T>          enum value type
     * @return enum value
     */
    @Nullable
    <T extends Enum<T>> T getEnumByPropertyOrDefault(PropertyEnum property, Class<T> valueType, @Nullable T defaultValue);

    /**
     * Gets value enum by property.
     *
     * @param property  property must not be blank
     * @param valueType enum value type must not be null
     * @param enumType  enum type must not be null
     * @param <V>       enum value type
     * @param <E>       value enum type
     * @return an optional value enum value
     */

    <V, E extends ValueEnum<V>> Optional<E> getValueEnumByProperty(PropertyEnum property, Class<V> valueType, Class<E> enumType);

    /**
     * Gets value enum by property.
     *
     * @param property     property must not be blank
     * @param valueType    enum value type must not be null
     * @param enumType     enum type must not be null
     * @param defaultValue default value enum value
     * @param <V>          enum value type
     * @param <E>          value enum type
     * @return value enum value or null if the default value is null
     */
    @Nullable
    <V, E extends ValueEnum<V>> E getValueEnumByPropertyOrDefault(PropertyEnum property, Class<V> valueType, Class<E> enumType, @Nullable E defaultValue);


    /**
     * Gets post page size.
     *
     * @return page size
     */
    int getPostPageSize();

    /**
     * Gets archives page size.
     *
     * @return page size
     */
    int getArchivesPageSize();

    /**
     * Gets comment page size.
     *
     * @return page size
     */
    int getCommentPageSize();

    /**
     * Gets rss page size.
     *
     * @return page size
     */
    int getRssPageSize();

    /**
     * Get qi niu yun zone.
     *
     * @return qiniu zone
     */

    @Deprecated
    Zone getQnYunZone();

    /**
     * Get qiniu oss region.
     *
     * @return qiniu region
     */

    Region getQiniuRegion();

    /**
     * Gets locale.
     *
     * @return locale user set or default locale
     */

    Locale getLocale();

    /**
     * Gets blog base url. (Without /)
     *
     * @return blog base url (If blog url isn't present, current machine IP address will be default)
     */
    String getBlogBaseUrl();

    /**
     * Gets blog title.
     *
     * @return blog title.
     */

    String getBlogTitle();

    /**
     * Gets global seo keywords.
     *
     * @return keywords
     */
    String getSeoKeywords();

    /**
     * Get global seo description.
     *
     * @return description
     */
    String getSeoDescription();

    /**
     * Gets blog birthday.
     *
     * @return birthday timestamp
     */
//    long getBirthday();

    /**
     * Get sheet custom prefix.
     *
     * @return sheet prefix.
     */
    String getSheetPrefix();

    /**
     * Get links page custom prefix.
     *
     * @return links page prefix.
     */
    String getLinksPrefix();

    /**
     * Get photos page custom prefix.
     *
     * @return photos page prefix.
     */
    String getPhotosPrefix();

    /**
     * Get journals page custom prefix.
     *
     * @return journals page prefix.
     */
    String getJournalsPrefix();

    /**
     * Get archives custom prefix.
     *
     * @return archives prefix.
     */
    String getArchivesPrefix();

    /**
     * Get categories custom prefix.
     *
     * @return categories prefix.
     */
    String getCategoriesPrefix();

    /**
     * Get tags custom prefix.
     *
     * @return tags prefix.
     */
    String getTagsPrefix();

    /**
     * Get custom path suffix.
     *
     * @return path suffix.
     */
    String getPathSuffix();

    /**
     * Is enabled absolute path.
     *
     * @return true or false.
     */
    Boolean isEnabledAbsolutePath();


    /**
     * 博客注册时间
     *
     * @return long
     */
    long getBirthday();
//
//    /**
//     * Replace option url in batch.
//     *
//     * @param oldUrl old blog url.
//     * @param newUrl new blog url.
//     * @return replaced options.
//     */
//    List<OptionDTO> replaceUrl( String oldUrl,  String newUrl);
//
//    /**
//     * Converts to option output dto.
//     *
//     * @param option option must not be null
//     * @return an option output dto
//     */
//    
//    OptionSimpleDTO convertToDto( Option option);
}
