// 1115. 交替打印FooBar https://leetcode-cn.com/problems/print-foobar-alternately/
package concurrency.printfoobaralternately.sync;

// synchronized 给FooBar对象加锁, wait()将当前线程阻塞,放入等待队列,并释放锁, notify()唤醒等待队列里的一个线程,等待调度
// 如果搞不清唤醒哪一个线程,可以用notifyAll(),这样代码更稳定,但是会出现更多线程唤醒然后再挂起
public class FooBar {
    private final int n;
    private volatile boolean isFoo = true;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            synchronized (this) {
                if (!isFoo) {
                    this.wait();
                }

                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                isFoo = !isFoo;
                this.notify();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (this) {
                if (isFoo) {
                    this.wait();
                }

                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                isFoo = !isFoo;
                this.notify();
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
