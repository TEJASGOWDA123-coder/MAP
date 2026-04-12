<script setup>
import { computed } from 'vue';
defineEmits(['show-notifications', 'show-profile']);

const formattedDate = computed(() => {
  const now = new Date();
  const options = { day: '2-digit', month: 'short', year: 'numeric' };
  const dateStr = now.toLocaleDateString('en-GB', options);
  // dateStr is like "12 Apr 2026"
  // We want "Today, 12 Apr, 2026"
  const parts = dateStr.split(' ');
  return `Today, ${parts[0]} ${parts[1]}, ${parts[2]}`;
});
</script>


<template>
  <header class="topbar">
    <div class="welcome">
      <h1>Welcome, Daniel</h1>
      <p>Tracking your fleet in real-time</p>
    </div>

    <div class="search-box glass-panel">
      <span class="search-icon">🔍</span>
      <input type="text" placeholder="Search shipments, vehicles..." />
    </div>

    <div class="actions">
      <div class="date-picker glass-panel">
        📅 {{ formattedDate }}
      </div>
      <div class="notification-btn glass-panel" @click="$emit('show-notifications')">
        🔔
      </div>
      <div class="user-profile" @click="$emit('show-profile')">
        <img src="https://i.pravatar.cc/150?u=daniel" alt="User" />
      </div>
    </div>
  </header>
</template>

<style scoped>
.topbar {
  height: var(--topbar-height);
  width: calc(100% - var(--sidebar-width) - 40px);
  margin-left: calc(var(--sidebar-width) + 40px);
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.welcome h1 {
  font-size: 1.5rem;
  margin: 0;
  font-weight: 600;
}

.welcome p {
  margin: 4px 0 0 0;
  color: var(--text-tertiary);
  font-size: 0.9rem;
}

.search-box {
  flex: 0 1 400px;
  height: 44px;
  display: flex;
  align-items: center;
  padding: 0 16px;
}

.search-box input {
  background: transparent;
  border: none;
  outline: none;
  color: var(--text-primary);
  margin-left: 12px;
  width: 100%;
}

.actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.date-picker {
  padding: 8px 16px;
  font-size: 0.9rem;
  color: var(--text-secondary);
}

.notification-btn {
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

.user-profile img {
  width: 44px;
  height: 44px;
  border-radius: 12px;
  border: 2px solid var(--accent-primary);
}
</style>
