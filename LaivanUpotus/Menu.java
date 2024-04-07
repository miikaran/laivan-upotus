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

        while(true){
          try{

            System.out.print(Vakiot.ANSI_BOLD + "\n=> " + Vakiot.ANSI_RESET);
            int kayttajanSyote = scanner.nextInt();
            scanner.nextLine();

            switch (kayttajanSyote){
              case 1:
                Peli.peliMuoto = Vakiot.pelimuodot[0];
                Menu.luoPelaajat(Peli.peliMuoto);
                break;         
              case 2:
                Peli.peliMuoto = Vakiot.pelimuodot[1];
                Menu.luoPelaajat(Peli.peliMuoto);
                break;
              case 3:
                naytaSaannot();
                break;
              case 4:
                System.out.println("Suljetaan ohjelma...");
                System.exit(0);
            }
            if(kayttajanSyote > 4){
              System.out.println("Valitse yllä olevista vaihtoehdoista...");
              continue;
            }
          } catch(Exception e){
            System.out.println("Yritä uudelleen...");
            scanner.nextLine();
            continue;
          }
          break;
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

    /**
     * Näytetään ohjeet / pelinkulku
     */
    public static void naytaSaannot() {
      System.out.println(Vakiot.ANSI_CYAN + "╔══════════════════════════════════════════════════════════════════════════════╗");
      System.out.println("║                            " + Vakiot.ANSI_BOLD + "OHJEET/PELINKULKU" + Vakiot.ANSI_CYAN + "                                 ║");
      System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");
      System.out.println();
      System.out.print(Vakiot.ANSI_BOLD + "Ohjeet => " + Vakiot.ANSI_RESET);
      System.out.println(Vakiot.ANSI_CYAN + "\nLaivanupotus on 2 pelaajan peli, jossa tavoitteena on upottaa vastustajan kaikki laivat arvaamalla vuorotellen vastustajan 10x10 kartalta sijainteja. Laivoja kummatkin pelaaja asettaa omille kartoilleen 5  kappaletta. Voittaja on se, joka upottaa vastustajan kaikki laivat ensimmäisenä.\n");
      System.out.print(Vakiot.ANSI_BOLD + "Pelinkulku => " + Vakiot.ANSI_RESET);
      System.out.println(Vakiot.ANSI_CYAN + "\nPelaajat aloittavat asettamalla laivansa kartalle. Kummallakin pelaajalla on tietty määrä laivoja, ja laivat voivat olla eri kokoisia.");
      System.out.println("Laivojen tulee sijaita pysty- tai vaakasuunnassa ja ne eivät saa koskettaa toisiaan, eikä niiden saa mennä ruudukon ulkopuolelle.");
      System.out.println("Pelaajat aloittavat hyökkäämisen, eli vastustajan laivojen upottamisen vuorotellen. Vuorolla pelaaja valitsee ruudun vastustajan pelilaudalta, johon hän epäilee vastustajan laivan sijoittuneen.");
      System.out.println("Jos pelaaja osuu vastustajan laivaan, peli ilmoittaa osuman.");
      System.out.println("Jos pelaaja ei osu laivaan, vastustaja peli ilmoittaa hutista.");
      System.out.println("Pelaajan vuoro päättyy, ja vuoro siirtyy vastustajalle.");
      System.out.println("Peli merkitsee pelaajan muistiinpanokartalle, mihin ruutuun he ovat arvanneet, jotta he eivät ampuisi samaan ruutuun uudelleen.");
      System.out.println("Peli jatkuu, kunnes jompikumpi pelaajista onnistuu upottamaan kaikki vastustajan laivat. Tällöin toinen pelaaja julistetaan voittajaksi.");
      naytaMenu();
  }
  
}
