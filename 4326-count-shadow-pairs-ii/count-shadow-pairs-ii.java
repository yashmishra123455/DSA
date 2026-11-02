
import java.util.*;

class Solution {

    int[] nums;
    int[] values;
    int[] b, c;

    long[] leftKeys, rightKeys;

    int base;
    FenwickTree bit;
    TreeSet<Integer> set = new TreeSet<>();

    public int shadowPairs(int[] nums) {

        this.nums = nums;

        int n = nums.length;
        base = n + 1;

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int x : sorted) {
            if (m == 0 || sorted[m - 1] != x) {
                sorted[m++] = x;
            }
        }

        values = Arrays.copyOf(sorted, m);

        // Reusable arrays
        b = new int[n];
        c = new int[n];

        leftKeys = new long[n];
        rightKeys = new long[n];

        bit = new FenwickTree(m);

        return (int) solve(0, n - 1);
    }

    private long solve(int l, int r) {

        if (l >= r) {
            return 0;
        }

        int mid = l + (r - l) / 2;

        long ans = solve(l, mid) + solve(mid + 1, r);

        int leftSize = mid - l + 1;
        int rightSize = r - mid;

        // Calculate b[i]
        set.clear();

        for (int i = mid; i >= l; i--) {

            Integer next = set.higher(nums[i]);

            b[i] = (next == null) ? 1000000001 : next;

            set.add(nums[i]);
        }

        // Calculate c[j]
        set.clear();

        for (int j = mid + 1; j <= r; j++) {

            Integer prev = set.lower(nums[j]);

            c[j] = (prev == null) ? 0 : prev;

            set.add(nums[j]);
        }

        // Sort left indices by b[i] ascending
        for (int i = 0; i < leftSize; i++) {

            int idx = l + i;

            leftKeys[i] = (long) b[idx] * base + idx;
        }

        Arrays.sort(leftKeys, 0, leftSize);

        // Sort right indices by nums[j] ascending
        for (int i = 0; i < rightSize; i++) {

            int idx = mid + 1 + i;

            rightKeys[i] = (long) nums[idx] * base + idx;
        }

        Arrays.sort(rightKeys, 0, rightSize);

        int p = leftSize - 1;

        // Process right indices in descending order
        for (int q = rightSize - 1; q >= 0; q--) {

            int j = (int) (rightKeys[q] % base);

            // Insert eligible left indices
            while (p >= 0) {

                int i = (int) (leftKeys[p] % base);

                if (b[i] < nums[j]) {
                    break;
                }

                int rank = lowerBound(nums[i]);

                bit.add(rank + 1, 1);

                p--;
            }

            // Count values in [c[j], nums[j])
            int low = lowerBound(c[j]);
            int high = lowerBound(nums[j]);

            ans += bit.query(high) - bit.query(low);
        }

        // Remove inserted elements
        for (int q = leftSize - 1; q > p; q--) {

            int i = (int) (leftKeys[q] % base);

            int rank = lowerBound(nums[i]);

            bit.add(rank + 1, -1);
        }

        return ans;
    }

    // Binary search
    private int lowerBound(int target) {

        int l = 0;
        int r = values.length;

        while (l < r) {

            int mid = l + (r - l) / 2;

            if (values[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }

    // Fenwick Tree
    class FenwickTree {

        int[] tree;
        int n;

        FenwickTree(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void add(int i, int val) {

            while (i <= n) {

                tree[i] += val;

                i += i & -i;
            }
        }

        int query(int i) {

            int sum = 0;

            while (i > 0) {

                sum += tree[i];

                i -= i & -i;
            }

            return sum;
        }
    }
}
