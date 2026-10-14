
class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < num; i++) {
            int digit = Math.min(9, sum);
            ans.append(digit);
            sum -= digit;
        }

        if (sum > 0) {
            return "";
        }

        return ans.toString();
    }
}
