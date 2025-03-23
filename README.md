# CGI praktikakohale kandideerimise ülesanne

Autor: Sten-Egert Märtson

## Käivitamisjuhised

### Frontend

Olles kaustas `cgi/lennu_soovitaja_frontend` jooksutada järgnevaid käske:

```
npm install
```

```
npm run serve
```

### Backend

Olles kaustas `cgi/lennu_soovitaja` jooksutada käsku:

```
./mvnw spring-boot:run
```

## Kommentaarid

Projekti tegemine nõudis umbes **15h** aega.

### Raskuskohad

- CSS
    - CSS on kindlasti üks minu nõrkadest kohtadest: pole piisavalt kogemust UI disainimisega, tavadega
    - Kasutasin tehisintellekti abi üldise stiili raami koostamiseks, kuid lihvisin seda ise paremaks väiksemate muudatusega
- Kohtade soovitamine
    - Kuna kohtade soovitamine sisuliselt toimub 4 *boolean*i (kas kasutaja soovib kohta akna ja/või välkapääsu lähedal, kas on soovi, et kohad oleks kõrvuti, et oleks jalaruumi) raames, siis erinevaid võimalusi kohtade valimiseks on 2^4=16
    - Seda teostab `LendView.vue` faili meetod `valiKohad()`
    - Ajapuuduse tõttu pidin poole ülesandest teostama tehisintellekti abil (Claude 3.7): koodijupid, mis langevad meetodi `if (this.lahedus)` else ossa on tehisaru poolt koostatud
    - Meetodi sees on ka palju koodi kordamist, et ideaalis oleks teostanud suuremat *refactor*imist koodi peal
    - Oleksin lisanud ka eraldi valiku, mis oleks valinud sisemisi istekohti (vastand aknakohtadest), aga antud meetod on juba piisavalt kompleksne ning aega on ülikooli tõttu vähe
    - See on perfektne meetod, mida testida, et ideaalis oleks lisanud ka Cypressi abil mõned ühiktestid
        - Ei saagi praegu garanteerida, et meetod töötab korrektselt iga kord, kui seda kasutatakse
- Spring Boot
    - Kuna spring boot on minu jaoks uus, siis selle õppimine nõudis aega, aga üldiselt selle tegelikult probleeme ei olnud 
- Ajapuudus
    - Väärtustan enda haridustaset kõrgelt, et õpingute kõrvalt ei ole palju aega selle projekti jaoks jäänud
    - Kindlasti oleksin rohkem kommentaare kirjutanud ja vähemal määral tehisintellekti kasutanud, kui aega oleks rohkem
    - Suvepraktika raames oleks kahtlemata rohkem aega ning suudaksin palju kvaliteetsemalt tööd teha