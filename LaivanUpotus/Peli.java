package LaivanUpotus;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.*;

public class Peli {

    /**
     * Luodaan pelinkulun kannalta tärkeät muuttujat tänne.
     * Vakio muuttujat löytyvät Vakiot- luokasta.
    */
    public static final Scanner scanner = new Scanner(System.in);
    public static String pelimuoto = "";
    public static boolean peliPaalla = false;
    public static String[] pelaajat = new String[2];
    public static HashMap<String, String[][]> pelaajienKartat = new HashMap<String, String[][]>();
    public static HashMap<String, int[][]> pelaajienLaivat = new HashMap<String, int[][]>();
    public static int vuoro = 0;

    /**
     * Suoritetaan tarvittavat alkutoimenpiteet ja aloitetaan peli.
    */
    public static void main(String[] args){
        peliPaalla = true;
        Menu.NaytaMenu();
        Menu.luoPelaajat(pelimuoto);
        MeriKartta.luoKartat();
        Laivat.kysyLaivat();
        aloitaTaistelu();
    }

    /**
     * Pelaaja aloittavat laivojen upotuksen.
    */
    public static void aloitaTaistelu(){
        System.out.println("\n\n\n\nTaistelu alkaa!");

        while(peliPaalla){
            
            String pelaaja = pelaajat[vuoro];                                                       
            String vastustaja = pelaajat[(vuoro + 1) % 2];
            System.out.println("\n" +pelaaja + ":n arvaus" + " vuoro" );

            // Jokaisella vuorolla suoritettavat metodit.
            pelaajaHyokkaa(vastustaja);
            tarkistaVoitto(pelaaja, vastustaja);
            scanner.nextLine();
            vuoro = (vuoro + 1) % 2; // Vaihdetaan vuoroa
        }
    }

    /**
     * Pelaaja arvaa vastustajan laivan sijainteja koordinaateilla.
     * Mikäli arvaus on oikein, merkitään se vastustajan tauluun ja
     * ilmoitetaan pelaajalle osuiko/upposiko.
     */
    public static void pelaajaHyokkaa(String vastustaja){
        try{
            while(true){
                    // Otetaan käyttäjälta koordinaatti.
                    String koordinaatti = scanner.next();

                    // Muutetaan koordinaatit kartan riveiksi ja sarakkeiksi.
                    int rivi = Integer.parseInt(koordinaatti.substring(1));
                    int sarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatti.charAt(0));
                    
                    // Haetaan vastustajan kartta ja laivat.
                    String[][] vastustajanKartta = pelaajienKartat.get(vastustaja);
                    int[][] vastaustajanLaivat = pelaajienLaivat.get(vastustaja);

                    //Tarkistetaan osusiko arvaus.
                    if(vastustajanKartta[rivi][sarake].equals(Vakiot.merkit[1])){   

                        // Jos osui merkitään se vastustajan karttaan.
                        System.out.println("\nRäjähdyssss! Osuit Laivaan!");
                        vastustajanKartta[rivi][sarake] = Vakiot.merkit[2];
                        int[] osuttuLaiva = new int[4];
        
                        outerLoop: // Haetaan osuttu laiva vastustajan kartasta.
                        for(int[] laiva : vastaustajanLaivat){
                            // Oikea laiva jos laivan koordinaatit sama pelaajan arvaamien koordinattien kanssa.
                            for (int i = laiva[0]; i <= laiva[1]; i++) {
                                for (int j = laiva[2]; j <= laiva[3]; j++) {
                                    if(i == rivi && j == sarake){
                                        osuttuLaiva = laiva;
                                        break outerLoop;
                                    }
                                }
                            }
                        }
                        /*System.out.println(Arrays.deepToString(vastaustajanLaivat));
                        System.out.println(rivi);
                        System.out.println(sarake);
                        System.out.println(Arrays.toString(osuttuLaiva));*/
                        boolean upposko = false;
                        outerLoop: // Tarkistetaan upposiko laiva arvauksella.
                        for(int i = osuttuLaiva[0]; i <= osuttuLaiva[1]; i++){
                            for (int j = osuttuLaiva[2]; j <= osuttuLaiva[3]; j++){
                                // Jos osutun laivan jokaisessa osassa on osuma: laiva uppoaa.
                                if(vastustajanKartta[i][j].equals(Vakiot.merkit[2])){
                                    upposko = true;
                                } else{ // Jos mitä tahansa muuta: ei uppoa.
                                    upposko = false;
                                    break outerLoop;
                                }
                            }
                        }
                        //MeriKartta.tulostaKartta(vastustaja);
                        if(upposko){
                            System.out.println("Uppos!!!!!!!!!! BOOOOOOOOOOOM.");
                        }
                    }
                    else{
                        System.out.println("\nOhi");
                    }
                    break;
            }
        } catch (Exception e){
            System.out.println("Yritä uudelleen...");
        }   
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
            System.out.println("\nPeli loppui.\n" + "Voittaja on: " + pelaaja);
            peliPaalla = false;
        }
    }
}
