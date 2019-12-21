public enum Direction {

    EAST,WEST,NORTH,SOUTH,NE,NW,SE,SW;

    public Vector2d toUnitVector() {
        switch (this) {
            case EAST:
                return new Vector2d(1,0);
            case WEST:
                return new Vector2d(-1,0);
            case NORTH:
                return new Vector2d(0,1);
            case SOUTH:
                return new Vector2d(0,-1);
            case NE:
                return new Vector2d(1,1);
            case NW:
                return new Vector2d(-1,1);
            case SE:
                return new Vector2d(1,-1);
            case SW:
                return new Vector2d(-1,-1);
            default:
                return null;
        }

    }

    public Direction next(){
        switch (this) {
            case EAST:
                return SE;
            case SE:
                return SOUTH;
            case SOUTH:
                return SW;
            case SW:
                return WEST;
            case WEST:
                return NW;
            case NW:
                return NORTH;
            case NORTH:
                return NE;
            case NE:
                return EAST;
            default:
                return null;
        }
    }

    public Direction turn(int n){
        Direction tmp = this;
        for(int i = 0; i < n; i++)
            tmp = tmp.next();
        return tmp;
    }
}
