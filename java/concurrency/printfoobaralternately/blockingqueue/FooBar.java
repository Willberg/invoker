// 1115. 交替打印FooBar https://leetcode-cn.com/problems/print-foobar-alternately/
package concurrency.printfoobaralternately.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// LinkedBlockingQueue可以在队头队尾进行插入删除,没有元素take()时会阻塞当前线程,队列有元素之后可以获取该元素
public class FooBar {
    private final int n;
    private final BlockingQueue<Integer> q1 = new LinkedBlockingQueue<Integer>() {{
        add(0);
    }};
    private final BlockingQueue<Integer> q2 = new LinkedBlockingQueue<>();

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            q1.take();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            q2.add(0);
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            q2.take();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            q1.add(0);
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
