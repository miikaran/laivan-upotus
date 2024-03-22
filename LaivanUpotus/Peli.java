package LaivanUpotus;
import java.util.HashMap;
import java.util.Scanner;

public class Peli {

    /**
        Luodaan pelinkulun kannalta tärkeät muuttujat tänne.
        Vakio muuttujat löytyvät Vakiot- luokasta.
    **/
    public static final Scanner scanner = new Scanner(System.in);
    public static String pelimuoto = "";
    public static boolean peliPaalla = false;
    public static String[] pelaajat = new String[2];
    public static HashMap<String, String[][]> pelaajienKartat = new HashMap<String, String[][]>();
    public static int vuoro = 0;

    /**
        Suoritetaan tarvittavat alkutoimenpiteet ja aloitetaan peli.
     *
    **/
    public static void main(String[] args){
        peliPaalla = true;
        Menu.NaytaMenu();
        luoPelaajat(pelimuoto);
        MeriKartta.luoKartat();
        Laivat.kysyLaivat();
        aloitaTaistelu();
    }

    /**
        Pelaaja aloittavat laivojen upotuksen.
    **/
    public static void aloitaTaistelu(){
        while(peliPaalla){
            String pelaaja = pelaajat[vuoro];
            String vastustaja = pelaajat[(vuoro + 1) % 2];
            vuoro = (vuoro + 1) % 2;
            System.out.println("\n" +pelaaja + ":n" + " vuoro" );
            pelaajaHyokkaa(vastustaja);
            tarkistaTilanne(pelaaja, vastustaja);
            scanner.nextLine();
        }
    }

    /**
        Jos pelataan kaverin kanssa, kysytään pelaajilta alussa heidän nimet.
        Mikäli pelataan tietokonetta vastaan, asetetaan pelaajille valmiit nimet.
    **/
    public static void luoPelaajat(String pelimuoto){
        if(pelimuoto.equals("kaveri")){
            for(int i = 0; i < pelaajat.length; i++){
                while(true){
                    System.out.print("\nPelaaja " + (i+1) + "\nAseta nimesi: \n=>");
                    try{
                        String syote = scanner.next();
                        pelaajat[i] = syote;
                        break;
                    } catch(Exception e){
                        System.out.println("Yritä uudelleen...");
                        continue;
                    }
                }
            }
        } else{
            pelaajat[0] = "Pelaaja";
            pelaajat[1] = "Tietokone";
        }
    }

    /**
     * Pelaaja koittaa arvata vastustaja laivan sijainnin koordinaatilla.
     * Mikäli vastustaja arvaa oikein, merkitään se vastustajan tauluun.
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
                if(vastustajanKartta[rivi][sarake].equals(Vakiot.merkit[1])){
                    
                    // Jos osui merkitään se vastustajan karttaan.
                    System.out.println("\nRäjähdyssss! Osuit Laivaan!");
                    vastustajanKartta[rivi][sarake] = Vakiot.merkit[2];
                    MeriKartta.tulostaKartta(vastustaja);
                }
                break;
            } catch(Exception e){
                System.out.println("Yritä uudelleen...");
            }
        }   
    }

    /**
     * Tarkistetaan vastustajan kartan tilanne.
     * Voittaja on selvillä jos kartassa ei yhtään laivaa.
     */
    public static void tarkistaTilanne(String pelaaja, String vastustaja){
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
        if(voitto){
            System.out.println("\nPeli loppui.\n" + "Voittaja on: " + pelaaja);
            peliPaalla = false;
        }
    }
}
