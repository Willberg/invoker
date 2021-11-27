//1117. H2O 生成 https://leetcode-cn.com/problems/building-h2o/
package concurrency.buildingh2o;

import java.util.concurrent.Semaphore;

// acquire(n)可以一次性获取n个许可, 如果可以获取就执行后面代码,否则阻塞当前线程直到别的线程release了足够多的许可为止
public class H2O {
    private final Semaphore hSema;
    private final Semaphore oSema;

    public H2O() {
        hSema = new Semaphore(2);
        oSema = new Semaphore(0);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hSema.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        oSema.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oSema.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        hSema.release(2);
    }
}
