package 系统设计相关.threadPool;

public interface AsyncService  {

    /**
     * 执行异步任务
     * 可以根据需求，自己加参数拟定
     */
    void executeAsync();
}
