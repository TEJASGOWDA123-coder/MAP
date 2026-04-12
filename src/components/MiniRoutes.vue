<template>
  <div class="mini-routes glass-panel">
    <div class="card-header">
      <h3>Active Routes</h3>
      <span class="live-tag">LIVE</span>
    </div>
    <div class="route-list">
      <div v-for="r in miniRoutes" :key="r.id" class="mini-route-item">
        <div class="r-top">
          <span class="id">#{{ r.id }}</span>
          <span class="status" :class="r.status.toLowerCase()">{{ r.status }}</span>
        </div>
        <div class="progress-bar">
          <div class="fill" :style="{ width: r.progress + '%' }"></div>
        </div>
        <div class="r-bottom">
          <span>{{ r.from }} → {{ r.to }}</span>
          <span class="load">{{ r.load }}% Load</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const miniRoutes = [
  { id: '882', from: 'BLR', to: 'WFD', status: 'Optimal', progress: 65, load: 85 },
  { id: '910', from: 'ECY', to: 'IDN', status: 'Delayed', progress: 30, load: 42 },
  { id: '743', from: 'HBL', to: 'KML', status: 'Congested', progress: 85, load: 95 }
];
</script>

<style scoped>
.mini-routes { padding: 24px; display: flex; flex-direction: column; gap: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.live-tag { background: rgba(34, 197, 94, 0.1); color: var(--success); padding: 2px 8px; border-radius: 6px; font-size: 0.65rem; font-weight: 700; }
.route-list { display: flex; flex-direction: column; gap: 16px; }
.mini-route-item { display: flex; flex-direction: column; gap: 8px; }
.r-top { display: flex; justify-content: space-between; font-size: 0.8rem; font-weight: 600; }
.status.optimal { color: var(--success); }
.status.delayed { color: var(--error); }
.status.congested { color: var(--warning); }
.progress-bar { height: 4px; background: var(--bg-tertiary); border-radius: 2px; overflow: hidden; }
.fill { height: 100%; background: var(--accent-primary); border-radius: 2px; }
.r-bottom { display: flex; justify-content: space-between; font-size: 0.7rem; color: var(--text-tertiary); }
</style>
