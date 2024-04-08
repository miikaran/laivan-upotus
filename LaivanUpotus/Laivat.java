package LaivanUpotus;
import java.util.Arrays;
import java.util.Scanner;

public class Laivat {

    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Kysyy laivat pelaajilta ja validoi heidän syötteet.
     * Mikäli validointi menee läpi => asetetaan laivat koordinaatteihin.
    */
    public static void kysyLaivat() {
        // Haetaan jokaiselta pelaajalta kartta ja alustetaan laivakoordinaatit.
        for (String pelaaja : Peli.pelaajat) {
            System.out.println("\n\n");
            int[][] laivaKoordinaatit = new int[Vakiot.laivaMaara][4];
            String[][] kartta = Peli.pelaajienKartat.get(pelaaja);      
            // Haetaan jokaiselle laivalle sen koko ja nimi
            for (int i = 0; i < Vakiot.laivaMaara; i++) {
                String laiva = Vakiot.laivat[i];
                int koko = Vakiot.laivaKoot[i];
                while (true) { // Pyydetään käyttäjiltä koordinaatit laivalle ja asetetaan karttaan.     
                    System.out.println("\n" + pelaaja + "\nAseta oman laivan koordinaatit");
                    System.out.print("Laiva: " + laiva + " -> Koko: " + koko + "\n=> ");
                    // Luodaan oma taulukko koordinaateille.
                    String[] koordinaatit = pyydaKoordinaatteja(kartta, koko, pelaaja);
                    Arrays.sort(koordinaatit);
                    // Tarkistetaan onko koordinaatit sopivia.
                    if (!validoiKoordinaatit(koordinaatit, kartta, koko)) { continue; } 
                    // Asetetaan laiva karttaan pelaajan antamiin koordinaatteihin.
                    laivaKoordinaatit[i] = asetaLaivaKoordinaatteihin(koordinaatit, pelaaja, kartta);
                    break;
                }
            }
            // Asetetaan pelaajan laivojen koordinaatit sille luotuun hashmappiin
            Peli.pelaajienLaivat.put(pelaaja, laivaKoordinaatit);
        }
    }

    /**
     * kysytään laivan koordinaatit muodossa => sarake+rivi ja erotellaan ne.
     */
    public static String[] pyydaKoordinaatteja(String[][] kartta, int laivanKoko, String pelaaja) {
        // Jos pelimuoto on tietokone vs pelaaja: generoidaan tietokoneelle omat laivat.
        if(pelaaja.equals("Tietokone") && Peli.peliMuoto.equals("tietokone")){
            return LaivanUpotus.AI.TietokoneenLaivat.generoiTietokoneLaivat(kartta, laivanKoko);
        } // Muuten palautetaan käyttäjän input.
        return scanner.nextLine().toUpperCase().split(" ");
    }

    /**
     * Tarkastetaan pelaajan antamat koordinaatit ja ilmoitetaan
     * mahdollisista virheistä tai säännön vastaisista arvoista.
     */
    private static boolean validoiKoordinaatit(String[] koordinaatit, String[][] taulu, int koko) {
        try{
            // Haetaan koordinaatit joiden välille laiva rakennetaan.
            int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
            int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
            int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
            int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));
            //Tarkistetaan onko koordinaattien lähellä jo laivaa.   
            for (int j = aloitusRivi; j <= lopetusRivi; j++) {
                for (int k = aloitusSarake; k <= lopetusSarake; k++) {
                    try { // Jos lähellä jo laiva: palautetaan false.
                        if (taulu[j][k].equals("O")                                 ||  // Päällä
                           (k > 0 && taulu[j][k - 1].equals("O"))                   ||  // Vasemmalla
                           (k < taulu[0].length - 1 && taulu[j][k + 1].equals("O")) ||  // Oikealla
                           (j > 0 && taulu[j - 1][k].equals("O"))                   ||  // Ylhäällä
                           (j < taulu.length - 1 && taulu[j + 1][k].equals("O")))       // Alhaalla
                        {
                            System.out.println("\nEt voi laittaa tähän kohtaan laivaa.");
                            return false;
                        }              
                    } catch (IndexOutOfBoundsException e) { // Käsitellään mahdolliset indeksi probleemat.
                        System.out.println("Koordinaatit ovat rajojen ulkopuolella.");
                        return false;
                    }
                }
            }
            // Muita oleellisia koordinaatti validointeja.
            // Vaara määrä koordinaatteja.
            if (koordinaatit.length != 2) {
                System.out.println("\nSyötä 2 koordinaattia!");
                return false;
            } 
            // Laiva yritetään asettaa viistoon.
            else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) {
                System.out.println("\nEt voi asettaa laivoja viistoon.");
                return false;
            }  
            // Laivan koko liian suuri.
            else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                System.out.println("\nLaivan koko on liian suuri...");
                System.out.println("Laivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
                return false;
            } 
            //Laivan koko liian pieni
            else if(lopetusRivi - aloitusRivi + 1 < koko && lopetusSarake - aloitusSarake + 1 < koko){
                System.out.println("\nLaivan koko on liian pieni...");
                System.out.println("Laivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
                return false;
            }
            return true;

        } catch (Exception e) { // Käsitellään myös muut mahdolliset poikkeukset.
            System.out.println("\nVirheellinen koordinaatti. Yritä uudelleen.");
            return false;
        }
    }

    /**
     *  Rakennetaan uusi laiva kartalle annettujen koordinattien mukaan.
     */
    private static int[] asetaLaivaKoordinaatteihin(String[] koordinaatit, String pelaaja, String[][] taulu) {
        // Haetaan koordinaatit joiden välille laiva rakennetaan.
        int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
        int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
        int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
        int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));
        // Käydään kartta läpi lasketuista koordinaateista.
        for (int i = aloitusRivi; i <= lopetusRivi; i++) {
            for (int j = aloitusSarake; j <= lopetusSarake; j++) {
                // Ja laitetaan laiva jokaiseen koordinaattiin
                taulu[i][j] = Vakiot.merkit[1];
            }
        }
        int[] laivaKoordinaatit = {aloitusRivi, lopetusRivi, aloitusSarake, lopetusSarake};
        return laivaKoordinaatit;

    }
}
