package LaivanUpotus;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class TulosTallennus {

    public static void tallennaTulos(String[] pelaajat, int arvaukset, String voittaja){
        try (PrintWriter kirjoittaja = new PrintWriter(new FileWriter(Vakiot.tulosTiedostoNimi, true))){
            kirjoittaja.print(":w:" + voittaja + " ");
            for(int i = 0; i < pelaajat.length; i++){
                String pelaajanNimi = pelaajat[i];
                kirjoittaja.print(":n:" + pelaajanNimi + " ");
            }
            kirjoittaja.print(":a:" + arvaukset);
            kirjoittaja.println();
            kirjoittaja.close();
        } catch (IOException io){
            System.out.println("Ongelmia tiedostoon kirjoittamisessa...");
        }
    }

    public static ArrayList<String> haeTulokset(){
        ArrayList<String> rivit = new ArrayList<String>();
        try(Scanner lukija = new Scanner(new File(Vakiot.tulosTiedostoNimi))){
            int riviIndeksi = 0;
            while(lukija.hasNext()){
                rivit.add(riviIndeksi, lukija.nextLine());
                riviIndeksi++;
            }
            lukija.close();
        } catch(IOException io){
            System.out.println("Ongelmia tiedostoon kirjoittamisessa...");
        }
        return rivit;
    }

    public static void haeTuloksetNimella(String nimi){
        ArrayList<String> tulokset = haeTulokset();
        ArrayList<String> suodatetutTulokset = new ArrayList<String>();
        for(int i = 0; i < tulokset.size(); i++){
            String rivi = tulokset.get(i);
            if(rivi.contains(nimi)){
                suodatetutTulokset.add(rivi);
            }
        }
    }

    public static void naytaTulokset(){
        ArrayList<String> tulokset = haeTulokset();
        for(int i = 0; i < tulokset.size(); i++){
            String[] riviTaulukoksi = tulokset.get(i).split(" ");
            ArrayList<String> nimet = new ArrayList<>();
            String voittaja = "";
            int arvaukset = 0;

            for(int j = 0; j < riviTaulukoksi.length; j++){
                if(riviTaulukoksi[j].contains(":n:")){
                    nimet.add(riviTaulukoksi[j].substring(3));
                }
                else if(riviTaulukoksi[j].contains(":w:")){
                    voittaja = riviTaulukoksi[j].substring(3);
                }
                else if(riviTaulukoksi[j].contains(":a:")){
                    arvaukset = Integer.parseInt(riviTaulukoksi[j].substring(3));
                }
            }
            System.out.print("Voittaja " + voittaja);
            System.out.print("Arvaukset " + arvaukset);
            System.out.println();
        }
    }

}
