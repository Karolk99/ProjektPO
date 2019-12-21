public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d( int x, int y ){
        this.x=x;
        this.y=y;
    }

    public Vector2d add( Vector2d other)
    {
        return new Vector2d(this.x + other.x,this.y + other.y);
    }

    public Vector2d add( Vector2d other, int width, int height)
    {
        int x = this.x + other.x;
        int y = this.y + other.y;
        if( this.x + other.x == width ) x = 0;
        if( this.x + other.x < 0 )  x = width -1;
        if( this.y + other.y == height ) y = 0;
        if( this.y + other.y < 0 ) y = height -1;

        return new Vector2d(x,y);
    }

    @Override
    public int hashCode(){
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 13;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj instanceof Vector2d){
            return this.x == ((Vector2d) obj).x && this.y == ((Vector2d) obj).y;
        }
        return false;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
