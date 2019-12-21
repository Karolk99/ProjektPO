import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int[] genes = new int[32];
    private Random generator = new Random();

    public Genes(){
        for(int i = 0; i <= 7; i++) {
            this.genes[i] = i;
        }
        for(int i = 8; i < 32; i++){
            this.genes[i] = generator.nextInt(8);
        }
        Arrays.sort(genes);
    }

    public Genes(int[] genes ){
        for( int i = 0; i < 32; i++ ){
            this.genes[i] = genes[i];
        }
    }

    public Genes createGenes( Genes other, int div1, int div2){
        int[] tab = new int[32];
        boolean is_all = false;
        boolean is_i_exists = false;
        for(int i = 0; i<div1; i++){
            tab[i] = this.genes[i];
        }
        for(int i = div1; i<div2; i++){
            tab[i] = other.genes[i];
        }
        for(int i = div2; i<32; i++){
            tab[i] = this.genes[i];
        }
        while(is_all == false){
            for( int i = 0; i<8; i++){
                is_i_exists = false;
                for(int j = 0; j<32; j++) {
                    if (tab[j] == i) {
                        is_i_exists = true;
                        break;
                    }
                }
                if(is_i_exists == false) {
                    tab[generator.nextInt(32)] = i;
                    break;
                }
                if( i == 7){
                    is_all = true;
                }
            }
        }
        Arrays.sort(tab);
        return new Genes(tab);
    }


    public void wypisz(){
        for( int i = 0; i<32; i++)
            System.out.print(genes[i]);
    }

    public int get(int index){
        return genes[index];
    }
}
