package datastructure.queue.priorityqueue;

/**
 * @Author john
 * @Description 通用优先队列
 * @Date 2022/10/1 下午5:09
 */
public final class PriorityQueue<T> {
    private int size;
    private final int capacity;
    private final transient Object[] q;
    private final Less<T> cmp;

    public PriorityQueue(int n, Less<T> lessT) {
        size = 0;
        capacity = n;
        q = new Object[n];
        cmp = lessT;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return size == capacity;
    }

    public void push(T t) {
        q[size] = t;
        size++;
        up(size - 1);
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        T top = (T)q[0];
        size--;
        q[0] = q[size];
        down(0);
        return top;
    }

    private void swap(int i, int j) {
        Object t = q[i];
        q[i] = q[j];
        q[j] = t;
    }

    @SuppressWarnings("unchecked")
    public T top() {
        return (T)q[0];
    }

    private void up(int p) {
        while (p > 0) {
            int r = (p - 1) / 2;
            if (less(r, p)) {
                return;
            }
            swap(r, p);
            p = r;
        }
    }

    private void down(int p) {
        while (p < size) {
            int small = p, l = 2 * p + 1;
            if (l < size && less(l, small)) {
                small = l;
            }
            int r = l + 1;
            if (r < size && less(r, small)) {
                small = r;
            }
            if (small == p) {
                return;
            }
            swap(small, p);
            p = small;
        }
    }

    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
        return cmp.less((T)q[i], (T)q[j]);
    }
}
