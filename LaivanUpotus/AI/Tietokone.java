package LaivanUpotus.AI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        int[] arvaus = new int[2];              // Alustetaan tietokoneen arvaus
        boolean edellinenArvausOsui = false;    // Osuiko edellisen kierroksen arvaus

        // Jos ei ole ensimmäinen arvaus, niin tarkistetaan osuiko aikaisempi arvaus. 
        edellinenArvausOsui = arvaukset > 0 ? osukoEdellinen(kartta) : false;

        // Haetaan mahdolliset arvaukset kartalta.
        haeJarkevatArvaukset(kartta);
      
        if(jahtaa){

            // Jos tietokone on osunut laivaan ja aikaisempi arvaus osui,   
            // haetaan koordinaatit joissa loppuosa laivasta voisi olla.      
            if(edellinenArvausOsui){
                haeKohdeKoordinaatit(edellinenArvaus);
            }

            // Jos jahtaamisen aikana tehtyjä arvauksia yli 1, niin haetaan loputkin samasta suunnasta
            if(jahtaamisArvaukset > 1 && edellinenArvausOsui){
                arvaus = kohdeKoordinaatit.get(jahtaamisOikeaSuunta);
            } 
            // Mikäli samasta suunnasta ei löydy enää laivan osia, otetaan jahtaamis 
            // kierroksen alkupäästä vastapäisestä suunnasta loput laivan osat.
            else if(jahtaamisArvaukset > 1 && !edellinenArvausOsui){

                int jahtaamisAloitusKierros = arvaukset-jahtaamisArvaukset-1;
                int jahtaamisToinenKierros = arvaukset-jahtaamisArvaukset;
                int[] jahtaamisAlkuArvaus = tehdytArvaukset.get(jahtaamisAloitusKierros);
                haeKohdeKoordinaatit(jahtaamisAlkuArvaus);
                arvaus = palautaVastapainenSuunta(kohdeKoordinaatit, tehdytArvaukset.get(jahtaamisToinenKierros)); 

            } 
            else {
                // Asetetaan arvaukseksi random suunta kohdekoordinaateista.
                arvaus = arvoRandomSuunta();
            } 
        } else {        
            // Jos tietokone ei ole osunut laivaan, haetaan satunnaisesti arvaus.
            arvaus = mahdollisetArvaukset.get(random.nextInt(mahdollisetArvaukset.size()-1));
        }

        arvaukset++;
        tehdytArvaukset.add(arvaus);
        return arvaus;  
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
    private static void haeJarkevatArvaukset(String[][] kartta){
        mahdollisetArvaukset.clear();
        for(int i = 0; i < kartta.length; i++){
            for(int j = 0; j < kartta.length; j++){

                // Jos kartan alkio sisältää merkin "~" => lisätään mahdollisiin arvauksiin.
                if(kartta[i][j].equals("~")){
                    mahdollisetArvaukset.add(new int[]{i, j});
                
                }
                
            }
        }
    }

    /**
     * Haetaan arvauksen ympäriltä kohde koordinaatit, jossa osutun laivan loppuosa voisi olla.
     */
    private static void haeKohdeKoordinaatit(int[] arvaus){
        kohdeKoordinaatit.clear();
        kohdeKoordinaatit.add(new int[]{arvaus[0]+1, arvaus[1]});   // Alas
        kohdeKoordinaatit.add(new int[]{arvaus[0]-1, arvaus[1]});   // Ylös
        kohdeKoordinaatit.add(new int[]{arvaus[0],   arvaus[1]+1}); // Oikea
        kohdeKoordinaatit.add(new int[]{arvaus[0],   arvaus[1]-1}); // Vasen
    }

    /** 
     * Arvotaan random suunta kohdekoordinaateista, josta lähdetään arvailemaan.
     */
    private static int[] arvoRandomSuunta(){
        int[] arvaus = new int[2];
        while(true){

            try{ // Suunta arvotaan kohdekoordinaattien koon mukaan (4).

                int randomArvausSuunta = random.nextInt(kohdeKoordinaatit.size());   
                arvaus = kohdeKoordinaatit.get(randomArvausSuunta);
                boolean onYliRajojen = arvaus[0] > 9 || arvaus[0] < 0 || arvaus[1] > 9 || arvaus[1] < 0;
                boolean onArvattuJo = tehdytArvaukset.contains(arvaus);
                
                if(arvaus.length == 0 || onYliRajojen || onArvattuJo){
                    continue;
                }

                jahtaamisOikeaSuunta = randomArvausSuunta;
                break;

            } catch(IllegalArgumentException iae){
                continue;
            }
        }
        return arvaus;
    }

    /**
     * Tällä metodilla palautetaan jahtaamisen alkupään vastapäinen suunta, kun samasta sunnasta
     * ei enää löydy laivoja ja laiva ei ole vielä uponnut.
     */
    public static int[] palautaVastapainenSuunta(List<int[]> koordinaatit, int[] koordinaatti){
        int[] vastaKoordinaatit = {};
        // Jos suunta alhaalla => ylös
        if (Arrays.equals(koordinaatti, koordinaatit.get(0))) {
            jahtaamisOikeaSuunta = 1;
            vastaKoordinaatit =  koordinaatit.get(1);
        // Jos suunta ylhäällä => alas     
        } else if (Arrays.equals(koordinaatti, koordinaatit.get(1))) {
            jahtaamisOikeaSuunta = 0;
            vastaKoordinaatit =  koordinaatit.get(0);
        // Jos suunta oikea => vasen
        } else if (Arrays.equals(koordinaatti, koordinaatit.get(2))) {
            jahtaamisOikeaSuunta = 3;
            vastaKoordinaatit =  koordinaatit.get(3);  
        // Jos suunta vasen => oikea
        } else if (Arrays.equals(koordinaatti, koordinaatit.get(3))) {
            jahtaamisOikeaSuunta = 2;
            vastaKoordinaatit =  koordinaatit.get(2);
        }
        // Jos mikään ei matchaa => {}
        return vastaKoordinaatit; 
    }
}
