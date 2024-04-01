package LaivanUpotus.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tietokone {

    public static Random random = new Random();
    public static int arvaukset = 0;                            
    public static ArrayList<int[]> tehdytArvaukset = new ArrayList<>();    
    public static ArrayList<int[]> mahdollisetArvaukset = new ArrayList<>();
    public static ArrayList<int[]> osututArvaukset = new ArrayList<>();
    public static ArrayList<int[]> kohdeKoordinaatit = new ArrayList<>();

    public static int[] laskeParasArvaus(String[][] kartta, String pelaaja, String vastustaja){

        int[] arvaus = new int[2];
        int[] aikaisempiArvaus = new int[2];
        boolean osukoAikaisempiArvaus = false;

        if(arvaukset > 0){
            aikaisempiArvaus = tehdytArvaukset.get(arvaukset-1);
            osukoAikaisempiArvaus = osututArvaukset.contains(aikaisempiArvaus);
        } 

        for(int i = 0; i < kartta.length; i++){
            for(int j = 0; j < kartta.length; j++){
                if(kartta[i][j].equals("~")){
                    mahdollisetArvaukset.add(new int[]{i, j});
                }
            }
        }
        System.out.println(Arrays.deepToString(osututArvaukset.toArray()));
        System.out.println(Arrays.toString(aikaisempiArvaus));
        
        if(osukoAikaisempiArvaus){

            kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0]+1, aikaisempiArvaus[1]});
            kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0]-1, aikaisempiArvaus[1]});
            kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0], aikaisempiArvaus[1]+1});
            kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0], aikaisempiArvaus[1]-1});

            for(int i = 0; i <= kohdeKoordinaatit.size(); i++){
                int[] koordinaatti = kohdeKoordinaatit.get(i);
                if(koordinaatti[0] > 9 || koordinaatti[0] < 0){
                    kohdeKoordinaatit.remove(i);
                }
                if(koordinaatti[1] > 9 || koordinaatti[1] < 0){
                    kohdeKoordinaatit.remove(i);
                }
            }

            while(true){
                int randomArvausSuunta = random.nextInt(kohdeKoordinaatit.size());     
                arvaus = kohdeKoordinaatit.get(randomArvausSuunta);
                if(arvaus.length == 0){
                    continue;
                }   break;
            }
            tehdytArvaukset.add(arvaus);
            arvaukset++;
            return arvaus;
    
        }

        arvaus = mahdollisetArvaukset.get(mahdollisetArvaukset.size()/2);
        tehdytArvaukset.add(arvaus);
        arvaukset++;
        System.out.println(Arrays.toString(arvaus));
        return arvaus;

    }
}
