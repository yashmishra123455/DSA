import java.util.*;

class Solution {

    static class Interval {
        int l, r, weight, index;

        Interval(int l, int r, int weight, int index) {
            this.l = l;
            this.r = r;
            this.weight = weight;
            this.index = index;
        }
    }

    static class State {
        long score;
        List<Integer> indices;

        State(long score, List<Integer> indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    Interval[] arr;
    State[][] dp;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        // Sort by starting position
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }
            return Integer.compare(a.r, b.r);
        });

        dp = new State[n + 1][5];

        State answer = solve(0, 4);

        int[] result = new int[answer.indices.size()];

        for (int i = 0; i < answer.indices.size(); i++) {
            result[i] = answer.indices.get(i);
        }

        return result;
    }

    private State solve(int i, int k) {

        // No intervals left or cannot select more
        if (i == arr.length || k == 0) {
            return new State(0, new ArrayList<>());
        }

        if (dp[i][k] != null) {
            return dp[i][k];
        }

        // Option 1: Skip current interval
        State skip = solve(i + 1, k);

        // Option 2: Take current interval
        int next = findNext(i);

        State takeNext = solve(next, k - 1);

        List<Integer> takeIndices =
            new ArrayList<>(takeNext.indices);

        takeIndices.add(arr[i].index);

        Collections.sort(takeIndices);

        State take = new State(
            arr[i].weight + takeNext.score,
            takeIndices
        );

        // Choose better option
        dp[i][k] = better(take, skip);

        return dp[i][k];
    }

    // Find first interval with start > current end
    private int findNext(int i) {

        int target = arr[i].r + 1;

        int left = i + 1;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid].l >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // Compare two states
    private State better(State a, State b) {

        if (a.score != b.score) {
            return a.score > b.score ? a : b;
        }

        // Same score → lexicographically smaller indices
        return lexicographicallySmaller(a.indices, b.indices)
                ? a
                : b;
    }

    private boolean lexicographicallySmaller(
            List<Integer> a,
            List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}