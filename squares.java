class DetectSquares {
    HashMap<Integer, HashMap<Integer, Integer>> points;
    public DetectSquares() {
        points = new HashMap<>();
    }
    
    // O(1)
    // keep points for x -> <y, yCount> 
    public void add(int[] point) {
        points.putIfAbsent(point[0], new HashMap<>());
        
        HashMap<Integer, Integer> yCount = points.get(point[0]);
        yCount.put(point[1], yCount.getOrDefault(point[1],0)+1);
    }
    
    // O(1 + yCountsize())
    public int count(int[] point) {
        // if point x not preset
        if(!points.containsKey(point[0]))
            return 0;
        
        int count = 0;
        // all y for x (vertical points)
        HashMap<Integer, Integer> yCounts = points.get(point[0]);
        
        // for all y
        for(int y : yCounts.keySet()) {
            // if same point(y is also same), ignore (point[1,2], y=2)
            // consider point on same x but diff y to make vertical line 
            if(y == point[1])
                continue;
            
            // get yCount for 
            int yCount = yCounts.get(y);
            
            // x for point[1] and current y i.e. dis of point and current y
            int diff = Math.abs(y - point[1]);
            
            // how to find next vertical line of same dis as diff
            // we have 2 (y, point[1]) 
            // (point[0] - diff) is x on the left of current vertical line
            // (x,y) (x,point[1]) are other 2 points for next vertical line on left of point
            // why * yCount? if count more than 1 on current vertical line yCount, need to consider all occurance of y
            count += countSquare(point[1], y, point[0] - diff) * yCount;
            
            // vertical line on right of current line(point[1], y)
            count += countSquare(point[1], y, point[0] + diff) * yCount;
            
        }
        
        return count;
    }
    
    // check if (x,y) (x,point[1]) present in the map
    // get its yCount and multiply
    // why multiply? to have cross product of multiple same points (2[x, y1], 2[x,y2] = 4)
    private int countSquare(int y1, int y2, int x) {
        if(!points.containsKey(x))
            return 0;
        
        int count1 = points.get(x).getOrDefault(y1, 0);
        int count2 = points.get(x).getOrDefault(y2, 0);
        
        return count1*count2;
    }
}
