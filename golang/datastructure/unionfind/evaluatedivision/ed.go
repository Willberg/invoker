package ed

type UnionFind struct {
	parent []int
	weight []float64
}

func Construct(n int) UnionFind {
	p, w := make([]int, n), make([]float64, n)
	for i := range p {
		p[i] = i
		w[i] = 1.0
	}
	return UnionFind{parent: p, weight: w}
}

func (u *UnionFind) Union(x, y int, value float64) {
	rootX, rootY := u.Find(x), u.Find(y)
	if rootX == rootY {
		return
	}
	u.parent[rootX] = rootY
	u.weight[rootX] = u.weight[y] * value / u.weight[x]
}

func (u *UnionFind) Find(x int) int {
	if x != u.parent[x] {
		p := u.parent[x]
		u.parent[x] = u.Find(p)
		u.weight[x] *= u.weight[p]
	}
	return u.parent[x]
}

func (u *UnionFind) IsConnected(x, y int) float64 {
	rootX, rootY := u.Find(x), u.Find(y)
	if rootX != rootY {
		return -1.0
	}
	return u.weight[x] / u.weight[y]
}

func calcEquation(equations [][]string, values []float64, queries [][]string) (ans []float64) {
	n := len(equations)
	u := Construct(2 * n)
	ids, id := make(map[string]int), 0
	for i, e := range equations {
		a, b, v := e[0], e[1], values[i]
		if _, ok := ids[a]; !ok {
			ids[a] = id
			id++
		}
		if _, ok := ids[b]; !ok {
			ids[b] = id
			id++
		}
		u.Union(ids[a], ids[b], v)
	}
	for _, q := range queries {
		if _, ok := ids[q[0]]; !ok {
			ans = append(ans, -1.0)
			continue
		}
		if _, ok := ids[q[1]]; !ok {
			ans = append(ans, -1.0)
			continue
		}
		a, b := ids[q[0]], ids[q[1]]
		ans = append(ans, u.IsConnected(a, b))
	}
	return
}

func GetCalcResult(equations [][]string, values []float64, queries [][]string) (ans []float64) {
	return calcEquation(equations, values, queries)
}
