package concurrency.chapter8;


public class DeadLockTest {
    public static void main(String[] args) {
        final OtherService otherService = new OtherService();
        final DeadLock deadLock = new DeadLock(otherService);
        otherService.setDeadLock(deadLock);


        (new Thread("T11111") {
            public void run() {
                while (true) {
                    deadLock.m1();
                }
            }
        }).start();
        (new Thread("T22222") {
            public void run() {
                while (true) {
                    otherService.s2();
                }
            }
        }).start();
    }
}
