<template>
    <div class="lend">
        <h1>Lend: {{ lend.kust }}</h1>
        <p>Kust: {{ lend.kust }}</p>
        <p>Algus: {{ lend.algus }}</p>
        <p>Kuhu: {{ lend.kuhu }}</p>
        <p>Saabumisaeg: {{ lend.lopp }}</p>
        <p>Kuupäev: {{ lend.kuupäev }}</p>
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
        <div class="vahe"></div>
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
    </div>
</template>

<script>

export default {
    name: 'LendView',
    data() {
        return {
            lend: {
                id: 0,
                kust: '',
                kuhu: '',
                kuupäev: '',
                algus: '',
                lõpp: '',
                istekohad: [
                    [[{ label: 'A', status: 'available', selected: false }, { label: 'B', status: 'occupied', selected: false }, { label: 'C', status: 'available', selected: false }], [{ label: 'D', status: 'available', selected: false }, { label: 'F', status: 'occupied', selected: false }, { label: 'G', status: 'available', selected: false }]],
                    [[{ label: 'A', status: 'available', selected: false }, { label: 'B', status: 'occupied', selected: false }, { label: 'C', status: 'available', selected: false }], [{ label: 'D', status: 'available', selected: false }, { label: 'F', status: 'occupied', selected: false }, { label: 'G', status: 'available', selected: false }]]
                ]
            }
        };
    },
    components: {

    },
    methods: {
        toggleIstekohtParem(ridaIndex, istekohtIndex) {
            this.lend.istekohad[ridaIndex][1][istekohtIndex].selected = !this.lend.istekohad[ridaIndex][1][istekohtIndex].selected;
        },
        toggleIstekohtVasak(ridaIndex, istekohtIndex) {
            this.lend.istekohad[ridaIndex][0][istekohtIndex].selected = !this.lend.istekohad[ridaIndex][0][istekohtIndex].selected;
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
    width: 40px;

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
</style>