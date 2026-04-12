<script setup>
const regions = [
  { name: 'North India Hub', vol: 85, count: 12.4, color: '#3b82f6', heat: 0.9 },
  { name: 'South Export DC', vol: 60, count: 8.9, color: '#22c55e', heat: 0.7 },
  { name: 'Western Coastal', vol: 45, count: 6.2, color: '#f59e0b', heat: 0.4 },
  { name: 'Eastern Region', vol: 75, count: 10.1, color: '#ec4899', heat: 0.8 }
];
</script>

<template>
  <div class="regional-card glass-panel">
    <div class="card-head">
      <h3>Regional Throughput</h3>
      <button class="more-btn">•••</button>
    </div>
    <div class="region-stats">
      <div v-for="r in regions" :key="r.name" class="region-row-enhanced">
        <div class="r-visual" :style="{ background: `rgba(59, 130, 246, ${r.heat})` }">
           <span>{{ Math.round(r.heat * 100) }}%</span>
        </div>
        <div class="r-info-enhanced">
          <div class="r-top">
            <span class="r-name">{{ r.name }}</span>
            <span class="r-vol">{{ r.count }}k</span>
          </div>
          <div class="r-bar-wrap-enhanced">
            <div class="r-bar-enhanced" :style="{ width: r.vol + '%', background: r.color }"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.regional-card { padding: 24px; display: flex; flex-direction: column; gap: 20px; height: 100%; }
.card-head { display: flex; justify-content: space-between; align-items: center; }
.card-head h3 { font-size: 1.1rem; font-weight: 700; margin: 0; }
.more-btn { background: none; border: none; color: var(--text-tertiary); cursor: pointer; font-size: 1.2rem; }
.region-stats { display: flex; flex-direction: column; gap: 16px; }
.region-row-enhanced { display: flex; align-items: center; gap: 16px; }
.r-visual { width: 50px; height: 40px; border-radius: 8px; display: flex; align-items: center; justify-content: center; font-size: 0.75rem; font-weight: 800; color: white; }
.r-info-enhanced { flex: 1; display: flex; flex-direction: column; gap: 6px; }
.r-top { display: flex; justify-content: space-between; align-items: center; }
.r-name { font-size: 0.85rem; font-weight: 600; color: var(--text-primary); }
.r-vol { font-size: 0.8rem; font-weight: 700; color: var(--text-secondary); }
.r-bar-wrap-enhanced { height: 6px; background: rgba(255, 255, 255, 0.05); border-radius: 3px; overflow: hidden; }
.r-bar-enhanced { height: 100%; border-radius: 3px; }
</style>
