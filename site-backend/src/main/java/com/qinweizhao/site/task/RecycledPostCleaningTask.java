package com.qinweizhao.site.task;

import com.qinweizhao.site.model.entity.BasePost;
import com.qinweizhao.site.model.entity.Post;
import com.qinweizhao.site.model.enums.PostStatus;
import com.qinweizhao.site.model.enums.TimeUnit;
import com.qinweizhao.site.model.properties.PostProperties;
import com.qinweizhao.site.service.OptionService;
import com.qinweizhao.site.service.PostService;
import com.qinweizhao.site.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Wh1te
 * @author guqing
 * @date 2020-10-19
 */
@Slf4j
@Component
public class RecycledPostCleaningTask {

    private final OptionService optionService;

    private final PostService postService;

    public RecycledPostCleaningTask(OptionService optionService, PostService postService) {
        this.optionService = optionService;
        this.postService = postService;
    }

    /**
     * Clean recycled posts if RECYCLED_POST_CLEANING_ENABLED is true
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public synchronized void run() {
        Boolean recycledPostCleaningEnabled = optionService
                .getByPropertyOrDefault(PostProperties.RECYCLED_POST_CLEANING_ENABLED, Boolean.class,
                        false);
        log.debug("{} = {}", PostProperties.RECYCLED_POST_CLEANING_ENABLED.getValue(),
                recycledPostCleaningEnabled);
        if (!recycledPostCleaningEnabled) {
            return;
        }

        Integer recycledPostRetentionTime = optionService
                .getByPropertyOrDefault(PostProperties.RECYCLED_POST_RETENTION_TIME, Integer.class,
                        PostProperties.RECYCLED_POST_RETENTION_TIME.defaultValue(Integer.class));
        TimeUnit timeUnit = optionService
                .getEnumByPropertyOrDefault(PostProperties.RECYCLED_POST_RETENTION_TIMEUNIT,
                        TimeUnit.class, TimeUnit.DAY);
        log.debug("{} = {}", PostProperties.RECYCLED_POST_RETENTION_TIME.getValue(),
                recycledPostRetentionTime);
        log.debug("{} = {}", PostProperties.RECYCLED_POST_RETENTION_TIMEUNIT.getValue(),
                Objects.requireNonNull(timeUnit).name());

        long expiredIn;
        switch (timeUnit) {
            case HOUR:
                expiredIn = recycledPostRetentionTime;
                break;
            case DAY:
            default:
                expiredIn = recycledPostRetentionTime * 24;
                break;
        }
        List<Post> recyclePost = postService.listAllBy(PostStatus.RECYCLE);
        LocalDateTime now = LocalDateTime.now();
        List<Integer> ids = recyclePost.stream().filter(post -> {
            LocalDateTime updateTime = DateTimeUtils.toLocalDateTime(post.getUpdateTime());
            long until = updateTime.until(now, ChronoUnit.HOURS);
            return until >= expiredIn;
        }).map(BasePost::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        log.info("Start cleaning recycled posts");
        List<Post> posts = postService.removeByIds(ids);
        log.info(
                "Recycled posts cleaning has been completed, {} posts has been permanently deleted",
                posts.size());
    }

}
