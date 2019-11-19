package concurrency.chapter8;


public class DeadLock {
    private OtherService otherService;
    private final Object lock;

    public DeadLock(OtherService otherService) {
        this.lock = new Object();
        this.otherService = otherService;
    }

    public void m1() {
        synchronized (this.lock) {
            System.out.println(" m1========= ");
            this.otherService.s1();
        }
    }

    public void m2() {
        synchronized (this.lock) {
            System.out.println(" m2========= ");
        }
    }
}