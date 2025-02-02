package com.qinweizhao.blog.framework.listener.post;

import com.qinweizhao.blog.framework.event.post.AbstractVisitEvent;
import com.qinweizhao.blog.service.PostService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Abstract visit event listener.
 *
 * @author johnniang
 * @author qinweizhao
 * @since 19-4-24
 */
@Slf4j
public abstract class AbstractVisitEventListener {

    private final Map<Integer, BlockingQueue<Integer>> visitQueueMap;

    private final Map<Integer, PostVisitTask> visitTaskMap;

    private final PostService postService;

    private final ExecutorService executor;

    protected AbstractVisitEventListener(PostService postService) {
        this.postService = postService;

        int initCapacity = 8;

        long count = postService.count();

        if (count < initCapacity) {
            initCapacity = (int) count;
        }

        visitQueueMap = new ConcurrentHashMap<>(initCapacity << 1);
        visitTaskMap = new ConcurrentHashMap<>(initCapacity << 1);

        this.executor = Executors.newCachedThreadPool();
    }

    /**
     * 处理访问事件
     *
     * @param event event
     * @throws InterruptedException e
     */
    protected void handleVisitEvent(@NonNull AbstractVisitEvent event) throws InterruptedException {
        Assert.notNull(event, "Visit event must not be null");

        Integer id = event.getId();

        log.debug("收到访问事件，文章 id: [{}]", id);


        BlockingQueue<Integer> postVisitQueue = visitQueueMap.computeIfAbsent(id, this::createEmptyQueue);

        visitTaskMap.computeIfAbsent(id, this::createPostVisitTask);

        postVisitQueue.put(id);
    }


    private PostVisitTask createPostVisitTask(Integer postId) {

        PostVisitTask postVisitTask = new PostVisitTask(postId);

        executor.execute(postVisitTask);

        log.debug("为文章 ID 创建了一个新的文章访问任务： [{}]", postId);
        return postVisitTask;
    }

    private BlockingQueue<Integer> createEmptyQueue(Integer postId) {

        return new LinkedBlockingQueue<>();
    }


    /**
     * Post visit task.
     */
    private class PostVisitTask implements Runnable {

        private final Integer id;

        private PostVisitTask(Integer id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BlockingQueue<Integer> postVisitQueue = visitQueueMap.get(id);
                    Integer postId = postVisitQueue.take();

                    log.debug("访问了文章: [{}]", postId);

                    boolean flag = postService.increaseVisit(postId);

                    log.debug("文章编号[{}]的访问量增加:{} ", postId, flag);
                } catch (InterruptedException e) {
                    log.debug("文章增加访问量失败，线程: " + Thread.currentThread().getName() + " 被打断", e);
                }
            }

            log.debug("线程: [{}] 被打断了", Thread.currentThread().getName());
        }
    }
}
