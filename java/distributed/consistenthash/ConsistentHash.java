package distributed.consistenthash;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;

/**
 * 核心思想: 用treeMap实现带虚拟节点的一致性hash算法，将服务器的ip:port映射到0～2^32 - 1的hash环上，每次顺时针获取最近的服务器
 * 
 * @author bill
 * @since 2022/3/25 2:59 PM
 */
public class ConsistentHash {
    private int vnNum;
    private SortedMap<Integer, String> virtualNodes;

    public ConsistentHash(String[] servers, int vnNum) {
        this.vnNum = vnNum;
        virtualNodes = new TreeMap<>();

        for (String s : servers) {
            for (int j = 0; j < vnNum; j++) {
                String server = s + "@@vn" + j;
                int hash = calHash(server);
                virtualNodes.put(hash, server);
            }
        }
    }

    /**
     * 可随意更改，尽量选择让key分布比较均匀的hash算法，防止过多的key映射到相同的服务器上 这里使用MurMurHash算法,性能高,碰撞率低，有规律的key的随机分布比较好
     * 
     * @param key
     * @return s对应的hashcode
     */
    public int calHash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }

        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;

        buf.order(byteOrder);
        return Long.valueOf(h).intValue();
    }

    public void add(String server) {
        for (int i = 0; i < vnNum; i++) {
            String s = server + "@@vn" + i;
            int hash = calHash(s);
            virtualNodes.put(hash, s);
        }
    }

    public void remove(String server) {
        for (int i = 0; i < vnNum; i++) {
            String s = server + "@@vn" + i;
            int hash = calHash(s);
            virtualNodes.remove(hash);
        }
    }

    public String getServer(String key) {
        int hash = calHash(key);
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        int f = subMap.isEmpty() ? virtualNodes.firstKey() : subMap.firstKey();
        String s = virtualNodes.get(f);
        return s.substring(0, s.indexOf("@@"));
    }

    public static void main(String[] args) {
        String ipPrefix = "192.168.0.";
        String[] servers = new String[5];
        for (int i = 0; i < servers.length; i++) {
            servers[i] = ipPrefix + (i + 1) + ":8080";
        }

        ConsistentHash ch = new ConsistentHash(servers, 5);
        List<Integer> hList = new ArrayList<>();
        hList.addAll(ch.virtualNodes.keySet());
        Collections.sort(hList);
        for (int h : hList) {
            System.out.println(ch.virtualNodes.get(h) + "," + h);
        }
        System.out.println(" ");
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 25; i++) {
            String key = UUID.randomUUID().toString();
            list.add(key);
            System.out.println(key + "," + ch.calHash(key) + "," + ch.getServer(key));
        }
        System.out.println(" ");

        ch.add(ipPrefix + 7 + ":8080");
        hList = new ArrayList<>();
        hList.addAll(ch.virtualNodes.keySet());
        Collections.sort(hList);
        for (int h : hList) {
            System.out.println(ch.virtualNodes.get(h) + "," + h);
        }
        System.out.println(" ");
        for (String key : list) {
            System.out.println(key + "," + ch.calHash(key) + "," + ch.getServer(key));
        }
        System.out.println(" ");

        ch.remove(ipPrefix + 7 + ":8080");
        hList = new ArrayList<>();
        hList.addAll(ch.virtualNodes.keySet());
        Collections.sort(hList);
        for (int h : hList) {
            System.out.println(ch.virtualNodes.get(h) + "," + h);
        }
        System.out.println(" ");
        for (String key : list) {
            System.out.println(key + "," + ch.calHash(key) + "," + ch.getServer(key));
        }
    }
}
