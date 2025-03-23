<template>
    <div class="lend-component">
        <h1>Lend: {{ lend.kust }} - {{ lend.kuhu }}</h1>
        <p>Algus: {{ lend.algus }}</p>
        <p>Lõpp: {{ lend.lopp }}</p>
        <p>Kuupäev: {{ lend.kuupaev }}</p>
        <p>Pileti hind: {{ lend.hind }}€</p>
    </div>
</template>
  
<script>
export default {
    name: 'LendComponent',
    props: [
      'lend'
    ],
};
</script>

<style scoped>

</style>
