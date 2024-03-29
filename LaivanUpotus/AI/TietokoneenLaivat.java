package LaivanUpotus.AI;
import java.util.ArrayList;
import java.util.Random;

public class TietokoneenLaivat {

    private static final Random random = new Random();

    public static String[] generoiTietokoneLaivat(String[][] kartta, int laivanKoko) {
        ArrayList<int[]> vapaatKoordinaatit = etsiVapaatKoordinaatit(kartta);

        while (true) {
            try {
                // Arvotaan laivan aloitus ja lopetus- koordinaatit.
                int[] aloitusKoordinaatti = arvoAloitusKoordinaatti(vapaatKoordinaatit);
                int[] lopetusKoordinaatti = arvoLopetusKoordinaatti(aloitusKoordinaatti, laivanKoko);

                // Muutetaan laivat sarakekirjain + rivi muotoon samanlailla kuin oikeilla pelaajilla.
                String aloitusKoordinaattiString = koordinaattiMerkkijonoksi(aloitusKoordinaatti);
                String lopetusKoordinaattiString = koordinaattiMerkkijonoksi(lopetusKoordinaatti);

                return new String[]{aloitusKoordinaattiString, lopetusKoordinaattiString};

            } catch (IndexOutOfBoundsException e) {
                System.out.println(e);
                continue;
            }
        }
    }

    private static ArrayList<int[]> etsiVapaatKoordinaatit(String[][] kartta) {
        ArrayList<int[]> vapaatKoordinaatit = new ArrayList<>();

        // Käydään kartta läpi ja etsitään vapaat koordinaatit
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Jos koordinaatissa "~" merkki: vapaa.
                if (kartta[i][j].equals(LaivanUpotus.Vakiot.merkit[0])) {
                    int[] koordinaatti = {i, j};
                    // Lisätään koordinaatti arraylistaan.
                    vapaatKoordinaatit.add(koordinaatti);
                }

            }
        }
        return vapaatKoordinaatit;
    }

    private static int[] arvoAloitusKoordinaatti(ArrayList<int[]> vapaatKoordinaatit){
        int randomKoordinaatti = random.nextInt(vapaatKoordinaatit.size());
        return vapaatKoordinaatit.get(randomKoordinaatti);
    }

    private static int[] arvoLopetusKoordinaatti(int[] aloitusKoordinaatti, int laivanKoko) {
        int[] lopetusKoordinaatti = new int[2];
        
        // Valitaan random suunta johon laivaa rakennetaan.
        int randomSuunta = random.nextInt(4);
        switch (randomSuunta) {
            case 0: // Oikealle
                lopetusKoordinaatti[0] = aloitusKoordinaatti[0];
                lopetusKoordinaatti[1] = aloitusKoordinaatti[1] + laivanKoko - 1;
                break;

            case 1: // Vasemmalle
                lopetusKoordinaatti[0] = aloitusKoordinaatti[0];
                lopetusKoordinaatti[1] = aloitusKoordinaatti[1] - laivanKoko + 1;
                break;

            case 2: // Ylös
                lopetusKoordinaatti[0] = aloitusKoordinaatti[0] - laivanKoko + 1;
                lopetusKoordinaatti[1] = aloitusKoordinaatti[1];
                break;

            case 3: // Alas
                lopetusKoordinaatti[0] = aloitusKoordinaatti[0] + laivanKoko - 1;
                lopetusKoordinaatti[1] = aloitusKoordinaatti[1];
                break;
        }
        return lopetusKoordinaatti;
    }

    private static String koordinaattiMerkkijonoksi(int[] koordinaatti) {
        char kirjain = LaivanUpotus.Vakiot.sarakeKirjaimet.charAt(koordinaatti[0]);
        int numero = koordinaatti[1];
        return String.valueOf(kirjain) + String.valueOf(numero).toUpperCase();
    }
}
