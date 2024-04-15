package LaivanUpotus;

public class VirheIlmoitukset {

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
                    System.out.println("\nOngelma koordinaatin syötteessä..");
                    break;             
                case "yliRajojenArvaus":
                    System.out.println("\nEt voi arvata yli rajojen meneviä alkioita...");
                    break;
                case "ongelmaOsumassa":
                    System.out.println("\nJotain meni pieleen osuman tarkistuksessa...");
                    break;
            }

        }
    }
}
