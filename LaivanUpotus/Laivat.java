package LaivanUpotus;
import java.util.Arrays;
import java.util.Scanner;

public class Laivat {

    public static final Scanner scanner = new Scanner(System.in);
    public static boolean tietokoneGeneroiLaivoja = false;

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

                if(!pelaaja.equals("Tietokone")){
                    MeriKartta.tulostaKartta(pelaaja, "kartta");
                }
    
                String laiva = Vakiot.laivat[i];
                int koko = Vakiot.laivaKoot[i];

                while (true) { // Pyydetään käyttäjiltä koordinaatit laivalle ja asetetaan karttaan.

                    if(!pelaaja.equals("Tietokone")){
                        System.out.println("\n" + Vakiot.ANSI_BOLD + pelaaja + Vakiot.ANSI_RESET + Vakiot.ANSI_CYAN + "\nAseta oman laivan koordinaatit" + Vakiot.ANSI_RESET);
                        System.out.print(Vakiot.ANSI_BOLD + "Laiva: " + Vakiot.ANSI_RESET + laiva + Vakiot.ANSI_BOLD + " -> Koko: " + Vakiot.ANSI_RESET + koko + "\n=> ");
                    }
             
                    // Luodaan oma taulukko koordinaateille.
                    String[] koordinaatit = pyydaKoordinaatteja(kartta, koko, pelaaja);
                    Arrays.sort(koordinaatit);

                    // Tarkistetaan onko koordinaatit sopivia.
                    if (!validoiKoordinaatit(koordinaatit, kartta, koko, pelaaja)) { continue; } 
                    
                    // Asetetaan laiva karttaan pelaajan antamiin koordinaatteihin.
                    laivaKoordinaatit[i] = asetaLaivaKoordinaatteihin(koordinaatit, pelaaja, kartta);

                    if(pelaaja.equals("Tietokone")){
                        System.out.println(Vakiot.ANSI_BOLD + pelaaja + " asetti laivan: " + Vakiot.ANSI_PURPLE + laiva + Vakiot.ANSI_RESET);
                    }

                    break;
                }
            }
            tietokoneGeneroiLaivoja = false;
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
            tietokoneGeneroiLaivoja = true;
            return LaivanUpotus.AI.TietokoneenLaivat.generoiTietokoneLaivat(kartta, laivanKoko);
        } // Muuten palautetaan käyttäjän input.
        return scanner.nextLine().toUpperCase().split(" ");
    }

    /**
     * Tarkastetaan pelaajan antamat koordinaatit ja ilmoitetaan
     * mahdollisista virheistä tai säännön vastaisista arvoista.
     */
    private static boolean validoiKoordinaatit(String[] koordinaatit, String[][] taulu, int koko, String pelaaja) {
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
                            VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[5]);
                            return false;
                        }   

                    } catch (IndexOutOfBoundsException e) { // Käsitellään mahdolliset indeksi probleemat.
                        VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[6]);
                        return false;           
                    }
                }
            }
            // Muita oleellisia koordinaatti validointeja.
            // Vaara määrä koordinaatteja.
            if (koordinaatit.length != 2) {
                VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[0]);
                return false;
            } 
            // Laiva yritetään asettaa viistoon.
            else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) {
                VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[1]);
                return false;
            }  
            // Laivan koko liian suuri.
            else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[2]);
                return false;
            } 
            //Laivan koko liian pieni
            else if(lopetusRivi - aloitusRivi + 1 < koko && lopetusSarake - aloitusSarake + 1 < koko){
                VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[3]);
                return false;
            }
            return true;

        } catch (Exception e) { // Käsitellään myös muut mahdolliset poikkeukset.
            VirheIlmoitukset.naytaIlmoitus(Vakiot.virheIlmoitukset[4]);
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
