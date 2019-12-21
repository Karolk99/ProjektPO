import java.util.*;

public class RectangularMap {
    protected final int width;
    protected final int height;

    private List<Animal> animals = new LinkedList<>();
    private Map<Vector2d, List<Animal>> animalsMap = new HashMap<>();
    private Map<Vector2d, Grass> grassMap = new HashMap<>();

    private Set<Vector2d> freePositions = new HashSet<>();
    private List<Vector2d> freePositionsList= new ArrayList<>();
    private Set<Vector2d> freePositionsJungle = new HashSet<>();
    private List<Vector2d> freePositionsListJungle= new ArrayList<>();


    private Random generator = new Random();

    private final Vector2d jungleUpperRight;
    private final Vector2d jungleLowerLeft;

    public RectangularMap(int width , int height, double jungleRatio){
        this.width = width;
        this.height = height;

        int jungleWidth=  (int) ((double)width * jungleRatio);
        int jungleHeight = (int)  ((double) height * jungleRatio);

        this.jungleLowerLeft= new Vector2d( (this.width/2 -  jungleWidth/2), (this.height/2 - jungleHeight/2));
        this.jungleUpperRight= new Vector2d ((this.width/2 + jungleWidth/2), (this.height/2 + jungleHeight/2));

        for (int i =0; i<width; i++){
            for(int j=0; j<height; j++) {
                animalsMap.put(new Vector2d(i, j), new ArrayList<>());
                if (isJungle(new Vector2d(i, j))){
                    freePositionsJungle.add(new Vector2d(i, j));
                    freePositionsListJungle.add(new Vector2d(i, j));
                }
                else{
                    freePositions.add(new Vector2d(i, j));
                    freePositionsList.add(new Vector2d(i, j));
                }
            }
        }
    }

    public void addAnimal(Animal animal){
        this.animals.add(animal);
        this.animalsMap.get(animal.getPosition()).add(animal);
        checkFreePosition(animal.getPosition());
    }

    public void removeAnimal(Animal animal){
        this.animals.remove(animal);
        this.animalsMap.get(animal.getPosition()).remove(animal);
        checkFreePosition(animal.getPosition());
    }

    public void addGrass(Vector2d position){
        this.grassMap.put(position,new Grass(position));
        checkFreePosition(position);
    }

    public void removeGrass(Vector2d position){
        this.grassMap.remove(position);
        checkFreePosition(position);
    }

    public boolean isOccupied(Vector2d position){
        return objectAt(position) != null;
    }

    public IMapElement objectAt(Vector2d position){

        if(this.animalsMap.get(position) != null && this.animalsMap.get(position).size() != 0)
            return this.animalsMap.get(position).get(0);

        if(this.grassMap.get(position) != null)
            return this.grassMap.get(position);

        return null;
    }

    public Vector2d findPosition(Vector2d position){
        Direction direction = Direction.NORTH.turn(generator.nextInt(8));
        for(int i = 0; i <= 8; i++){
            if(animalsMap.get(position.add(direction.toUnitVector(),width,height)).size() == 0 )
                break;
            direction.next();
            if( i == 7) return position;
        }

        return position.add(direction.toUnitVector(),width,height);
    }

    public void checkFreePosition(Vector2d position){
        Set set = freePositions;
        List list = freePositionsList;

        if(isJungle(position)) {
            set = freePositionsJungle;
            list = freePositionsListJungle;
        }

        if (isOccupied(position) && set.contains(position)) {
            set.remove(position);
            list.remove(position);
        }

        if (!isOccupied(position) && !set.contains(position)) {
            set.add(position);
            list.add(position);
        }
    }


    public boolean isEmptyOutsideJungle(){
        return freePositionsList.isEmpty();
    }

    public boolean isEmptyJungle(){
        return freePositionsListJungle.isEmpty();
    }

    public Vector2d randPosition(){
        if(generator.nextInt(3) == 0 && !isEmptyJungle())
            return this.freePositionsListJungle.get(generator.nextInt(this.freePositionsListJungle.size()));
        return this.freePositionsList.get(generator.nextInt(this.freePositionsList.size()));
    }

    public Vector2d randPositionOutsideJungle(){
        return this.freePositionsList.get(generator.nextInt(this.freePositionsList.size()));
    }

    public Vector2d randPositionJungle(){
        return this.freePositionsListJungle.get(generator.nextInt(this.freePositionsListJungle.size()));
    }

    private boolean isJungle( Vector2d position){
        return (position.x >= jungleLowerLeft.x && position.x <= jungleUpperRight.x && position.y >= jungleLowerLeft.y && position.y <= jungleUpperRight.y);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Animal> getList( Vector2d position){
        return animalsMap.get(position);
    }

    public boolean grassExist(Vector2d position){
        return (grassMap.get(position) != null);
    }

    public int sizeOfGrassMap(){
        return grassMap.size();
    }
}
