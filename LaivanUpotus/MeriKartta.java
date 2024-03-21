package LaivanUpotus;

public class MeriKartta {
    /**
        * Luodaan kullekkin pelaajalle oma taulu ja asetetaan
        * kyseinen taulu hashmappiin pelaajan nimi avaimen alle.
    **/
    public static void luoTaulut(){
        for(String pelaaja : Peli.pelaajat){
            String[][] taulu = new String[Vakiot.rivit][Vakiot.sarakkeet];
            for(int i = 0; i < Vakiot.rivit; i++){
                for(int j = 0; j < Vakiot.sarakkeet; j++){ 
                    taulu[i][j] = Vakiot.merkit[0]; 
                }
            }
            Peli.pelaajienKartat.put(pelaaja, taulu);
        }
    }

    /**
        * Tulostaa pelaajan taulun koko taulun.
        * Lisää tulokseen myös osoittajia taulun riveistä ja sarakkeista.
    **/
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
    }
    
    /**
        * Tulostaa yksittäisen rivin kartasta
    **/
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
    **/
    private static void tulostaKartanKulma(int korkeus) {
        for (int i = 0; i < korkeus; i++) {
            System.out.print("───");

            if (i < korkeus - 1) {
                System.out.print("┬");
            }
        }
    }
}