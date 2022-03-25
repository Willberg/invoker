package distributed.selfIncID;

/**
 * 核心思想: 分布式自增ID， 由时间戳 + 数据中心编号 + 机器号 + 序列号组成
 * 
 * @author bill
 * @since 2022/3/25 5:54 PM
 */
public class SelfIncID {
    /**
     * 设置为某一个特定时间戳，以后均不能修改，否则会造成冲突
     */
    private static final long START_STAMP = System.currentTimeMillis();
    private static final long DATACENTER_BIT = 5L;
    private static final long MACHINE_BIT = 5L;
    private static final long SEQUENCE_BIT = 12L;

    private static final long MAX_DATACENTER_NUM = ~(-1 << DATACENTER_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1 << MACHINE_BIT);
    private static final long MAX_SEQUENCE = ~(-1 << SEQUENCE_BIT);

    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = MACHINE_BIT + MACHINE_LEFT;
    private static final long TIMESTAMP_LEFT = DATACENTER_BIT + DATACENTER_LEFT;

    private long dataCenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStmp = -1L;

    public SelfIncID(long dataCenterId, long machineId) {
        this.dataCenterId = dataCenterId % MAX_DATACENTER_NUM;
        this.machineId = machineId % MAX_MACHINE_NUM;
    }

    public static long getNewStmp() {
        return System.currentTimeMillis();
    }

    public long getNextMill() {
        long mill = getNewStmp();
        while (mill <= lastStmp) {
            mill = getNewStmp();
        }
        return mill;
    }

    public synchronized long nextID() {
        long curStmp = getNewStmp();
        if (curStmp == lastStmp) {
            // 时间戳相同，序列号自增
            sequence = (sequence + 1) % MAX_SEQUENCE;
            // 序列号达到最大，已经全部用完
            if (sequence == 0L) {
                curStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，sequence重置为0
            sequence = 0;
        }
        lastStmp = curStmp;
        return (curStmp - START_STAMP) << TIMESTAMP_LEFT | dataCenterId << DATACENTER_LEFT | machineId << MACHINE_LEFT
            | sequence;
    }

    public static void main(String[] args) {
        SelfIncID selfIncID = new SelfIncID(200000, 200000);
        System.out.println(selfIncID.dataCenterId + "," + selfIncID.machineId);
        System.out.println(TIMESTAMP_LEFT + "," + DATACENTER_LEFT + "," + MACHINE_LEFT);
        for (int i = 0; i < 1000000; i++) {
            System.out.println(selfIncID.nextID());
        }
    }
}