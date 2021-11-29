//1195. 交替打印字符串 https://leetcode-cn.com/problems/fizz-buzz-multithreaded/
package concurrency.fizzbuzzmultithreaded;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

public class FizzBuzz {
    private final int n;
    private final Semaphore numberSema = new Semaphore(1);
    private final Semaphore fizzSema = new Semaphore(0);
    private final Semaphore buzzSema = new Semaphore(0);
    private final Semaphore fizzbuzzSema = new Semaphore(0);

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 != 0) {
                fizzSema.acquire();
                printFizz.run();
                numberSema.release();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 != 0 && i % 5 == 0) {
                buzzSema.acquire();
                printBuzz.run();
                numberSema.release();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                fizzbuzzSema.acquire();
                printFizzBuzz.run();
                numberSema.release();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            numberSema.acquire();
            if (i % 3 == 0 && i % 5 == 0) {
                fizzbuzzSema.release();
            } else if (i % 3 == 0) {
                fizzSema.release();
            } else if (i % 5 == 0) {
                buzzSema.release();
            } else {
                printNumber.accept(i);
                numberSema.release();
            }
        }
    }
}
