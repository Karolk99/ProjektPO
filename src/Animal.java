import java.util.Random;
import java.util.Arrays;

public class Animal implements IMapElement {
    private Vector2d position;
    private Direction direction;
    private final Genes genes;
    private int energy;
    private Random generator = new Random();


    public Animal(Vector2d birthPosition, int strength) {                   // Adam i Ewa
        this.position = birthPosition;
        this.energy = strength;
        this.direction = Direction.NORTH.turn(generator.nextInt(8));
        this.genes = new Genes();
    }

    
    private Animal( Animal dad, Animal mum, Vector2d position){                                                             // Dzieci
        this.position = position;
        this.energy =(int) (dad.energy*0.25 + mum.energy *0.25);                                        // ucina cyfry po zerze
        this.direction = Direction.NORTH.turn(generator.nextInt(8));
        int div1= 1 + generator.nextInt(30);
        int div2 = 1 + generator.nextInt(30);
        while( div1 == div2){
            div2 = 1 + generator.nextInt(30);
        }
        this.genes = dad.genes.createGenes(mum.genes,div1,div2);                                                      //  czy tak można??????????
    }


    public Animal baby(Animal other, Vector2d position){
        return new Animal(this, other,position);
    }

    public void move(int width , int height) {
        int gen = this.genes.get(generator.nextInt(32));
        direction = direction.turn(gen);
        this.position = this.position.add(direction.toUnitVector());
        if( this.position.x == width ) this.position = new Vector2d(0,position.y);
        if( this.position.x < 0 ) this.position = new Vector2d(width -1,position.y);
        if( this.position.y == height ) this.position = new Vector2d(position.x, 0);
        if( this.position.y < 0 ) this.position = new Vector2d(position.x, height -1);
    }

    public void eat(int grassEnergy){
        this.energy += grassEnergy;
    }

    public void eat( Animal other, int grassEnergy){ // czy nie bedzię problemu ze zmiennymi ????
        this.energy += grassEnergy/2;
        other.energy += grassEnergy/2;
    }

    public void endOfDay(int energy){
        this.energy -= energy;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "A";
    }

    public int getEnergy() {
        return energy;
    }
}
