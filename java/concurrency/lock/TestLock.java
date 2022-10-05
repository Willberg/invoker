package concurrency.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import concurrency.lock.impl.RwLockImpl;

/**
 * @Author john
 * @Description 测试
 * @Date 2022/9/28 下午10:34
 */
public class TestLock {
    private static final Queue<Long[]> q = new ConcurrentLinkedQueue<>();
    private static final Map<String, Long> createdThreads = new ConcurrentHashMap<>();
    private static final Map<String, Long> executeThreads = new ConcurrentHashMap<>();
    private static final AtomicInteger ci = new AtomicInteger(0);
    private static final AtomicInteger xi = new AtomicInteger(0);
    private static final AtomicInteger mi = new AtomicInteger(0);

    private static void testLockByThread(CountDownLatch latch, int c) {
        RwLock rwLock = new RwLockImpl();
        int n = (int)latch.getCount();
        Val val = new Val();
        for (int i = 0; i < n; i++) {
            int finalI = i;
            new Thread(() -> {
                ThreadMXBean tmBean = ManagementFactory.getThreadMXBean();
                tmBean.setThreadContentionMonitoringEnabled(true);
                for (int j = 0; j < c; j++) {
                    rwLock.lock();
                    val.setVal(val.getVal() + 1);
                    rwLock.unlock();
                    rwLock.rLock();
                    System.out.printf("pid %d, v=%d\n", finalI, val.getVal());
                    rwLock.rUnlock();
                }
                long useTime = tmBean.getCurrentThreadCpuTime();
                long waitTime = tmBean.getThreadInfo(Thread.currentThread().getId()).getWaitedTime() * (int)1e6;
                q.offer(new Long[] {useTime, waitTime, tmBean.getCurrentThreadUserTime()});
                latch.countDown();
            }).start();
        }
    }

    /**
     * 手动创建线程池， 避免OOM， 多线程的推荐写法 CPU密集型： 线程池大小推荐为 CPU数量+1. CPU数量由Runtime.getRuntime().availableProcessors()获取 IO密集型：
     * CPU数量 * CPU利用率 * (1 + 线程等待时间/线程CPU时间) CallerRunsPolicy 拒绝策略： 将任务交给调用execute的线程执行（一般为主线程）, 此时主线程因为要处理正在执行的任务，
     * 将在一段时间内不能提交任何任务. 此时客户端提交的任务将保存在TCP队列（半连接队列和全连接队列）中， TCP队列满将影响客户端， 这是一种平缓的性能降低模式, 但是不会丢失任务（其他方式会丢失任务）
     */
    private static void testLockByThreadPool(int rate, double useRate, int queueSize, int taskCount) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        int coreThreads = Double.valueOf(Math.floor(nThreads * useRate * (1 + rate))).intValue();
        // 创建线程极其耗费资源， 在1百万任务的情况下，最大线程数大于核心线程数但存活时间为0，会导致频繁创建和销毁线程，反而用时会超过使用固定线程数的情况
        // 经过测试在1百万任务的情况下，核心线程数 = 最大线程数（不创建任何临时线程）的情况下，用时最短，效率最高
        int maxThreads = (int)(coreThreads * 1.1);
        RwLock rwLock = new RwLockImpl();
        Val val = new Val();
        ThreadPoolExecutor es = new ThreadPoolExecutor(coreThreads, maxThreads, 5000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(queueSize), new RwLockThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        long start = System.currentTimeMillis();
        for (int i = 0; i < taskCount; i++) {
            es.execute(() -> {
                rwLock.lock();
                val.setVal(val.getVal() + 1);
                executeThreads.put(Thread.currentThread().getName(), Thread.currentThread().getId());
                rwLock.unlock();
                rwLock.rLock();
                System.out.printf("name: %s, pid: %d, v=%d\n", Thread.currentThread().getName(),
                    Thread.currentThread().getId(), val.getVal());
                rwLock.rUnlock();
                if ("main".equals(Thread.currentThread().getName())) {
                    mi.getAndIncrement();
                }
            });
        }
        System.out.printf("关闭线程池之前, 获取任务总数(近似值): %d,完成的任务(近似值):%d\n", es.getTaskCount(), es.getCompletedTaskCount());
        es.shutdown();
        while (!es.isTerminated()) {
            // 自旋，直到线程池关闭
        }
        System.out.printf("执行完所有任务的用时：%d\n", System.currentTimeMillis() - start);
        System.out.printf("关闭线程池之后, 获取任务总数(近似值): %d,完成的任务(近似值):%d\n", es.getTaskCount(), es.getCompletedTaskCount());
        System.out.println("线程池创建的线程数量: " + createdThreads.size() + ", 执行任务的线程数量:" + executeThreads.size());
        System.out.println("core threads: " + es.getCorePoolSize() + ", max threads: " + es.getMaximumPoolSize());
        for (String k : executeThreads.keySet()) {
            if (!createdThreads.containsKey(k)) {
                System.out.printf("执行了任务，但不是线程池创建的线程: %s: %d\n", k, executeThreads.get(k));
            }
        }
        System.out.printf("主线程： %s = %d, 主线程执行次数:%d\n", Thread.currentThread().getName(),
            Thread.currentThread().getId(), mi.get());
        System.out.printf("创建线程池次数：%d, 线程池创建线程次数： %d\n", ci.get(), xi.get());

    }

    static class RwLockThreadFactory implements ThreadFactory {
        private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        RwLockThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "rwLockPool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
            ci.getAndIncrement();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            createdThreads.put(t.getName(), t.getId());
            xi.getAndIncrement();
            return t;
        }
    }

    private static void calThreadCpuTimeAndWaitTimeRate(int n, int c, double useRate) {
        int nThreads = Runtime.getRuntime().availableProcessors();
        CountDownLatch latch = new CountDownLatch(n);
        testLockByThread(latch, c);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double rate = 0D;
        for (Long[] rs : q) {
            long r = rs[1] / rs[0];
            rate += r;
            System.out.printf("总cpu时间: %d ns, 等待时间: %d ns, 等待时间/执行时间: %d, 用户空间执行时间: %d ns, 内核空间执行时间: %d ns\n", rs[0],
                rs[1], r, rs[2], rs[0] - rs[2]);
        }
        rate /= q.size();
        int maxThreads = Double.valueOf(Math.floor(nThreads * useRate * (1 + rate))).intValue();
        System.out.println("最大线程数： " + maxThreads);
    }

    /**
     * rate = 50, useRate = 0.9, n = 1024, c = 10000 (50 0.9 1024 10000)
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int rate = sc.nextInt();
        double useRate = sc.nextDouble();
        int n = sc.nextInt();
        int c = sc.nextInt();
        if (rate == 0) {
            calThreadCpuTimeAndWaitTimeRate(n, c, useRate);
        } else {
            testLockByThreadPool(rate, useRate, n, c);
        }
    }
}
