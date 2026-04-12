<template>
  <div class="revenue-trends glass-panel">
    <div class="header">
      <h3 class="card-title">Revenue Trends</h3>
      <div class="tabs">
        <span class="active">All</span>
        <span>Air</span>
        <span>Road</span>
        <span>Sea</span>
      </div>
    </div>

    <div class="chart-container">
      <div v-for="(val, i) in data" :key="i" class="bar-group">
        <div class="bar-stack">
          <div class="bar air" :style="{ height: val.air + '%' }"></div>
          <div class="bar road" :style="{ height: val.road + '%' }"></div>
          <div class="bar sea" :style="{ height: val.sea + '%' }"></div>
        </div>
        <span class="label">{{ val.day }}</span>
      </div>
    </div>

    <div class="legend">
      <div class="item"><span class="dot air"></span> Air</div>
      <div class="item"><span class="dot road"></span> Road</div>
      <div class="item"><span class="dot sea"></span> Sea</div>
    </div>
  </div>
</template>

<script setup>
const data = [
  { day: 'Sun', air: 30, road: 40, sea: 20 },
  { day: 'Mon', air: 50, road: 30, sea: 40 },
  { day: 'Tue', air: 40, road: 60, sea: 30 },
  { day: 'Wed', air: 60, road: 40, sea: 50 },
  { day: 'Thu', air: 45, road: 70, sea: 35 },
  { day: 'Fri', air: 55, road: 50, sea: 60 },
  { day: 'Sat', air: 40, road: 30, sea: 40 },
];
</script>

<style scoped>
.revenue-trends {
  padding: 20px;
  display: flex;
  flex-direction: column;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.tabs {
  display: flex;
  gap: 12px;
  font-size: 0.8rem;
  color: var(--text-tertiary);
}

.tabs span.active {
  color: var(--text-primary);
  border-bottom: 2px solid var(--accent-primary);
}

.chart-container {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  padding-bottom: 8px;
}

.bar-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  flex: 1;
}

.bar-stack {
  width: 12px;
  height: 140px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 6px;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  overflow: hidden;
}

.bar { width: 100%; border-radius: 2px; }
.bar.air { background: var(--accent-primary); }
.bar.road { background: var(--accent-secondary); }
.bar.sea { background: var(--accent-tertiary); }

.label { font-size: 0.7rem; color: var(--text-tertiary); }

.legend {
  display: flex;
  gap: 16px;
  margin-top: 16px;
  font-size: 0.75rem;
}

.item { display: flex; align-items: center; gap: 6px; }
.dot { width: 8px; height: 8px; border-radius: 50%; }
.dot.air { background: var(--accent-primary); }
.dot.road { background: var(--accent-secondary); }
.dot.sea { background: var(--accent-tertiary); }
</style>
