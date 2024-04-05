package LaivanUpotus.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tietokone {

    public static Random random = new Random();
    public static int arvaukset = 0;                            
    public static int[] aikaisempiArvaus = new int[2];
    public static boolean hunt = false;
    public static ArrayList<int[]> tehdytArvaukset = new ArrayList<>();    
    public static ArrayList<int[]> mahdollisetArvaukset = new ArrayList<>();
    public static ArrayList<int[]> osututArvaukset = new ArrayList<>();
    public static ArrayList<int[]> kohdeKoordinaatit = new ArrayList<>();
    public static ArrayList<int[]> upotetutLaivat = new ArrayList<>();

    /**
     * Tätä metodia käytetään tietokoneen arvauksen tekemiseen tietokone vs pelaaja pelimuodossa.
     * Tietokoneella on käytössä vain samat tiedot kuin normaalilla pelaajalla, eli tietokone ei tee 
     * arvaustaan esim. hakemalla laivan sijainnin vastustajan kartasta, koska se olisi ns "huijausta".
     */
    public static int[] laskeParasArvaus(String[][] kartta, String pelaaja, String vastustaja){

        int[] arvaus = new int[2];              // Alustetaan tietokoneen arvaus
        boolean aikaisempiArvausOsui = false;   // Osuiko aikaisemman kierroksen arvaus

        // Jos ei ole ensimmäinen arvaus, niin tarkistetaan osuiko aikaisempi arvaus. 
        aikaisempiArvausOsui = arvaukset > 0 ? osukoAikaisempi(kartta) : false;

        // Haetaan mahdolliset arvaukset kartalta.
        haeMahdollisetArvaukset(kartta);

        if(hunt){
             //Jos tietokone "Hunt"-tilassa ja aikaisempi arvaus osui,   
             //haetaan koordinaatit joissa loppuosa laivasta voisi olla.      
            if(aikaisempiArvausOsui){
                haeKohdeKoordinaatit();
            } 
            // Asetetaan arvaukseksi random suunta kohdekoordinaateista.
            arvaus = arvoRandomSuunta();

        } else {        
            // Jos tietokone ei "Hunt"-tilassa, haetaan satunnaisesti arvaus.
            arvaus = mahdollisetArvaukset.get(mahdollisetArvaukset.size()/2);
        }

        System.out.println(Arrays.deepToString(upotetutLaivat.toArray()));
        System.out.println(Arrays.toString(aikaisempiArvaus));
        System.out.println(hunt);
        System.out.println(Arrays.deepToString(kohdeKoordinaatit.toArray()));
        System.out.println(Arrays.toString(arvaus));

        arvaukset++;
        tehdytArvaukset.add(arvaus);
        return arvaus;

    }


    /**
     * Tarkistetaan osuiko tietokoneen aikaisemmalla vuorolla tekemä arvaus.
     */
    private static boolean osukoAikaisempi(String[][] kartta){
        aikaisempiArvaus = tehdytArvaukset.get(arvaukset-1);
        for (int[] arvaus : osututArvaukset) {
            // aikaisempi arvaus on osututArvaus Arrayssa => palautetaan true
            if (Arrays.equals(arvaus, aikaisempiArvaus)) {
                return true;
            }
          // Muuten false
        } return false;
    }
    

    /**
     * Haetaan tietokoneen muistiinpanokartasta kaikki mahdolliset arvaukset
     */
    private static void haeMahdollisetArvaukset(String[][] kartta){
        for(int i = 0; i < kartta.length; i++){
            for(int j = 0; j < kartta.length; j++){
                // Jos kartan alkio sisältää merkin "~" => 
                // lisätään mahdollisiin arvauksiin.
                if(kartta[i][j].equals("~")){
                    mahdollisetArvaukset.add(new int[]{i, j});
                }
            }
        }
    }


    /**
     * Haetaan arvauksen ympäriltä kohde koordinaatit, jossa osutun laivan
     * loppuosa voisi olla, ja poistetaan yli rajojen menevät koordinaatit.
     */
    private static void haeKohdeKoordinaatit(){
        kohdeKoordinaatit.clear();
        kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0]+1, aikaisempiArvaus[1]});   // Alas
        kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0]-1, aikaisempiArvaus[1]});   // Ylös
        kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0], aikaisempiArvaus[1]+1});   // Oikea
        kohdeKoordinaatit.add(new int[]{aikaisempiArvaus[0], aikaisempiArvaus[1]-1});   // Vasen

        // Käydään kohde koordinaatit läpi
        for(int i = 0; i < kohdeKoordinaatit.size(); i++){
            int[] koordinaatti = kohdeKoordinaatit.get(i);
            if(koordinaatti[0] > 9 || koordinaatti[0] < 0){
                // Jos koordinaatin rivi yli rajojen => poista taulukosta
                kohdeKoordinaatit.remove(i);
            }
            if(koordinaatti[1] > 9 || koordinaatti[1] < 0){
                // Jos koordinaatin sarake yli rajojen => poista taulukosta
                kohdeKoordinaatit.remove(i);
            }
        }
    }

    /** 
     * Arvotaan random suunta kohdekoordinaateista josta lähdetään arvailemaan.
     */
    private static int[] arvoRandomSuunta(){
        int[] arvaus = new int[2];
        while(true){
            int randomArvausSuunta = random.nextInt(kohdeKoordinaatit.size());     
            arvaus = kohdeKoordinaatit.get(randomArvausSuunta);
            if(arvaus.length == 0){
                continue;
            }   
            break;
        }
        return arvaus;
    }
}
