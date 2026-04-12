<template>
  <div class="map-widget glass-panel">
    <div class="header">
      <h3 class="card-title">Live Fleet Location</h3>
      <div class="map-actions">
        <button class="action-btn">+ New Vehicle</button>
        <button class="action-btn secondary">+ New Shipment</button>
      </div>
    </div>
    <div id="map" ref="mapElement"></div>
    <div class="map-overlay" v-if="latency">
       <span class="latency" :class="{ high: latency > 200 }">● Live ({{ latency }}ms)</span>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

const props = defineProps({
  parcels: Array,
  latency: Number
});

const mapElement = ref(null);
const map = ref(null);
const markers = ref({});
const routingControls = ref({});

const center = [12.9716, 77.5946];
const destinations = {
  'P001': [12.9716 + 0.03, 77.5946 + 0.03],
  'P002': [12.9716 - 0.03, 77.5946 + 0.03]
};

const updateMarker = (parcel) => {
  if (!map.value) return;
  const [lng, lat] = parcel.currentLocation.coordinates;
  const id = parcel.parcelId;

  if (markers.value[id]) {
    markers.value[id].setLatLng([lat, lng]);
  } else {
    markers.value[id] = L.marker([lat, lng], {
      icon: L.icon({
        iconUrl: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
        iconSize: [32, 32],
        iconAnchor: [16, 32]
      })
    }).addTo(map.value).bindPopup(`Parcel: ${id}`);
  }

  // Update routing line
  const dest = destinations[id] || [lat + 0.01, lng + 0.01];
  if (!routingControls.value[id]) {
    routingControls.value[id] = L.polyline([[lat, lng], dest], {
      color: '#3b82f6', weight: 2, opacity: 0.4, dashArray: '5, 10'
    }).addTo(map.value);
  } else {
    routingControls.value[id].setLatLngs([[lat, lng], dest]);
  }
};

watch(() => props.parcels, (newParcels) => {
  newParcels.forEach(updateMarker);
}, { deep: true });

onMounted(() => {
  map.value = L.map(mapElement.value, {
    zoomControl: false,
    attributionControl: false
  }).setView(center, 13);

  L.tileLayer('https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png').addTo(map.value);
  L.control.zoom({ position: 'bottomright' }).addTo(map.value);

  // Initial update for any parcels already loaded
  props.parcels.forEach(updateMarker);
});
</script>

<style scoped>
.map-widget {
  height: 500px;
  display: flex;
  flex-direction: column;
  position: relative;
  overflow: hidden;
}

.header {
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  z-index: 10;
}

.map-actions {
  display: flex;
  gap: 12px;
}

.action-btn {
  padding: 6px 12px;
  border-radius: 20px;
  border: none;
  font-size: 0.75rem;
  font-weight: 600;
  cursor: pointer;
  background: white;
  color: black;
}

.action-btn.secondary {
  background: var(--glass-bg);
  color: var(--text-primary);
  border: 1px solid var(--glass-border);
}

#map {
  flex: 1;
  width: 100%;
  z-index: 1;
}

.map-overlay {
  position: absolute;
  bottom: 20px;
  left: 20px;
  z-index: 10;
}

.latency {
  background: rgba(0,0,0,0.6);
  backdrop-filter: blur(4px);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  color: var(--success);
}

.latency.high {
  color: var(--error);
}
</style>
