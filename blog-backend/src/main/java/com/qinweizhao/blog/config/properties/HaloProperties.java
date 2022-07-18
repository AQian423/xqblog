package com.qinweizhao.blog.config.properties;

import com.qinweizhao.blog.model.enums.Mode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

import static com.qinweizhao.blog.model.support.HaloConst.FILE_SEPARATOR;
import static com.qinweizhao.blog.model.support.HaloConst.USER_HOME;
import static com.qinweizhao.blog.util.HaloUtils.ensureSuffix;


/**
 * 配置属性
 *
 * @author johnniang
 * @author ryanwang
 * @author qinweizhao
 * @date 2019-03-15
 */
@Data
@ConfigurationProperties("halo")
public class HaloProperties {

    /**
     * 是否开启文档
     */
    private boolean docDisabled = true;

    /**
     * 生产环境（默认为真）
     */
    private boolean productionEnv = true;

    /**
     * 启用身份验证
     */
    private boolean authEnabled = true;

    /**
     * 启动模式
     */
    private Mode mode = Mode.PRODUCTION;

    /**
     * 后台管理路径
     */
    private String adminPath = "admin";

    /**
     * 工作目录
     */
    private String workDir = ensureSuffix(USER_HOME, FILE_SEPARATOR) + FILE_SEPARATOR;

    /**
     * 上传前缀
     */
    private String uploadUrlPrefix = "image";

    /**
     * 下载超时
     */
    private Duration downloadTimeout = Duration.ofSeconds(30);

    /**
     * 缓存存储实现
     * memory
     * level
     */
    private String cache = "memory";

}
