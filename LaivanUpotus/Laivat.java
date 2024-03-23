package LaivanUpotus;
import java.util.Arrays;
import java.util.Scanner;

public class Laivat {
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Kysyy laivat pelaajilta ja validoi pelaajien antamat
     * syötteet taulukossa valmiiksi olevien laivojen mukaan sekä 
     * tarkistaa, että laivat lisätään taulukkoon sääntöjen mukaisesti.
    */
    public static void kysyLaivat() {

        for (String pelaaja : Peli.pelaajat) {
            int[][] laivaKoordinaatit = new int[Vakiot.laivaMaara][4];
            String[][] taulu = Peli.pelaajienKartat.get(pelaaja);
        
            for (int i = 0; i < Vakiot.laivaMaara; i++) {
                String laiva = Vakiot.laivat[i];
                int koko = Vakiot.laivaKoot[i];
                MeriKartta.tulostaKartta(pelaaja);

                while (true) {
                    System.out.println("\n" + pelaaja);
                    System.out.println("\nAseta oman laivan koordinaatit");
                    System.out.print("Laiva: " + laiva + " -> Koko: " + koko + "\n=> ");

                    // Luodaan oma taulukko koordinaateille.
                    String[] koordinaatit = pyydaKoordinaatteja();
                    Arrays.sort(koordinaatit);

                    // Tarkistetaan onko koordinaatit sopivia.
                    if (!validoiKoordinaatit(koordinaatit, taulu, koko)) {
                        continue;
                    } 
                    // Asetetaan laiva karttaan pelaajan antamiin koordinaatteihin.
                    laivaKoordinaatit[i] = asetaLaivaKoordinaatteihin(koordinaatit, pelaaja, taulu);
                    break;
                }
            }
            Peli.pelaajienLaivat.put(pelaaja, laivaKoordinaatit);
        }
    }

    /**
     * kysytään laivan koordinaatit muodossa => sarake+rivi ja erotellaan ne.
     */
    public static String[] pyydaKoordinaatteja() {
        return scanner.nextLine().toUpperCase().split(" ");
    }

    /**
     * Tarkastetaan pelaajan antamat koordinaatit ja ilmoitetaan
     * mahdollisista virheistä tai säännön vastaisista arvoista.
     */
    private static boolean validoiKoordinaatit(String[] koordinaatit, String[][] taulu, int koko) {
        try{
            //Lasketaan koordinaatit jonka mukaan laiva rakennetaan kartalle.
            int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
            int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
            int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
            int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));
            
            //Tarkistetaan onko koordinaattien lähellä jo laivaa.   
            for (int j = aloitusRivi; j <= lopetusRivi; j++) {
                for (int k = aloitusSarake; k <= lopetusSarake; k++) {

                    try {

                        if (taulu[j][k].equals("O")                                 ||  // Päällä
                           (k > 0 && taulu[j][k - 1].equals("O"))                   ||  // Vasemmalla
                           (k < taulu[0].length - 1 && taulu[j][k + 1].equals("O")) ||  // Oikealla
                           (j > 0 && taulu[j - 1][k].equals("O"))                   ||  // Ylhäällä
                           (j < taulu.length - 1 && taulu[j + 1][k].equals("O")))       // Alhaalla
                        {
                            System.out.println("\nEt voi laittaa tähän kohtaan laivaa.");
                            return false;
                        }

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Koordinaatit ovat rajojen ulkopuolella.");
                    }
                }
            }

            /**
                Alla muita olennaisia laivojen asettamiseen liityviä validointeja.
                - Koordinaattien määrä
                - Viistoossa olevat laivat
                - Laivan koko.
            */
            if (koordinaatit.length != 2) {
                System.out.println("\nSyötä 2 koordinaattia!");
                return false;
            } 
            else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) {
                System.out.println("\nEt voi asettaa laivoja viistoon.");
                return false;
            } 
            else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                System.out.println("\nLaivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
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

        //Lasketaan koordinaatit joiden mukaan laiva rakennetaan kartalle.
        int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
        int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
        int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
        int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));

        // Käydään kartta läpi lasketuista koordinaateista.
        for (int i = aloitusRivi; i <= lopetusRivi; i++) {
            for (int j = aloitusSarake; j <= lopetusSarake; j++) {
                // Ja laitetaan laiva jokaiseen sarakkeeseen.
                taulu[i][j] = Vakiot.merkit[1];
            }
        }

        int[] laivaKoordinaatit = {aloitusRivi, lopetusRivi, aloitusSarake, lopetusSarake};
        System.out.println(Arrays.toString(laivaKoordinaatit));
        return laivaKoordinaatit;

    }
}
