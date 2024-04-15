package LaivanUpotus;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TulosTallennus {

    /**
     * Tällä metodilla tallennetaan pelin tulokset tulokset.txt tiedostoon.
     */
    public static void tallennaTulos(String[] pelaajat, int arvaukset, String voittaja){
        try (PrintWriter kirjoittaja = new PrintWriter(new FileWriter(Vakiot.tulosTiedostoNimi, true))){

            // Haetaan pelin loppumis aika.
            LocalDateTime aikaNyt = LocalDateTime.now();
            DateTimeFormatter aikaFormatointi = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm");
            String formatoituAika = aikaNyt.format(aikaFormatointi);

            // Kirjoitetaan aika ja voittaja tiedostoon.
            kirjoittaja.print(":p:" + formatoituAika + " ");
            kirjoittaja.print(":w:" + voittaja + " ");

            // Käydään pelaajat läpi ja kirjoitetaan tiedostoon.
            for(int i = 0; i < pelaajat.length; i++){
                String pelaajanNimi = pelaajat[i];
                kirjoittaja.print(":n:" + pelaajanNimi + " ");
            }

            // Kirjoitetaan arvaukset ja suljetaan kirjoittaja
            kirjoittaja.print(":a:" + arvaukset);
            kirjoittaja.println();
            kirjoittaja.close();

        } catch (IOException io){
            System.out.println("Ongelmia tiedostoon kirjoittamisessa...");
        }
    }


    /**
     * Tällä metodilla haetaan ja palautetaan tulokset tulokset.txt tiedostosta halutulla tavalla.
     */
    public static ArrayList<String> haeTulokset(String tyyppi){
        ArrayList<String> tulokset = new ArrayList<String>();
        try(Scanner lukija = new Scanner(new File(Vakiot.tulosTiedostoNimi))){

            int riviIndeksi = 0;
            while(lukija.hasNext()){
                // Haetaan aluksi kaikki tulokset tiedostosta.
                tulokset.add(riviIndeksi, lukija.nextLine());
                riviIndeksi++;
            }
            lukija.close();

        } catch(IOException io){
            System.out.println("Ongelmia tiedostoon kirjoittamisessa...");
        }

        int tulostenKoko = tulokset.size();  

        // Suodatetaan tuloksia argumentin mukaan.
        switch (tyyppi){
            case "kaikki": // Palautetaan kaikki tulokset.
                break;

            case "kierros": // Palautetaan pelatun kierroksen tulokset.
                String kierroksenTulos = tulokset.get(tulostenKoko-1);
                tulokset.clear();
                tulokset.add(kierroksenTulos);
                break;

        }
        return tulokset;
    }


    /**
     * Tällä metodilla haetaan tulokset argumentin mukaan ja näytetään komentorivillä.
     */
    public static void naytaTulokset(String tyyppi){
        ArrayList<String> tulokset = haeTulokset(tyyppi);

        for(int i = 0; i < tulokset.size(); i++){

            // Splitataan tulos-rivi sanoittain taulukoksi.
            String[] riviTaulukoksi = tulokset.get(i).split(" ");

            // Alustetaan muuttujat johon eri tiedot tallennetaan.
            ArrayList<String> nimet = new ArrayList<>();
            String voittaja = "";
            String tulosAika = "";
            int arvaukset = 0;

            for(int j = 0; j < riviTaulukoksi.length; j++){
                // Jos tunnisteena :n: => lisätään nimiin.
                if(riviTaulukoksi[j].contains(":n:")){
                    nimet.add(riviTaulukoksi[j].substring(3));
                }
                // Jos tunnisteena :w: => lisätään voittajaksi.
                else if(riviTaulukoksi[j].contains(":w:")){
                    voittaja = riviTaulukoksi[j].substring(3);
                }
                // Jos tunnisteena :a: => lisätään arvauksiksi.
                else if(riviTaulukoksi[j].contains(":a:")){
                    arvaukset = Integer.parseInt(riviTaulukoksi[j].substring(3));
                }
                // Jos tunnisteena :p: => lisätään ajaksi.
                else if(riviTaulukoksi[j].contains(":p:")){
                    tulosAika = riviTaulukoksi[j].substring(3);
                }
            }

            // Määritetään tuloksien otsikko käyttäjän inputin mukaan.
            String tulosOtsikko = tyyppi.equals("kierros") ? " KIERROKSEN TULOKSET " : "TULOKSET";

            // Tulostetaan kaikki haetut tiedot.
            System.out.println(Vakiot.ANSI_BOLD + Vakiot.ANSI_CYAN + "\n══════════════════" + Vakiot.ANSI_GREEN + tulosOtsikko + Vakiot.ANSI_CYAN +  "═════════════════════\n" + Vakiot.ANSI_RESET);
            System.out.println(Vakiot.ANSI_BOLD + "Aika: " + Vakiot.ANSI_RESET + tulosAika);
            System.out.println(Vakiot.ANSI_BOLD + "Voittaja: " + Vakiot.ANSI_RESET + voittaja);
            System.out.println(Vakiot.ANSI_BOLD + "Määrä " + Vakiot.ANSI_RESET + arvaukset);
        }
    }

}
