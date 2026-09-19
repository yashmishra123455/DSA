class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Find closest x-coordinate in rectangle
        int closestX = Math.max(x1, Math.min(xCenter, x2));

        // Find closest y-coordinate in rectangle
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Distance between circle center and closest rectangle point
        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        // Compare squared distance with squared radius
        return dx * dx + dy * dy <= radius * radius;
    }
}