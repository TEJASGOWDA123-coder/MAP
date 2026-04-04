<script setup>
import MapComponent from './components/MapComponent.vue'
</script>

<template>
  <main>
    <MapComponent />
  </main>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;600&display=swap');

html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow: hidden;
  font-family: 'Outfit', sans-serif;
  background-color: #f0f2f5;
  color: #2c3e50;
}

main {
  height: 100vh;
  width: 100vw;
}
</style>
