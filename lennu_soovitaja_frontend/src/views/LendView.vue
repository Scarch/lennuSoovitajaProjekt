<template>
    <div class="lend">

        <div class="lend-info">
            <h1>Lend: {{ lend.kust }} - {{ lend.kuhu }}</h1>
            <div class="lend-details">
                <div class="detail">
                    <span class="label">Piletihind:</span>
                    <span class="value">{{ lend.hind }}€</span>
                </div>
                <div class="detail">
                    <span class="label">Kust:</span>
                    <span class="value">{{ lend.kust }}</span>
                </div>
                <div class="detail">
                    <span class="label">Kuhu:</span>
                    <span class="value">{{ lend.kuhu }}</span>
                </div>
                <div class="detail">
                    <span class="label">Algus:</span>
                    <span class="value">{{ lend.algus }}</span>
                </div>
                <div class="detail">
                    <span class="label">Saabumisaeg:</span>
                    <span class="value">{{ lend.lopp }}</span>
                </div>
                <div class="detail">
                    <span class="label">Kuupäev:</span>
                    <span class="value">{{ lend.kuupaev }}</span>
                </div>
            </div>
        </div>

        <div class="istekoha-valija">
            <div class="inputs">
                <div class="aken">
                    <label for="aken">Kas soovite, et istekoht oleks akna ääres?</label>
                    <input type="checkbox" name="aken" required v-model="aken">
                </div>
                <div class="valjapaas">
                    <label for="valjapaas">Kas soovite, et istekoht oleks lähedal väljapääsule?</label>
                    <input type="checkbox" name="valjapaas" required v-model="valjapaas">
                </div>
                <div class="jalaruum">
                    <label for="jalaruum">Kas soovite, et istekohal oleks lisajalaruumi?</label>
                    <input type="checkbox" name="jalaruum" required v-model="jalaruum">
                </div>
                <div class="lahedus">
                    <label for="lahedus">Kas soovite, et istekohad oleksid võimalikult lähedal üksteisele?</label>
                    <input type="checkbox" name="lahedus" required v-model="lahedus">
                </div>
                <div class="piletidNr">
                    <label for="piletidNr">Kogus pileteid</label>
                    <input type="range" name="piletidNr" required v-model="piletidNr" min="1" max="10" step="1">
                    {{ piletidNr }}
                </div>
            </div>
            <div class="esita">
                <button @click="valiKohad()">Vali kohad</button>
            </div>
        </div>

        <h2>Lennuki eesots</h2>
        <div v-for="(rida, ridaIndex) in lend.istekohad.slice(0, lend.istekohad.length / 2)" :key="ridaIndex"
            class="istekoht-rida">

            <div class="rea-number">
                <p><b>{{ ridaIndex + 1 }}</b></p>
            </div>
            <div v-for="(istekoht, istekohtIndex) in rida[0]" :key="istekohtIndex"
                :class="['istekoht', istekoht.status, { selected: istekoht.selected }]"
                @click="toggleIstekohtVasak(ridaIndex, istekohtIndex)">
                <p>{{ istekoht.label }}</p>
            </div>
            <div class="vahe"></div>
            <div v-for="(istekoht, istekohtIndex) in rida[1]" :key="istekohtIndex"
                :class="['istekoht', istekoht.status, { selected: istekoht.selected }]"
                @click="toggleIstekohtParem(ridaIndex, istekohtIndex)">
                <p>{{ istekoht.label }}</p>
            </div>
        </div>

        <div class="vahe">
            <h3>Avarii väljapääs</h3>
        </div>

        <div v-for="(rida, ridaIndex) in lend.istekohad.slice(lend.istekohad.length / 2)" :key="ridaIndex"
            class="istekoht-rida">
            <div class="rea-number">
                <p><b>{{ ridaIndex + 1 + lend.istekohad.length / 2 }}</b></p>
            </div>
            <div v-for="(istekoht, istekohtIndex) in rida[0]" :key="istekohtIndex"
                :class="['istekoht', istekoht.status, { selected: istekoht.selected }]"
                @click="toggleIstekohtVasak(ridaIndex + lend.istekohad.length / 2, istekohtIndex)">
                <p>{{ istekoht.label }}</p>
            </div>
            <div class="vahe"></div>
            <div v-for="(istekoht, istekohtIndex) in rida[1]" :key="istekohtIndex"
                :class="['istekoht', istekoht.status, { selected: istekoht.selected }]"
                @click="toggleIstekohtParem(ridaIndex + lend.istekohad.length / 2, istekohtIndex)">
                <p>{{ istekoht.label }}</p>
            </div>
        </div>

        <h2>Lennuki tagaots</h2>

        <div class="eemalda-valikud">
            <button @click="eemaldaValikud()">Tühista valikud</button>
        </div>
    </div>
</template>

<script>

export default {
    name: 'LendView',
    data() {
        return {
            mituKohtaValitud: 0,
            aken: false,
            valjapaas: false,
            lahedus: false,
            jalaruum: false,
            piletidNr: 1,
            lend: {
                id: 0,
                kust: '',
                kuhu: '',
                kuupäev: '',
                algus: '',
                lõpp: '',
                hind: 100,
                istekohad: this.generateRandomSeats()
            }
        };
    },
    components: {

    },
    methods: {
        generateRandomSeats() {
            // Create an array to hold 10 rows of seats
            const seats = [];
            
            // Generate rows
            for (let i = 0; i < 30; i++) {
                // Each row has a left and right section with 3 seats each
                const leftSection = [];
                const rightSection = [];
                
                // Generate left section (A, B, C)
                leftSection.push({ label: 'A', status: Math.random() > 0.3 ? 'available' : 'occupied', selected: false });
                leftSection.push({ label: 'B', status: Math.random() > 0.3 ? 'available' : 'occupied', selected: false });
                leftSection.push({ label: 'C', status: Math.random() > 0.3 ? 'available' : 'occupied', selected: false });
                
                // Generate right section (D, E, F)
                rightSection.push({ label: 'D', status: Math.random() > 0.3 ? 'available' : 'occupied', selected: false });
                rightSection.push({ label: 'E', status: Math.random() > 0.3 ? 'available' : 'occupied', selected: false });
                rightSection.push({ label: 'F', status: Math.random() > 0.3 ? 'available' : 'occupied', selected: false });
                
                // Add this row to the seats array
                seats.push([leftSection, rightSection]);
            }
            
            return seats;
        },
        eemaldaValikud() {
            for (let rida of this.lend.istekohad) {
                for (let istekoht of rida[0]) {
                    if (istekoht.status === 'available') {
                        istekoht.selected = false;
                    }
                }
                for (let istekoht of rida[1]) {
                    if (istekoht.status === 'available') {
                        istekoht.selected = false;
                    }
                }
            }
            this.mituKohtaValitud = 0;
        },
        valiKohad() {
            this.eemaldaValikud();

            // Kuna kasutajaeelistusi on mitu, siis nende põhjal saab teha erinevaid valikuid
            // Peame eelistustele seega prioriteedid määrama, et algoritm toimuks deterministlikult
            // Saaks ka küsida kasutajalt järjestada eelistusi, kuid see võib rikkuda kasutajakogemust

            // Prioriteedid (oma loomult subjektiivsed):
            // 1. Lähedal väljapääsule
            // 2. Istekohtade lähedus
            // 3. Lisajalaruum
            // 4. Akna ääres

            // Jalaruumi pakkuvad read:
            // 1. rida
            // Avarii väljapääsu rida (indeksil lend.istekohad.length / 2)

            // Väljapääsule kõige lähedasemad read (arvestamata avarii väljapääsu rida ning eeldades, et lennukist saab sisse/välja nii eest kui ka tagant):
            // 1. rida
            // Viimane rida

            // Suurima erinevuse tekitab aga istekohtade lähedus, sest ei ole garanteeritud, et kõik soovid on täidetavad

            // Mõjuv faktor on ka see, et saab kuni 10 piletit osta
            // Raske on arvestada iga olukorraga

            if (this.lahedus) {

                if (this.valjapaas) {

                    if (this.jalaruum) {
                        // Antud juhul on ainult sobilik esimene rida, sest jalaruumiga kohtade valik on limiteeritud
                        // Aknakoht peab siis antud juhul olemagi sellel esimesel real
                        // Kontrollime, kas aknakohta leidub esimesel real
                        let aknaKohtLeitudVasakul = this.lend.istekohad[0][0][0].status === 'available';
                        let aknaKohtLeitudParemal = this.lend.istekohad[0][1][2].status === 'available';


                        if (!aknaKohtLeitudVasakul && !aknaKohtLeitudParemal) {
                            alert('Jalaruumiga aknakohad väljapääsu lähedal on hõivatud.');
                            this.eemaldaValikud();
                            return;
                        }

                        let veergOffset = 0;
                        if (aknaKohtLeitudParemal) {
                            veergOffset = 1;
                        }

                        // Liigume esireast tagumiste poole, valides kohti
                        // Halvimal juhul tagab vaid ühele inimesele aknakoha ja jalaruumi
                        for (let rida of Array(this.lend.istekohad.length).keys()) {

                            // Alustame veerust, kus on aknakoht vaba
                            for (let istekohtIndex of Array(this.lend.istekohad[0][0].length).keys()) {
                                if (this.lend.istekohad[rida][veergOffset % 2][istekohtIndex].status === 'available') {
                                    this.lend.istekohad[rida][veergOffset % 2][istekohtIndex].selected = true;
                                    this.mituKohtaValitud++;
                                    if (this.mituKohtaValitud >= this.piletidNr) {
                                        return;
                                    }
                                }
                            }

                            // Kui esimene veerg on täis, siis liigume teise veeru peale
                            // Convert iterator to array with spread operator before calling reverse()
                            for (let istekohtIndex of [...Array(this.lend.istekohad[0][1].length).keys()].reverse()) {
                                if (this.lend.istekohad[rida][(veergOffset + 1) % 2][istekohtIndex].status === 'available') {
                                    this.lend.istekohad[rida][(veergOffset + 1) % 2][istekohtIndex].selected = true;
                                    this.mituKohtaValitud++;
                                    if (this.mituKohtaValitud >= this.piletidNr) {
                                        return;
                                    }
                                }
                            }
                        }


                    } else {

                        // Juhtum, kui kasutaja soovib istekohtade lähedust ja väljapääsu lähedust, kuid ei soovi lisajalaruumi
                        // Seega sobivateks alguskohtadeks on esimene rida ja viimane rida
                        // Liigume ridade vahel edasi tagasi, sisuliselt alustades lennuki otsadest ning liikudes keskele
                        let ridadeLabimiseJarjekordAlgne = Array(this.lend.istekohad.length).keys()
                        ridadeLabimiseJarjekordAlgne = [...ridadeLabimiseJarjekordAlgne];
                        let ridadeLabimineEesots = ridadeLabimiseJarjekordAlgne.slice(0, this.lend.istekohad.length / 2);
                        let ridadeLabimineTagaots = ridadeLabimiseJarjekordAlgne.slice(this.lend.istekohad.length / 2);
                        ridadeLabimineTagaots = [...ridadeLabimineTagaots].reverse();
                        let ridadeLabimiseJarjekord = []
                        let n = Math.max(ridadeLabimineEesots.length, ridadeLabimineTagaots.length);
                        for (let i = 0; i < n; i++) {
                            if (i < ridadeLabimineEesots.length) {
                                ridadeLabimiseJarjekord.push(ridadeLabimineEesots[i]);
                            }
                            if (i < ridadeLabimineTagaots.length) {
                                ridadeLabimiseJarjekord.push(ridadeLabimineTagaots[i]);
                            }
                        }

                        let veergOffset = 0;
                        let ridaIndex = 0;
                        // Leiame sobiva alguskoha, kui kasutaja soovib aknakohta
                        if (this.aken) {
                            // Otsime esimese rea, kus on aknakoht ning tegutseme sealt edasi
                            for (let rida of ridadeLabimiseJarjekord) {

                                // Otsime kõigepealt vasakust veerust aknakohta
                                if (this.lend.istekohad[rida][0][0].status === 'available') {
                                    veergOffset = 0;
                                    ridaIndex = rida;
                                    break;
                                }

                                // Kui vasakus ei olnud, siis otsime paremast veerust
                                if (this.lend.istekohad[rida][0][0].status === 'available') {
                                    veergOffset = 1;
                                    ridaIndex = rida;
                                    break;
                                }
                            }

                            // Kui alguskoht on leitud, siis saame leida ülejäänud kohad selle lähedusest
                            // Kontrollime ridasid aknakoha real ning siis selle ees ja siis selle taga
                            ridadeLabimiseJarjekord = Array(this.lend.istekohad.length).keys()
                            for (let ridaOffset of ridadeLabimiseJarjekord) {
                                // Alustame veerust, kus on aknakoht vaba

                                let eesLeidubRida = ridaIndex - ridaOffset >= 0;
                                let tagaLeidubRida = ridaIndex + ridaOffset < this.lend.istekohad.length;

                                if (!eesLeidubRida && !tagaLeidubRida) {
                                    // Kui ei leidu rohkem ridu, siis lõpetame otsimise
                                    break;
                                }

                                if (eesLeidubRida) {
                                    // Eespool olev rida või aknakohaga rida

                                    // Esimene pool/veerg (kas parem või vasak)
                                    for (let istekohtIndex of Array(this.lend.istekohad[ridaIndex - ridaOffset][veergOffset % 2].length).keys()) {
                                        if (this.lend.istekohad[ridaIndex - ridaOffset][veergOffset % 2][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[ridaIndex - ridaOffset][veergOffset % 2][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            if (this.mituKohtaValitud >= this.piletidNr) {
                                                return;
                                            }
                                        }
                                    }

                                    // Teine pool/veerg (kas parem või vasak)
                                    for (let istekohtIndex of Array(this.lend.istekohad[ridaIndex - ridaOffset][(veergOffset + 1) % 2].length).keys()) {
                                        if (this.lend.istekohad[ridaIndex - ridaOffset][(veergOffset + 1) % 2][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[ridaIndex - ridaOffset][(veergOffset + 1) % 2][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            if (this.mituKohtaValitud >= this.piletidNr) {
                                                return;
                                            }
                                        }
                                    }

                                    
                                }

                                if (tagaLeidubRida && ridaOffset != 0) {
                                    // Tagapool olev rida

                                    // Esimene pool/veerg (kas parem või vasak)
                                    for (let istekohtIndex of Array(this.lend.istekohad[ridaIndex + ridaOffset][veergOffset % 2].length).keys()) {
                                        if (this.lend.istekohad[ridaIndex + ridaOffset][veergOffset % 2][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[ridaIndex + ridaOffset][veergOffset % 2][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            if (this.mituKohtaValitud >= this.piletidNr) {
                                                return;
                                            }
                                        }
                                    }
                                    

                                    // Teine pool/veerg (kas parem või vasak)
                                    for (let istekohtIndex of Array(this.lend.istekohad[ridaIndex + ridaOffset][(veergOffset + 1) % 2].length).keys()) {
                                        if (this.lend.istekohad[ridaIndex + ridaOffset][(veergOffset + 1) % 2][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[ridaIndex + ridaOffset][(veergOffset + 1) % 2][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            if (this.mituKohtaValitud >= this.piletidNr) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else {
                    // Juhtum, kui kasutaja soovib istekohtade lähedust, kuid ei soovi väljapääsu lähedust
                    if (this.jalaruum) {
                        // Juhtum, kui kasutaja soovib istekohtade lähedust ning jalaruumi, kuid ei soovi väljapääsu lähedust
                        
                        //  Jalaruumi on avarii väljapääsu reas ja esimeses reas
                        let jalaruumiRead = [0, Math.floor(this.lend.istekohad.length / 2)];
                        
                        // Alustame esimesest reas, kust leiame vaba koha
                        for (let ridaIndex of jalaruumiRead) {
                            // Otsime reas vaba koha
                            let istekohtLeitud = false;

                            let esimeneIstekohtId = null;
                            
                            // Otsime esmalt aknakohta, kui kasutaja seda soovib
                            if (this.aken) {
                                // Vaatame vasakut aknakohta
                                if (this.lend.istekohad[ridaIndex][0][0].status === 'available') {
                                    this.lend.istekohad[ridaIndex][0][0].selected = true;
                                    this.mituKohtaValitud++;
                                    istekohtLeitud = true;

                                    esimeneIstekohtId = [ridaIndex, 0, 0];
                                }
                                // Vaatame paremat aknakohta
                                else if (this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].status === 'available') {
                                    this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].selected = true;
                                    this.mituKohtaValitud++;
                                    istekohtLeitud = true;
                                    esimeneIstekohtId = [ridaIndex, 1, this.lend.istekohad[ridaIndex][1].length - 1];
                                }
                            } else {
                                // Kui aknakohta ei soovita, otsime mistahes vaba kohta
                                for (let veerg = 0; veerg < 2; veerg++) {
                                    for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[ridaIndex][veerg].length; istekohtIndex++) {
                                        if (this.lend.istekohad[ridaIndex][veerg][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            istekohtLeitud = true;

                                            esimeneIstekohtId = [ridaIndex, veerg, istekohtIndex];
                                            break;
                                        }
                                    }
                                    if (istekohtLeitud) break;
                                }
                            }
                            
                            // Kui leidsime koha, valime lähedalt ülejäänud kohad
                            if (istekohtLeitud) {
                                // Jätame valitud koha vahele ja valime ülejäänud kohad samas reas
                                for (let veerg = 0; veerg < 2; veerg++) {
                                    for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[ridaIndex][veerg].length; istekohtIndex++) {
                                        // Kui pole juba valitud koht
                                        if (!(veerg === esimeneIstekohtId[1] && istekohtIndex === esimeneIstekohtId[2]) && 
                                            this.lend.istekohad[ridaIndex][veerg][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            if (this.mituKohtaValitud >= this.piletidNr) {
                                                return;
                                            }
                                        }
                                    }
                                }
                                
                                // Kui jalaruumi ridades ei ole piisavalt kohti, jätkame teistest ridadest
                                if (this.mituKohtaValitud < this.piletidNr) {
                                    for (let rida = 0; rida < this.lend.istekohad.length; rida++) {
                                        // Jätame jalaruumi read vahele, kuna need juba läbisime
                                        if (jalaruumiRead.includes(rida)) continue;
                                        
                                        for (let veerg = 0; veerg < 2; veerg++) {
                                            for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[rida][veerg].length; istekohtIndex++) {
                                                if (this.lend.istekohad[rida][veerg][istekohtIndex].status === 'available') {
                                                    this.lend.istekohad[rida][veerg][istekohtIndex].selected = true;
                                                    this.mituKohtaValitud++;
                                                    if (this.mituKohtaValitud >= this.piletidNr) {
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                
                                return; // Kui jõudsime siia, siis kohad on leitud või ei ole piisavalt kohti
                            }
                        }
                    } else {
                        // Juhtum, kui kasutaja soovib istekohtade lähedust, kuid ei soovi väljapääsu lähedust ega jalaruumi
                        let ridaLeitud = false;
                        let ridaIndex = 0;
                        
                        // Kui kasutaja soovib aknakohta, otsime rea, kus on vaba aknakoht
                        if (this.aken) {
                            for (let rida = 0; rida < this.lend.istekohad.length; rida++) {
                                if (this.lend.istekohad[rida][0][0].status === 'available' ||
                                    this.lend.istekohad[rida][1][this.lend.istekohad[rida][1].length - 1].status === 'available') {
                                    ridaLeitud = true;
                                    ridaIndex = rida;
                                    break;
                                }
                            }
                        } else {
                            // Kui aknakohta ei soovita, otsime mistahes rida, kus on piisavalt vabu kohti kõrvuti
                            for (let rida = 0; rida < this.lend.istekohad.length; rida++) {
                                let vabuKohti = 0;
                                for (let veerg = 0; veerg < 2; veerg++) {
                                    for (let koht of this.lend.istekohad[rida][veerg]) {
                                        if (koht.status === 'available') vabuKohti++;
                                    }
                                }
                                if (vabuKohti >= this.piletidNr) {
                                    ridaLeitud = true;
                                    ridaIndex = rida;
                                    break;
                                }
                            }
                        }
                        
                        if (ridaLeitud) {
                            // Valitud reas valime kohad
                            // Kui kasutaja soovib aknakohta, alustame sellest
                            if (this.aken) {
                                if (this.lend.istekohad[ridaIndex][0][0].status === 'available') {
                                    this.lend.istekohad[ridaIndex][0][0].selected = true;
                                    this.mituKohtaValitud++;
                                }
                                else if (this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].status === 'available') {
                                    this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].selected = true;
                                    this.mituKohtaValitud++;
                                }
                            }
                            
                            // Valime ülejäänud kohad valitud reast
                            for (let veerg = 0; veerg < 2; veerg++) {
                                for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[ridaIndex][veerg].length; istekohtIndex++) {
                                    if (this.lend.istekohad[ridaIndex][veerg][istekohtIndex].status === 'available' && 
                                        !this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected) {
                                        this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected = true;
                                        this.mituKohtaValitud++;
                                        if (this.mituKohtaValitud >= this.piletidNr) {
                                            return;
                                        }
                                    }
                                }
                            }
                            
                            // Kui valitud reas pole piisavalt kohti, jätkame järgmistest ridadest
                            for (let rida = 0; rida < this.lend.istekohad.length; rida++) {
                                if (rida === ridaIndex) continue; // Jätame valitud rea vahele
                                
                                for (let veerg = 0; veerg < 2; veerg++) {
                                    for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[rida][veerg].length; istekohtIndex++) {
                                        if (this.lend.istekohad[rida][veerg][istekohtIndex].status === 'available') {
                                            this.lend.istekohad[rida][veerg][istekohtIndex].selected = true;
                                            this.mituKohtaValitud++;
                                            if (this.mituKohtaValitud >= this.piletidNr) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // Kui ei leidu reas piisavalt kohti, siis valime lihtsalt järjest vabu kohti
                            this.valiVabadKohad();
                        }
                    }
                }

            } else {
                // Juhtum, kui kasutaja ei soovi istekohtade lähedust
                if (this.valjapaas) {
                    // Kasutaja soovib istekohti väljapääsu lähedal
                    let valjapaasuRead = [0, this.lend.istekohad.length - 1, Math.floor(this.lend.istekohad.length / 2)];
                    
                    // Kui soovitakse jalaruumi, prioritiseerime vastavad read
                    if (this.jalaruum) {
                        valjapaasuRead = [0, Math.floor(this.lend.istekohad.length / 2)];
                    }
                    
                    // Läbime väljapääsu lähedal olevad read
                    for (let ridaIndex of valjapaasuRead) {
                        // Aknakoha kontroll
                        if (this.aken) {
                            // Vasakpoolne aknakoht
                            if (this.lend.istekohad[ridaIndex][0][0].status === 'available') {
                                this.lend.istekohad[ridaIndex][0][0].selected = true;
                                this.mituKohtaValitud++;
                                if (this.mituKohtaValitud >= this.piletidNr) return;
                            }
                            // Parempoolne aknakoht
                            if (this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].status === 'available') {
                                this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].selected = true;
                                this.mituKohtaValitud++;
                                if (this.mituKohtaValitud >= this.piletidNr) return;
                            }
                        }
                        
                        // Ülejäänud kohad väljapääsu lähedal
                        for (let veerg = 0; veerg < 2; veerg++) {
                            for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[ridaIndex][veerg].length; istekohtIndex++) {
                                // Aknakohtade puhul kontrolli, kas on juba valitud
                                if ((veerg === 0 && istekohtIndex === 0) || 
                                    (veerg === 1 && istekohtIndex === this.lend.istekohad[ridaIndex][veerg].length - 1)) {
                                    if (this.aken && this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected) {
                                        continue; // Jätame vahele, kui aknakoht on juba valitud
                                    }
                                }
                                
                                if (this.lend.istekohad[ridaIndex][veerg][istekohtIndex].status === 'available' && 
                                    !this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected) {
                                    this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected = true;
                                    this.mituKohtaValitud++;
                                    if (this.mituKohtaValitud >= this.piletidNr) return;
                                }
                            }
                        }
                    }
                    
                    // Kui pole piisavalt kohti väljapääsude lähedal, valime muud vabad kohad
                    this.valiVabadKohad();
                } else if (this.jalaruum) {
                    // Kasutaja soovib jalaruumi, kuid ei hooli teistest tingimustest
                    let jalaruumiRead = [0, Math.floor(this.lend.istekohad.length / 2)];
                    
                    for (let ridaIndex of jalaruumiRead) {
                        // Aknakoha kontroll
                        if (this.aken) {
                            // Vasakpoolne aknakoht
                            if (this.lend.istekohad[ridaIndex][0][0].status === 'available') {
                                this.lend.istekohad[ridaIndex][0][0].selected = true;
                                this.mituKohtaValitud++;
                                if (this.mituKohtaValitud >= this.piletidNr) return;
                            }
                            // Parempoolne aknakoht
                            if (this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].status === 'available') {
                                this.lend.istekohad[ridaIndex][1][this.lend.istekohad[ridaIndex][1].length - 1].selected = true;
                                this.mituKohtaValitud++;
                                if (this.mituKohtaValitud >= this.piletidNr) return;
                            }
                        }
                        
                        // Valime ülejäänud kohad jalaruumi ridadest
                        for (let veerg = 0; veerg < 2; veerg++) {
                            for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[ridaIndex][veerg].length; istekohtIndex++) {
                                if (this.lend.istekohad[ridaIndex][veerg][istekohtIndex].status === 'available' && 
                                    !this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected) {
                                    this.lend.istekohad[ridaIndex][veerg][istekohtIndex].selected = true;
                                    this.mituKohtaValitud++;
                                    if (this.mituKohtaValitud >= this.piletidNr) return;
                                }
                            }
                        }
                    }
                    
                    // Kui jalaruumi ridadel pole piisavalt kohti, valime muud vabad kohad
                    this.valiVabadKohad();
                } else if (this.aken) {
                    // Kasutaja soovib ainult aknakohta
                    for (let rida = 0; rida < this.lend.istekohad.length; rida++) {
                        // Vasakpoolne aknakoht
                        if (this.lend.istekohad[rida][0][0].status === 'available') {
                            this.lend.istekohad[rida][0][0].selected = true;
                            this.mituKohtaValitud++;
                            if (this.mituKohtaValitud >= this.piletidNr) return;
                        }
                        // Parempoolne aknakoht
                        if (this.lend.istekohad[rida][1][this.lend.istekohad[rida][1].length - 1].status === 'available') {
                            this.lend.istekohad[rida][1][this.lend.istekohad[rida][1].length - 1].selected = true;
                            this.mituKohtaValitud++;
                            if (this.mituKohtaValitud >= this.piletidNr) return;
                        }
                    }
                    
                    // Kui pole piisavalt aknakohti, valime muud vabad kohad
                    this.valiVabadKohad();
                    return;
                } else {
                    // Kasutajal pole eelistusi, valime lihtsalt vabad kohad
                    this.valiVabadKohad();
                    return;
                }
            }

            // Kui oleme jõudnud siia, siis ei ole sobivaid kohti leitud
            alert('Sobivaid kohti ei leitud.');
            this.eemaldaValikud();
        },
        
        // Abifunktsioon vabade kohtade valimiseks
        valiVabadKohad() {
            for (let rida = 0; rida < this.lend.istekohad.length; rida++) {
                for (let veerg = 0; veerg < 2; veerg++) {
                    for (let istekohtIndex = 0; istekohtIndex < this.lend.istekohad[rida][veerg].length; istekohtIndex++) {
                        if (this.lend.istekohad[rida][veerg][istekohtIndex].status === 'available' && 
                            !this.lend.istekohad[rida][veerg][istekohtIndex].selected) {
                            this.lend.istekohad[rida][veerg][istekohtIndex].selected = true;
                            this.mituKohtaValitud++;
                            if (this.mituKohtaValitud >= this.piletidNr) return;
                        }
                    }
                }
            }
        },
        
        toggleIstekohtParem(ridaIndex, istekohtIndex) {

            if (this.lend.istekohad[ridaIndex][1][istekohtIndex].status === 'occupied') {
                return;
            }

            let istekohaVaartus = this.lend.istekohad[ridaIndex][1][istekohtIndex].selected;

            // Kontrollime, kas kohtade valik sobitub piletite numbriga
            if (istekohaVaartus === false && this.mituKohtaValitud >= this.piletidNr) {
                // Juhul kui kasutaja proovib valida uut kohta, kuigi piletide arv on täis
                return;
            }

            this.lend.istekohad[ridaIndex][1][istekohtIndex].selected = !this.lend.istekohad[ridaIndex][1][istekohtIndex].selected;

            if (istekohaVaartus === false) {
                this.mituKohtaValitud++;
            } else {
                this.mituKohtaValitud--;
            }

        },
        toggleIstekohtVasak(ridaIndex, istekohtIndex) {

            if (this.lend.istekohad[ridaIndex][0][istekohtIndex].status === 'occupied') {
                return;
            }

            let istekohaVaartus = this.lend.istekohad[ridaIndex][0][istekohtIndex].selected;

            // Kontrollime, kas kohtade valik sobitub piletite numbriga
            if (istekohaVaartus === false && this.mituKohtaValitud >= this.piletidNr) {
                // Juhul kui kasutaja proovib valida uut kohta, kuigi piletide arv on täis
                return;
            }

            this.lend.istekohad[ridaIndex][0][istekohtIndex].selected = !this.lend.istekohad[ridaIndex][0][istekohtIndex].selected;

            if (istekohaVaartus === false) {
                this.mituKohtaValitud++;
            } else {
                this.mituKohtaValitud--;
            }

        },
        fetchLend(id) {
            fetch(`http://localhost:3000/api/lend?id=${id}`)
                .then((response) => response.json())
                .then((data) => (this.lend = data))
                .catch((err) => console.log(err.message));
        },
    },
    mounted() {
        this.fetchLend(this.$route.params.id);
        if (this.$route.params.piletidNr) {
            this.piletidNr = this.$route.params.piletidNr;
        }
    },
}
</script>


<style scoped>
.seat-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px;
}

.lend {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.istekoht-rida {
    display: flex;
    gap: 10px;
}

.rea-number {
    width: 40px;
    height: 40px;

    border-width: 5px;
    border-color: black;
}

.istekoht {
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 5px;
    cursor: pointer;
    font-weight: bold;
    transition: 0.2s;
}

.vahe {

    margin-bottom: 0px;
}

.available {
    background-color: lightgreen;
}

.occupied {
    background-color: gray;
    pointer-events: none;
}

.selected {
    background-color: orange;
}

.selected-seats {
    margin-top: 10px;
}

/* Istekoha valija stiilid */
.istekoha-valija {
    text-align: left;
    max-width: 540px;
    background-color: #f8f9fa;
    border-radius: 8px;
    padding: 24px;
    margin: 0 auto 30px auto;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.istekoha-valija .inputs {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 25px 40px;
    margin-bottom: 30px;
}

@media (max-width: 768px) {
    .istekoha-valija .inputs {
        grid-template-columns: 1fr;
    }
}

.istekoha-valija .inputs > div {
    display: flex;
    flex-direction: column;
    min-height: 80px;
}

.istekoha-valija .piletidNr {
    grid-column: span 2;
    line-height: 45px;
}

@media (max-width: 768px) {
    .istekoha-valija .piletidNr {
        grid-column: span 1;
    }
}

.istekoha-valija label {
    font-weight: 600;
    margin-bottom: 10px;
    color: #333;
    font-size: 0.95rem;
}

.istekoha-valija input[type="checkbox"] {
    margin-top: 5px;
    width: 20px;
    height: 20px;
    cursor: pointer;
}

.istekoha-valija input[type="range"] {
    -webkit-appearance: none;
    width: 100%;
    height: 6px;
    background: #ddd;
    border-radius: 5px;
    padding: 0;
    margin-top: 8px;
}

.istekoha-valija input[type="range"]::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 18px;
    height: 18px;
    background: #4a90e2;
    border-radius: 50%;
    cursor: pointer;
}

.istekoha-valija input[type="range"]::-moz-range-thumb {
    width: 18px;
    height: 18px;
    background: #4a90e2;
    border-radius: 50%;
    cursor: pointer;
    border: none;
}

.istekoha-valija .esita {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.istekoha-valija button {
    background-color: #4a90e2;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 12px 30px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.2s ease;
}

.istekoha-valija button:hover {
    background-color: #3a7bbf;
}

/* Lennu info stiilid */
.lend-info {
    max-width: 540px;
    background-color: #f8f9fa;
    border-radius: 8px;
    padding: 24px;
    margin: 0 auto 30px auto;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
    text-align: left;
}

.lend-info h1 {
    margin-top: 0;
    font-size: 1.8rem;
    color: #333;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
    padding-bottom: 15px;
}

.lend-details {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 15px 40px;
}

@media (max-width: 768px) {
    .lend-details {
        grid-template-columns: 1fr;
    }
}

.detail {
    display: flex;
    flex-direction: row;
    align-items: center;
}

.detail .label {
    font-weight: 600;
    color: #666;
    margin-right: 10px;
    min-width: 110px;
}

.detail .value {
    color: #333;
    font-size: 1.1rem;
}

/* Jätkab olemasolevate stilidega */

.eemalda-valikud {
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.eemalda-valikud button {
    background-color: #4a90e2;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 12px 30px;
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.2s ease;
}

.eemalda-valikud button:hover {
    background-color: #3a7bbf;
}

/* Lennu info stiilid */
</style>