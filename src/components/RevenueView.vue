<template>
  <div class="revenue-view">
    <div class="header">
      <div class="title-group">
        <h2>Revenue Management</h2>
        <p class="subtitle">Financial performance and projected earnings from batch operations</p>
      </div>
      <div class="actions">
        <button @click="syncData" :disabled="syncing" class="sync-btn" :class="{ rotating: syncing }">
          {{ syncing ? 'Syncing...' : '🔄 Sync Reality' }}
        </button>
      </div>
    </div>

    <div class="financial-dashboard">
      <!-- Main Cards -->
      <div class="revenue-cards">
        <div class="f-card glass-panel gold">
          <div class="f-info">
            <span class="f-lbl">Total Batch Volume</span>
            <span class="f-val">{{ totalShipments.toLocaleString() }}</span>
            <div class="f-sub">100k records analyzed</div>
          </div>
          <div class="f-icon">💰</div>
        </div>

        <div class="f-card glass-panel cyan">
          <div class="f-info">
            <span class="f-lbl">Estimated Revenue</span>
            <span class="f-val">${{ (totalShipments * 0.42).toLocaleString(undefined, { maximumFractionDigits: 2 }) }}</span>
            <div class="f-sub">Avg $0.42 per unit</div>
          </div>
          <div class="f-icon">📊</div>
        </div>

        <div class="f-card glass-panel purple">
          <div class="f-info">
            <span class="f-lbl">System Margin</span>
            <span class="f-val">42.5%</span>
            <div class="f-sub">Optimum threshold</div>
          </div>
          <div class="f-icon">📈</div>
        </div>
      </div>

      <!-- Service Breakdown -->
      <div class="breakdown-section">
        <div class="breakdown-card glass-panel">
          <div class="card-head">
            <h3>Service Type Contribution</h3>
            <span class="info-icon">ℹ️</span>
          </div>
          <div class="breakdown-list">
            <div v-for="s in services" :key="s.name" class="service-row">
              <div class="s-main">
                <div class="s-dot" :style="{ background: s.color }"></div>
                <span class="s-name">{{ s.name }}</span>
                <span class="s-perc">{{ s.perc }}%</span>
              </div>
              <div class="s-bar-wrap">
                <div class="s-bar" :style="{ width: s.perc + '%', background: s.color }"></div>
              </div>
              <div class="s-val-group">
                 <span class="s-val">${{ s.val }}M</span>
                 <span class="s-trend up">↑ 2.4%</span>
              </div>
            </div>
          </div>
        </div>

        <div class="mini-stats glass-panel">
          <h3>Efficiency Metrics</h3>
          <div class="stat-bubble">
            <span class="b-val">{{ avgSpeed.toFixed(1) }}</span>
            <span class="b-lbl">KM/H AVG</span>
          </div>
          <div class="stat-divider"></div>
          <div class="stat-summary">
            <p>System speed is <strong>optimal</strong> based on batch processing results.</p>
            <button class="report-btn">Generate Report</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import BatchService from '../services/BatchService';

const totalShipments = ref(100000);
const avgSpeed = ref(45.5);
const syncing = ref(false);

const services = [
  { name: 'Last-Mile Delivery', perc: 45, val: 2.4, color: '#3b82f6' },
  { name: 'Long-Haul Freighting', perc: 30, val: 1.6, color: '#10b981' },
  { name: 'Warehousing & Storage', perc: 15, val: 0.8, color: '#f59e0b' },
  { name: 'International Shipping', perc: 10, val: 0.5, color: '#6366f1' }
];

const syncData = async () => {
  syncing.value = true;
  try {
    const results = await BatchService.getAnalyticsResults();
    if (results && results.length > 0) {
      totalShipments.value = results.reduce((acc, curr) => acc + curr.totalRecords, 0);
      avgSpeed.value = results.reduce((acc, curr) => acc + curr.avgSpeed, 0) / results.length;
    }
  } catch (err) {
    console.error('Failed to sync revenue data');
  } finally {
    setTimeout(() => { syncing.value = false; }, 800);
  }
};

onMounted(syncData);
</script>

<style scoped>
.revenue-view {
  padding: 40px;
  max-width: 1400px;
  margin: 0 auto;
}

.header { display: flex; justify-content: space-between; margin-bottom: 40px; align-items: flex-end; }
.subtitle { color: var(--text-tertiary); font-size: 0.9rem; margin-top: 4px; }

.sync-btn {
  background: var(--accent-primary);
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}

.sync-btn:hover { transform: translateY(-2px); box-shadow: 0 8px 16px rgba(59,130,246,0.3); }
.sync-btn.rotating { pointer-events: none; }

.financial-dashboard { display: flex; flex-direction: column; gap: 30px; }

.revenue-cards { display: grid; grid-template-columns: repeat(3, 1fr); gap: 24px; }

.f-card {
  padding: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.f-card::after {
  content: '';
  position: absolute;
  top: 0; right: 0; bottom: 0; left: 0;
  background: linear-gradient(135deg, rgba(255,255,255,0.05), transparent);
  pointer-events: none;
}

.f-info { display: flex; flex-direction: column; gap: 8px; }
.f-lbl { font-size: 0.9rem; color: var(--text-tertiary); }
.f-val { font-size: 2rem; font-weight: 800; }
.f-sub { font-size: 0.75rem; color: var(--text-tertiary); opacity: 0.7; }

.f-icon { font-size: 2.5rem; opacity: 0.2; }

.f-card.gold { border-left: 4px solid #f59e0b; }
.f-card.cyan { border-left: 4px solid #06b6d4; }
.f-card.purple { border-left: 4px solid #a855f7; }

.breakdown-section { display: grid; grid-template-columns: 2fr 1fr; gap: 24px; }

.breakdown-card { padding: 30px; }
.card-head { display: flex; justify-content: space-between; margin-bottom: 30px; }
.info-icon { opacity: 0.5; cursor: pointer; }

.service-row {
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 20px 0;
  border-bottom: 1px solid var(--glass-border);
}
.service-row:last-child { border-bottom: none; }

.s-main { display: flex; align-items: center; gap: 12px; }
.s-dot { width: 10px; height: 10px; border-radius: 50%; }
.s-name { font-weight: 600; flex: 1; }
.s-perc { font-size: 0.9rem; color: var(--text-tertiary); }

.s-bar-wrap { height: 6px; background: var(--bg-tertiary); border-radius: 3px; overflow: hidden; }
.s-bar { height: 100%; border-radius: 3px; transition: width 1s ease-out; }

.s-val-group { display: flex; justify-content: space-between; align-items: baseline; }
.s-val { font-weight: 700; font-size: 1.1rem; }
.s-trend { font-size: 0.75rem; color: var(--success); font-weight: 700; }

.mini-stats { padding: 30px; display: flex; flex-direction: column; align-items: center; justify-content: center; text-align: center; }

.stat-bubble {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: var(--bg-tertiary);
  border: 4px solid var(--accent-primary);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 30px 0;
  box-shadow: 0 0 30px rgba(59,130,246,0.2);
}

.b-val { font-size: 2rem; font-weight: 800; color: var(--accent-primary); }
.b-lbl { font-size: 0.7rem; font-weight: 700; color: var(--text-tertiary); }

.stat-divider { width: 40px; height: 2px; background: var(--glass-border); margin-bottom: 20px; }

.report-btn {
  margin-top: 20px;
  width: 100%;
  padding: 12px;
  background: var(--bg-tertiary);
  color: var(--text-primary);
  border: 1px solid var(--glass-border);
  border-radius: 12px;
  font-weight: 600;
  cursor: pointer;
  transition: 0.3s;
}
.report-btn:hover { background: var(--text-primary); color: var(--bg-primary); }
</style>
