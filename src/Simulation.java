import java.util.*;

public class Simulation {

    private Random generator = new Random();

    private RectangularMap map;

    private final int moveEnergy;
    private final int grassEnergy;
    private final int startEnergy;

    public Simulation( int width, int height, int moveEnergy, int grassEnergy, int startEnergy, double jungleRatio, int firstAnimals,int amountofGrass ){
        this.grassEnergy = grassEnergy;
        this.moveEnergy = moveEnergy;
        this.startEnergy = startEnergy;
        this.map = new RectangularMap(width, height, jungleRatio);

        for(int i = 0; i < firstAnimals; i++){
            Vector2d position = this.map.randPosition();
            this.map.addAnimal(new Animal(position,startEnergy));
        }

        for(int i = 0; i < amountofGrass; i++){
            Vector2d position = this.map.randPosition();
            this.map.addGrass((position));
        }

    }

    private void feedAllAnimals() {
        for (Animal animal : this.map.getAnimals()) {
            if (this.map.grassExist(animal.getPosition())) {
                this.map.removeGrass(animal.getPosition());
                List<Animal> tmp = map.getList(animal.getPosition());
                if(tmp.size()==1)
                    tmp.get(0).eat(grassEnergy);
                else {
                    Animal strongest_1 = tmp.get(0);
                    Animal strongest_2 = tmp.get(1);
                    for (int index = 2; index < tmp.size(); index++) {
                        if (strongest_1.getEnergy() < tmp.get(index).getEnergy())
                            strongest_1 = tmp.get(index);
                        else if (strongest_2.getEnergy() < tmp.get(index).getEnergy())
                            strongest_2 = tmp.get(index);
                    }
                    if (strongest_1.getEnergy() > strongest_2.getEnergy()) strongest_1.eat(grassEnergy);
                    if (strongest_1.getEnergy() < strongest_2.getEnergy()) strongest_2.eat(grassEnergy);
                    if (strongest_1.getEnergy() == strongest_2.getEnergy()) strongest_1.eat(strongest_2, grassEnergy);
                }
            }
        }
    }

    private void moveAllAnimals(){

        for(Animal animal : this.map.getAnimals()) {
            this.map.getList(animal.getPosition()).remove(animal);
            this.map.checkFreePosition(animal.getPosition());
            animal.move(map.width,map.height);
            this.map.getList(animal.getPosition()).add(animal);
            this.map.checkFreePosition(animal.getPosition());
        }
    }

    private void copulation(int newborn){

        Set<Vector2d> placeOfBirth = new HashSet<>();
        List<Animal> newAnimals = new ArrayList<>();

        for(Animal animal : this.map.getAnimals()) {

            if (!placeOfBirth.contains(animal.getPosition()) && this.map.getList(animal.getPosition()).size() >= 2) {

                placeOfBirth.add(animal.getPosition());

                List<Animal> tmp = this.map.getList(animal.getPosition());

                Animal strongest_1 = tmp.get(0);
                Animal strongest_2 = tmp.get(1);

                for (int index = 2; index < tmp.size(); index++) {
                    if (strongest_1.getEnergy() < tmp.get(index).getEnergy())
                        strongest_1 = tmp.get(index);
                    else if (strongest_2.getEnergy() < tmp.get(index).getEnergy())
                        strongest_2 = tmp.get(index);
                }

                if (strongest_1.getEnergy() > startEnergy / 2 && strongest_2.getEnergy() > startEnergy / 2) {
                    Animal child = strongest_1.baby(strongest_2, this.map.findPosition(strongest_1.getPosition()));
                    newAnimals.add(child);
                    newborn +=1;
                }
            }
        }

        for(Animal animal : newAnimals){
            this.map.addAnimal(animal);
        }

    }

    private void lowerEnergy(int death){

        List<Animal> deadAnimals = new ArrayList<>();

        for(Animal animal : this.map.getAnimals()) {
            animal.endOfDay(moveEnergy);
            if( animal.getEnergy() <= 0 )
                deadAnimals.add(animal);
                death += 1;
        }

        for(Animal animal : deadAnimals){
            this.map.removeAnimal(animal);
        }

    }

    private void addGrassEachDay(){

        if(!this.map.isEmptyOutsideJungle()){
            this.map.addGrass(this.map.randPositionOutsideJungle());
        }
        if(!this.map.isEmptyJungle()){
            this.map.addGrass(this.map.randPositionJungle());
        }
    }

    public RectangularMap getMap() {
        return map;
    }

    public void oneDay(){
        int death = 0;
        int newborn = 0;
        moveAllAnimals();
        feedAllAnimals();
        copulation(newborn);
        lowerEnergy(death);
        addGrassEachDay();
        System.out.print("Animals: ");
        System.out.print(this.map.getAnimals().size());
        System.out.print("   Grass: ");
        System.out.print(this.map.sizeOfGrassMap());
        System.out.print("   newborn: ");
        System.out.print(newborn);
        System.out.print("   death: ");
        System.out.println(death);
    }
}


