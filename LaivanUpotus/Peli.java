package LaivanUpotus;
import java.util.HashMap;
import java.util.Scanner;

public class Peli {

    /**
     * Luodaan pelinkulun kannalta tärkeät muuttujat tänne.
     * Vakio muuttujat löytyvät Vakiot- luokasta.
     */
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
     * Suoritetaan tarvittavat alkutoimenpiteet ja aloitetaan peli.
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

            // Jos tällä kierroksi peli loppu => tallennetaan pelin tiedot 
            // tiedostoon ja tulostetaan ne näkyviin komentoriville.
            if(!peliPaalla){

                TulosTallennus.tallennaTulos(pelaajat, arvaukset, voittaja);
                TulosTallennus.naytaTulokset();
                
            }
        }       
    }

    /**
     * Pelaaja arvaa vastustajan laivan sijainteja koordinaateilla.
     * Mikäli arvaus on oikein, merkitään se vastustajan tauluun ja
     * ilmoitetaan pelaajalle osuiko/upposiko.
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

                } else {

                    int[] koordinaatit = LaivanUpotus.AI.Tietokone.laskeParasArvaus(pelaajienKartat.get(pelaaja+"-muistiinpano"), pelaaja, vastustaja);
                    rivi = koordinaatit[0];
                    sarake = koordinaatit[1];

                }

            } catch(Exception e){
                
                if(!pelaaja.equals("Tietokone")){
                    System.out.println("Ongelma koordinaatin syötteessä..");
                }
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

                System.out.println(Vakiot.ANSI_BOLD + Vakiot.ANSI_RED + "\nHUTI" + Vakiot.ANSI_RESET);

                try{

                    if(!pelaajanMuistiinpanot[rivi][sarake].equals(Vakiot.merkit[3]) && !pelaajanMuistiinpanot[rivi][sarake].equals(Vakiot.merkit[2]) ){
                        pelaajanMuistiinpanot[rivi][sarake] = Vakiot.merkit[3];
                    }

                } catch (ArrayIndexOutOfBoundsException e){
                    
                    if(!pelaaja.equals("Tietokone")){
                        System.out.println("Et voi muokata yli rajojen meneviä alkioita...");
                    }

                }
            }

            break;  
        }  
    }

    /**
     * Tarkistetaan osusiko arvaus. Mikäli osui, merkitään se vastustajan 
     * karttaan ja palautetaan laiva johon osuttiin.
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

            if(!pelaajat[vuoro].equals("Tietokone")){
                System.out.println("\nJotain meni pieleen...");
            }
            
        }
        return new int[0];
    }

    /**
     * Tarkistetaan upposiko laiva.
     * Mikäli upposi: palautetaan true. Jos ei: false
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
}
