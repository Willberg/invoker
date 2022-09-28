package concurrency.lock;

import concurrency.lock.impl.RwLockImpl;

/**
 * @Author john
 * @Description 测试
 * @Date 2022/9/28 下午10:34
 */
public class TestLock {
    public static void main(String[] args) {
        RwLock rwLock = new RwLockImpl();
        int n = 100, c = 10000;
        Val val = new Val();
        for (int i = 0; i < n; i++) {
            int finalI = i;
            new Thread(() -> {
                for (int j = 0; j < c; j++) {
                    rwLock.lock();
                    val.setVal(val.getVal() + 1);
                    rwLock.unlock();
                    rwLock.rLock();
                    System.out.printf("pid %d, v=%d\n", finalI, val.getVal());
                    rwLock.rUnlock();
                }
            }).start();
        }
    }
}
