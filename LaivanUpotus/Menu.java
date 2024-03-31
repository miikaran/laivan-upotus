package LaivanUpotus;
import java.util.Scanner;

public class Menu {
  public static final Scanner scanner = new Scanner(System.in);
    /**
     * Näytetään menu, sekä asetetaan käyttäjän valitsema pelimuoto.
    */
    public static void naytaMenu(){
      
        System.out.println("");
        System.out.println("\n" + Vakiot.ANSI_YELLOW + " Tervetuloa laivanupotukseen!" + Vakiot.ANSI_RESET);
        System.out.println(Vakiot.ANSI_BOLD + " Valitse pelimuoto:" + Vakiot.ANSI_RESET);
        System.out.println(Vakiot.ANSI_PURPLE + "\n 1 => Yksinpeli");
        System.out.println(" 2 => Moninpeli");
        System.out.println(" 3 => Säännöt");
        System.out.println(" 4 => Poistu" + Vakiot.ANSI_RESET);
        System.out.println("\n" + Vakiot.ANSI_BOLD + "_______________________" + Vakiot.ANSI_RESET);
        System.out.print(Vakiot.ANSI_BOLD + "\n=> " + Vakiot.ANSI_RESET);

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

                System.out.print(Vakiot.ANSI_BOLD + "\nPelaaja " + (i+1) + Vakiot.ANSI_RESET + Vakiot.ANSI_CYAN + "\nAseta nimesi: \n=> " + Vakiot.ANSI_RESET);

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
