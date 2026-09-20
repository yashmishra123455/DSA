class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Reverse alphabet position
            int reversePosition = 'z' - ch + 1;

            // String position is i + 1
            int position = i + 1;

            sum += reversePosition * position;
        }

        return sum;
    }
}