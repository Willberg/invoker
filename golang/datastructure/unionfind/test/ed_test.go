package test

import (
	"github.com/stretchr/testify/assert"
	ed "golang/datastructure/unionfind/evaluatedivision"
	"testing"
)

func TestEd(t *testing.T) {
	ans := ed.GetCalcResult([][]string{{"a", "b"}, {"b", "c"}}, []float64{2.0, 3.0}, [][]string{{"a", "c"}, {"b", "a"}, {"a", "e"}, {"a", "a"}, {"x", "x"}})
	assert.Equal(t, ans, []float64{6.00000, 0.50000, -1.00000, 1.00000, -1.00000})
}
