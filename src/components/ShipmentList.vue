<template>
  <div class="shipment-list glass-panel">
    <div class="header">
      <h3 class="card-title">Live Shipments ({{ parcels.length }})</h3>
      <button class="more-btn">...</button>
    </div>

    <div class="list-container">
      <div v-if="parcels.length === 0" class="empty">No active shipments nearby</div>
      <div v-for="shipment in parcels" :key="shipment.parcelId" class="shipment-card">
        <div class="meta">
          <span class="id">#{{ shipment.parcelId }}</span>
          <span class="status in-transit">
            {{ shipment.status }}
          </span>
        </div>
        <div class="route">
          <div class="loc">
            <span class="city">{{ shipment.origin }}</span>
          </div>
          <div class="connector">
             <div class="line"></div>
             <span class="mode-icon">🚛</span>
          </div>
          <div class="loc">
            <span class="city">{{ shipment.destination }}</span>
          </div>
        </div>
        <div class="footer">
          <span class="company">{{ shipment.company }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  parcels: Array
});
</script>

<style scoped>
.shipment-list {
  padding: 20px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.more-btn {
  background: transparent;
  border: none;
  color: var(--text-tertiary);
  cursor: pointer;
}

.list-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
  overflow-y: auto;
}

.empty {
  text-align: center;
  color: var(--text-tertiary);
  padding: 40px 0;
}

.shipment-card {
  padding: 16px;
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid var(--glass-border);
  border-radius: 12px;
}

.meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12px;
}

.id { font-weight: 600; color: var(--accent-primary); }

.status {
  font-size: 0.75rem;
  padding: 2px 8px;
  border-radius: 20px;
  background: var(--bg-tertiary);
  color: var(--text-secondary);
}

.status.in-transit { background: rgba(34, 197, 94, 0.1); color: var(--success); }

.route {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 12px;
}

.loc { display: flex; flex-direction: column; }
.city { font-weight: 500; font-size: 0.9rem; }

.connector {
  flex: 1;
  display: flex;
  align-items: center;
  padding: 0 12px;
}

.line {
  flex: 1;
  height: 1px;
  background: var(--glass-border);
  border-top: 1px dashed var(--text-tertiary);
}

.mode-icon { margin: 0 8px; font-size: 0.8rem; }

.footer {
  font-size: 0.75rem;
  color: var(--text-tertiary);
  display: flex;
  justify-content: flex-end;
}
</style>
