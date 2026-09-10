class Solution {

    int count = 0;

    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return count;
    }

    // Returns {sum, numberOfNodes}
    private int[] dfs(TreeNode node) {

        if (node == null) {
            return new int[]{0, 0};
        }

        // Get left subtree information
        int[] left = dfs(node.left);

        // Get right subtree information
        int[] right = dfs(node.right);

        // Calculate current subtree
        int sum = node.val + left[0] + right[0];
        int nodes = 1 + left[1] + right[1];

        // Integer division automatically rounds DOWN
        int average = sum / nodes;

        if (node.val == average) {
            count++;
        }

        return new int[]{sum, nodes};
    }
}