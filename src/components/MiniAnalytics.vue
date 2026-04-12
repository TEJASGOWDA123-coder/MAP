<template>
  <div class="mini-analytics glass-panel">
    <div class="card-header">
      <h3>Regional Performance</h3>
      <span class="trend-pill">+12.5%</span>
    </div>
    <div class="mini-chart">
      <div v-for="(h, i) in [30, 50, 40, 70, 45, 90, 60]" :key="i" class="bar-wrap">
        <div class="bar" :style="{ height: h + '%' }"></div>
      </div>
    </div>
    <div class="stats-row">
      <div class="s-item">
        <span class="lbl">Total Output</span>
        <span class="val">14.2k</span>
      </div>
      <div class="s-item">
        <span class="lbl">Efficiency</span>
        <span class="val">98.2%</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.mini-analytics { padding: 24px; display: flex; flex-direction: column; gap: 20px; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.trend-pill { background: rgba(34, 197, 94, 0.1); color: var(--success); padding: 2px 8px; border-radius: 6px; font-size: 0.65rem; font-weight: 700; }
.mini-chart { height: 80px; display: flex; align-items: flex-end; justify-content: space-between; gap: 8px; border-bottom: 1px solid var(--glass-border); padding-bottom: 4px; }
.bar-wrap { flex: 1; height: 100%; display: flex; align-items: flex-end; }
.bar { width: 100%; background: var(--accent-primary); border-radius: 2px 2px 0 0; opacity: 0.8; }
.stats-row { display: flex; justify-content: space-between; }
.s-item { display: flex; flex-direction: column; gap: 4px; }
.lbl { font-size: 0.65rem; color: var(--text-tertiary); }
.val { font-size: 1rem; font-weight: 700; }
</style>
