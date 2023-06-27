// https://github.com/Willberg/invoker/blob/master/golang/datastructure/hashtable/comap/comap.go
// 实现一个面向高并发的map
// 1. 面向高并发
// 2. 插入和查询操作（O(1)）
// 3. 查询时，如果key存在，直接返回val,否则，阻塞到key被放入后，返回val；等待指定时间仍未放入key,返回超时。
// 4. 不能有死锁和panic

package comap

import (
	"context"
	"sync"
	"time"
)

type MyChan struct {
	sync.Once
	ch chan struct{}
}

func NewMyChan() *MyChan {
	return &MyChan{
		ch: make(chan struct{}),
	}
}

func (m *MyChan) Close() {
	m.Do(func() {
		close(m.ch)
	})
}

type MyConcurrentMap struct {
	sync.Mutex
	mp      map[int]int
	keyToCh map[int]*MyChan
}

func NewMyConCurrentMap() *MyConcurrentMap {
	return &MyConcurrentMap{
		mp:      make(map[int]int),
		keyToCh: make(map[int]*MyChan),
	}
}

func (m *MyConcurrentMap) Put(k, v int) {
	m.Lock()
	defer m.Unlock()
	m.mp[k] = v
	ch, ok := m.keyToCh[k]
	if !ok {
		return
	}
	ch.Close()
}

func (m *MyConcurrentMap) Get(k int, maxWaitingTime time.Duration) (int, error) {
	m.Lock()
	v, ok := m.mp[k]
	if ok {
		m.Unlock()
		return v, nil
	}

	ch, ok := m.keyToCh[k]
	if !ok {
		ch = NewMyChan()
		m.keyToCh[k] = ch
	}
	ctx, cancel := context.WithTimeout(context.Background(), maxWaitingTime)
	defer cancel()
	m.Unlock()

	select {
	case <-ctx.Done():
		return -1, ctx.Err()
	case <-ch.ch:
	}

	m.Lock()
	v = m.mp[k]
	m.Unlock()
	return v, nil
}
