<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import WebSocketService from '../services/WebSocketService'

const parcelIds = ['P001', 'P002', 'P003']
const parcelId = ref('P001')
const location = ref({ lat: 0, lng: 0 })
const latency = ref(0)
const status = ref('Initializing...')

onMounted(() => {
  WebSocketService.connect();
  const unsubscribe = WebSocketService.subscribe((update) => {
    if (update.parcelId === parcelId.value) {
      const [lng, lat] = update.coordinates;
      location.value = { lat, lng };
      latency.value = Date.now() - update.timestamp;
      status.value = 'On Route (Live)';
    }
  });

  onUnmounted(() => {
    unsubscribe();
    WebSocketService.disconnect();
  });
});

const isSigning = ref(false)
const signatureCanvas = ref(null)

const handleParcelChange = () => {
  status.value = 'Initializing...';
  location.value = { lat: 0, lng: 0 };
  latency.value = 0;
  isSigning.value = false;
};

const startSigning = () => {
  isSigning.value = true;
};

const confirmDelivery = () => {
  status.value = 'DELIVERED';
  isSigning.value = false;
  // In a real app, we would POST the signature to the backend here
};

const clearSignature = () => {
  const canvas = signatureCanvas.value;
  const ctx = canvas.getContext('2d');
  ctx.clearRect(0, 0, canvas.width, canvas.height);
};

const handleMouseDown = (e) => {
  const canvas = signatureCanvas.value;
  const ctx = canvas.getContext('2d');
  ctx.beginPath();
  ctx.moveTo(e.offsetX, e.offsetY);
  canvas.isDrawing = true;
};

const handleMouseMove = (e) => {
  const canvas = signatureCanvas.value;
  if (!canvas.isDrawing) return;
  const ctx = canvas.getContext('2d');
  ctx.lineTo(e.offsetX, e.offsetY);
  ctx.stroke();
};

const handleMouseUp = () => {
  signatureCanvas.value.isDrawing = false;
};
</script>

<template>
  <div class="driver-view">
    <div class="mobile-card">
      <div class="status-badge" :class="{ 'live': status === 'On Route (Live)', 'delivered': status === 'DELIVERED' }">
        {{ status }}
      </div>
      
      <div v-if="status !== 'DELIVERED' && !isSigning" class="parcel-selector">
        <label>Select Your Parcel:</label>
        <select v-model="parcelId" @change="handleParcelChange">
          <option v-for="id in parcelIds" :key="id" :value="id">{{ id }}</option>
        </select>
      </div>
      
      <h1>Active Tracking: {{ parcelId }}</h1>
      
      <div class="stat-row">
        <label>Current Location:</label>
        <span>{{ location.lat.toFixed(4) }}, {{ location.lng.toFixed(4) }}</span>
      </div>
      
      <div class="stat-row">
        <label>Latency:</label>
        <span :class="latency > 200 ? 'red' : 'green'">{{ latency }}ms</span>
      </div>

      <div v-if="status === 'On Route (Live)' && !isSigning" class="actions">
        <button class="delivery-btn" @click="startSigning">Mark as Delivered</button>
      </div>

      <div v-if="isSigning" class="signature-box">
        <h3>Customer Signature</h3>
        <canvas ref="signatureCanvas" width="300" height="150" class="sig-pad"
                @mousedown="handleMouseDown" @mousemove="handleMouseMove" @mouseup="handleMouseUp"></canvas>
        <div class="sig-buttons">
          <button @click="clearSignature" class="clear-btn">Clear</button>
          <button @click="confirmDelivery" class="confirm-btn">Confirm Delivery</button>
        </div>
      </div>

      <div class="map-placeholder">
        <p v-if="status !== 'DELIVERED'">Live GPS Feed Active</p>
        <p v-else>Delivery Complete ✅</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.driver-view {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f4f7f6;
  padding: 20px;
}

.mobile-card {
  background: white;
  width: 100%;
  max-width: 400px;
  padding: 30px;
  border-radius: 24px;
  box-shadow: 0 10px 40px rgba(0,0,0,0.1);
  text-align: center;
}

.status-badge {
  display: inline-block;
  padding: 8px 16px;
  border-radius: 20px;
  background: #bdc3c7;
  color: white;
  font-weight: 600;
  margin-bottom: 20px;
}

.status-badge.live {
  background: #27ae60;
  animation: pulse 1s infinite alternate;
}

.status-badge.delivered {
  background: #1a2a6c;
}

.delivery-btn {
  width: 100%;
  padding: 15px;
  background: #1a2a6c;
  color: white;
  border: none;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  margin-top: 20px;
}

.signature-box {
  margin-top: 20px;
  background: #f9f9f9;
  padding: 15px;
  border-radius: 15px;
}

.sig-pad {
  border: 1px solid #ddd;
  background: white;
  border-radius: 8px;
  margin-bottom: 10px;
  cursor: crosshair;
}

.sig-buttons {
  display: flex;
  gap: 10px;
}

.sig-buttons button {
  flex: 1;
  padding: 10px;
  border-radius: 8px;
  border: none;
  font-weight: 600;
  cursor: pointer;
}

.clear-btn { background: #ecf0f1; color: #7f8c8d; }
.confirm-btn { background: #27ae60; color: white; }

@keyframes pulse {
  from { opacity: 1; }
  to { opacity: 0.7; }
}

h1 {
  margin: 0 0 30px;
  color: #2c3e50;
  font-size: 1.8rem;
}

.stat-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  font-size: 1.1rem;
}

.stat-row label {
  color: #7f8c8d;
}

.parcel-selector {
  margin-bottom: 25px;
  text-align: left;
}

.parcel-selector label {
  display: block;
  font-size: 0.9rem;
  color: #95a5a6;
  margin-bottom: 8px;
  font-weight: 600;
}

.parcel-selector select {
  width: 100%;
  padding: 12px 15px;
  border-radius: 12px;
  border: 2px solid #edf2f4;
  font-family: 'Outfit', sans-serif;
  font-size: 1rem;
  color: #2c3e50;
  outline: none;
  transition: border-color 0.3s;
  background: white;
  appearance: none;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right 15px center;
  background-size: 16px;
}

.parcel-selector select:focus {
  border-color: #1a2a6c;
}

.red { color: #e74c3c; }
.green { color: #27ae60; }

.map-placeholder {
  margin-top: 30px;
  height: 100px;
  background: #edf2f4;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #95a5a6;
  border: 2px dashed #bdc3c7;
}
</style>
