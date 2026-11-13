
class Solution {

    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;

        // Step 1: Prefix sum
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Step 2: Find Next Greater Element
        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {

            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = (top == -1) ? n : stack[top];

            stack[++top] = i;
        }

        // Step 3: Binary Lifting
        int LOG = 1;

        while ((1 << LOG) <= n) {
            LOG++;
        }

        int[][] jump = new int[LOG][n + 1];
        long[][] cost = new long[LOG][n];

        for (int i = 0; i < n; i++) {

            jump[0][i] = next[i];

            cost[0][i] =
                (long) nums[i] * (next[i] - i)
                - (prefix[next[i]] - prefix[i]);
        }

        for (int j = 1; j < LOG; j++) {

            for (int i = 0; i < n; i++) {

                int mid = jump[j - 1][i];

                if (mid < n) {
                    jump[j][i] = jump[j - 1][mid];

                    cost[j][i] =
                        cost[j - 1][i] + cost[j - 1][mid];

                } else {
                    jump[j][i] = n;
                    cost[j][i] = cost[j - 1][i];
                }
            }
        }

        // Step 4: Count valid subarrays
        long answer = 0;

        for (int r = 0; r < n; r++) {

            int low = 0;
            int high = r;

            // Binary search for smallest valid left index
            while (low < high) {

                int mid = low + (high - low) / 2;

                if (getCost(mid, r, nums, prefix,
                            jump, cost, LOG) <= k) {

                    high = mid;

                } else {

                    low = mid + 1;
                }
            }

            answer += (r - low + 1);
        }

        return answer;
    }

    // Calculate minimum operations for nums[l...r]
    private long getCost(
        int l,
        int r,
        int[] nums,
        long[] prefix,
        int[][] jump,
        long[][] cost,
        int LOG
    ) {

        long total = 0;
        int cur = l;

        // Jump over complete maximum-value blocks
        for (int j = LOG - 1; j >= 0; j--) {

            int nxt = jump[j][cur];

            if (nxt <= r) {

                total += cost[j][cur];
                cur = nxt;
            }
        }

        // Calculate the remaining partial block
        total += (long) nums[cur] * (r - cur + 1)
                 - (prefix[r + 1] - prefix[cur]);

        return total;
    }
}
