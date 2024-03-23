package LaivanUpotus;
import java.util.Scanner;

public class Menu {
  public static final Scanner scanner = new Scanner(System.in);
    /**
        Näytetään menu, sekä asetetaan käyttäjän valitsema pelimuoto.
     */
    public static void NaytaMenu(){
        System.out.println("=====================================");
        System.out.println("\n Tervetuloa pelaamaan laivanupotusta! ");
        System.out.println("\n Valitse pelimuoto jota haluat pelata:");
        System.out.println(" 1 => Tietokonetta vastaan\n 2 => Kaveria vastaan\n 3 => Lopeta peli");
        System.out.println("\n=====================================");
        System.out.print("\n=> ");

        int kayttajanSyote = scanner.nextInt();

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

    /**
        Jos pelataan kaverin kanssa, kysytään pelaajilta alussa heidän nimet.
        Mikäli pelataan tietokonetta vastaan, asetetaan pelaajille valmiit nimet.
    **/
    public static void luoPelaajat(String pelimuoto){
      if(pelimuoto.equals("kaveri")){

          for(int i = 0; i < Peli.pelaajat.length; i++){
          
              while(true){
                  System.out.print("\nPelaaja " + (i+1) + "\nAseta nimesi: \n=>");

                  try{
                      String syote = scanner.next();
                      Peli.pelaajat[i] = syote;
                      break;

                  } catch(Exception e){

                      System.out.println("Yritä uudelleen...");
                      continue;
                  }
              }
          }

      } else{

          Peli.pelaajat[0] = "Pelaaja";
          Peli.pelaajat[1] = "Tietokone";
          
      }
  }
}
