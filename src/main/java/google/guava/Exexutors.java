package google.guava;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * @ClassName Exexutors
 * @Description
 * @Author wpj
 * @Date 2021/5/20 18:44
 **/

public class Exexutors {


    public static void main(String[] args) throws InterruptedException {
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            final ListenableFuture<String> future = listeningExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                    soutI(finalI);
                return "Hello";
            }
        });
        }
    }

    private static void soutI(int i) throws InterruptedException {

        System.out.println(i);
    }

}
