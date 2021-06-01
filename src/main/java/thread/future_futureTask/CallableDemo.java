package thread.future_futureTask;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @ClassName CallableDemo https://www.jianshu.com/p/b24f75fee11d
 * @Description
 *
 * Callable 和 Runnable 的不同之处
 * 方法名 ：Callable 规定的执行方法是 call()，而 Runnable 规定的执行方法是 run()；
 * 返回值 ：Callable 的任务执行后有返回值，而 Runnable 的任务执行后是没有返回值的；
 * 抛出异常 ：call() 方法可抛出异常，而 run() 方法是不能抛出受检查异常的；
 * 与 Callable 配合的有一个 Future 接口，通过 Future 可以了解任务执行情况，或者取消任务的执行，还可获取任务执行的结果，这些功能都是 Runnable 做不到的，
 *
 *  Future的作用
 * 简单来说就是利用线程达到异步的效果，同时还可以获取子线程的返回值。
 * 比如当做一定运算的时候，运算过程可能比较耗时，有时会去查数据库，或是繁重的计算，比如压缩、加密等，在这种情况下，如果我们一直在原地等待方法返回，显然是不明智的，整体程序的运行效率会大大降低。
 * 我们可以把运算的过程放到子线程去执行，再通过 Future 去控制子线程执行的计算过程，最后获取到计算结果。这样一来就可以把整个程序的运行效率提高，是一种异步的思想。
 *
 * get方法（获取结果）
 *    已执行完可以获取结果, 执行中,队列中都会阻塞.
 *    任务取消抛出 CancellationException。  任务执行过程中抛出异常抛出 ExecutionException 异常 ,任务被中断 抛出InterruptedException。 任务超时，抛出 TimeoutException，
 * cancel方法（取消任务的执行）
 *    任务还没有开始,这个任务就会被正常取消，未来也不会被执行，那么 cancel 方法返回 true
 *    任务已经完成，或者之前已经被取消过了，那么执行 cancel 方法则代表取消失败，返回 false
 *    会根据我们传入的参数,true，执行任务的线程就会收到一个中断的信号，正在执行的任务可能会有一些处理中断的逻辑，进而停止，
 *                     false 则就代表不中断正在运行的任务，也就是说，本次 cancel 不会有任何效果，同时 cancel 方法会返回 false
 *
 *
 * @Author wpj
 * @Date 2021/5/20 22:00
 **/

public class CallableDemo {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10)); //google guava
        List< Future<List<Integer>> > futures =new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {  //模拟提交10个任务

            Future<List<Integer>> f = executorService.submit(() -> {
                    Thread.sleep(500);
                 List<Integer> ints = getInts();
//                 ints.parallelStream().forEach(System.out::println);
//                throw new Exception("故意抛出异常");
                 return ints;
            });


            futures.add(f);
        }
        futures.parallelStream().forEach(f->{ //循环取值
            try {
                System.out.println(f.isDone()); //任务是否完成
                System.out.println(f.get()); //异步 最后取不到的话会阻塞, 或抛出各种类型异常
                System.out.println(f.cancel(false)); //取消任务
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }catch (Exception e){

            }
        });
    }

    private static List<Integer> getInts(){
        return Lists.newArrayList(new Random().nextInt(200),new Random().nextInt(300),new Random().nextInt(400));

    }
}
