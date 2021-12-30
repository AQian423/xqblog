package com.qinweizhao.site.event.logger;

import com.qinweizhao.site.model.enums.LogType;
import com.qinweizhao.site.model.params.LogParam;
import com.qinweizhao.site.utils.ServletUtils;
import com.qinweizhao.site.utils.ValidationUtils;
import org.springframework.context.ApplicationEvent;

/**
 * @author johnniang
 * @date 19-4-20
 */
public class LogEvent extends ApplicationEvent {

    private final LogParam logParam;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source   the object on which the event initially occurred (never {@code null})
     * @param logParam login param
     */
    public LogEvent(Object source, LogParam logParam) {
        super(source);

        // Validate the log param
        ValidationUtils.validate(logParam);

        // Set ip address
        logParam.setIpAddress(ServletUtils.getRequestIp());

        this.logParam = logParam;
    }

    public LogEvent(Object source, String logKey, LogType logType, String content) {
        this(source, new LogParam(logKey, logType, content));
    }

    public LogParam getLogParam() {
        return logParam;
    }
}
