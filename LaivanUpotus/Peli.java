package LaivanUpotus;
import java.util.HashMap;

public class Peli {

    /**
        * Luodaan pelinkulun kannalta tärkeät muuttujat tänne.
        * Vakio muuttujat löytyvät Vakiot- luokasta.
    **/
    public static String pelimuoto = "";
    public static boolean peliPaalla = false;
    public static String[] pelaajat = new String[2];
    public static HashMap<String, String[][]> pelaajienKartat = new HashMap<String, String[][]>();
    public static String vuoro = "" ;

    /**
        * Suoritetaan tarvittavat alkutoimenpiteet ja aloitetaan peli.
    */
    public static void main(String[] args){
        peliPaalla = true;
        Menu.NaytaMenu();
        luoPelaajat(pelimuoto);
        MeriKartta.luoTaulut();
        Laivat.kysyLaivat();
    }

    /**
     * Pelaaja aloittavat laivojen upotuksen.
     */
    public static void aloitaTaistelu(){
        vuoro = pelaajat[0];
        while(peliPaalla){
            System.out.println(vuoro + ":n" + " vuoro" );
            MeriKartta.tulostaKartta(vuoro);
        }
    }

    /**
        * Jos pelataan kaverin kanssa, kysytään pelaajilta alussa heidän nimet.
        * Mikäli pelataan tietokonetta vastaan, asetetaan pelaajille valmiit nimet.
    **/
    public static void luoPelaajat(String pelimuoto){
        if(pelimuoto.equals("kaveri")){
            for(int i = 0; i < 2; i++){
                while(true){
                    System.out.print("\nPelaaja " + (i+1) + "\nAseta nimesi: \n=>");
                    String syote = Vakiot.scanner.nextLine();
                    if(syote.length() < 1){
                        System.out.println("Yritä uudelleen...");
                        continue;
                    }
                    pelaajat[i] = syote;
                    break;
                }
            }
        } else{
            pelaajat[0] = "Pelaaja";
            pelaajat[1] = "Tietokone";
        }
    }
}
