<template>
  <div class="map-container">
    <div id="map" ref="mapElement"></div>
    <div class="controls">
      <h3>Logistics Tracking</h3>
      <p>Radius: 5km</p>
      <p :class="{ 'latency-ok': latency < 200, 'latency-bad': latency >= 200 }">
        Latency: {{ latency }}ms
      </p>
      <button @click="fetchParcels" :disabled="loading">
        {{ loading ? 'Searching...' : 'Refresh Parcels' }}
      </button>
      <div v-if="parcels.length > 0" class="parcel-list">
        <h4>Parcels within 5km:</h4>
        <ul>
          <li v-for="parcel in parcels" :key="parcel.id">
            <strong>{{ parcel.parcelId }}</strong>: {{ parcel.status }}
          </li>
        </ul>
      </div>
      <p v-else-if="!loading">No parcels found in this area.</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import ParcelService from '../services/ParcelService';
import WebSocketService from '../services/WebSocketService';

const mapElement = ref(null);
const map = ref(null);
const parcels = ref([]);
const loading = ref(false);
const markers = ref({}); // Use an object for quick marker lookup by ID
const latency = ref(0);

// Center: Bangalore (matches DataSeeder)
const center = [12.9716, 77.5946];

onMounted(() => {
  // Initialize map
  map.value = L.map(mapElement.value).setView(center, 13);

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map.value);

  // Add 5km radius circle
  L.circle(center, {
    color: '#3498db',
    fillColor: '#3498db',
    fillOpacity: 0.1,
    radius: 5000 // 5km
  }).addTo(map.value);

  // Add center marker
  L.marker(center).addTo(map.value)
    .bindPopup('City Center (Bangalore)')
    .openPopup();

  fetchParcels();

  // Connect WebSocket
  WebSocketService.connect();
  const unsubscribe = WebSocketService.subscribe((update) => {
    handleLocationUpdate(update);
  });

  onUnmounted(() => {
    unsubscribe();
    WebSocketService.disconnect();
  });
});

const handleLocationUpdate = (update) => {
  const { parcelId, coordinates, timestamp } = update;
  const [lng, lat] = coordinates;

  // Calculate Latency
  latency.value = Date.now() - timestamp;
  if (latency.value > 200) {
    console.warn(`High Latency: ${latency.value}ms for parcel ${parcelId}`);
  }

  // Update or Create Marker
  if (markers.value[parcelId]) {
    markers.value[parcelId].setLatLng([lat, lng]);
  } else {
    const marker = L.marker([lat, lng], {
      icon: L.icon({
        iconUrl: 'https://cdn-icons-png.flaticon.com/512/684/684908.png',
        iconSize: [32, 32],
        iconAnchor: [16, 32]
      })
    })
    .addTo(map.value)
    .bindPopup(`Parcel: ${parcelId}<br>Status: Moving Real-Time`);
    
    markers.value[parcelId] = marker;
  }

  // Update parcel list state for UI
  const existing = parcels.value.find(p => p.parcelId === parcelId);
  if (existing) {
    existing.currentLocation.coordinates = [lng, lat];
  } else {
    parcels.value.push({
      parcelId,
      status: 'In Transit (Live)',
      currentLocation: { type: 'Point', coordinates: [lng, lat] }
    });
  }
};

const fetchParcels = async () => {
  loading.value = true;
  try {
    const response = await ParcelService.getNearbyParcels(center[1], center[0], 5);
    response.data.forEach(p => handleLocationUpdate({
        parcelId: p.parcelId,
        coordinates: p.currentLocation.coordinates,
        timestamp: Date.now()
    }));
  } catch (error) {
    console.error('Error fetching parcels:', error);
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.map-container {
  position: relative;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

#map {
  height: 100%;
  width: 100%;
}

.controls {
  position: absolute;
  top: 20px;
  right: 20px;
  width: 320px;
  z-index: 1000;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(12px);
  padding: 24px;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.3);
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.15);
}

.controls h3 {
  margin-top: 0;
  color: #1a2a6c;
  font-weight: 600;
}

button {
  width: 100%;
  padding: 12px;
  background: linear-gradient(135deg, #1a2a6c, #b21f1f, #fdbb2d);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  transition: transform 0.2s, opacity 0.2s;
}

button:hover {
  transform: translateY(-2px);
  opacity: 0.9;
}

button:disabled {
  background: #bdc3c7;
  transform: none;
}

.latency-ok {
  color: #27ae60;
  font-weight: 600;
}

.latency-bad {
  color: #e74c3c;
  font-weight: 600;
}

.parcel-list {
  margin-top: 20px;
  max-height: 400px;
  overflow-y: auto;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  padding: 12px 0;
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
  font-size: 0.95rem;
}

li:last-child {
  border-bottom: none;
}
</style>
