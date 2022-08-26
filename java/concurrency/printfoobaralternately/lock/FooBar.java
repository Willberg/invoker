// 1115. 交替打印FooBar https://leetcode-cn.com/problems/print-foobar-alternately/
package concurrency.printfoobaralternately.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock 可重入锁,获得锁的线程可以继续多次加锁;
// lock()加锁,否则阻塞当前线程直到持有该锁的其他所有线程都释放了该锁, unlock()释放锁,必须配套,最好在finally里释放
// Condition作为条件, await()将当前线程阻塞,放入等待队列,并释放锁, signal()唤醒等待队列里的一个线程,等待调度
public class FooBar {
    private final int n;
    private volatile boolean flag = true;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition c = lock.newCondition();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // 在使用阻塞等待获取锁的方式中，必须在try代码块之外，并且在加锁方法与try代码块之间没有任何可能抛出异常的方法调用，避免加锁成功后，在finally中无法解锁。
            // 说明一：如果在lock方法与try代码块之间的方法调用抛出异常，那么无法解锁，造成其它线程无法成功获取锁。
            // 说明二：如果lock方法在try代码块之内，可能由于其它方法抛出异常，导致在finally代码块中，unlock对未加锁的对象解锁，它会调用AQS的tryRelease方法（取决于具体实现类），抛出IllegalMonitorStateException异常。
            // 说明三：在Lock对象的lock方法实现中可能抛出unchecked异常，产生的后果与说明二相同。
            lock.lock();
            try {
                if (!flag) {
                    c.await();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                flag = !flag;
                c.signal();
            } finally {
                lock.unlock();
            }
        }

    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            try {
                if (flag) {
                    c.await();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                flag = !flag;
                c.signal();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        FooBar fooBar = new FooBar(10);

        new Thread(() -> {
            try {
                fooBar.foo(() -> System.out.print("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fooBar.bar(() -> System.out.print("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
