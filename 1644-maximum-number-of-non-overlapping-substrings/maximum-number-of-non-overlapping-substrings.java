import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {

        int n = s.length();

        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // Find first and last occurrence of each character
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        // Find valid substring for every character
        for (int c = 0; c < 26; c++) {

            if (last[c] == -1) {
                continue;
            }

            int start = first[c];
            int end = last[c];

            boolean valid = true;

            for (int i = start; i <= end; i++) {

                int x = s.charAt(i) - 'a';

                // This character appeared before our start
                if (first[x] < start) {
                    valid = false;
                    break;
                }

                // Include all occurrences of this character
                end = Math.max(end, last[x]);
            }

            if (valid) {
                intervals.add(new int[]{start, end});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));

        List<String> result = new ArrayList<>();

        int previousEnd = -1;

        // Greedy selection
        for (int[] interval : intervals) {

            int start = interval[0];
            int end = interval[1];

            if (start > previousEnd) {

                result.add(s.substring(start, end + 1));

                previousEnd = end;
            }
        }

        return result;
    }
}