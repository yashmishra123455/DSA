class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                if (balance > 0) {
                    result.append(ch);
                }
                balance++;
            } 
            else {
                balance--;

                if (balance > 0) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}