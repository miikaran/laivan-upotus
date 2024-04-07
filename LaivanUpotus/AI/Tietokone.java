package LaivanUpotus.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tietokone {

    public static Random random = new Random();
    public static int arvaukset = 0;                        
    public static boolean jahtaa = false;
    public static int jahtaamisArvaukset = 0;    
    public static int[] edellinenArvaus = new int[2];
    public static int jahtaamisOikeaSuunta; 
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
        while(true){
            int[] arvaus = new int[2];              // Alustetaan tietokoneen arvaus
            boolean edellinenArvausOsui = false;    // Osuiko edellisen kierroksen arvaus

            // Jos ei ole ensimmäinen arvaus, niin tarkistetaan osuiko aikaisempi arvaus. 
            edellinenArvausOsui = arvaukset > 0 ? osukoEdellinen(kartta) : false;
            // Haetaan mahdolliset arvaukset kartalta.
            haeMahdollisetArvaukset(kartta);
    
            if(jahtaa){
                 // Jos tietokone on osunut laivaan ja aikaisempi arvaus osui,   
                 // haetaan koordinaatit joissa loppuosa laivasta voisi olla.      
                if(edellinenArvausOsui){
                    haeKohdeKoordinaatit(edellinenArvaus);
                }
                System.out.println("Kohde koordinaattien koko: " + kohdeKoordinaatit.size());
                if(jahtaamisArvaukset > 1 && edellinenArvausOsui){
                    int tiedettySuunta = jahtaamisOikeaSuunta;
                    arvaus = kohdeKoordinaatit.get(tiedettySuunta);
                } 
                else if(jahtaamisArvaukset > 1 && !edellinenArvausOsui){
                    int jahtaamisAloitusKierros = arvaukset-jahtaamisArvaukset;
                    int[] alkuArvaus = tehdytArvaukset.get(jahtaamisAloitusKierros);
                    haeKohdeKoordinaatit(alkuArvaus);
                    arvaus = arvoRandomSuunta();
                } 
                else {
                    // Asetetaan arvaukseksi random suunta kohdekoordinaateista.
                    arvaus = arvoRandomSuunta();
                } 
            } else {        
                // Jos tietokone ei ole osunut laivaan, haetaan satunnaisesti arvaus.
                arvaus = mahdollisetArvaukset.get(random.nextInt(mahdollisetArvaukset.size()-1));
            }  
            if(tehdytArvaukset.contains(arvaus)){
                continue;
            } else {
                arvaukset++;
                tehdytArvaukset.add(arvaus);
                return arvaus;  
            }   
        }

    }

    /**
     * Tarkistetaan osuiko tietokoneen edellisellä vuorolla tekemä arvaus.
     */
    private static boolean osukoEdellinen(String[][] kartta){
        edellinenArvaus = tehdytArvaukset.get(arvaukset-1);
        for (int[] arvaus : osututArvaukset) {
            // Jos edellinen arvaus on osututArvaus Arrayssa => palautetaan true
            if (Arrays.equals(arvaus, edellinenArvaus)) {
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
    private static void haeKohdeKoordinaatit(int[] arvaus){
        kohdeKoordinaatit.clear();
        kohdeKoordinaatit.add(new int[]{arvaus[0]+1, arvaus[1]});   // Alas
        kohdeKoordinaatit.add(new int[]{arvaus[0]-1, arvaus[1]});   // Ylös
        kohdeKoordinaatit.add(new int[]{arvaus[0],   arvaus[1]+1}); // Oikea
        kohdeKoordinaatit.add(new int[]{arvaus[0],   arvaus[1]-1}); // Vasen

        // Käydään kohde koordinaatit läpi
        for(int i = 0; i < kohdeKoordinaatit.size(); i++){
            int[] koordinaatti = kohdeKoordinaatit.get(i);
            if((koordinaatti[0] > 9 || koordinaatti[0] < 0) || (koordinaatti[1] > 9 || koordinaatti[1] < 0)){
                // Jos koordinaatin rivi yli rajojen => poista taulukosta
                kohdeKoordinaatit.remove(i);
                i--;
            }
        }
        for(int[] k : kohdeKoordinaatit){
            System.out.println(Arrays.toString(k));
        }
    }

    /** 
     * Arvotaan random suunta kohdekoordinaateista josta lähdetään arvailemaan.
     */
    private static int[] arvoRandomSuunta(){
        int[] arvaus = new int[2];
        while(true){
            try{
                int randomArvausSuunta = random.nextInt(kohdeKoordinaatit.size());     
                arvaus = kohdeKoordinaatit.get(randomArvausSuunta);
                if(arvaus.length == 0){
                    continue;
                }   
                jahtaamisOikeaSuunta = randomArvausSuunta;
                System.out.println("Jahtaamissuunta" + jahtaamisOikeaSuunta);
                break;
            } catch(IllegalArgumentException iae){
                continue;
            }
        }
        return arvaus;
    }
}
