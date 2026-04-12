<template>
  <div class="analytics-view">
    <div class="header">
      <div class="title-group">
        <h2>Advanced Logistics Analytics</h2>
        <p class="subtitle">Deep insights into operational efficiency and regional performance</p>
      </div>
      <div class="time-filter glass-panel">
        <span class="active">Daily</span>
        <span>Weekly</span>
        <span>Monthly</span>
        <span class="custom">📅 Custom Range</span>
      </div>
    </div>

    <div class="analytics-layout">
      <!-- Main Performance Section -->
      <div class="performance-grid">
        <div class="chart-main glass-panel">
          <div class="card-head">
            <h3>Shipment Volume Trend</h3>
            <span class="trend-pill">+15.4%</span>
          </div>
          <div class="line-chart-sim">
             <div v-for="(h, i) in [40, 60, 45, 80, 55, 90, 70]" :key="i" class="point-wrap">
               <div class="bar-lite" :style="{ height: h + '%' }"></div>
               <div class="dot" :style="{ bottom: h + '%' }"></div>
             </div>
          </div>
          <div class="days">
            <span>Mon</span><span>Tue</span><span>Wed</span><span>Thu</span><span>Fri</span><span>Sat</span><span>Sun</span>
          </div>
        </div>

        <div class="efficiency-card glass-panel">
          <h3>Delivery Efficiency</h3>
          <div class="radial-container">
            <svg viewBox="0 0 100 100" class="radial-svg">
              <circle cx="50" cy="50" r="40" class="bg" />
              <circle cx="50" cy="50" r="40" class="meter" style="stroke-dasharray: 238, 251;" />
              <text x="50" y="55" class="val">95%</text>
            </svg>
          </div>
          <p class="stat-desc">Your operational efficiency is <strong>above average</strong> this week.</p>
        </div>
      </div>

      <!-- Regional Breakdown -->
      <div class="regional-card glass-panel">
        <div class="card-head">
          <h3>Regional Throughput</h3>
          <button class="more-btn">•••</button>
        </div>
        <div class="region-stats">
          <div v-for="r in regions" :key="r.name" class="region-row">
            <div class="r-info">
              <span class="r-name">{{ r.name }}</span>
              <span class="r-vol">{{ r.count }}k shipments</span>
            </div>
            <div class="r-bar-wrap">
              <div class="r-bar" :style="{ width: r.vol + '%', background: r.color }"></div>
            </div>
          </div>
        </div>
      </div>

      <!-- Fleet Breakdown -->
      <div class="fleet-analytics-card glass-panel">
        <h3>Fleet Usage by Mode</h3>
        <div class="mode-grid">
          <div v-for="m in modes" :key="m.label" class="mode-item">
            <span class="mode-icon">{{ m.icon }}</span>
            <div class="mode-info">
              <span class="mode-label">{{ m.label }}</span>
              <span class="mode-val">{{ m.value }}%</span>
            </div>
            <div class="mini-progress">
              <div class="mini-fill" :style="{ width: m.value + '%', background: m.color }"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const regions = [
  { name: 'North India Hub', vol: 85, count: 12.4, color: 'var(--accent-primary)' },
  { name: 'South Export DC', vol: 60, count: 8.9, color: 'var(--accent-secondary)' },
  { name: 'Western Coastal', vol: 45, count: 6.2, color: 'var(--accent-tertiary)' },
  { name: 'Eastern Region', vol: 75, count: 10.1, color: '#f59e0b' }
];

const modes = [
  { label: 'Air Freight', value: 85, icon: '✈️', color: '#3b82f6' },
  { label: 'Road Express', value: 70, icon: '🚛', color: '#22c55e' },
  { label: 'Cargo Ships', value: 45, icon: '🚢', color: '#6366f1' },
  { label: 'Rail Cargo', value: 60, icon: '🚆', color: '#ec4899' }
];
</script>

<style scoped>
.analytics-view {
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

.time-filter {
  padding: 8px;
  display: flex;
  gap: 12px;
  border-radius: 12px;
}

.time-filter span {
  padding: 8px 16px;
  border-radius: 8px;
  font-size: 0.85rem;
  cursor: pointer;
  transition: 0.3s;
}

.time-filter span.active { background: var(--accent-primary); color: white; }
.time-filter .custom { color: var(--accent-secondary); font-weight: 600; }

.analytics-layout {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: 24px;
}

.performance-grid {
  grid-column: span 8;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
}

.chart-main { grid-column: span 2; padding: 30px; }
.card-head { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }

.trend-pill {
  background: rgba(34, 197, 94, 0.1);
  color: var(--success);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 700;
}

.line-chart-sim {
  height: 200px;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  padding: 0 20px;
  position: relative;
  border-bottom: 1px solid var(--glass-border);
}

.point-wrap {
  flex: 1;
  height: 100%;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
}

.bar-lite {
  width: 20px;
  background: linear-gradient(to top, rgba(59, 130, 246, 0.2), transparent);
  border-radius: 4px 4px 0 0;
}

.dot {
  width: 8px;
  height: 8px;
  background: var(--accent-primary);
  border-radius: 50%;
  position: absolute;
  box-shadow: 0 0 10px var(--accent-primary);
}

.days { display: flex; justify-content: space-between; padding: 12px 20px; color: var(--text-tertiary); font-size: 0.8rem; }

.efficiency-card { grid-column: span 1; padding: 30px; display: flex; flex-direction: column; align-items: center; }

.radial-container { width: 150px; height: 150px; margin: 20px 0; }
.radial-svg { width: 100%; height: 100%; transform: rotate(-90deg); }
.radial-svg .bg { fill: none; stroke: var(--bg-tertiary); stroke-width: 8; }
.radial-svg .meter {
  fill: none;
  stroke: var(--accent-primary);
  stroke-width: 8;
  stroke-linecap: round;
  transition: 1s;
}
.radial-svg .val {
  fill: var(--text-primary);
  font-size: 1.2rem;
  font-weight: 700;
  text-anchor: middle;
  transform: rotate(90deg);
  transform-origin: center;
}

.stat-desc { font-size: 0.85rem; color: var(--text-tertiary); text-align: center; line-height: 1.5; }

.regional-card { grid-column: span 4; padding: 30px; }
.region-stats { display: flex; flex-direction: column; gap: 24px; }
.region-row { display: flex; flex-direction: column; gap: 8px; }
.r-info { display: flex; justify-content: space-between; }
.r-name { font-size: 0.9rem; font-weight: 500; }
.r-vol { font-size: 0.75rem; color: var(--text-tertiary); }
.r-bar-wrap { height: 6px; background: var(--bg-tertiary); border-radius: 3px; overflow: hidden; }
.r-bar { height: 100%; border-radius: 3px; transition: 1s ease-out; }

.fleet-analytics-card { grid-column: span 12; padding: 30px; }
.mode-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 24px; }

.mode-item {
  background: rgba(255, 255, 255, 0.02);
  padding: 24px;
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.mode-icon { font-size: 2rem; }
.mode-info { display: flex; justify-content: space-between; align-items: baseline; }
.mode-label { font-size: 0.9rem; color: var(--text-secondary); }
.mode-val { font-size: 1.2rem; font-weight: 700; }
.mini-progress { height: 4px; background: var(--bg-tertiary); border-radius: 2px; }
.mini-fill { height: 100%; border-radius: 2px; }

.more-btn { background: none; border: none; color: var(--text-tertiary); cursor: pointer; font-size: 1.2rem; }

@media (max-width: 1200px) {
  .performance-grid { grid-column: span 12; }
  .regional-card { grid-column: span 12; }
}
</style>
