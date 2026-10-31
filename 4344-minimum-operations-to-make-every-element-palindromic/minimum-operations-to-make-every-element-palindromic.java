import java.util.*;

class Solution {

    static List<Integer> evenPal = new ArrayList<>();
    static List<Integer> oddPal = new ArrayList<>();

    static {
        generatePalindromes();
    }

    public long minOperations(int[] nums) {
        long ans = 0;

        for (int num : nums) {
            List<Integer> list = (num % 2 == 0) ? evenPal : oddPal;
            ans += closestCost(list, num);
        }

        return ans;
    }

    private long closestCost(List<Integer> list, int num) {
        int idx = Collections.binarySearch(list, num);

        if (idx >= 0) return 0;

        idx = -idx - 1;
        long best = Long.MAX_VALUE;

        if (idx < list.size()) {
            best = Math.min(best, Math.abs((long) list.get(idx) - num) / 2);
        }

        if (idx > 0) {
            best = Math.min(best, Math.abs((long) list.get(idx - 1) - num) / 2);
        }

        return best;
    }

    private static void generatePalindromes() {
        HashSet<Integer> seen = new HashSet<>();

        for (int i = 1; i <= 100000; i++) {
            String s = String.valueOf(i);

            // Odd-length palindrome
            String oddStr = s + new StringBuilder(s.substring(0, s.length() - 1)).reverse();
            long odd = Long.parseLong(oddStr);
            if (odd <= 1_000_000_000L && seen.add((int) odd)) {
                if ((odd & 1) == 0) evenPal.add((int) odd);
                else oddPal.add((int) odd);
            }

            // Even-length palindrome
            String evenStr = s + new StringBuilder(s).reverse();
            long even = Long.parseLong(evenStr);
            if (even <= 1_000_000_000L && seen.add((int) even)) {
                if ((even & 1) == 0) evenPal.add((int) even);
                else oddPal.add((int) even);
            }
        }

        Collections.sort(evenPal);
        Collections.sort(oddPal);
    }
}