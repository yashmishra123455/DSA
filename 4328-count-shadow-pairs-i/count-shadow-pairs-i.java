
import java.util.*;

class Solution {
    public long shadowPairs(int[] nums) {
        int n = nums.length;

        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        int[] nextSmaller = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() &&
                   nums[stack.peek()] >= nums[i]) {
                stack.pop();
            }

            nextSmaller[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int right = nextSmaller[i];

            List<Integer> positions = map.get(nums[i]);

            int total = right - i - 1;

            int equalCount =
                lowerBound(positions, right) -
                lowerBound(positions, i + 1);

            ans += total - equalCount;
        }

        return ans;
    }

    private int lowerBound(List<Integer> list, int target) {
        int low = 0, high = list.size();

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (list.get(mid) < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }
}
