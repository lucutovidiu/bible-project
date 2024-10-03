package ro.bible.quarkus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutorService {
    public final static ExecutorService executorService;

    static {
        executorService = new ThreadPoolExecutor(
                2, 4, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

    /*
    abort: Throws a RejectedExecutionException if the queue is full.
    discard: Discards the new task.
    discard-oldest: Discards the oldest task in the queue to make room for new tasks.
    caller-runs: The task is run in the caller’s thread.

     */


}
