import java.util.Arrays;
import java.util.Scanner;

public class laivanupotus {

    /**
     * Luodaan pelin kannalta kriittiset kiinteät muuttujat.
     */
    static final Scanner scanner = new Scanner(System.in);
    static String sarakeKirjaimet = "ABCDEFGHIJ";
    static int rivit = 10;
    static int sarakkeet = 10;
    static String [][] taulu = new String[rivit][sarakkeet];
    static String[] laivat = {"Sukellusvene", "Hävittäjä", "Risteilijä", "Taistelulaiva", "Lentotukialus"};
    static int[] laivaKoot = {1,2,3,4,5};


    /**
     * Tästä metodista aloitetaan peli.
     */
    public static void main(String[] args){
        taulujenLuonti();
        tulostaTaulu();
        kysyJaValidoiLaivat();
    }

    /**
     * Palauttaa pelaajien taulukot.
     */
    public static String taulujenLuonti(){
        for(int i = 0; i < rivit; i++){
            for(int j = 0; j < sarakkeet; j++){ 
                taulu[i][j] = "~"; 
            }
        } return Arrays.deepToString(taulu);
    }

    /**
     * Tulostaa pelaajan taulun.
     * Lisää tulokseen myös osoittajia taulun riveistä ja sarakkeista.
     */
    public static void tulostaTaulu(){
        System.out.print("  ");
        for(char kirjain : sarakeKirjaimet.toCharArray()){
            System.out.print(kirjain + " ");
        }
        System.out.println();

        for(int i = 0; i < taulu.length; i++){
            System.out.print((i) + " ");
            for(String alkio : taulu[i]){
                System.out.print(alkio + " ");
            }
            System.out.println();
        }
    }

    /**
     * Asettaa uuden laivan pelaajan taulukolle sille annettujen
     * aloitus ja lopetus parametrien mukaan ja tulostaa sen lopuksi.
     */
    public static void asetaLaiva(int aloitusRivi, int aloitusSarake, int lopetusRivi, int lopetusSarake){
        for(int i = aloitusRivi; i <= lopetusRivi; i++){
            for(int j = aloitusSarake; j <= lopetusSarake; j++){
                taulu[i][j] = "X";
            }
        }
        tulostaTaulu();
    }

    /**
     * Kysyy laivat pelaajilta ja validoi pelaajien antamat
     * syötteet taulukossa valmiiksi olevien laivojen mukaan sekä 
     * tarkistaa, että laivat lisätään taulukkoon sääntöjen mukaisesti.
     */
    public static void kysyJaValidoiLaivat(){
        int laivaMaara = laivat.length;
        for(int i = 0; i < laivaMaara; i++){
            String laiva = laivat[i];
            int koko = laivaKoot[i];

            while (true) {
                System.out.println("\nLaiva: " + laiva + " -> Koko: " + koko);
                System.out.print("Aseta oman laivan koordinaatit\n=> ");

                // Otetaan käyttäjän koordinaatit ja erotellaan ne.
                String[] koordinaatit = scanner.nextLine().split(" ");
                
                // Rivi ja sarake, joista laivaa lähdetään rakentamaan.
                int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
                int aloitusSarake = sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));
                
                // Rivi ja sarake, johon laivan rakennus lopetetaan.
                int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
                int lopetusSarake = sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));

                boolean laivaaVoiSijoittaa = true;

                //Tarkistetaan onko koordinaattien lähellä jo laiva. 
                for(int j = aloitusRivi; j <= lopetusRivi; j++){
                    for(int k = aloitusSarake; k <= lopetusSarake; k++){
                        if(!taulu[j][k].equals("~")   ||  // Päällä
                           !taulu[j][k-1].equals("~") ||  // Vasemmalla
                           !taulu[j][k+1].equals("~") ||  // Oikealla
                           !taulu[j-1][k].equals("~") ||  // Ylhäällä
                           !taulu[j+1][k].equals("~")){   // Alhaalla
                            laivaaVoiSijoittaa = false;
                        }
                    }
                }
            
                /**
                 *  Alla muita olennaisia laivojen asettamiseen liityviä validointeja.
                    * Koordinaattien määrä
                    * Viistoossa olevat laivat
                    * Laivan koko.
                */
                if (koordinaatit.length != 2) {
                    System.out.println("Syötä 2 koordinaatiia!");
                } else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) { // 
                    System.out.println("Et voi asettaa laivoja viistoon.");
                } else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                    System.out.println("Laivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
                } else if(!laivaaVoiSijoittaa){
                    System.out.println("Et voi laittaa tähän kohtaan laivaa.");
                }
                else{ /* Mikäli kaikki suoritetut validaatiot menee läpi, 
                         asetetaan laiva haluttuun positioon. */
                    asetaLaiva(aloitusRivi, aloitusSarake, lopetusRivi, lopetusSarake);
                    break;
                }
            }
            
        }
        scanner.close();
    }
}
