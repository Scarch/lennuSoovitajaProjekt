<template>
    <div class="filters">
            <div class="inputs">
                <div class="kust">
                    <label for="kust">Kust?</label>
                    <input list="kust" name="kust" required v-model="kust" />
                    <datalist id="kust">
                        <option value="Tallinn" />
                        <option value="London" />
                        <option value="Pariis" />
                        <option value="Riia" />
                        <option value="Vilnius" />
                    </datalist>
                </div>
                <div class="kuhu">
                    <label for="kuhu">Kuhu?</label>
                    <input list="kuhu" name="kuhu" required v-model="kuhu">
                    <datalist id="kuhu">
                        <option value="Tallinn" />
                        <option value="London" />
                        <option value="Pariis" />
                        <option value="Riia" />
                        <option value="Vilnius" />
                    </datalist>
                </div>
                <div class="kuupäev">
                    <label for="kuupäev">Kuupäev</label>
                    <input type="date" name="kuupäev" required v-model="kuupäev">
                </div>
                <div class="lennuaeg">
                    <label for="lennuaeg">Lennuaja piirang</label>
                    <input type="range" name="lennuaeg" required v-model="lennuaeg" min="1" max="20" step="1">
                    {{ lennuaeg }}h
                </div>
                <div class="hind">
                    <label for="hind">Hinna piirang</label>
                    <input type="range" name="hind" required v-model="hind" min="100" max="2000" step="25">
                    {{ hind }}€
                </div>
                <div class="piletidNr">
                    <label for="piletidNr">Kogus pileteid</label>
                    <input type="range" name="piletidNr" required v-model="piletidNr" min="1" max="10" step="1">
                    {{ piletidNr }}
                </div>
            </div>
            <div class="esita">
                <button @click="LeiaLennud()">Leia Lennud</button>
            </div>
    </div>
    <div v-if="lennud.length!=0" class="lennud">
        <LendComponent v-for="lend in lennud" :key="lend.id" :lend="lend" @click="routeLend(lend.id)" class="lend" />
    </div>
    <div v-else>
        <p>Lennupileteid ei leitud või otsingut pole alustatud</p>
    </div>
</template>

<script>
import LendComponent from '@/components/LendComponent.vue'

export default {
    name: 'LennuSoovitajaView',
    data() {
        return {
            linnad: ['Tallinn', 'London', 'Pariis', 'Riia', 'Vilnius'],
            kust: '',
            kuhu: '',
            kuupäev: '',
            lennuaeg: 1,
            lennud: [
                /*
                {
                    id: 1,
                    kust: 'Tallinn',
                    kuhu: 'London',
                    kuupäev: '2021-12-24',
                    algus: '12:00',
                    lopp: '14:00',
                    hind: 200,
                },
                {
                    id: 2,
                    kust: 'Tallinn',
                    kuhu: 'Pariis',
                    kuupäev: '2021-12-24',
                    algus: '12:00',
                    lopp: '14:00',
                    hind: 100,
                },
                {
                    id: 3,
                    kust: 'Tallinn',
                    kuhu: 'Riia',
                    kuupäev: '2021-12-24',
                    algus: '12:00',
                    lopp: '14:00',
                    hind: 150,
                },
                {
                    id: 4,
                    kust: 'Tallinn',
                    kuhu: 'Vilnius',
                    kuupäev: '2021-12-24',
                    algus: '12:00',
                    lopp: '14:00',
                    hind: 100,
                },
                */
            ],
            hind: 100,
            piletidNr: 1
        }
    },
    components: {
        LendComponent
    },
    methods: {
        routeLend(id) {
            // Suuna kasutaja valitud lennu detailvaatesse
            this.$router.push("/lennud/" + id + "/" + this.piletidNr);
        },
        LeiaLennud() {
            // Kontrolli sisendandmete õigsust
            let kustCheck = !this.linnad.includes(this.kust);
            let kuhuCheck = !this.linnad.includes(this.kuhu);
            let kustKuhuCheck = this.kust === this.kuhu;
            let kuupäevCheck = this.kuupäev === '';
            let lennuaegCheck = this.lennuaeg === 0;
            let hindCheck = this.hind === 0;
            let kuupaevMinevikusCheck = new Date(this.kuupäev) < new Date();

            if (kustCheck || kuhuCheck || kustKuhuCheck || kuupäevCheck || lennuaegCheck || hindCheck || kuupaevMinevikusCheck) {
                
                let errorText = "Vigased andmed:";
                if (kustCheck) {
                    errorText += "\nKust ei saa olla '" + this.kust + "'.";
                }
                if (kuhuCheck) {
                    errorText += "\nKuhu ei saa olla '" + this.kuhu + "'.";
                }
                if (kustKuhuCheck) {
                    errorText += "\nKust ja Kuhu ei saa olla samad.";
                }
                if (kuupäevCheck) {
                    errorText += "\nKuupäev ei saa olla tühi.";
                }
                if (lennuaegCheck) {
                    errorText += "\nLennuaeg ei saa olla 0.";
                }
                if (hindCheck) {
                    errorText += "\nHind ei saa olla 0. ";
                }
                if (kuupaevMinevikusCheck) {
                    errorText += "\nKuupäev ei saa olla minevikus.";
                }

                alert(errorText);

                return;
            }

            // Kasutades fetch'i, leiame lennud, mis vastavad esitatud filtritele
            fetch(`http://localhost:3000/api/lennud?kust=${this.kust}&kuhu=${this.kuhu}&kuupaev=${this.kuupäev}&lennuaeg=${this.lennuaeg}&hind=${this.hind}&piletidNr=${this.piletidNr}`, {

            })
                .then((response) => response.json())
                .then((data) => {
                    console.log(data);
                    console.log(this.lennud);
                    this.lennud = data;
                })
                .catch((e) => {
                    console.log(e);
                });

        },
    }
}
</script>

<style scoped>

.lend {

    background-color: #f8f9fa;
}

/* Filtrite üldine konteiner, keskel joondatud */
.filters {
    max-width: 540px;
    background-color: #f8f9fa;
    border-radius: 8px;
    padding: 24px;
    margin: 0 auto 30px auto; /* Horisontaalselt keskel automaatsete vasak/parem ääristega */
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

/* Sisendväljade ruudustik, kahe veeruga */
.inputs {
    align-items: center;
    display: grid;
    /* Muudetud auto-fit'ist fikseeritud kahe veeru paigutuseks */
    grid-template-columns: 1fr 1fr;
    gap: 25px 40px; /* Suurendatud vertikaalne ja horisontaalne vahe */
    margin-bottom: 30px;
}

/* Väiksematel ekraanidel ühe veeruna kuvamine */
@media (max-width: 768px) {
    .inputs {
        grid-template-columns: 1fr;
    }
}

.inputs > div {
    display: flex;
    flex-direction: column;
    /* Lisa ühtlane kõrgus sisendkonteineritele */
    min-height: 80px;
}

/* Vahemiku liugurid täislaiusega */
.lennuaeg, .hind, .piletidNr {
    grid-column: span 2;
}

/* Väiksemate ekraanide puhul kõik elemendid täislaiuses */
@media (max-width: 768px) {
    .lennuaeg, .hind, .piletidNr {
        grid-column: span 1;
    }
}

label {
    font-weight: 600;
    margin-bottom: 10px; /* Veidi suurendatud */
    color: #333;
    font-size: 0.95rem;
}

input {
    padding: 12px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-size: 1rem;
    transition: all 0.2s ease;
    /* Tagab ühtlase kõrguse */
    height: 45px;
    box-sizing: border-box;
}

input[type="date"] {
    font-family: inherit;
}

input:focus {
    border-color: #4a90e2;
    box-shadow: 0 0 0 2px rgba(74, 144, 226, 0.2);
    outline: none;
}


.lennuaeg input, .hind input {
    margin-bottom: 8px;
}

/* Vahemiku väärtuste kuvamise stiil */
.lennuaeg, .hind, .piletidNr {
    display: flex;
    flex-direction: column;
}

/* Lisa kujundatud kuvamine vahemiku väärtustele */
.lennuaeg::after, .hind::after {
    content: attr(data-value);
    display: inline-block;
}

.esita {
    display: flex;
    justify-content: center;
    margin-top: 20px; /* Suurendatud */
}

button {
    background-color: #4a90e2;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 12px 30px; /* Veidi laiem */
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.2s ease;
}

button:hover {
    background-color: #3a7bbf;
}

.lennud {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
}

/* Kohandatud stiilid vahemiku liuguritele */
input[type="range"] {
    -webkit-appearance: none;
    width: 100%;
    height: 6px;
    background: #ddd;
    border-radius: 5px;
}

input[type="range"] {
    padding: 0;
    margin-top: 8px;
}

input[type="range"]::-webkit-slider-thumb {
    -webkit-appearance: none;
    appearance: none;
    width: 18px;
    height: 18px;
    background: #4a90e2;
    border-radius: 50%;
    cursor: pointer;
}

input[type="range"]::-moz-range-thumb {
    width: 18px;
    height: 18px;
    background: #4a90e2;
    border-radius: 50%;
    cursor: pointer;
    border: none;
}

/* Vahemiku väärtuste kuvamise parandamine */
.lennuaeg, .hind, .piletidNr {
    position: relative;
}

.lennuaeg::after, .hind::after, .piletidNr::after {
    position: absolute;
    right: 0;
    top: 0;
    font-size: 0.9rem;
    color: #666;
}
</style>