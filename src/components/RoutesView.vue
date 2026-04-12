<template>
  <div class="routes-view">
    <div class="header">
      <div class="title-group">
        <h2>Active Delivery Routes</h2>
        <p class="subtitle">Real-time path optimization and delay monitoring</p>
      </div>
      <div class="filter-tabs glass-panel">
        <span :class="{ active: selectedFilter === 'all' }" @click="selectedFilter = 'all'">All Routes</span>
        <span :class="{ active: selectedFilter === 'domestic' }" @click="selectedFilter = 'domestic'">Domestic</span>
        <span :class="{ active: selectedFilter === 'international' }" @click="selectedFilter = 'international'">International</span>
      </div>
    </div>

    <div class="routes-layout">
      <!-- Live Optimization Side Panel -->
      <div class="optimization-panel glass-panel">
        <div class="panel-header">
          <h3>Live Optimization Log</h3>
          <span class="live-tag">LIVE</span>
        </div>
        <div class="log-entries">
          <div v-for="log in optimizationLog" :key="log.id" class="log-entry" :class="log.type">
            <span class="time">{{ log.time }}</span>
            <p>{{ log.event }}</p>
          </div>
        </div>
        <button class="optimize-now-btn">Run AI Re-optimization</button>
      </div>

      <div class="routes-grid">
        <div v-for="route in filteredRoutes" :key="route.id" class="route-card glass-panel">
          <div class="route-header">
            <div class="route-id-group">
              <span class="id-tag">Route #{{ route.id }}</span>
              <span class="status-indicator" :class="route.status.toLowerCase()">
                {{ route.status }}
              </span>
            </div>
            <div class="delay-tag" v-if="route.delay > 0">+{{ route.delay }}m Delay</div>
            <div class="priority-flag" v-else-if="route.load > 90">🔥 High Priority</div>
          </div>

          <div class="tracking-visual">
            <div class="track-line">
              <div class="start-node"></div>
              <div class="current-progress" :style="{ width: route.progress + '%' }">
                <div class="vehicle-marker">🚛</div>
              </div>
              <div class="end-node"></div>
            </div>
            <div class="cities">
              <span class="city">{{ route.from }}</span>
              <span class="city">{{ route.to }}</span>
            </div>
          </div>

          <div class="route-grid-metrics">
            <div class="metric-box">
              <span class="m-lbl">Distance Remaining</span>
              <span class="m-val">{{ (route.distance * (1 - route.progress/100)).toFixed(1) }} km</span>
            </div>
            <div class="metric-box">
              <span class="m-lbl">Current Load</span>
              <span class="m-val">{{ route.load }}%</span>
            </div>
          </div>

          <div class="footer-actions">
             <button class="expand-btn" @click="openLog(route)">View Full Log</button>
             <button class="map-btn" @click="$emit('live-track', route.id)">📍 Live Track</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Full Log Modal -->
    <div v-if="showLogModal && selectedRouteForLog" class="modal-overlay" @click="showLogModal = false">
      <div class="modal-content glass-panel" @click.stop>
        <h3>Full Log for Route #{{ selectedRouteForLog.id }}</h3>
        <p><strong>Status:</strong> <span :class="selectedRouteForLog.status.toLowerCase()">{{ selectedRouteForLog.status }}</span></p>
        <p><strong>Path:</strong> {{ selectedRouteForLog.from }} → {{ selectedRouteForLog.to }}</p>
        <p><strong>Load:</strong> {{ selectedRouteForLog.load }}%</p>
        <p><strong>Delay:</strong> {{ selectedRouteForLog.delay }}m</p>
        <br/>
        <div class="log-entries">
           <div class="log-entry info">
              <span class="time">10:00 AM</span>
              <p>Departed from {{ selectedRouteForLog.from }}</p>
           </div>
           <div v-if="selectedRouteForLog.delay > 0" class="log-entry warning">
              <span class="time">10:15 AM</span>
              <p>Delay detected due to traffic/congestion.</p>
           </div>
           <div class="log-entry success" v-if="selectedRouteForLog.status === 'Optimal'">
              <span class="time">10:30 AM</span>
              <p>Maintaining optimal speed and route.</p>
           </div>
        </div>
        <button class="close-btn" @click="showLogModal = false" style="margin-top: 20px; width: 100%; padding: 10px; background: var(--bg-tertiary); color: var(--text-primary); border-radius: 8px; border: none; cursor: pointer;">Close</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const emit = defineEmits(['live-track']);

const optimizationLog = ref([
  { id: 1, time: '10:25 AM', event: 'Route RT-882 re-optimized: Traffic avoided', type: 'info' },
  { id: 2, time: '10:14 AM', event: 'Delay detected on RT-910: Estimating alt path', type: 'warning' },
  { id: 3, time: '09:45 AM', event: 'RT-743 fuel priority established', type: 'success' },
]);

const selectedFilter = ref('all');
const showLogModal = ref(false);
const selectedRouteForLog = ref(null);

const routes = ref([
  { id: 'RT-882', from: 'Bangalore East', to: 'Whitefield', status: 'Optimal', distance: 12.5, load: 85, progress: 65, delay: 0, type: 'domestic' },
  { id: 'RT-910', from: 'Electronic City', to: 'Indiranagar', status: 'Delayed', distance: 24.2, load: 42, progress: 30, delay: 15, type: 'domestic' },
  { id: 'RT-743', from: 'Hebbal', to: 'Koramangala', status: 'Congested', distance: 18.7, load: 95, progress: 85, delay: 8, type: 'domestic' },
  { id: 'RT-221', from: 'DC Main Hub', to: 'Airport', status: 'Optimal', distance: 35.0, load: 60, progress: 45, delay: 0, type: 'international' },
  { id: 'RT-556', from: 'Mysore Road', to: 'Yeshwanthpur', status: 'Optimal', distance: 22.1, load: 70, progress: 15, delay: 0, type: 'domestic' }
]);

const filteredRoutes = computed(() => {
  if (selectedFilter.value === 'all') return routes.value;
  return routes.value.filter(r => r.type === selectedFilter.value);
});

const openLog = (route) => {
  selectedRouteForLog.value = route;
  showLogModal.value = true;
};
</script>

<style scoped>
.routes-view {
  padding: 40px;
  max-width: 1400px;
  margin: 0 auto;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0,0,0,0.5);
  backdrop-filter: blur(8px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  padding: 40px;
  width: 500px;
  max-width: 90vw;
}

.modal-content h3 {
  margin-bottom: 20px;
  color: var(--accent-secondary);
}

.modal-content strong {
  margin-right: 8px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: 40px;
}

.subtitle { color: var(--text-tertiary); font-size: 0.9rem; margin-top: 4px; }

.filter-tabs {
  padding: 6px;
  display: flex;
  gap: 8px;
  border-radius: 12px;
}

.filter-tabs span {
  padding: 8px 16px;
  border-radius: 8px;
  font-size: 0.85rem;
  cursor: pointer;
  transition: 0.3s;
}

.filter-tabs span.active { background: var(--accent-primary); color: white; }

.routes-layout {
  display: grid;
  grid-template-columns: 350px 1fr;
  gap: 24px;
}

.optimization-panel {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
  height: fit-content;
  position: sticky;
  top: 20px;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.live-tag {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
  padding: 2px 8px;
  border-radius: 6px;
  font-size: 0.7rem;
  font-weight: 700;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { opacity: 1; }
  50% { opacity: 0.5; }
  100% { opacity: 1; }
}

.log-entries {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.log-entry {
  padding: 12px;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.02);
  border-left: 3px solid transparent;
}

.log-entry.info { border-left-color: var(--accent-primary); }
.log-entry.warning { border-left-color: var(--warning); }
.log-entry.success { border-left-color: var(--success); }

.log-entry .time { font-size: 0.7rem; color: var(--text-tertiary); display: block; margin-bottom: 4px; }
.log-entry p { font-size: 0.8rem; line-height: 1.4; color: var(--text-secondary); }

.optimize-now-btn {
  background: var(--bg-tertiary);
  color: var(--text-primary);
  border: 1px solid var(--glass-border);
  padding: 12px;
  border-radius: 10px;
  font-size: 0.85rem;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.optimize-now-btn:hover { background: var(--accent-primary); color: white; border-color: transparent; }

.routes-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 24px;
}

.route-card {
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 24px;
  transition: var(--transition-smooth);
}

.route-card:hover { border-color: var(--accent-primary); transform: translateY(-4px); }

.route-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.id-tag { font-weight: 700; color: var(--accent-secondary); font-size: 0.9rem; }
.priority-flag { font-size: 0.75rem; color: #ef4444; font-weight: 700; background: rgba(239, 68, 68, 0.1); padding: 4px 10px; border-radius: 8px; }
.delay-tag { font-size: 0.75rem; color: #ef4444; font-weight: 700; background: rgba(239, 68, 68, 0.1); padding: 4px 10px; border-radius: 8px; }

.status-indicator {
  font-size: 0.7rem;
  padding: 2px 8px;
  border-radius: 8px;
  margin-left: 10px;
}
.status-indicator.optimal { background: rgba(34, 197, 94, 0.1); color: var(--success); }
.status-indicator.delayed { background: rgba(239, 68, 68, 0.1); color: var(--error); }
.status-indicator.congested { background: rgba(245, 158, 11, 0.1); color: var(--warning); }

.tracking-visual {
  background: var(--bg-tertiary);
  padding: 30px 20px;
  border-radius: 16px;
  position: relative;
}

.track-line {
  height: 4px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  position: relative;
  margin-bottom: 20px;
}

.start-node, .end-node {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.start-node { left: 0; background: var(--success); box-shadow: 0 0 10px var(--success); }
.end-node { right: 0; background: var(--accent-secondary); }

.current-progress {
  position: absolute;
  height: 100%;
  background: var(--accent-primary);
  left: 0;
  top: 0;
  transition: width 1s ease-in-out;
}

.vehicle-marker {
  position: absolute;
  right: -10px;
  top: -24px;
  font-size: 1.2rem;
  filter: drop-shadow(0 4px 4px rgba(0,0,0,0.5));
}

.cities { display: flex; justify-content: space-between; }
.city { font-size: 0.8rem; font-weight: 600; color: var(--text-secondary); }

.route-grid-metrics {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.metric-box {
  background: rgba(255, 255, 255, 0.02);
  padding: 12px;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.m-lbl { font-size: 0.7rem; color: var(--text-tertiary); }
.m-val { font-size: 0.95rem; font-weight: 600; }

.footer-actions {
  display: flex;
  gap: 12px;
}

.expand-btn, .map-btn {
  flex: 1;
  padding: 10px;
  border-radius: 10px;
  font-size: 0.85rem;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.expand-btn { background: var(--bg-tertiary); color: var(--text-primary); border: 1px solid var(--glass-border); }
.map-btn { background: var(--accent-primary); color: white; border: none; }

.map-btn:hover { opacity: 0.9; transform: translateY(-2px); }
</style>
