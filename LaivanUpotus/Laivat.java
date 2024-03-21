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
        int laivaMaara = Vakiot.laivat.length;
        boolean laivanVoiSijoittaa = true;

        for(String pelaaja : Peli.pelaajat){
            String[][] taulu = Peli.pelaajienKartat.get(pelaaja);

            for(int i = 0; i < laivaMaara; i++){
                String laiva = Vakiot.laivat[i];
                int koko = Vakiot.laivaKoot[i];
                MeriKartta.tulostaKartta(pelaaja);

                while (true) {
                    System.out.println("\n" + pelaaja);
                    System.out.print("\nAseta oman laivan koordinaatit");
                    System.out.print("\nLaiva: " + laiva + " -> Koko: " + koko + "\n=> ");

                    // Luodaan oma taulukko koordinaateille.
                    String[] koordinaatit = new String[2];

                    // Otetaan koordinaatit muodossa => sarake+rivi ja erotellaan ne.
                    koordinaatit = scanner.nextLine().toUpperCase().split(" ");

                    Arrays.sort(koordinaatit);
                    
                    try{
                        // Rivi ja sarake, joista laivaa lähdetään rakentamaan.
                        int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
                        int aloitusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));

                        // Rivi ja sarake, johon laivan rakennus lopetetaan.
                        int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
                        int lopetusSarake = Vakiot.sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));

                        //Tarkistetaan onko koordinaattien lähellä jo laivaa. 
                        for(int j = aloitusRivi; j <= lopetusRivi; j++){
                            for(int k = aloitusSarake; k <= lopetusSarake; k++){
                                try{                         
                                    if(taulu[j][k].equals("O")||  // Päällä
                                    taulu[j][k-1].equals("O") ||  // Vasemmalla
                                    taulu[j][k+1].equals("O") ||  // Oikealla
                                    taulu[j-1][k].equals("O") ||  // Ylhäällä
                                    taulu[j+1][k].equals("O"))    // Alhaalla
                                    { 
                                        laivanVoiSijoittaa = false;
                                    }      
                                } catch (Exception IndexOutOfBoundsException) {
                                    // Jos tarkistukset menee yli rajojen, jatketaan peliä.
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
                            continue;
                        } else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) {
                            System.out.println("\nEt voi asettaa laivoja viistoon.");
                            continue;
                        } else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                            System.out.println("\nLaivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
                            continue;
                        } else if(!laivanVoiSijoittaa){
                            System.out.println("\nEt voi laittaa tähän kohtaan laivaa.");
                            continue;
                        } else{ //Mikäli kaikki suoritetut validaatiot menee läpi, asetetaan laiva haluttuun positioon.
                            asetaLaiva(aloitusRivi, aloitusSarake, lopetusRivi, lopetusSarake, pelaaja);
                            break;
                        }
                    }
                    catch (Exception e){
                        // Mikäli jokin muu virhe ilmeentyy, yritetään uudelleen.
                        System.out.println("\nEt voi asettaa laivaa tähän. Yritä uudeelleen.");
                    }
                }
            }
        }
    }
    
    /**
        * Asettaa uuden laivan pelaajan taulukkoon sille annettujen
        * aloitus, lopetus ja pelaaja parametrien mukaan.
    **/
    public static void asetaLaiva(int aloitusRivi, int aloitusSarake, int lopetusRivi, int lopetusSarake, String pelaaja){
        for(int i = aloitusRivi; i <= lopetusRivi; i++){
            for(int j = aloitusSarake; j <= lopetusSarake; j++){
                String[][] taulu = Peli.pelaajienKartat.get(pelaaja);
                taulu[i][j] = Vakiot.merkit[1];
            }
        }
    } 
}
