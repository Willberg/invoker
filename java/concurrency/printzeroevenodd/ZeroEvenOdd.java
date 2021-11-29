//1116. 打印零与奇偶数 https://leetcode-cn.com/problems/print-zero-even-odd/
package concurrency.printzeroevenodd;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class ZeroEvenOdd {
    private final int n;
    private final Semaphore zeroSema = new Semaphore(1);
    private final Semaphore evenSema = new Semaphore(0);
    private final Semaphore oddSema = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSema.acquire();
            printNumber.accept(0);
            if ((i & 1) == 0) {
                // 偶数
                evenSema.release();
            } else {
                oddSema.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if ((i & 1) == 0) {
                evenSema.acquire();
                printNumber.accept(i);
                zeroSema.release();
            }
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if ((i & 1) == 1) {
                oddSema.acquire();
                printNumber.accept(i);
                zeroSema.release();
            }
        }
    }
}
