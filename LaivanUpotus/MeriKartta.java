package LaivanUpotus;

/**
 * Tämä luokka sisältää pelaajien merikarttojen luonti ja tulostus metodeja.
 * @author Miika Rantalaiho
 */
public class MeriKartta {  

    /**
     * Luodaan kullekkin pelaajalle oma kartta johon laivat asetetaan.
     * Luodaan myös muistiinpanokartat jokaiselle pelaajalle, johon
     * pelaajan arvaamat osumat ja hudit näkyviin.
     */
    public static void luoKartat(){
        for(String pelaaja : Peli.pelaajat){
            String[][] kartta = new String[Vakiot.rivit][Vakiot.sarakkeet];

            for(int i = 0; i < Vakiot.rivit; i++){
                for(int j = 0; j < Vakiot.sarakkeet; j++){ 
                    kartta[i][j] = Vakiot.merkit[0]; 
                }
            }
            
            Peli.pelaajienKartat.put(pelaaja, kartta);    
            /**
             * Kopioidaan kokonaan uusi array muistiinpanoille, koska jos asetetaan muuttuja kartta
             * suoraan pelaajien muistiinpano kartaksi, niin muutokset joita pelaajan oikeaan karttaan
             * tehdään muokkaantuu myös muistiinpano karttaan ja sama toistepäin.
            */
            String[][] muistiinpanoKartta = new String[Vakiot.rivit][Vakiot.sarakkeet];
            for(int i = 0; i < Vakiot.rivit; i++) {
                muistiinpanoKartta[i] = kartta[i].clone();
            }
            Peli.pelaajienKartat.put(pelaaja + "-muistiinpano", muistiinpanoKartta);
        }
    }

    /**
     * Tulostaa pelaajan koko kartan hienosti consoleen.
     * Lisää tulokseen myös osoittajia kartan riveistä ja sarakkeista.
     * @param pelaaja Määrittää kumman pelaajan kartta tulostetaan
     * @param tyyppi  Kertoo mikä kartta halutaan tulostaa (peruskartta, muistiinpanot, jne)
     */
    public static void tulostaKartta(String pelaaja, String tyyppi) {
        String[][] kartta;
        if(tyyppi.equals("muistiinpano")){
            kartta = Peli.pelaajienKartat.get(pelaaja + "-muistiinpano");
        } else {
            kartta = Peli.pelaajienKartat.get(pelaaja);
        }    

        int korkeus = kartta.length;
        int leveys = kartta[0].length;

        System.out.print("\n\n   ");

        for (int j = 0; j < leveys; j++) {

            System.out.print(Vakiot.ANSI_BOLD + Vakiot.ANSI_CYAN);
            System.out.printf(" %c  ", 'A' + j);
            System.out.print(Vakiot.ANSI_RESET);

        }

        System.out.println("\n  ┌" + "───".repeat(Vakiot.rivit+2) + "┐");

        for (int i = 0; i < korkeus; i++) {

            System.out.print(Vakiot.ANSI_BOLD);
            System.out.printf(" %d│", i);
            System.out.print(Vakiot.ANSI_RESET);

            tulostaRivi(kartta[i]);

            System.out.println("│");

            if (i < korkeus - 1) {
                System.out.println("  ├" + "───".repeat(Vakiot.rivit+2) + "┤");
            }
            
        }
        System.out.println("  └" + "───".repeat(Vakiot.rivit+2) + "┘");
    }

    /**
     * Tulostaa yksittäisen rivin karttaan
     * @param rivi Tulostettavan kartan rivi
     */
    private static void tulostaRivi(String[] rivi) {
        for (int j = 0; j < rivi.length; j++) {

            System.out.printf(" %s ", rivi[j]);

            if (j < rivi.length - 1) {
                System.out.print("│");
            }
            
        }
    }
}
