<template>
  <div class="fleet-view">
    <div class="header">
      <div class="title-group">
        <h2>Vehicle Fleet Management</h2>
        <p class="subtitle">Real-time status and health monitoring for all active vehicles</p>
      </div>
      <div class="actions">
        <button class="add-btn" @click="showAddModal = true">+ Add Vehicle</button>
      </div>
    </div>

    <!-- Add Vehicle Modal -->
    <div v-if="showAddModal" class="modal-overlay" @click="showAddModal = false">
      <div class="modal-content glass-panel" @click.stop>
        <h3>Add New Vehicle</h3>
        <form @submit.prevent="addVehicle" class="add-form">
          <div class="input-group">
            <label>Vehicle ID</label>
            <input v-model="newVehicle.id" type="text" placeholder="e.g. V-103" required />
          </div>
          <div class="input-group">
            <label>Driver Name</label>
            <input v-model="newVehicle.driver" type="text" placeholder="Enter driver name" required />
          </div>
          <div class="input-group">
            <label>Vehicle Type</label>
            <select v-model="newVehicle.type">
              <option>Heavy Truck</option>
              <option>Delivery Van</option>
              <option>Container Truck</option>
              <option>Cargo Plane</option>
            </select>
          </div>
          <div class="modal-actions">
            <button type="button" class="cancel-btn" @click="showAddModal = false">Cancel</button>
            <button type="submit" class="confirm-btn">Add Vehicle</button>
          </div>
        </form>
      </div>
    </div>

    <div class="stats-grid">
      <div class="stat-card glass-panel orange">
        <div class="stat-icon">🚛</div>
        <div class="stat-info">
          <span class="lbl">Total Fleet</span>
          <span class="val">42</span>
        </div>
      </div>
      <div class="stat-card glass-panel blue">
        <div class="stat-icon">🛣️</div>
        <div class="stat-info">
          <span class="lbl">Active Now</span>
          <span class="val">38</span>
        </div>
      </div>
      <div class="stat-card glass-panel pink">
        <div class="stat-icon">🔧</div>
        <div class="stat-info">
          <span class="lbl">Maintenance</span>
          <span class="val">4</span>
        </div>
      </div>
    </div>

    <div class="fleet-grid">
      <div v-for="v in fleet" :key="v.id" class="vehicle-card glass-panel">
        <div class="card-header">
          <span class="vid">{{ v.id }}</span>
          <span class="status-pill" :class="v.status.toLowerCase()">{{ v.status }}</span>
        </div>
        
        <div class="vehicle-preview">
           <div class="v-icon">{{ getVehicleIcon(v.type) }}</div>
           <div class="v-details">
             <h4>{{ v.type }}</h4>
             <p>{{ v.driver }}</p>
           </div>
        </div>

        <div class="metrics">
          <div class="metric">
            <span class="m-lbl">Fuel Level</span>
            <div class="fuel-track">
              <div class="fuel-fill" :style="{ width: v.fuel + '%', background: getFuelColor(v.fuel) }"></div>
            </div>
            <span class="m-val">{{ v.fuel }}%</span>
          </div>
          <div class="metric">
             <span class="m-lbl">Last Known Location</span>
             <span class="m-val loc">{{ v.location }}</span>
          </div>
        </div>

        <button class="view-btn">View Route</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const showAddModal = ref(false);
const newVehicle = ref({
  id: '',
  type: 'Heavy Truck',
  driver: '',
  status: 'Active',
  fuel: 100,
  location: 'Main Hub'
});

const fleet = ref([
  { id: 'V-001', type: 'Heavy Truck', driver: 'John Doe', status: 'Active', fuel: 85, location: 'Bangalore East' },
  { id: 'V-002', type: 'Delivery Van', driver: 'Jane Smith', status: 'Active', fuel: 45, location: 'City Center' },
  { id: 'V-003', type: 'Container Truck', driver: 'Mike Ross', status: 'Idle', fuel: 90, location: 'Main Hub' },
  { id: 'V-004', type: 'Heavy Truck', driver: 'Sarah Connor', status: 'Maintenance', fuel: 12, location: 'Service Center' },
  { id: 'V-005', type: 'Delivery Van', driver: 'Alex Hunt', status: 'Active', fuel: 60, location: 'Whitefield' },
  { id: 'V-102', type: 'Cargo Plane', driver: 'Capt. Rogers', status: 'Active', fuel: 100, location: 'Intl Airport' }
]);

const addVehicle = () => {
  if (!newVehicle.value.id || !newVehicle.value.driver) return;
  
  fleet.value.unshift({ ...newVehicle.value });
  showAddModal.value = false;
  
  // Reset form
  newVehicle.value = {
    id: '',
    type: 'Heavy Truck',
    driver: '',
    status: 'Active',
    fuel: 100,
    location: 'Main Hub'
  };
};

const getVehicleIcon = (type) => {
  if (type.includes('Truck')) return '🚛';
  if (type.includes('Van')) return '🚐';
  if (type.includes('Plane')) return '✈️';
  return '🚗';
};

const getFuelColor = (fuel) => {
  if (fuel > 50) return 'var(--success)';
  if (fuel > 20) return 'var(--warning)';
  return 'var(--error)';
};
</script>

<style scoped>
.fleet-view {
  padding: 40px;
  max-width: 1400px;
  margin: 0 auto;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: 40px;
}

.subtitle {
  color: var(--text-tertiary);
  font-size: 0.9rem;
  margin-top: 4px;
}

.add-btn {
  background: var(--accent-primary);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition-smooth);
}

.add-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(59, 130, 246, 0.3);
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
  margin-bottom: 40px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 24px;
}

.stat-icon {
  width: 56px;
  height: 56px;
  background: var(--bg-tertiary);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
}

.stat-card.orange .stat-icon { color: #f59e0b; background: rgba(245, 158, 11, 0.1); }
.stat-card.blue .stat-icon { color: #3b82f6; background: rgba(59, 130, 246, 0.1); }
.stat-card.pink .stat-icon { color: #ec4899; background: rgba(236, 72, 153, 0.1); }

.stat-info .lbl { font-size: 0.85rem; color: var(--text-tertiary); display: block; }
.stat-info .val { font-size: 1.8rem; font-weight: 700; }

.fleet-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 24px;
}

.vehicle-card {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  transition: var(--transition-smooth);
}

.vehicle-card:hover { border-color: var(--accent-primary); transform: translateY(-4px); }

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.vid { font-weight: 700; color: var(--accent-secondary); font-size: 0.9rem; }

.status-pill {
  font-size: 0.7rem;
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 600;
}
.status-pill.active { background: rgba(34, 197, 94, 0.1); color: var(--success); }
.status-pill.idle { background: rgba(148, 163, 184, 0.1); color: var(--text-secondary); }
.status-pill.maintenance { background: rgba(239, 68, 68, 0.1); color: var(--error); }

.vehicle-preview {
  display: flex;
  align-items: center;
  gap: 16px;
}

.v-icon {
  width: 64px;
  height: 64px;
  background: var(--bg-tertiary);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 2rem;
}

.v-details h4 { font-size: 1.1rem; margin-bottom: 4px; }
.v-details p { font-size: 0.85rem; color: var(--text-tertiary); }

.metrics { display: flex; flex-direction: column; gap: 16px; }
.metric { display: flex; flex-direction: column; gap: 8px; }
.m-lbl { font-size: 0.75rem; color: var(--text-tertiary); }
.m-val { font-size: 0.9rem; font-weight: 500; }
.m-val.loc { font-style: italic; color: var(--accent-tertiary); }

.fuel-track {
  height: 6px;
  background: var(--bg-tertiary);
  border-radius: 3px;
  overflow: hidden;
}
.fuel-fill { height: 100%; transition: width 1s ease; }

.view-btn {
  width: 100%;
  padding: 12px;
  background: var(--bg-tertiary);
  color: var(--text-primary);
  border: 1px solid var(--glass-border);
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.view-btn:hover { background: var(--accent-primary); color: white; border-color: transparent; }

/* Modal Styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.7);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  width: 100%;
  max-width: 450px;
  padding: 40px;
}

.modal-content h3 {
  margin-bottom: 24px;
  font-size: 1.5rem;
  color: var(--accent-primary);
}

.add-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.input-group label {
  font-size: 0.85rem;
  color: var(--text-tertiary);
}

.input-group input, .input-group select {
  background: var(--bg-tertiary);
  border: 1px solid var(--glass-border);
  padding: 12px;
  border-radius: 10px;
  color: var(--text-primary);
  outline: none;
}

.modal-actions {
  display: flex;
  gap: 12px;
  margin-top: 10px;
}

.cancel-btn, .confirm-btn {
  flex: 1;
  padding: 12px;
  border-radius: 10px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.cancel-btn {
  background: transparent;
  border: 1px solid var(--glass-border);
  color: var(--text-secondary);
}

.confirm-btn {
  background: var(--accent-primary);
  border: none;
  color: white;
}

.confirm-btn:hover {
  filter: brightness(1.1);
  transform: translateY(-2px);
}
</style>
