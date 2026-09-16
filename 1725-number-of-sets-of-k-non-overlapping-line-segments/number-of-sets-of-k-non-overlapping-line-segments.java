class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;

        long[][] dp = new long[k + 1][n];

        // 0 segments = 1 way for any number of points
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }

        for (int j = 1; j <= k; j++) {
            long sum = 0;

            for (int i = 1; i < n; i++) {

                // Ways where the last segment starts before i
                sum = (sum + dp[j - 1][i - 1]) % MOD;

                // Either:
                // 1. Don't use point i as the ending point
                // 2. End a segment at i
                dp[j][i] = (dp[j][i - 1] + sum) % MOD;
            }
        }

        return (int) dp[k][n - 1];
    }
}