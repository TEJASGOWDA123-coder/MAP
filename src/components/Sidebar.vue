<template>
  <aside class="sidebar glass-panel">
    <div class="logo">
      <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect width="32" height="32" rx="8" fill="url(#logo_grad)" />
        <path d="M8 12C8 10.8954 8.89543 10 10 10H22C23.1046 10 24 10.8954 24 12V20C24 21.1046 23.1046 22 22 22H10C8.89543 22 8 21.1046 8 20V12Z" stroke="white" stroke-width="2" />
        <path d="M8 14H24" stroke="white" stroke-width="2" />
        <defs>
          <linearGradient id="logo_grad" x1="0" y1="0" x2="32" y2="32" gradientUnits="userSpaceOnUse">
            <stop stop-color="#3b82f6" />
            <stop offset="1" stop-color="#6366f1" />
          </linearGradient>
        </defs>
      </svg>
    </div>

    <nav class="nav-links">
      <div v-for="(item, index) in navItems" :key="index" 
           class="nav-item" :class="{ active: modelValue === index }"
           @click="$emit('update:modelValue', index)">
        <span class="icon">{{ item.icon }}</span>
      </div>
    </nav>

    <div class="bottom-actions">
      <div class="nav-item" @click="$emit('toggle-theme')">
        <span class="icon">{{ isDark ? '☀️' : '🌙' }}</span>
      </div>
      <div class="nav-item" @click="$emit('logout')">
        <span class="icon">🚪</span>
      </div>
    </div>
  </aside>
</template>

<script setup>
const props = defineProps(['modelValue', 'isDark']);
const emit = defineEmits(['update:modelValue', 'toggle-theme', 'logout']);

const navItems = [
  { icon: '📁', label: 'Dashboard' },
  { icon: '🚛', label: 'Fleet' },
  { icon: '📍', label: 'Routes' },
  { icon: '📊', label: 'Analytics' },
  { icon: '💰', label: 'Revenue' },
];

</script>

<style scoped>
.sidebar {
  width: var(--sidebar-width);
  height: calc(100vh - 40px);
  margin: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 0;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 100;
}

.logo {
  margin-bottom: 48px;
}

.nav-links {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.nav-item {
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  cursor: pointer;
  transition: var(--transition-smooth);
  color: var(--text-secondary);
  font-size: 1.2rem;
}

.nav-item:hover {
  background: var(--glass-bg-hover, rgba(255, 255, 255, 0.08));
  color: var(--text-primary);
  transform: scale(1.05);
}

.nav-item.active {
  background: var(--accent-primary);
  color: white;
  box-shadow: 0 4px 20px rgba(59, 130, 246, 0.5);
  transform: scale(1.1);
}

.bottom-actions {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: auto;
}
</style>
