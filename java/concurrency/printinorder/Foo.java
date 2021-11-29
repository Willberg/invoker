//1114. 按序打印 https://leetcode-cn.com/problems/print-in-order/
package concurrency.printinorder;

public class Foo {
    private final Object fc = new Object();
    // 多线程可见
    private volatile int k = 0;

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        synchronized (fc) {
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            k++;
            fc.notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        synchronized (fc) {
            while (k != 1) {
                fc.wait();
            }
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            k++;
            fc.notifyAll();
        }

    }

    public void third(Runnable printThird) throws InterruptedException {
        synchronized (fc) {
            while (k != 2) {
                fc.wait();
            }
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
        }
    }
}
