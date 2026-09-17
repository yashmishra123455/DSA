class Solution {
    public int countSpecialIntegers(int[] nums) {
        boolean[] seen = new boolean[101];
        boolean[] bad = new boolean[101];

        for (int i = 0; i < nums.length; i++) {

            // New block starts
            if (i == 0 || nums[i] != nums[i - 1]) {

                // Number already had a block
                if (seen[nums[i]]) {
                    bad[nums[i]] = true;
                }

                seen[nums[i]] = true;
            }
        }

        int ans = 0;

        for (int i = 1; i <= 100; i++) {
            if (seen[i] && !bad[i]) {
                ans++;
            }
        }

        return ans;
    }
}