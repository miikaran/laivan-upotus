package LaivanUpotus;
import java.util.Scanner;

public class Vakiot {

    /**
        * Tämä luokka sisältää vakio (constant) muuttujia, eli muuttujia 
        * joiden arvoja ei tarvitse pelin aikana muuttaa ollenkaan.
    **/
    public static final Scanner scanner = new Scanner(System.in);
    public static String sarakeKirjaimet = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
    public static int rivit = 10;
    public static int sarakkeet = 10;
    public static String[] laivat = {"Sukellusvene", "Hävittäjä", "Risteilijä", "Taistelulaiva", "Lentotukialus"};
    public static int[] laivaKoot = {1,2,3,4,5};
    public static String[] merkit = {"~", "O", "H", "M"};

}
