import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;

public class laivanupotus {

    /**
     * Luodaan pelin kannalta 
     * kriittiset muuttujat.
     */
    static final Scanner scanner = new Scanner(System.in);
    static String sarakeKirjaimet = "ABCDEFGHIJ";
    static int rivit = 10;
    static int sarakkeet = 10;
    static String[] laivat = {"Sukellusvene", "Hävittäjä", "Risteilijä", "Taistelulaiva", "Lentotukialus"};
    static int[] laivaKoot = {1,2,3,4,5};
    static String pelimuoto = "";
    static boolean peliPaalla = false;
    static String[] merkit = {"~", "O", "H", "M"};
    static String[] pelaajat = new String[2];
    static HashMap<String, String[][]> pelaajienTaulut = new HashMap<String, String[][]>();
    static String vuoro = "" ;

    /**
     * Aloitetaan peli.
     */
    public static void main(String[] args){
        peliPaalla = true;
        menu();
        luoPelaajat(pelimuoto);
        luoTaulut();
        kysyLaivat();
    }

    /**
     * Näytetään menu ja kysytään käyttäjältä pelimuoto.
     */
    public static void  menu(){
        System.out.println("=====================================");
        System.out.println("\n Tervetuloa pelaamaan laivanupotusta! ");
        System.out.println("\n Valitse pelimuoto jota haluat pelata:");
        System.out.println(" 1 => Tietokonetta vastaan\n 2 => Kaveria vastaan\n 3 => Lopeta peli");
        System.out.println("\n=====================================");
        System.out.print("\n=> ");
        int kayttajanSyote = scanner.nextInt();
        switch (kayttajanSyote){
            case 1:
              pelimuoto = "tietokone";
              break;
            case 2:
              pelimuoto = "kaveri";
              break;
            case 3:
              System.out.println("Suljetaan ohjelma...");
              System.exit(0);
        }
    }

    public static void luoPelaajat(String pelimuoto){
        if(pelimuoto.equals("kaveri")){
            scanner.nextLine();
            for(int i = 0; i < 2; i++){
                while(true){
                    System.out.print("\nPelaaja " + (i+1) + "\nAseta nimesi: \n=>");
                    String syote = scanner.nextLine();
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

    /**
     * Luodaan pelaajien taulut.
     */
    public static void luoTaulut(){
        String[][] taulu = new String[rivit][sarakkeet];
        for(int i = 0; i < rivit; i++){
            for(int j = 0; j < sarakkeet; j++){ 
                taulu[i][j] = merkit[0]; 
            }
        }
        for(String pelaaja : pelaajat){
            pelaajienTaulut.put(pelaaja, taulu);
        }
    }

    public static void aloitaTaistelu(){
        vuoro = pelaajat[0];
        while(peliPaalla){
            System.out.println(vuoro + ":n" + " vuoro" );
            tulostaTaulu(vuoro);
        }
    }

    /**
     * Tulostaa pelaajan taulun koko taulun.
     * Lisää tulokseen myös osoittajia taulun riveistä ja sarakkeista.
     */
    public static void tulostaTaulu(String pelaaja) {
        String[][] taulu = pelaajienTaulut.get(pelaaja);
        int korkeus = taulu.length;
        int leveys = taulu[0].length;
    
        System.out.print("   ");
        for (int j = 0; j < leveys; j++) {
            System.out.print(" " + (char)('A' + j) + "  ");
        }
        System.out.println();
    
        System.out.print("  ┌");
        for (int i = 0; i < leveys; i++) {
            System.out.print("───");
            if (i < leveys - 1) {
                System.out.print("┬");
            }
        }
        System.out.println("┐");
    
        for (int i = 0; i < korkeus; i++) {
            System.out.print(" " + (i) + "│");
            for (int j = 0; j < leveys; j++) {
                System.out.print(" " + taulu[i][j] + " ");
                if (j < leveys - 1) {
                    System.out.print("│");
                }
            }
            System.out.println("│");
    
            if (i < korkeus - 1) {
                System.out.print("  ├");
                for (int j = 0; j < leveys; j++) {
                    System.out.print("───");
                    if (j < leveys - 1) {
                        System.out.print("┼");
                    }
                }
                System.out.println("┤");
            }
        }
    
        System.out.print("  └");
        for (int i = 0; i < leveys; i++) {
            System.out.print("───");
            if (i < leveys - 1) {
                System.out.print("┴");
            }
        }
        System.out.println("┘");
    }
    
    
    /**
     * Asettaa uuden laivan pelaajan taulukolle sille annettujen
     * aloitus ja lopetus parametrien mukaan.
     */
    public static void asetaLaiva(int aloitusRivi, int aloitusSarake, int lopetusRivi, int lopetusSarake, String pelaaja){
        for(int i = aloitusRivi; i <= lopetusRivi; i++){
            for(int j = aloitusSarake; j <= lopetusSarake; j++){
                String[][] taulu = pelaajienTaulut.get(pelaaja);
                taulu[i][j] = merkit[1];
            }
        }
    }

    /**
     * Kysyy laivat pelaajilta ja validoi pelaajien antamat
     * syötteet taulukossa valmiiksi olevien laivojen mukaan sekä 
     * tarkistaa, että laivat lisätään taulukkoon sääntöjen mukaisesti.
     */
    public static void kysyLaivat(){
        int laivaMaara = laivat.length;

        for(String pelaaja : pelaajat){
            String[][] taulu = pelaajienTaulut.get(pelaaja);
            for(int i = 0; i < laivaMaara; i++){
                String laiva = laivat[i];
                int koko = laivaKoot[i];
                tulostaTaulu(pelaaja);
        
                while (true) {
                    System.out.println("\n" + pelaaja);
                    System.out.print("\nAseta oman laivan koordinaatit");
                    System.out.print("\nLaiva: " + laiva + " -> Koko: " + koko + "\n=> ");

                    // Otetaan käyttäjän koordinaatit ja erotellaan ne.
                    String[] koordinaatit = new String[2];
                    try{ // Otetaan koordinaatit muodossa => kirjain+sarake
                        koordinaatit = scanner.nextLine().toUpperCase().split(" ");
                    } catch(Exception NumberFormatException){
                        System.out.println("Aseta koordinaatit muodossa: sarake+rivi");
                    }

                    Arrays.sort(koordinaatit);

                    // Rivi ja sarake, joista laivaa lähdetään rakentamaan.
                    int aloitusRivi = Integer.parseInt(koordinaatit[0].substring(1));
                    int aloitusSarake = sarakeKirjaimet.indexOf(koordinaatit[0].charAt(0));

                    
                    // Rivi ja sarake, johon laivan rakennus lopetetaan.
                    int lopetusRivi = Integer.parseInt(koordinaatit[1].substring(1));
                    int lopetusSarake = sarakeKirjaimet.indexOf(koordinaatit[1].charAt(0));


                    boolean laivaaVoiSijoittaa = true;


                    //Tarkistetaan onko koordinaattien lähellä jo laivaa. 
                    for(int j = aloitusRivi; j <= lopetusRivi; j++){

                        for(int k = aloitusSarake; k <= lopetusSarake; k++){

                            try{
                                
                                if(taulu[j][k].equals("O")||  // Päällä
                                taulu[j][k-1].equals("O") ||  // Vasemmalla
                                taulu[j][k+1].equals("O") ||  // Oikealla
                                taulu[j-1][k].equals("O") ||  // Ylhäällä
                                taulu[j+1][k].equals("O")     // Alhaalla
                                )
                                { 
                                    laivaaVoiSijoittaa = false;
                                }
                                
                            } catch (Exception IndexOutOfBoundsException) {
                                // Jos tarkistukset menee yli rajojen, jatketaan peliä.
                                System.out.println();

                            }
                        }
                    }
                
                    /**
                     *  Alla muita olennaisia laivojen asettamiseen liityviä validointeja.
                        * Koordinaattien määrä
                        * Viistoossa olevat laivat
                        * Laivan koko.
                    */
                    if (koordinaatit.length != 2) {
                        System.out.println("\nSyötä 2 koordinaattia!");
                    } else if (aloitusRivi != lopetusRivi && aloitusSarake != lopetusSarake) { // 
                        System.out.println("\nEt voi asettaa laivoja viistoon.");
                    } else if (lopetusRivi - aloitusRivi + 1 > koko || lopetusSarake - aloitusSarake + 1 > koko) {
                        System.out.println("\nLaivan koko on " + koko + ". Syötä koordinaatit uudelleen.");
                    } else if(!laivaaVoiSijoittaa){
                        System.out.println("\nEt voi laittaa tähän kohtaan laivaa.");
                    }
                    else{ /* Mikäli kaikki suoritetut validaatiot menee läpi, 
                            asetetaan laiva haluttuun positioon. */
                        asetaLaiva(aloitusRivi, aloitusSarake, lopetusRivi, lopetusSarake, pelaaja);
                        break;
                    }
                }
            }
        }
    }
}
