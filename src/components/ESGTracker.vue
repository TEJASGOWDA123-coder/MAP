<script setup>
const esgMetrics = [
  { label: 'CO2 Saved', value: '12.4t', trend: '+12%', icon: '🍃' },
  { label: 'Eco-Routes', value: '45%', trend: '+5%', icon: '🔄' },
  { label: 'Electric Miles', value: '2.8k', trend: '+18%', icon: '⚡' },
];
</script>

<template>
  <div class="esg-card glass-panel">
    <h3>ESG Sustainability Tracker</h3>
    <div class="esg-grid">
       <div v-for="e in esgMetrics" :key="e.label" class="esg-item">
         <span class="esg-icon">{{ e.icon }}</span>
         <div class="esg-info">
           <span class="esg-val">{{ e.value }}</span>
           <span class="esg-label">{{ e.label }}</span>
         </div>
         <span class="esg-trend">{{ e.trend }}</span>
       </div>
    </div>
  </div>
</template>

<style scoped>
.esg-card { padding: 24px; display: flex; flex-direction: column; gap: 20px; height: 100%; }
.esg-card h3 { font-size: 1.1rem; font-weight: 700; margin: 0; }
.esg-grid { display: grid; gap: 16px; }
.esg-item {
  display: flex;
  align-items: center;
  gap: 12px;
  background: rgba(34, 197, 94, 0.05);
  padding: 12px;
  border-radius: 12px;
  position: relative;
}
.esg-icon { font-size: 1.2rem; }
.esg-info { display: flex; flex-direction: column; }
.esg-val { font-weight: 700; font-size: 1rem; }
.esg-label { font-size: 0.7rem; color: var(--text-tertiary); }
.esg-trend { position: absolute; right: 12px; font-size: 0.7rem; font-weight: 700; color: var(--success, #22c55e); }
</style>
