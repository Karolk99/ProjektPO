import java.io.IOException;
import java.util.*;

public class World {
    public static void main( String[] args ) throws InterruptedException, IOException {
        int width = 20;
        int height = 20;
        int moveEnergy = 1;
        int grassEnregy = 5;
        int startEnergy = 5;
        double jungleRatio = 0.4;
        int firstAnimals = 10;
        int amountofGrass = 50;

        Simulation pierwsza = new Simulation(width,height,moveEnergy,grassEnregy,startEnergy,jungleRatio, firstAnimals, amountofGrass);
        MapVisualizer visualizer = new MapVisualizer(pierwsza.getMap());

        int days = 1000;
        for(int i = 0; i< days; i++) {
            System.out.println(visualizer.draw(new Vector2d(0, 0), new Vector2d(19, 19)));
            pierwsza.oneDay();
            Thread.sleep(100);
        }
    }

}
// przepraszam ze nie nakłada się jedno na drugie ale nie potrafiłem sobie z tym poradzić