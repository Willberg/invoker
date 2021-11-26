// 1226. 哲学家进餐 https://leetcode-cn.com/problems/the-dining-philosophers/
package concurrency.thediningphilosophers;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

// 死锁四个条件:
// 1.互斥, 一个资源只能被一个线程持有,其他线程请求被持有的资源必须等待;
// 2.请求与保持, 线程至少保持一个资源,同时还请求被的资源,自己持有的资源会一直保持;
// 3.不可剥夺, 其他线程请求被持有的资源时, 持有资源的线程不会被剥夺资源;
// 4.环路, 持有资源的线程之间形成互相等待
// 如何解决死锁: 破坏以上任意一个条件即可
// 下面的方法破坏了环路, 信号量保证最多只有4个哲学家请求5把叉子(即5把锁),且哲学家和叉子是交替排列成环的,因此必定有一个哲学家可以获得左右两把叉子
public class DiningPhilosophers {
    // 创建5把锁表示5把叉子
    private final ReentrantLock[] locks = {
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()
    };

    // 最多允许4个哲学家取叉子,这样可以保证必定有一个哲学家可以取到两把叉子进行就餐(2,3,4个都可以)
    private final Semaphore sema = new Semaphore(4);

    public DiningPhilosophers() {

    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        // 分别表示左右两边锁的下标
        int leftLock = (philosopher + 1) % 5;
        int rightLock = philosopher;
        try {
            // 允许获取叉子的哲学家数量减少1
            sema.acquire();
            locks[leftLock].lock();
            locks[rightLock].lock();

            pickLeftFork.run();
            pickRightFork.run();
            eat.run();
            putLeftFork.run();
            putRightFork.run();
        } finally {
            locks[leftLock].unlock();
            locks[rightLock].unlock();
            sema.release();
        }
    }
}
