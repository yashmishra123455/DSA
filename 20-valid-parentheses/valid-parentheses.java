import java.util.*;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {

            // Opening brackets
            if (c == '(') {
                stack.push(')');
            } 
            else if (c == '[') {
                stack.push(']');
            } 
            else if (c == '{') {
                stack.push('}');
            }

            // Closing brackets
            else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}