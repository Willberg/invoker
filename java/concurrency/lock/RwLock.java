package concurrency.lock;

/**
 * @Author john
 * @Description 读写锁
 * @Date 2022/9/28 下午10:06
 */
public interface RwLock {
    /**
     * 写锁
     */
    void lock();

    /**
     * 写解锁
     */
    void unlock();

    /**
     * 读锁
     */
    void rLock();

    /**
     * 读解锁
     */
    void rUnlock();
}
