class Solution {
    public int maxPalindromes(String s, int k) {

        int n = s.length();

        // palindrome[i][j] tells whether s[i...j] is a palindrome
        boolean[][] palindrome = new boolean[n][n];

        // Build palindrome table
        for (int len = 1; len <= n; len++) {

            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;

                if (s.charAt(i) == s.charAt(j) &&
                    (len <= 2 || palindrome[i + 1][j - 1])) {

                    palindrome[i][j] = true;
                }
            }
        }

        // dp[i] = maximum number of palindromes
        // using first i characters
        int[] dp = new int[n + 1];

        for (int i = 1; i <= n; i++) {

            // Option 1: Don't use character i - 1
            dp[i] = dp[i - 1];

            // Option 2: Take a palindrome ending at i - 1
            for (int j = 0; j < i; j++) {

                int len = i - j;

                if (len >= k && palindrome[j][i - 1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n];
    }
}