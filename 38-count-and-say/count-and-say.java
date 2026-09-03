class Solution {
    public String countAndSay(int n) {
        String result = "1";

        for (int i = 2; i <= n; i++) {
            StringBuilder next = new StringBuilder();
            int count = 1;

            for (int j = 1; j < result.length(); j++) {
                if (result.charAt(j) == result.charAt(j - 1)) {
                    count++;
                } else {
                    next.append(count);
                    next.append(result.charAt(j - 1));
                    count = 1;
                }
            }

            // Add the last group
            next.append(count);
            next.append(result.charAt(result.length() - 1));

            result = next.toString();
        }

        return result;
    }
}