// 1115. 交替打印FooBar https://leetcode-cn.com/problems/print-foobar-alternately/
package concurrency.printfoobaralternately.locksurport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

// LockSupport park() 阻塞当前线程, unpark() 可以唤醒指定线程
public class FooBar {
    private final int n;
    private final Map<String, Thread> map = new ConcurrentHashMap<>();
    private volatile boolean isFoo = true;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        map.put("foo", Thread.currentThread());
        for (int i = 0; i < n; i++) {
            while (!isFoo) {
                // 阻塞
                LockSupport.park();
            }

            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            isFoo = false;
            LockSupport.unpark(map.get("bar"));
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        map.put("bar", Thread.currentThread());
        for (int i = 0; i < n; i++) {
            while (isFoo) {
                LockSupport.park();
            }

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            isFoo = true;
            LockSupport.unpark(map.get("foo"));
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

