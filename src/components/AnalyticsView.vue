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

    <!-- Top Highlights -->
    <div class="highlights-grid">
      <div v-for="stat in statHighlights" :key="stat.label" class="stat-mini-card glass-panel">
        <span class="s-label">{{ stat.label }}</span>
        <div class="s-main">
          <span class="s-value">{{ stat.value }}</span>
          <span class="s-trend" :class="{ up: stat.isUp }">
            {{ stat.isUp ? '↗' : '↘' }} {{ stat.trend }}
          </span>
        </div>
      </div>
    </div>

    <div class="analytics-layout">
      <!-- Main Performance Section -->
      <div class="performance-grid">
        <div class="chart-main glass-panel">
          <div class="card-head">
            <div class="title-group">
              <h3>Shipment Volume Trend</h3>
              <p class="subtitle">Real-time tracking of actual vs. predicted volume</p>
            </div>
            <span class="trend-pill">+15.4%</span>
          </div>
          <div class="line-chart-sim">
             <div v-for="(v, i) in volumeTrend" :key="i" class="point-wrap">
               <!-- Actual Segment (Primary) -->
               <div v-if="v.actual" class="bar-actual" :style="{ height: v.actual + '%' }"></div>
               <!-- Predicted/Confidence Segment (Secondary) -->
               <div class="bar-predicted" :style="{ height: v.predicted + '%', borderTopStyle: v.actual ? 'solid' : 'dashed' }"></div>
               <div v-if="v.actual" class="dot actual" :style="{ bottom: v.actual + '%' }"></div>
               <div class="dot predicted" :style="{ bottom: v.predicted + '%' }"></div>
             </div>
          </div>
          <div class="days">
            <span v-for="v in volumeTrend" :key="v.day">{{ v.day }}</span>
          </div>
          <div class="chart-legend">
            <span class="l-item"><span class="l-dot actual"></span> Actual Content</span>
            <span class="l-item"><span class="l-dot predicted"></span> AI Forecasted</span>
          </div>
        </div>

        <!-- NEW: Financial Integrity Chart -->
        <div class="financial-card glass-panel">
          <div class="card-head">
            <h3>Revenue vs. Operational Cost</h3>
            <span class="subtitle">Net Margin Analysis</span>
          </div>
          <div class="bar-chart-sim">
            <div v-for="f in financialData" :key="f.month" class="bar-pair">
               <div class="bars">
                 <div class="b-rev" :style="{ height: f.revenue/8 + '%' }"></div>
                 <div class="b-cost" :style="{ height: f.cost/8 + '%' }"></div>
               </div>
               <span class="b-month">{{ f.month }}</span>
            </div>
          </div>
          <div class="financial-legend">
            <span class="l-item"><span class="l-dot revenue"></span> Revenue</span>
            <span class="l-item"><span class="l-dot cost"></span> Cost</span>
          </div>
        </div>

        <div class="efficiency-card glass-panel">
          <h3>Current Fleet Health</h3>
          <div class="radial-container">
            <svg viewBox="0 0 100 100" class="radial-svg">
              <circle cx="50" cy="50" r="40" class="bg" />
              <circle cx="50" cy="50" r="40" class="meter" style="stroke-dasharray: 238, 251;" />
              <text x="50" y="55" class="val">95%</text>
            </svg>
          </div>
          <p class="stat-desc">Your operational efficiency is <strong>above average</strong>.</p>
        </div>
      </div>

      <!-- Regional Breakthrough & Risk -->
      <div class="secondary-grid">
        <!-- Risk Forecast Widget -->
        <PredictiveRisk />

        <!-- ESG Sustainability Tracker -->
        <ESGTracker />

        <!-- Regional Throughput -->
        <RegionalThroughput />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import PredictiveRisk from './PredictiveRisk.vue';
import ESGTracker from './ESGTracker.vue';
import RegionalThroughput from './RegionalThroughput.vue';

const volumeTrend = ref([
  { day: 'Mon', actual: 40, predicted: 42 },
  { day: 'Tue', actual: 60, predicted: 58 },
  { day: 'Wed', actual: 45, predicted: 48 },
  { day: 'Thu', actual: 80, predicted: 82 },
  { day: 'Fri', actual: 55, predicted: 60 },
  { day: 'Sat', actual: 90, predicted: 85 },
  { day: 'Sun', actual: null, predicted: 95 }
]);

const financialData = ref([
  { month: 'Jan', revenue: 450, cost: 320 },
  { month: 'Feb', revenue: 520, cost: 340 },
  { month: 'Mar', revenue: 480, cost: 310 },
  { month: 'Apr', revenue: 600, cost: 400 },
]);

const riskRoutes = [
  { id: 'RT-910', risk: 'High', factor: 'Weather/Traffic', change: '+25m' },
  { id: 'RT-743', risk: 'Medium', factor: 'Congestion', change: '+8m' },
  { id: 'RT-221', risk: 'Low', factor: 'Optimal', change: '-5m' },
];

const esgMetrics = [
  { label: 'CO2 Saved', value: '12.4t', trend: '+12%', icon: '🍃' },
  { label: 'Eco-Routes', value: '45%', trend: '+5%', icon: '🔄' },
  { label: 'Electric Miles', value: '2.8k', trend: '+18%', icon: '⚡' },
];

const statHighlights = [
  { label: 'Total Revenue', value: '$842k', trend: '+12.5%', isUp: true },
  { label: 'Op. Margin', value: '24.2%', trend: '+3.1%', isUp: true },
  { label: 'Fuel Savings', value: '$12.8k', trend: '+8.4%', isUp: true },
];

const regions = [
  { name: 'North India Hub', vol: 85, count: 12.4, color: '#3b82f6', heat: 0.9 },
  { name: 'South Export DC', vol: 60, count: 8.9, color: '#22c55e', heat: 0.7 },
  { name: 'Western Coastal', vol: 45, count: 6.2, color: '#f59e0b', heat: 0.4 },
  { name: 'Eastern Region', vol: 75, count: 10.1, color: '#ec4899', heat: 0.8 }
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
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.highlights-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.stat-mini-card {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.s-label { font-size: 0.75rem; color: var(--text-tertiary); font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; }
.s-main { display: flex; justify-content: space-between; align-items: baseline; }
.s-value { font-size: 1.5rem; font-weight: 800; }
.s-trend { font-size: 0.8rem; font-weight: 700; }
.s-trend.up { color: var(--success); }

.performance-grid {
  display: grid;
  grid-template-columns: 1fr 350px 300px;
  gap: 24px;
}

.line-chart-sim {
  height: 180px;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  padding: 0 10px;
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

.bar-actual {
  width: 14px;
  background: var(--accent-primary);
  border-radius: 4px 4px 0 0;
  z-index: 2;
  position: absolute;
}

.bar-predicted {
  width: 14px;
  background: rgba(59, 130, 246, 0.1);
  border: 1px dashed var(--accent-primary);
  border-bottom: none;
  border-radius: 4px 4px 0 0;
  z-index: 1;
}

.dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  position: absolute;
  z-index: 3;
}

.dot.actual { background: white; box-shadow: 0 0 10px white; }
.dot.predicted { background: var(--accent-primary); }

.days { display: flex; justify-content: space-between; padding: 10px; color: var(--text-tertiary); font-size: 0.75rem; font-weight: 600; }

.bar-chart-sim {
  height: 120px;
  display: flex;
  align-items: flex-end;
  justify-content: space-around;
  padding: 10px;
  border-bottom: 1px solid var(--glass-border);
}

.bar-pair { display: flex; flex-direction: column; align-items: center; gap: 8px; flex: 1; }
.bars { display: flex; align-items: flex-end; gap: 4px; height: 100%; width: 100%; justify-content: center; }
.b-rev { width: 10px; background: var(--accent-primary); border-radius: 2px 2px 0 0; }
.b-cost { width: 10px; background: #6366f1; border-radius: 2px 2px 0 0; }
.b-month { font-size: 0.7rem; color: var(--text-tertiary); }

.financial-legend { display: flex; gap: 15px; justify-content: center; margin-top: 10px; font-size: 0.7rem; }

.efficiency-card { grid-column: span 4; padding: 24px; display: flex; flex-direction: column; align-items: center; justify-content: center; }
.radial-container { width: 120px; height: 120px; margin: 10px 0; }
.radial-svg { width: 100%; height: 100%; }
.radial-svg .bg { fill: none; stroke: rgba(255, 255, 255, 0.05); stroke-width: 8; }
.radial-svg .meter { fill: none; stroke: var(--accent-primary); stroke-width: 8; stroke-linecap: round; transform: rotate(-90deg); transform-origin: center; filter: drop-shadow(0 0 5px var(--accent-primary)); }
.radial-svg .val { fill: white; font-size: 1.2rem; font-weight: 800; text-anchor: middle; }

.secondary-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 24px;
}

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
.esg-trend { position: absolute; right: 12px; font-size: 0.7rem; font-weight: 700; color: var(--success); }

@media (max-width: 1400px) {
  .performance-grid { grid-template-columns: 1fr 1fr; }
  .secondary-grid { grid-template-columns: 1fr 1fr; }
}

@media (max-width: 900px) {
  .performance-grid { grid-template-columns: 1fr; }
  .secondary-grid { grid-template-columns: 1fr; }
  .highlights-grid { grid-template-columns: 1fr; }
}

@media (max-width: 768px) {
  .analytics-view { padding: 20px; }
  .header { flex-direction: column; align-items: flex-start; gap: 16px; }
  .time-filter { width: 100%; justify-content: space-between; overflow-x: auto; }
}
</style>
