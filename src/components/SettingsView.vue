<template>
  <div class="settings-view glass-panel">
    <div class="header">
      <h2>System Settings</h2>
    </div>

    <div class="settings-sections">
      <div class="section glass-panel">
        <h3>General Configuration</h3>
        <div class="setting-item">
          <div class="info">
            <span class="title">Automatic Refresh</span>
            <span class="desc">Refresh data every 30 seconds</span>
          </div>
          <div class="toggle-btn active"></div>
        </div>
        <div class="setting-item">
          <div class="info">
            <span class="title">Map Style</span>
            <span class="desc">Switch between Dark Matter and Light Voyager</span>
          </div>
          <select class="settings-select">
            <option>Dark Master</option>
            <option>Light Theme</option>
          </select>
        </div>
      </div>

      <div class="section glass-panel">
        <h3>Account & Privacy</h3>
        <div class="setting-item">
          <div class="info">
            <span class="title">Email Notifications</span>
            <span class="desc">Receive alerts for critical shipment delays</span>
          </div>
          <div class="toggle-btn"></div>
        </div>
        <div class="setting-item">
          <div class="info">
            <span class="title">Data Export</span>
            <span class="desc">Download logs for the past 30 days</span>
          </div>
          <button class="action-btn">Export .CSV</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.settings-view {
  margin: 20px;
  padding: 30px;
  height: calc(100vh - 120px);
  overflow-y: auto;
}

.header { margin-bottom: 30px; }

.settings-sections { display: flex; flex-direction: column; gap: 20px; }

.section { padding: 24px; }
.section h3 { font-size: 1.1rem; margin-bottom: 24px; color: var(--accent-primary); }

.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 0;
  border-bottom: 1px solid var(--glass-border);
}

.setting-item:last-child { border-bottom: none; }

.info { display: flex; flex-direction: column; gap: 4px; }
.title { font-weight: 600; font-size: 1rem; }
.desc { font-size: 0.85rem; color: var(--text-tertiary); }

.toggle-btn {
  width: 44px;
  height: 24px;
  background: var(--bg-tertiary);
  border-radius: 12px;
  position: relative;
  cursor: pointer;
}

.toggle-btn::after {
  content: '';
  position: absolute;
  top: 2px;
  left: 2px;
  width: 20px;
  height: 20px;
  background: white;
  border-radius: 50%;
  transition: 0.3s;
}

.toggle-btn.active { background: var(--accent-primary); }
.toggle-btn.active::after { left: 22px; }

.settings-select {
  background: var(--bg-tertiary);
  color: var(--text-primary);
  border: 1px solid var(--glass-border);
  padding: 8px 12px;
  border-radius: 8px;
}

.action-btn {
  background: var(--bg-tertiary);
  color: var(--text-primary);
  border: 1px solid var(--glass-border);
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
}
</style>
