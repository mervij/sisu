## Taustaa

Opiskelijaryhmätyönä tehty harjoitustyö, jossa tavoitteena oli toteuttaa yksinkertainen versio monessa suomalaisessa yliopistossa käytössä olevasta opintotietojärjestelmä Sisusta. Järjestelmä hyödynsi oikeaa korkeakoulujen dataa. Harjoitustyö oli osa Ohjelmointi 3 -kurssia keväällä 2022. Omat vastuualueeni:  

* Sisu API
* Käyttäjän tietojen tallennus JSON:iin
* Testit
* Luokat:
  * StudyElement: Abstrakti kantaluokka tutkinto-ohjelmalle, opintokokonaisuudelle ja kurssille
  * StudyElementData: Abstrakti kantaluokka tietojen lukemiseen SisuAPIn JSONista
  * UniversityData: Hakee tutkinto-ohjelmien tiedot SisuAPIsta
  * Programme: Tutkinto-ohjelman tietojen rakenne
  * ProgrammeData: Hakee tutkinto-ohjelman tiedot SisuAPIsta
  * Module: Opintokokonaisuuden tietojen rakenne
  * ModuleData: Hakee opintokokonaisuuden tiedot SisuAPIsta
  * Course: Kurssin tietojen rakenne
  * CourseData: Hakee kurssin tiedot SisuAPIsta
  * User: Käyttäjän tietojen rakenne
  * UserData: Käyttäjätietojen hakeminen ja tallentaminen JSON-tiedostoon
* Testiluokat:
  * ModuleDataTest
  * ModuleTest
  * StudyElementDataTest
  * StudyElementTest
  * UserTest

## Käyttöohjeet

Käyttäjä voi syöttää joko uuden tai olemassa olevan opiskelijanumeron (tallennettu valmiiksi dataa opiskelijanumerolle 123456). Jos opiskelijanumero on olemassa ohjelma lataa sille tiedot, muuten luodaan uusi opiskelijatili. Tämän jälkeen opiskelijan tietoja voi lisätä tai muokata ja ne voi tallentaa napista.

Uuden opintosuunnitelman voi luoda napista ja sille pitää antaa nimi, tai olemassa olevan voi valita valikosta.

Seuraavalla välilehdellä voi tutkia valittua opintosuunnitelmaa, ja merkitä eri kursseja suoritetuksi valitsemalla kokonaisuuden ja rastittamalla halutun kurssin. Tiedot tallentuvat automaattisesti. 
