package companyinterview;

class SubrectangleQueries {


    int[][] rectangle;
    public SubrectangleQueries(int[][] rectangle) {
        this.rectangle = rectangle;
    }
    
    public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {

        for(int row = row1; row < row2 ; row++){
            for(int col = col1; col < col2 ; col++) {
                rectangle[row][col] = newValue;
            }
        }
    }
    
    public int getValue(int row, int col) {
        return rectangle[row][col];
    }
}

/**
 * Your Interview.SubrectangleQueries object will be instantiated and called as such:
 * Interview.SubrectangleQueries obj = new Interview.SubrectangleQueries(rectangle);
 * obj.updateSubrectangle(row1,col1,row2,col2,newValue);
 * int param_2 = obj.getValue(row,col);
 */