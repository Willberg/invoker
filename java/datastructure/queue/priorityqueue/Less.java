package datastructure.queue.priorityqueue;

/**
 * @Author john
 * @Description 比较
 * @Date 2022/10/1 下午5:53
 */
public interface Less<T> {
    /**
     * 返回 a < b
     * 
     * @param a
     * @param b
     * @return boolean
     */
    boolean less(T a, T b);
}
