class Solution {
    public String generateString(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();
        int len = n + m - 1;

        char[] ans = new char[len];
        boolean[] fixed = new boolean[len];

        // Start with the smallest possible characters: 'a'
        for (int i = 0; i < len; i++) {
            ans[i] = 'a';
        }

        // ------------------------------------------------
        // Step 1: Apply all T constraints
        // ------------------------------------------------
        for (int i = 0; i < n; i++) {
            if (str1.charAt(i) == 'T') {

                for (int j = 0; j < m; j++) {
                    int pos = i + j;
                    char c = str2.charAt(j);

                    if (fixed[pos] && ans[pos] != c) {
                        return "";
                    }

                    ans[pos] = c;
                    fixed[pos] = true;
                }
            }
        }

        // ------------------------------------------------
        // Step 2: Fix F constraints
        // ------------------------------------------------
        for (int i = 0; i < n; i++) {

            if (str1.charAt(i) != 'F') {
                continue;
            }

            // Check whether current substring equals str2
            boolean equal = true;

            for (int j = 0; j < m; j++) {
                if (ans[i + j] != str2.charAt(j)) {
                    equal = false;
                    break;
                }
            }

            // Already different -> F condition satisfied
            if (!equal) {
                continue;
            }

            // Need to change one unfixed character
            boolean changed = false;

            for (int j = m - 1; j >= 0; j--) {
                int pos = i + j;

                if (!fixed[pos] && ans[pos] < 'z') {
                    ans[pos]++;
                    changed = true;
                    break;
                }
            }

            if (!changed) {
                return "";
            }
        }

        // ------------------------------------------------
        // Step 3: Verify everything
        // ------------------------------------------------
        for (int i = 0; i < n; i++) {

            boolean equal = true;

            for (int j = 0; j < m; j++) {
                if (ans[i + j] != str2.charAt(j)) {
                    equal = false;
                    break;
                }
            }

            if (str1.charAt(i) == 'T' && !equal) {
                return "";
            }

            if (str1.charAt(i) == 'F' && equal) {
                return "";
            }
        }

        return new String(ans);
    }
}