// 1115. 交替打印FooBar https://leetcode-cn.com/problems/print-foobar-alternately/
package concurrency.printfoobaralternately.semaphore;

import java.util.concurrent.Semaphore;

// 使用信号量acquire方法使信号量减1,当信号量为0时不能减小,并阻塞当前线程; release使信号量加1,
public class FooBar {
    private int n;
    private final Semaphore fooSema = new Semaphore(1);
    private final Semaphore barSema = new Semaphore(0);

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // 能拿到就执行以下代码,否则就将线程休眠
            fooSema.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            barSema.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // 信号量减1(只有大于0时可以成功)
            barSema.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            // 信号量加1
            fooSema.release();
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