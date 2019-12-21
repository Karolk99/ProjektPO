public class Grass implements IMapElement {
    public Vector2d position;

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public Grass(Vector2d position){
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
