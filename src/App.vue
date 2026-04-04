<script setup>
import { ref } from 'vue'
import MapComponent from './components/MapComponent.vue'
import DriverView from './components/DriverView.vue'

const currentView = ref('dashboard')
</script>

<template>
  <div class="view-toggle">
    <button @click="currentView = 'dashboard'" :class="{ active: currentView === 'dashboard' }">Logistics Hub</button>
    <button @click="currentView = 'driver'" :class="{ active: currentView === 'driver' }">Driver View</button>
  </div>

  <main>
    <MapComponent v-if="currentView === 'dashboard'" />
    <DriverView v-if="currentView === 'driver'" />
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

.view-toggle {
  position: absolute;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2000;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  padding: 5px;
  border-radius: 30px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  display: flex;
  gap: 5px;
}

.view-toggle button {
  padding: 8px 20px;
  border: none;
  border-radius: 25px;
  background: transparent;
  cursor: pointer;
  font-family: 'Outfit', sans-serif;
  font-weight: 600;
  color: #7f8c8d;
  transition: all 0.3s;
}

.view-toggle button.active {
  background: #1a2a6c;
  color: white;
}

main {
  height: 100vh;
  width: 100vw;
}
</style>
