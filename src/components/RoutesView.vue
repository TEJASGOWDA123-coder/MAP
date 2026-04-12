<template>
  <div class="routes-view">
    <div class="header">
      <div class="title-group">
        <h2>Active Delivery Routes</h2>
        <p class="subtitle">Real-time path optimization and delay monitoring</p>
      </div>
      <div class="filter-tabs glass-panel">
        <span class="active">All Routes</span>
        <span>Domestic</span>
        <span>International</span>
      </div>
    </div>

    <div class="routes-layout">
      <div v-for="route in routes" :key="route.id" class="route-card glass-panel">
        <div class="route-header">
          <div class="route-id-group">
            <span class="id-tag">Route #{{ route.id }}</span>
            <span class="status-indicator" :class="route.status.toLowerCase()">
              {{ route.status }}
            </span>
          </div>
          <div class="priority-flag" v-if="route.load > 90">🔥 High Priority</div>
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

        <div class="route-grid">
          <div class="metric-box">
            <span class="m-lbl">Distance Remaining</span>
            <span class="m-val">{{ (route.distance * (1 - route.progress/100)).toFixed(1) }} km</span>
          </div>
          <div class="metric-box">
            <span class="m-lbl">Current Load</span>
            <span class="m-val">{{ route.load }}%</span>
          </div>
          <div class="metric-box">
            <span class="m-lbl">Estimated Arrival</span>
            <span class="m-val">14:45 PM</span>
          </div>
          <div class="metric-box">
            <span class="m-lbl">Vibration Index</span>
            <span class="m-val">Normal</span>
          </div>
        </div>

        <div class="footer-actions">
           <button class="expand-btn">View Full Log</button>
           <button class="map-btn">📍 Track Lite</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const routes = [
  { id: 'RT-882', from: 'Bangalore East', to: 'Whitefield', status: 'Optimal', distance: 12.5, load: 85, progress: 65 },
  { id: 'RT-910', from: 'Electronic City', to: 'Indiranagar', status: 'Delayed', distance: 24.2, load: 42, progress: 30 },
  { id: 'RT-743', from: 'Hebbal', to: 'Koramangala', status: 'Congested', distance: 18.7, load: 95, progress: 85 },
  { id: 'RT-221', from: 'DC Main Hub', to: 'Airport', status: 'Optimal', distance: 35.0, load: 60, progress: 45 },
  { id: 'RT-556', from: 'Mysore Road', to: 'Yeshwanthpur', status: 'Optimal', distance: 22.1, load: 70, progress: 15 }
];
</script>

<style scoped>
.routes-view {
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

.route-grid {
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
