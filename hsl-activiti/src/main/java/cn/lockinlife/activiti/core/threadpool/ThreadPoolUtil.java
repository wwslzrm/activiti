package cn.lockinlife.activiti.core.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Yuck on 2019/6/27
 */
public class ThreadPoolUtil {
    private static volatile ThreadPoolExecutor threadPool;
    private static int size = Runtime.getRuntime().availableProcessors() + 1;

    private ThreadPoolUtil() {}

    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(size, 2 * size, 60, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(32));
                }
            }
        }
        return threadPool;
    }
}

