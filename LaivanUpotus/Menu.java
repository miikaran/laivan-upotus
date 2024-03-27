package LaivanUpotus;
import java.util.Scanner;

public class Menu {
  public static final Scanner scanner = new Scanner(System.in);
    /**
     * Näytetään menu, sekä asetetaan käyttäjän valitsema pelimuoto.
    */
    public static void NaytaMenu(){
        System.out.println("=====================================");
        System.out.println("\n Tervetuloa pelaamaan laivanupotusta! ");
        System.out.println(" Valitse pelimuoto jota haluat pelata:");
        System.out.println("\n 1 => Tietokone\n 2 => Kaveri\n 3 => Saannot\n 4 => Lopeta peli");
        System.out.println("\n=====================================");
        System.out.print("\n=> ");

        int kayttajanSyote = scanner.nextInt();

        switch (kayttajanSyote){
            case 1:
              Peli.pelimuoto = Vakiot.pelimuodot[0];
              Menu.luoPelaajat(Peli.pelimuoto);
              break;         
            case 2:
              Peli.pelimuoto = Vakiot.pelimuodot[1];
              Menu.luoPelaajat(Peli.pelimuoto);
              break;
            case 3:
              System.out.println("Pelin säännöt: ");
            case 4:
              System.out.println("Suljetaan ohjelma...");
              System.exit(0);
        }
    }

    /**
     * Jos pelataan kaverin kanssa, kysytään pelaajilta alussa heidän nimet.
     * Mikäli pelataan tietokonetta vastaan, asetetaan pelaajille valmiit nimet.
    */
    public static void luoPelaajat(String pelimuoto){
      if(pelimuoto.equals("kaveri")){
          for(int i = 0; i < Peli.pelaajat.length; i++){
              while(true){

                System.out.print("\nPelaaja " + (i+1) + "\nAseta nimesi: \n=> ");

                try{
                  String syote = scanner.next();
                  Peli.pelaajat[i] = syote;
                  break;
                  
                } catch(Exception e) {
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
