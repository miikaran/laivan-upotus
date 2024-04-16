package LaivanUpotus;

/**
 * Tämä luokka sisältää virheilmoituksia, joita eri poikkeustilanteissa halutaan käyttäjälle näyttää.
 * @author Miika Rantalaiho
 */
public class VirheIlmoitukset {

    /**
     * Tällä metodilla näytetään haluttu virheilmoitus tietyssä tilanteissa.
     * @param tyyppi Määrittää minkälainen ilmoitus halutaan näyttää
     */
    public static void naytaIlmoitus(String tyyppi){
        String pelaaja = Peli.pelaajat[Peli.vuoro];

        if(!pelaaja.equals("Tietokone") && !Laivat.tietokoneGeneroiLaivoja){

            switch (tyyppi){
                case "vaaraMaara":
                    System.out.println(Vakiot.ANSI_RED + "\nSyötä 2 koordinaattia!" + Vakiot.ANSI_RESET);
                    break;

                case "viistoon":
                    System.out.println(Vakiot.ANSI_RED + "\nEt voi asettaa laivoja viistoon." + Vakiot.ANSI_RESET);
                    break;

                case "liianSuuri":
                    System.out.println(Vakiot.ANSI_RED + "\nLaivan koko on liian suuri...");
                    break;

                case "liianPieni":
                    System.out.println(Vakiot.ANSI_RED + "\nLaivan koko on liian pieni...");
                    break;   

                case "muutenVirheellinen":
                    System.out.println(Vakiot.ANSI_RED + "\nVirheellinen koordinaatti. Yritä uudelleen." + Vakiot.ANSI_RESET);
                    break;   
                                 
                case "onJoLaiva":
                    System.out.println(Vakiot.ANSI_RED +"\nEt voi laittaa tähän kohtaan laivaa." + Vakiot.ANSI_RESET);
                    break;

                case "rajojenUlkopuolella":
                    System.out.println(Vakiot.ANSI_RED + "\nKoordinaatit ovat rajojen ulkopuolella." + Vakiot.ANSI_RESET);
                    break;

                case "ongelmaArvauksessa":
                    System.out.println(Vakiot.ANSI_RED + "\nOngelma koordinaatin syötteessä..." + Vakiot.ANSI_RESET);
                    break; 

                case "yliRajojenArvaus":
                    System.out.println(Vakiot.ANSI_RED + "\nEt voi arvata yli rajojen meneviä alkioita..." + Vakiot.ANSI_RESET);
                    break;

                case "ongelmaOsumassa":
                    System.out.println(Vakiot.ANSI_RED + "\nJotain meni pieleen osuman tarkistuksessa..." + Vakiot.ANSI_RESET);
                    break;
            }

        }
    }
}
