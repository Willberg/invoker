package concurrency.lock.impl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import concurrency.lock.RwLock;

/**
 * @Author john
 * @Description
 * @Date 2022/9/28 下午10:10
 */
public class RwLockImpl implements RwLock {
    private ReentrantLock lock;
    private AtomicBoolean w;
    private AtomicInteger r;
    private Condition c;

    public RwLockImpl() {
        lock = new ReentrantLock();
        w = new AtomicBoolean(false);
        r = new AtomicInteger(0);
        c = lock.newCondition();
    }

    @Override
    public void lock() {
        lock.lock();
        try {
            while (w.get() || r.get() > 0) {
                c.await();
            }
            w.compareAndSet(false, true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void unlock() {
        lock.lock();
        try {
            w.compareAndSet(true, false);
            c.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void rLock() {
        lock.lock();
        try {
            while (w.get()) {
                c.await();
            }
            r.getAndAdd(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void rUnlock() {
        lock.lock();
        try {
            if (r.get() > 0) {
                r.getAndAdd(-1);
            }
            if (r.get() == 0) {
                c.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
