package thread.future_futureTask;



import lombok.extern.slf4j.Slf4j;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @ClassName FutureTaskDemo1
 * @Description
 * FutureTask有一个方法 void done()会在每个线程执行完成return结果时回调。
 * 假设现在需要实现每个线程完成任务执行后主动执行后续任务。
 *
 * @Author wpj
 * @Date 2021/5/26 22:37
 **/


@Slf4j
public class FutureTaskDemo1 {

    public static void main(String[] args) throws InterruptedException {
        // 月饼生产者
        final Callable<Integer> productor = () -> { //创建一个生产月饼的线程
            log.info("月饼制作中。。。。");
            Thread.sleep(5000);
            return (Integer) new Random().nextInt(1000);
        };

        // 月饼消费者
        Runnable customer = new Runnable() {
            @Override
            public void run() {
                ExecutorService es = Executors.newCachedThreadPool();
                log.info("老板给我来一个月饼");
                for (int i = 0; i < 3; i++) {
                    FutureTask<Integer> futureTask = new FutureTask<Integer>(productor) {
                        @Override
                        protected void done() {
                            super.done();  //done()会在每个线程执行完成return结果时回调
                            try {
                                log.info(String.format(" 编号[%s]月饼已打包好", get()));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    es.submit(futureTask);
                }
            }
        };
        new Thread(customer).start();
    }
}
