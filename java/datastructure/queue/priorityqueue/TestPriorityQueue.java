// 23. 合并K个升序链表 https://leetcode.cn/problems/merge-k-sorted-lists/
package datastructure.queue.priorityqueue;

/**
 * @Author john
 * @Description
 * @Date 2022/10/1 下午6:01
 */
public class TestPriorityQueue {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {}

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeKLists(ListNode[] lists, Less<ListNode> cmp) {
        if (lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, cmp);
        for (ListNode node : lists) {
            if (node != null) {
                pq.push(node);
            }
        }

        ListNode dummy = new ListNode(-1), p = dummy;
        while (!pq.isEmpty()) {
            ListNode top = pq.pop();
            if (top.next != null) {
                pq.push(top.next);
            }
            p.next = top;
            p = p.next;
        }
        return dummy.next;
    }

    private ListNode[] createLists(int[][] arr) {
        ListNode[] lists = new ListNode[3];
        int i = 0;
        for (int[] a : arr) {
            ListNode p = new ListNode(-1), t = p;
            for (int v : a) {
                t.next = new ListNode(v);
                t = t.next;
            }
            lists[i++] = p.next;
        }
        return lists;
    }

    private void testPq(int[][] arr, Less<ListNode> cmp) {
        ListNode[] lists = createLists(arr);
        for (ListNode l : lists) {
            while (l != null) {
                System.out.printf("%d,", l.val);
                l = l.next;
            }
            System.out.println("");
        }
        ListNode p = mergeKLists(lists, cmp);
        while (p != null) {
            System.out.printf("%d,", p.val);
            p = p.next;
        }
        System.out.printf("\n\n");
    }

    public static void main(String[] args) {
        // [[1,4,5],[1,3,4],[2,6]]
        TestPriorityQueue tpq = new TestPriorityQueue();
        tpq.testPq(new int[][] {{1, 4, 5}, {1, 3, 4}, {2, 6}}, (o1, o2) -> o1.val < o2.val);
        tpq.testPq(new int[][] {{5, 4, 1}, {4, 3, 1}, {6, 2}}, (o1, o2) -> o1.val > o2.val);
    }
}
