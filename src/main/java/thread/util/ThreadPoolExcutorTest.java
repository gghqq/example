package thread.util;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * @ClassName ThreadPoolExcutor
 * @Description
 * @Author agan
 * @Date 2021/4/12 21:34
 **/

public class ThreadPoolExcutorTest {
    private static AtomicInteger ai = new AtomicInteger();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));
        ListenableFuture<List<Object>> future = executorService.submit(() -> {
            List<Object> objects = new ArrayList<>();
            objects.add(ai.incrementAndGet());
            return objects;
        });
        for (Object o : future.get()) {
            System.out.println(o);
        }
    }
}
