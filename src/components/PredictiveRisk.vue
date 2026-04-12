<script setup>
const riskRoutes = [
  { id: 'RT-910', risk: 'High', factor: 'Weather/Traffic', change: '+25m' },
  { id: 'RT-743', risk: 'Medium', factor: 'Congestion', change: '+8m' },
  { id: 'RT-221', risk: 'Low', factor: 'Optimal', change: '-5m' },
];
</script>

<template>
  <div class="risk-card glass-panel">
    <div class="card-head">
      <h3>Predictive Risk Forecasting</h3>
      <p class="subtitle">High-risk routes identified by AI</p>
    </div>
    <div class="risk-list">
      <div v-for="r in riskRoutes" :key="r.id" class="risk-item">
        <div class="risk-header">
          <span class="risk-id">Route #{{ r.id }}</span>
          <span class="risk-level" :class="r.risk.toLowerCase()">{{ r.risk }} Risk</span>
        </div>
        <div class="risk-meta">
          <span>{{ r.factor }}</span>
          <span class="risk-change">{{ r.change }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.risk-card { padding: 24px; display: flex; flex-direction: column; gap: 20px; height: 100%; }
.card-head h3 { font-size: 1.1rem; font-weight: 700; margin: 0; }
.subtitle { color: var(--text-tertiary); font-size: 0.8rem; margin-top: 4px; }
.risk-list { display: flex; flex-direction: column; gap: 12px; }
.risk-item {
  padding: 12px;
  background: rgba(255, 255, 255, 0.02);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  gap: 4px;
}
.risk-header { display: flex; justify-content: space-between; align-items: center; }
.risk-id { font-weight: 700; font-size: 0.85rem; }
.risk-level { font-size: 0.7rem; font-weight: 800; padding: 2px 8px; border-radius: 6px; }
.risk-level.high { background: rgba(239, 68, 68, 0.1); color: #ef4444; }
.risk-level.medium { background: rgba(245, 158, 11, 0.1); color: #f59e0b; }
.risk-level.low { background: rgba(34, 197, 94, 0.1); color: #22c55e; }
.risk-meta { display: flex; justify-content: space-between; font-size: 0.75rem; color: var(--text-tertiary); }
.risk-change { font-weight: 700; color: #ef4444; }
</style>
