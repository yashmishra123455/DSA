class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;

        // minLen[i] = minimum length of a valid subarray
        // completely inside arr[0...i]
        int[] minLen = new int[n];

        int INF = Integer.MAX_VALUE;
        int answer = INF;

        int left = 0;
        int sum = 0;
        int best = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Since all elements are positive
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // Found a subarray with sum = target
            if (sum == target) {
                int len = right - left + 1;

                // Previous subarray must end before 'left'
                if (left > 0 && minLen[left - 1] != INF) {
                    answer = Math.min(answer, len + minLen[left - 1]);
                }

                // Keep the shortest subarray found so far
                best = Math.min(best, len);
            }

            // Store best valid subarray up to this index
            minLen[right] = best;
        }

        return answer == INF ? -1 : answer;
    }
}