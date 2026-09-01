import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();

        // Sort to handle duplicates easily
        Arrays.sort(candidates);

        backtrack(candidates, target, 0, new ArrayList<>(), result);

        return result;
    }

    private void backtrack(int[] candidates, int target, int start,
                           List<Integer> current,
                           List<List<Integer>> result) {

        // Found a valid combination
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < candidates.length; i++) {

            // Skip duplicates at the same recursion level
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue;
            }

            // Since array is sorted, no need to continue further
            if (candidates[i] > target) {
                break;
            }

            // Choose
            current.add(candidates[i]);

            // i + 1 because each element can be used only once
            backtrack(candidates, target - candidates[i],
                      i + 1, current, result);

            // Backtrack
            current.remove(current.size() - 1);
        }
    }
}