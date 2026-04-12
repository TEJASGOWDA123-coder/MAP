<template>
  <div class="fleet-status glass-panel">
    <div class="header">
      <h3 class="card-title">Fleet Status</h3>
      <button class="more-btn">...</button>
    </div>

    <div class="metrics">
      <div v-for="m in metrics" :key="m.label" class="metric-item">
        <div class="top">
          <span class="val">{{ m.value }}%</span>
          <span class="lbl">{{ m.label }}</span>
        </div>
        <div class="progress-bg">
          <div class="progress-fill" :style="{ width: m.value + '%', background: m.color }"></div>
        </div>
        <p class="desc">{{ m.desc }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
const metrics = [
  { label: 'Fleet Efficiency', value: 68, color: 'var(--accent-primary)', desc: 'Overall fleet performance based on uptime, fuel efficiency, and maintenance compliance.' },
  { label: 'Fuel Efficiency', value: 91, color: 'var(--success)', desc: 'Average fuel efficiency across all vehicles compared to industry standards.' },
  { label: 'Maintenance Compliance', value: 83, color: 'var(--accent-tertiary)', desc: 'Percentage of vehicles with up-to-date maintenance schedules.' }
];
</script>

<style scoped>
.fleet-status {
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.more-btn { background: transparent; border: none; color: var(--text-tertiary); cursor: pointer; }

.metrics {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.metric-item .top {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 8px;
}

.metric-item .val { font-size: 1.4rem; font-weight: 700; }
.metric-item .lbl { font-size: 0.85rem; color: var(--text-secondary); }

.progress-bg {
  height: 6px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 3px;
  margin-bottom: 8px;
}

.progress-fill {
  height: 100%;
  border-radius: 3px;
}

.desc {
  font-size: 0.75rem;
  color: var(--text-tertiary);
  margin: 0;
  line-height: 1.4;
}
</style>
