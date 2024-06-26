package LaivanUpotus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Tämä luokka sisältää pelinkulun hallintaan tarvittavat metodit ja muuttujat.
 * @author Miika Rantalaiho
 */
public class Peli {

    // Alustetaan pelinkulun kannalta tärkeät muuttujat tänne.
    public static final Scanner scanner = new Scanner(System.in);                                    
    public static String peliMuoto = "";                                                         
    public static boolean peliPaalla = false;
    public static String[] pelaajat = new String[2];
    public static HashMap<String, String[][]> pelaajienKartat = new HashMap<String, String[][]>();
    public static HashMap<String, int[][]> pelaajienLaivat = new HashMap<String, int[][]>();
    public static int vuoro = 0;
    public static int arvaukset = 0;
    public static String voittaja;
    
    /**
     * Tällä funktiolla suoritetaan tarvittavat alkutoimenpiteet ja aloitetaan taistelu.
     * @param args Argumentit jota käytetään kun suoritetaan koodi komentoriviltä
     */
    public static void main(String[] args) {   
        peliPaalla = true;      // Aloitaan peli
        Menu.naytaMenu();       // Näytetään alkuvalikko
        MeriKartta.luoKartat(); // Luodaan kartat pelaajille
        Laivat.kysyLaivat();    // Kysytään pelaajien laivat
        aloitaTaistelu();       // Aloitetaan laivojen upotus
    }

    /**
     * Pelaaja aloittavat laivojen upotuksen.
     */
    public static void aloitaTaistelu(){
        System.out.println(Vakiot.ANSI_BOLD + "\n\n\n\nTAISTELU ALKAA!" + Vakiot.ANSI_RESET);

        while(peliPaalla){ // Niin kauan kun peliPaalla: true => pyöritetään taistelusilmukkaa.
            
            String pelaaja = pelaajat[vuoro];                                                       
            String vastustaja = pelaajat[(vuoro + 1) % 2];
            System.out.println("\n" + Vakiot.ANSI_PURPLE + Vakiot.ANSI_BOLD + pelaaja + Vakiot.ANSI_RESET + Vakiot.ANSI_CYAN + " arvaus vuoro" + Vakiot.ANSI_RESET);

            pelaajaHyokkaa(vastustaja, pelaaja);
            tarkistaVoitto(pelaaja, vastustaja);     
            MeriKartta.tulostaKartta(pelaaja, "muistiinpano"); 
            arvaukset++;  
            vuoro = (vuoro + 1) % 2; // Vaihdetaan vuoroa

            if(!peliPaalla){
                // Jos tällä kierroksella peli loppu => tallennetaan pelin tiedot 
                // tiedostoon, tulostetaan näytölle ja kysytään pelataanko uudelleen.
                TulosTallennus.tallennaTulos(pelaajat, arvaukset, voittaja);
                TulosTallennus.naytaTulokset("kierros");
                pelataankoUusiksi();
                
            }
        }       
    }

    /**
     * Pelaaja arvaa vastustajan laivan sijainteja koordinaateilla.
     * Jos arvaus oikein => merkitään vastustajan tauluun ja ilmoitetaan pelaajalle.
     * @param vastustaja Pelaajan vastustaja
     * @param pelaaja    Vuoron pelaaja
     */
    public static void pelaajaHyokkaa(String vastustaja, String pelaaja){
        while(true){

            int rivi = 0;   // Arvaus rivi
            int sarake = 0; // Arvaus sarake

            try{ // Otetaan pelaajilta vuoroltaan arvaus koordinaatit.

                if(!pelaaja.equals("Tietokone")){

                    // Otetaan käyttäjälta koordinaatti.
                    String koordinaatti = scanner.next().toUpperCase();
                    // Muutetaan koordinaatit kartan riveiksi ja sarakkeiksi.
                    rivi = Integer.parseInt(koordinaatti.substring(1));
                    sarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatti.charAt(0)); 
                    scanner.nextLine();

                } else {

                    int[] koordinaatit = LaivanUpotus.AI.Tietokone.laskeParasArvaus(pelaajienKartat.get(pelaaja+"-muistiinpano"), pelaaja, vastustaja);
                    rivi = koordinaatit[0];
                    sarake = koordinaatit[1];

                }

            } catch(Exception e){
                VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[7]);
                continue;
            }

            // Haetaan vastustajan kartta ja laivat.
            String[][] vastustajanKartta = pelaajienKartat.get(vastustaja);
            int[][] vastustajanLaivat = pelaajienLaivat.get(vastustaja);
            String[][] pelaajanMuistiinpanot = pelaajienKartat.get(pelaaja+"-muistiinpano");
            
            // Haetaan osuttu laiva, mikäli osui.
            int[] osuttuLaiva = tarkistaOsuma(vastustajanKartta, vastustajanLaivat, pelaajanMuistiinpanot, rivi, sarake);

            //Tarkistetaan osusiko arvaus.
            if(osuttuLaiva.length > 0){

                if(peliMuoto.equals("tietokone") && pelaaja.equals("Tietokone")){

                    LaivanUpotus.AI.Tietokone.osututArvaukset.add(0, new int[]{rivi, sarake});
                    LaivanUpotus.AI.Tietokone.jahtaa = true;
                    LaivanUpotus.AI.Tietokone.jahtaamisArvaukset++;

                }

                // Tarkistetaan upposiko osumalla.
                boolean upposko = upposko(osuttuLaiva, vastustajanKartta);
                if(upposko){
                
                    if(peliMuoto.equals("tietokone") && pelaaja.equals("Tietokone")){

                        LaivanUpotus.AI.Tietokone.upotetutLaivat.add(new int[]{rivi, sarake});
                        LaivanUpotus.AI.Tietokone.jahtaa = false;
                        LaivanUpotus.AI.Tietokone.jahtaamisArvaukset = 0;
                        LaivanUpotus.AI.Tietokone.kohdeKoordinaatit.clear();
                        
                    }
                    System.out.println(Vakiot.ANSI_BOLD + Vakiot.ANSI_CYAN + pelaaja + Vakiot.ANSI_RESET + " UPOTTI VASTUSTAJAN LAIVAN");
                }

            } else {

                try{

                    if(!pelaajanMuistiinpanot[rivi][sarake].equals(Vakiot.merkit[3]) && !pelaajanMuistiinpanot[rivi][sarake].equals(Vakiot.merkit[2]) ){
                        pelaajanMuistiinpanot[rivi][sarake] = Vakiot.merkit[3];
                    }

                } catch (ArrayIndexOutOfBoundsException e){
                    VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[8]);
                    continue;
                }

                System.out.println(Vakiot.ANSI_BOLD + Vakiot.ANSI_RED + "\nHUTI" + Vakiot.ANSI_RESET);
            }

            break;  
        }  
    }

    /**
     * Tarkistetaan osusiko arvaus. Mikäli osui, merkitään se vastustajan 
     * karttaan ja palautetaan laiva johon osuttiin.
     * @param vastustajanKartta Vastustajan merikartta, jolla tarkistetaan arvaukset
     * @param vastustajanLaivat Sisältää vastustajan omien laivojen sijainnit
     * @param muistiinPanot     Pelaajan muistiinpanokartta, johon merkataan arvaukset
     * @param rivi              Kierroksella tehdyn arvauksen rivi numero
     * @param sarake            Kierroksen tehdyn arvauksen sarake numero
     * @return                  Palautetaan osutun laivan koordinaatit.
     */
    public static int[] tarkistaOsuma(String[][] vastustajanKartta, int[][] vastustajanLaivat, String[][] muistiinPanot, int rivi, int sarake){
        try{

            if(vastustajanKartta[rivi][sarake].equals(Vakiot.merkit[1])){

                // Jos osui merkitään se vastustajan karttaan.
                System.out.println(Vakiot.ANSI_BOLD + Vakiot.ANSI_CYAN + "\nRÄJÄHDYSS!!!" + Vakiot.ANSI_RESET + " OSUIT LAIVAAN");
                vastustajanKartta[rivi][sarake] = Vakiot.merkit[2];     
                muistiinPanot[rivi][sarake] = Vakiot.merkit[2];     

                int[] osuttuLaiva = new int[4];

                outerLoop: // Haetaan osuttu laiva vastustajan kartasta.
                for(int[] laiva : vastustajanLaivat){

                    for (int i = laiva[0]; i <= laiva[1]; i++) {  
                        for (int j = laiva[2]; j <= laiva[3]; j++) {   
                             
                            // Oikea laiva jos laivan koordinaatit sama pelaajan arvaamien koordinattien kanssa.
                            if(i == rivi && j == sarake){             
                                osuttuLaiva = laiva;
                                break outerLoop;
                            }
                            
                        }
                    }
                }
                return osuttuLaiva;
            }

        } catch (Exception e){
           VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[9]);       
        }
        return new int[0];
    }

    /**
     * Tarkistetaan upposiko laiva.
     * Mikäli upposi: palautetaan true. Jos ei: false
     * @param osuttuLaiva       Osutun laivan koordinaatit
     * @param vastustajanKartta Vastustajan merikartta, jolla tarkistetaan upposiko
     * @return                  Boolean arvo upposiko laiva arvauksella vai ei
     */
    public static boolean upposko(int[] osuttuLaiva, String[][] vastustajanKartta){
        boolean upposko = false;

        outerLoop: // Tarkistetaan upposiko laiva arvauksella.
        for(int i = osuttuLaiva[0]; i <= osuttuLaiva[1]; i++){
            for (int j = osuttuLaiva[2]; j <= osuttuLaiva[3]; j++){

                // Jos osutun laivan jokaisessa osassa on osuma => laiva uppoaa.
                if(vastustajanKartta[i][j].equals(Vakiot.merkit[2])){
                    upposko = true;

                } else { // Jos mitä tahansa muuta: ei uppoa.
                    upposko = false;
                    break outerLoop;
                }    

            }
        }
        return upposko;
    }

    /**
     * Tarkistetaan vastustajan kartan tilanne.
     * Voittaja on selvillä jos kartassa ei yhtään laivaa.
     * @param pelaaja    Vuoron pelaaja
     * @param vastustaja Pelaajan vastustaja
     */
    public static void tarkistaVoitto(String pelaaja, String vastustaja){
        String[][] vastustajanKartta = pelaajienKartat.get(vastustaja);
        boolean voitto = true;

        // Käydään vastustajan koko kartta läpi
        for(String[] rivi : vastustajanKartta){
            for(String sarake : rivi){

                // Mikäli sarakkeessa muu kuin O, ei voittoa.
                if(sarake.equals(Vakiot.merkit[1])){
                    voitto = false;
                    break;
                }

            }
        }
        if(voitto){ // Mikäli voittaja selvillä peli loppuu.
            System.out.println(Vakiot.ANSI_BOLD + "\nPeli loppui.\n" + Vakiot.ANSI_RESET + Vakiot.ANSI_GREEN +  "Voittaja on: " + Vakiot.ANSI_BOLD + pelaaja + Vakiot.ANSI_RESET);
            voittaja = pelaaja;
            peliPaalla = false;
        }
    }

    /**
     * Kysytään haluaako käyttäjä pelin jälkeen pelata uudelleen.
     */
    public static void pelataankoUusiksi(){
        System.out.println(Vakiot.ANSI_BOLD + "\nPelataanko uudelleen vai lopetaanko peli?");
        System.out.println(Vakiot.ANSI_CYAN + "1 = Uudelleen | 2 = Lopeta" + Vakiot.ANSI_RESET);
        System.out.print("=> ");
        int kayttajanSyote = scanner.nextInt();
        switch(kayttajanSyote) {
            case 1:
                alustaPeliMuuttujatUudelleen();
                main(null);
                break;
            case 2:
                System.exit(0);

        }
    }

    /**
     * Mikäli pelaaja haluaa pelata uudelleen => asetetaan globaalit muuttujat defaulteiksi.
     */
    public static void alustaPeliMuuttujatUudelleen(){                                 
        peliMuoto = "";                                                         
        peliPaalla = false;
        pelaajat = new String[2];
        pelaajienKartat = new HashMap<String, String[][]>();
        pelaajienLaivat = new HashMap<String, int[][]>();
        vuoro = 0;
        arvaukset = 0;
        voittaja = "";
        LaivanUpotus.AI.Tietokone.arvaukset = 0;   
        LaivanUpotus.AI.Tietokone.jahtaa = false;                                   
        LaivanUpotus.AI.Tietokone.jahtaamisArvaukset = 0;    
        LaivanUpotus.AI.Tietokone.edellinenArvaus = new int[2];
        LaivanUpotus.AI.Tietokone.jahtaamisOikeaSuunta = 0;
        LaivanUpotus.AI.Tietokone.tehdytArvaukset = new ArrayList<>(); 
        LaivanUpotus.AI.Tietokone.mahdollisetArvaukset = new ArrayList<>();
        LaivanUpotus.AI.Tietokone.osututArvaukset = new ArrayList<>();
        LaivanUpotus.AI.Tietokone.kohdeKoordinaatit = new ArrayList<>();
        LaivanUpotus.AI.Tietokone.upotetutLaivat = new ArrayList<>();

    }
}
