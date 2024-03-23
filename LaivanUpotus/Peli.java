package LaivanUpotus;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Peli {

    /**
        Luodaan pelinkulun kannalta tärkeät muuttujat tänne.
        Vakio muuttujat löytyvät Vakiot- luokasta.
    */
    public static final Scanner scanner = new Scanner(System.in);
    public static String pelimuoto = "";
    public static boolean peliPaalla = false;
    public static String[] pelaajat = new String[2];
    public static HashMap<String, String[][]> pelaajienKartat = new HashMap<String, String[][]>();
    public static HashMap<String, int[][]> pelaajienLaivat = new HashMap<String, int[][]>();
    public static int vuoro = 0;

    /**
        Suoritetaan tarvittavat alkutoimenpiteet ja aloitetaan peli.
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
        Pelaaja aloittavat laivojen upotuksen.
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
            System.out.println(Arrays.deepToString(pelaajienLaivat.get(pelaaja)));
            scanner.nextLine();
            vuoro = (vuoro + 1) % 2; // Vaihdetaan vuoroa
        }
    }

    /**
       Pelaaja koittaa arvata vastustaja laivan sijainnin koordinaatilla.
       Mikäli vastustaja arvaa oikein, merkitään se vastustajan tauluun.
     */
    public static void pelaajaHyokkaa(String vastustaja){
        while(true){
            try{ 
                // Otetaan käyttäjälta koordinaatti.
                String koordinaatti = scanner.next();

                // Muutetaan koordinaatit kartan riveiksi ja sarakkeiksi.
                int rivi = Integer.parseInt(koordinaatti.substring(1));
                int sarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatti.charAt(0));
                
                // Haetaan vastustajan kartta ja tarkistetaan osuko arvaus.
                String[][] vastustajanKartta = pelaajienKartat.get(vastustaja);
                int[][] vastaustajanLaivat = pelaajienLaivat.get(vastustaja);
                if(vastustajanKartta[rivi][sarake].equals(Vakiot.merkit[1])){           
                    // Jos osui merkitään se vastustajan karttaan.
                    System.out.println("\nRäjähdyssss! Osuit Laivaan!");
                    vastustajanKartta[rivi][sarake] = Vakiot.merkit[2];
                    int[] osuttuLaiva = new int[4];
                    for(int[] laiva : vastaustajanLaivat){
                        System.out.println(rivi);
                        System.out.println(sarake);
                        System.out.println(Arrays.toString(laiva));
                        if(laiva[0] == rivi && laiva[3] == sarake){
                            osuttuLaiva = laiva;
                        }
                    }
                    boolean upposko = false;
                    //System.out.println(Arrays.toString(osuttuLaiva));
                    for(int i = osuttuLaiva[0]; i <= osuttuLaiva[1]; i++){
                        for (int j = osuttuLaiva[2]; j <= osuttuLaiva[3]; j++){
                            if(vastustajanKartta[i][j].equals(Vakiot.merkit[2])){
                                upposko = true;
                            } else{
                                upposko = false;
                                break;
                            }
                        }
                    }
                    
                    if(upposko){
                        System.out.println("Uppos!!!!!!!!!! BOOOOOOOOOOOM.");
                    }
                }
                else{
                    System.out.println("\nOhi");
                }
                break;
                
            } catch(Exception e){ // Käsitellään mahdolliset poikkeukset
                System.out.println("Yritä uudelleen...");
            }
        }   
    }

    /**
       Tarkistetaan vastustajan kartan tilanne.
       Voittaja on selvillä jos kartassa ei yhtään laivaa.
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
