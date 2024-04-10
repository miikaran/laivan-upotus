package LaivanUpotus;

public class Vakiot {

    /**
     * Tämä luokka sisältää vakio (constant) muuttujia, eli muuttujia 
     * joiden arvoja ei tarvitse pelin aikana muuttaa ollenkaan.
    */
    public static String sarakeKirjaimet = "ABCDEFGHIJ";
    public static int rivit = 10;
    public static int sarakkeet = 10;
    public static String[] laivat = {"Sukellusvene", "Hävittäjä", "Risteilijä", "Taistelulaiva", "Lentotukialus"};
    public static int[] laivaKoot = {6,5,4};
    public static int laivaMaara = 3;
    public static String[] merkit = {"~", "O", "X", "M"};
    public static String[] pelimuodot = {"tietokone", "kaveri", "saannot", "lopeta"};
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BOLD = "\033[0;1m";
    public static final String ANSI_RED	 = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
}
