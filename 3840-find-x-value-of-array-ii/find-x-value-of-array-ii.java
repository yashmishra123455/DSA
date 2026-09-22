class Solution {

    int n;
    int k;
    int[] nums;
    Node[] tree;

    class Node {
        int prod;
        int[] pref;

        Node() {
            prod = 1 % k;
            pref = new int[k];
        }
    }

    // Merge two adjacent segments
    Node merge(Node left, Node right) {

        Node res = new Node();

        // Product of the complete segment
        res.prod = (left.prod * right.prod) % k;

        // Prefixes completely inside the left segment
        for (int r = 0; r < k; r++) {
            res.pref[r] += left.pref[r];
        }

        // Prefixes containing all of left
        // and then a prefix of right
        for (int r = 0; r < k; r++) {

            int newRemainder = (left.prod * r) % k;

            res.pref[newRemainder] += right.pref[r];
        }

        return res;
    }

    // Build the segment tree
    void build(int node, int l, int r) {

        if (l == r) {

            tree[node] = new Node();

            int rem = nums[l] % k;

            tree[node].prod = rem;

            // Only one non-empty prefix at a leaf
            tree[node].pref[rem] = 1;

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = merge(
            tree[node * 2],
            tree[node * 2 + 1]
        );
    }

    // Point update
    void update(
        int node,
        int l,
        int r,
        int index,
        int value
    ) {

        if (l == r) {

            tree[node] = new Node();

            int rem = value % k;

            tree[node].prod = rem;
            tree[node].pref[rem] = 1;

            nums[l] = value;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {

            update(
                node * 2,
                l,
                mid,
                index,
                value
            );

        } else {

            update(
                node * 2 + 1,
                mid + 1,
                r,
                index,
                value
            );
        }

        tree[node] = merge(
            tree[node * 2],
            tree[node * 2 + 1]
        );
    }

    // Range query
    Node query(
        int node,
        int l,
        int r,
        int ql,
        int qr
    ) {

        // Completely inside
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        int mid = (l + r) / 2;

        // Completely in left
        if (qr <= mid) {

            return query(
                node * 2,
                l,
                mid,
                ql,
                qr
            );
        }

        // Completely in right
        if (ql > mid) {

            return query(
                node * 2 + 1,
                mid + 1,
                r,
                ql,
                qr
            );
        }

        // Overlaps both sides
        Node left = query(
            node * 2,
            l,
            mid,
            ql,
            qr
        );

        Node right = query(
            node * 2 + 1,
            mid + 1,
            r,
            ql,
            qr
        );

        return merge(left, right);
    }

    // IMPORTANT:
    // LeetCode expects this exact method name
    public int[] resultArray(
        int[] nums,
        int k,
        int[][] queries
    ) {

        this.nums = nums;
        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n + 5];

        // Build tree
        build(1, 0, n - 1);

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Persistent update
            update(
                1,
                0,
                n - 1,
                index,
                value
            );

            // Query nums[start ... n-1]
            Node res = query(
                1,
                0,
                n - 1,
                start,
                n - 1
            );

            // Number of prefixes with
            // product % k == x
            answer[i] = res.pref[x];
        }

        return answer;
    }
}