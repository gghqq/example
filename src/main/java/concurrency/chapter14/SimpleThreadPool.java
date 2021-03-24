package concurrency.chapter14;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: Andy
 * @date: 2019/11/22
 * @Time: 15:58
 * @Description:  为了应对程序中 多次调用线程时 反复创建销毁线程而占用大量资源    空间换时间
 *        1.任务队列
 *        2.拒绝策略(防止任务过多等   方式  跑出异常,直接丢弃,阻塞,临时队列)
 *        3.init (初始值,,)
 *        4.active ()
 *        5.max
 *        init <= active <= max
 *
 **/
public class SimpleThreadPool {

    private final  int size;

    private final  static  int DEFAULT_SIZE = 10;

    private final static LinkedList<Thread>  TASK_QUEUE = new LinkedList<>();

    public SimpleThreadPool() {
        this(DEFAULT_SIZE);
    }

    public SimpleThreadPool(int size) {
        this.size = size;
        init();
    }

    private void init() {
    }

    private enum TaskState{
        FREE,RUNNING,BLOCKED,DEAD
    }

    private static class WorkerTask extends  Thread{
        private volatile  TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group,String name){
            super(group,name);
        }

        public TaskState getTaskState(){
            return  this.taskState;
        }

        public void run(){
            OUOTER:
            while (this.taskState != TaskState.DEAD){
                Runnable runnable;
                synchronized (TASK_QUEUE){
                    while (TASK_QUEUE.isEmpty()){
                        try {
                            taskState = TaskState.BLOCKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break    OUOTER;
                        }
                    }

                    runnable  =  TASK_QUEUE.removeFirst();
                    if (runnable != null){
                        taskState = TaskState.RUNNING;
                        runnable.run();
                        taskState = TaskState.FREE;

                    }
                }
            }
        }

        public void close(){
            this.taskState = TaskState.DEAD;
        }
    }
}