# LaivanUpotus + AI
 ### Mikä?
Javalla toteutettu komentoriviltä pelattava laivanupotus peli, jossa voi pelata tietokonetta tai kaveria vastaan.

 ### Ohjeet
Laivanupotus on 2 pelaajan peli, jossa tavoitteena on upottaa vastustajan kaikki laivat arvaamalla
vuorotellen vastustajan **10x10** kokoiselta kartalta sijainteja. Laivoja kummatkin pelaaja asettaa omille kartoilleen 5 
kappaletta. Asetettavia laivoja on eri kokoisia. Voittaja on se, joka upottaa vastustajan kaikki laivat 
ensimmäisenä. Kierroksen tulokset tallennetaan tekstitiedostoon, josta niitä voi tarkastaa päävalikon kautta.

 ### Säännöt
Yleisimpiä sääntöjä on, että omat laivat ei saa olla vinossa tai vierekkäin.

 ### Pelinkulku
1. Asetetaan omat laivat kartoilleen
2. Pelaajat arvailevat toistensa laivoja, kunnes jommankumman kaikki laivat on arvattu.
3. Voittaja on se, kumpi arvaa toisen pelaajan kaikki laivat ensimmäisenä.
4. Peli tallentaa kierroksen tulokset, ja niitä voi käydä katselemassa päävalikon kautta.

 ### Tietokone vastus
Tietokone tekee arvauksia saman datan perusteella, kun normaalilla pelaajalla on pelin tilanteessa.
Eli tietokone vastus ei siis esim. hae suoraan vastustajan kartasta laivoja joita arvata, koska se olisi ns. *"huijausta"*.
Tietokone arvaa satunnaisesti kartan kohtia, kunnes se osuu laivaan. Kun se osuu laivaan, se alkaa arvailemaan ympärillä olevia alkioita.
Kun tietokone on osunut 2 kertaa samaan laivaan, se tietää osittain missä suunnassa laiva on, ja sitä mukaan arvaa eteenpäin/taaksepäin.


### Kuvia pelistä

![OPR_säännöt](https://github.com/miikaran/laivan-upotus/assets/88707539/5b9ef939-47ec-47ab-a769-a4ebb7f43a69)

<div float="left">
  <img src="https://github.com/miikaran/laivan-upotus/assets/88707539/f63656ea-5373-44d2-8070-048153d3cccb" width="26%">
  <img src="https://github.com/miikaran/laivan-upotus/assets/88707539/80d72102-44e3-4b2c-9f47-6ade7651b5eb" width="24%">
  <img src="https://github.com/miikaran/laivan-upotus/assets/88707539/1cb634de-c72b-4b45-bd1e-ef831ffd5bd4" width="23%">
  <img src="https://github.com/miikaran/laivan-upotus/assets/88707539/a7567950-f66d-4b24-b85c-53a50d54eef8" width="23%">
</div>

<br>

### Abstrakti vuokaavio pelin toiminnasta
<img width=50% src="https://github.com/miikaran/laivan-upotus/assets/88707539/98eca324-80fb-44a1-a2f1-9532d4112f3a">

