<template>
  <div class="stats-card glass-panel" :style="{ borderLeft: `4px solid ${color}` }">
    <div class="icon-box" :style="{ backgroundColor: `${color}15`, color: color }">
      <slot name="icon"></slot>
    </div>
    <div class="info">
      <span class="label">{{ label }}</span>
      <h2 class="value">{{ value }}</h2>
      <div class="trend" :class="trendDirection">
        {{ trendDirection === 'up' ? '↑' : '↓' }} {{ trendValue }}%
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  label: String,
  value: String,
  trendValue: String,
  trendDirection: String, // 'up' or 'down'
  color: String
});
</script>

<style scoped>
.stats-card {
  padding: 24px;
  display: flex;
  gap: 20px;
  align-items: center;
  transition: var(--transition-smooth);
  cursor: pointer;
}

.stats-card:hover {
  transform: translateY(-5px);
  background: var(--glass-bg-hover, rgba(255, 255, 255, 0.06));
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.2);
}

.icon-box {
  width: 56px;
  height: 56px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
}

.info {
  display: flex;
  flex-direction: column;
}

.label {
  color: var(--text-tertiary);
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.value {
  margin: 4px 0;
  font-size: 1.8rem;
  font-weight: 700;
}

.trend {
  font-size: 0.85rem;
  font-weight: 500;
}

.trend.up { color: var(--success); }
.trend.down { color: var(--error); }
</style>
