// 921. 使括号有效的最少添加 https://leetcode.cn/problems/minimum-add-to-make-parentheses-valid/
#include <string.h>

int minAddToMakeValid(char *s) {
    int cnt = 0, ans = 0;
    for (int i = 0; i < strlen(s); i++) {
        if (s[i] == '(') {
            cnt++;
        } else {
            if (cnt > 0) {
                cnt--;
            } else {
                ans++;
            }
        }
    }
    return ans + cnt;
}

