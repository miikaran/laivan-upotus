package LaivanUpotus;

import java.util.Arrays;

public class MeriKartta {    
    /**
     * Luodaan kullekkin pelaajalle oma kartta ja asetetaan
     * kyseinen kartta hashmappiin pelaajan nimi avaimen alle.
    */
    public static void luoKartat(){
        for(String pelaaja : Peli.pelaajat){
            String[][] kartta = new String[Vakiot.rivit][Vakiot.sarakkeet];
            for(int i = 0; i < Vakiot.rivit; i++){
                for(int j = 0; j < Vakiot.sarakkeet; j++){ 
                    kartta[i][j] = Vakiot.merkit[0]; 
                }
            }
            luoMuistiinpanot(pelaaja);
            Peli.pelaajienKartat.put(pelaaja, kartta);
        }
    }


    /**
     * Luodaan pelaajille myös kartat josta näkyy heidän tekemät 
     * arvaukset kartalla, ja siihen asti ilmenneet osumat ja hudit.
    */
    public static void luoMuistiinpanot(String pelaaja){
        String[][] kartta = new String[Vakiot.rivit][Vakiot.sarakkeet];
        for(int i = 0; i < Vakiot.rivit; i++){
            for(int j = 0; j < Vakiot.sarakkeet; j++){ 
                kartta[i][j] = Vakiot.merkit[0]; 
            }
        }
        String muistiinpano = pelaaja + "-muistiinpano";
        Peli.pelaajienKartat.put(muistiinpano, kartta);
    }

    /**
     * Tulostaa pelaajan koko kartan hienosti consoleen.
     * Lisää tulokseen myös osoittajia kartan riveistä ja sarakkeista.
    */
    public static void tulostaKartta(String pelaaja) {
        String[][] kartta = Peli.pelaajienKartat.get(pelaaja);
        int korkeus = kartta.length;
        int leveys = kartta[0].length;

        System.out.print("\n\n   ");

        for (int j = 0; j < leveys; j++) {
            System.out.print(" " + (char)('A' + j) + "  ");
        }

        System.out.println();
        System.out.print("  ┌");
        tulostaKartanKulma(leveys);
        System.out.println("┐");
    
        // Tulostetaan rivit
        for (int i = 0; i < korkeus; i++) {
            System.out.print(" " + (i) + "│");
            tulostaRivi(kartta[i]);
            if (i < korkeus - 1) {
                System.out.println("│");
                System.out.print("  ├");
                tulostaKartanKulma(leveys);
                System.out.println("┤");
            }
        }

        System.out.println("│");
        System.out.print("  └");
        tulostaKartanKulma(leveys);
        System.out.println("┘");

        System.out.println(Arrays.deepToString(Peli.pelaajienKartat.get(pelaaja+"-muistiinpano")));
    }
    
    /**
     * Tulostaa yksittäisen rivin kartasta
    */
    private static void tulostaRivi(String[] rivi) {
        for (int j = 0; j < rivi.length; j++) {
            System.out.print(" " + rivi[j] + " ");

            if (j < rivi.length - 1) {
                System.out.print("│");
            }
        }
    }
    
    /**
     * Tulostaa kartan kulmat.
    */
    private static void tulostaKartanKulma(int korkeus) {
        for (int i = 0; i < korkeus; i++) {
            System.out.print("───");

            if (i < korkeus - 1) {
                System.out.print("┬");
            }
        }
    }
}
