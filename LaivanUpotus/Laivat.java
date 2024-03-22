package LaivanUpotus;
import java.util.Arrays;
import java.util.Scanner;

public class Laivat {
    public static final Scanner scanner = new Scanner(System.in);

    /**
        Kysyy laivat pelaajilta ja validoi pelaajien antamat
        syötteet taulukossa valmiiksi olevien laivojen mukaan sekä 
        tarkistaa, että laivat lisätään taulukkoon sääntöjen mukaisesti.
    **/
    public static void kysyLaivat() {
        for (String pelaaja : Peli.pelaajat) {
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

                    System.out.println(Arrays.toString(koordinaatit));

                    // Tarkistetaan onko koordinaatit sopivia.
                    if (!validoiKoordinaatit(koordinaatit, taulu, koko)) {
                        continue;
                    }

                    // Asetetaan laiva karttaan pelaajan antamiin koordinaatteihin.
                    asetaLaivaKoordinaatteihin(koordinaatit, pelaaja, taulu);
                    break;
                }
            }
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
     * mahdollisista virheistä tai epäsäännönmukaisuuksista niissä.
     */
    private static boolean validoiKoordinaatit(String[] koordinaatit, String[][] taulu, int koko) {
        try {
            //Lasketaan koordinaatit jonka mukaan laiva rakennetaan kartalle.
            int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
            int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
            int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
            int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));

            //Tarkistetaan onko koordinaattien lähellä jo laivaa. 
            for (int j = aloitusRivi; j < lopetusRivi; j++) {
                for (int k = aloitusSarake; k < lopetusSarake; k++) {
                    if (taulu[j][k].equals("O")   ||    // Päällä
                        taulu[j][k-1].equals("O") ||    // Vasemmalla
                        taulu[j][k+1].equals("O") ||    // Oikealla
                        taulu[j-1][k].equals("O") ||    // Ylhäällä
                        taulu[j+1][k].equals("O")) {    // Alhaalla
                        
                        System.out.println("\nEt voi laittaa tähän kohtaan laivaa.");
                        return false;
                    }
                }
            }

            /**
                Alla muita olennaisia laivojen asettamiseen liityviä validointeja.
                * Koordinaattien määrä
                * Viistoossa olevat laivat
                * Laivan koko.
            **/
            if (koordinaatit.length != 2) {
                System.out.println("\nSyötä 2 koordinaattia!");
                return false;
            } else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) {
                System.out.println("\nEt voi asettaa laivoja viistoon.");
                return false;
            } else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                System.out.println("\nLaivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
                return false;
            }
            return true;
        
        // Käsitellään myös muut mahdolliset poikkeustilanteet.
        } catch (NumberFormatException e) {
            System.out.println("\nVirheellinen koordinaatti. Yritä uudelleen.");
            return false;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\nKoordinaatit ylittävät taulukon rajat. Yritä uudelleen.");
            return false;
        }

    }

    /**
        Asettaa uuden laivan pelaajan taulukkoon.
    **/
    private static void asetaLaivaKoordinaatteihin(String[] koordinaatit, String pelaaja, String[][] taulu) {
        //Lasketaan koordinaatit jonka mukaan laiva rakennetaan kartalle.
        int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
        int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
        int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
        int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));

        for (int i = aloitusRivi; i <= lopetusRivi; i++) {
            for (int j = aloitusSarake; j <= lopetusSarake; j++) {
                taulu[i][j] = Vakiot.merkit[1];
            }
        }
    }
}
