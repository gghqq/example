package thread.future_futureTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ClassName FutureTask
 * @Description
 *
 *
 * Future的使用
 * FutureTask可用于异步获取执行结果或取消执行任务的场景。通过传入Runnable或者Callable的任务给FutureTask，直接调用其run方法或者放入线程池执行，之后可以在外部通过FutureTask的get方法异步获取执行结果，因此，FutureTask非常适合用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。另外，FutureTask还可以确保即使调用了多次run方法，它都只会执行一次Runnable或者Callable任务，或者通过cancel取消FutureTask的执行等。
 *
 * FutureTask执行多任务计算的使用场景
 * 利用FutureTask和ExecutorService，可以用多线程的方式提交计算任务，主线程继续执行其他任务，当主线程需要子线程的计算结果时，在异步获取子线程的执行结果。
 *
 *
 *
 *
 * @Author wpj
 * @Date 2021/5/26 21:48
 **/

public class FutureTaskDemo {


    public static void main(String[] args) {
        FutureTaskDemo f = new FutureTaskDemo();

        // 创建任务集合
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
        // 创建线程池
        ExecutorService exec = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            // 传入Callable对象创建FutureTask对象
            FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(f.new ComputerTask(i, "" + i));
            taskList.add(integerFutureTask);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            exec.submit(integerFutureTask);  //提交给线程池   integerFutureTask.run();
        }

        System.out.println("所有计算任务提交完毕, 主线程接着干其他事情！");
        // 开始统计各计算线程计算结果
        Integer totalResult = 0;
        for (FutureTask<Integer> ft : taskList) {
            try {
                //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                totalResult = totalResult + ft.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池
        exec.shutdown();
        System.out.println("多任务计算后的总结果是:" + totalResult);

    }


    private class ComputerTask implements Callable {

        private Integer i = 0;
        private String name = "";

        public ComputerTask(Integer i, String name) {
            this.i = i;
            this.name = name;
            System.out.println("生成子线程任务: " + name);
        }

        public String getTaskName() {
            return this.name;
        }

        @Override
        public Object call() throws Exception {
            for (int j = 0; j < 100; j++) {
                i = +j;
            }
            // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。
            Thread.sleep(5000);
            System.out.println("子线程计算任务: " + name + " 执行完成!");
            return i;
        }
    }
}

