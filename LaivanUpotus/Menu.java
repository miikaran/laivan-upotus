package LaivanUpotus;
import java.util.Scanner;

public class Menu {

    /**
     * Näytetään menu, sekä asetetaan käyttäjän valitsema pelimuoto.
     */
    public static void NaytaMenu(){

        System.out.println("=====================================");
        System.out.println("\n Tervetuloa pelaamaan laivanupotusta! ");
        System.out.println("\n Valitse pelimuoto jota haluat pelata:");
        System.out.println(" 1 => Tietokonetta vastaan\n 2 => Kaveria vastaan\n 3 => Lopeta peli");
        System.out.println("\n=====================================");
        System.out.print("\n=> ");

        int kayttajanSyote = Vakiot.scanner.nextInt();

        switch (kayttajanSyote){

            case 1:
              Peli.pelimuoto = "tietokone";
              break;
              
            case 2:
              Peli.pelimuoto = "kaveri";
              break;

            case 3:
              System.out.println("Suljetaan ohjelma...");
              System.exit(0);
        }
    }
}
