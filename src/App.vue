<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import Sidebar from './components/Sidebar.vue'
import Topbar from './components/Topbar.vue'
import StatsCard from './components/StatsCard.vue'
import ShipmentList from './components/ShipmentList.vue'
import MapComponent from './components/MapComponent.vue'
import RevenueTrends from './components/RevenueTrends.vue'
import FleetStatus from './components/FleetStatus.vue'
import FleetView from './components/FleetView.vue'
import RoutesView from './components/RoutesView.vue'
import AnalyticsView from './components/AnalyticsView.vue'
import RevenueView from './components/RevenueView.vue'
import SettingsView from './components/SettingsView.vue'
import LoginView from './components/LoginView.vue'
import DriverView from './components/DriverView.vue'
import MiniRoutes from './components/MiniRoutes.vue'
import MiniAnalytics from './components/MiniAnalytics.vue'
import PredictiveRisk from './components/PredictiveRisk.vue'
import ESGTracker from './components/ESGTracker.vue'
import RegionalThroughput from './components/RegionalThroughput.vue'
import ParcelService from './services/ParcelService'
import WebSocketService from './services/WebSocketService'

const isLoggedIn = ref(false)
const userRole = ref(null) // 'manager' or 'driver'
const activeTab = ref(0)
const isDark = ref(true)
const showModal = ref(null) // 'notifications', 'profile', null
const parcels = ref([])
const latency = ref(0)
const loading = ref(false)
const center = [12.9716, 77.5946]

const handleLocationUpdate = (update) => {
  const { parcelId, coordinates, timestamp } = update
  latency.value = Date.now() - timestamp
  
  const [lng, lat] = coordinates
  const existing = parcels.value.find(p => p.parcelId === parcelId)
  
  if (existing) {
    existing.currentLocation.coordinates = [lng, lat]
    existing.status = 'In Transit'
    existing.lastUpdate = Date.now()
  } else {
    parcels.value.push({
      parcelId,
      status: 'In Transit',
      currentLocation: { type: 'Point', coordinates: [lng, lat] },
      origin: 'DC Bangalore',
      destination: 'Local Delivery',
      mode: 'Road',
      company: 'Express Logistics'
    })
  }
}

const fetchParcels = async () => {
  loading.value = true
  try {
    const response = await ParcelService.getNearbyParcels(center[1], center[0], 5)
    response.data.forEach(p => {
      handleLocationUpdate({
        parcelId: p.parcelId,
        coordinates: p.currentLocation.coordinates,
        timestamp: Date.now()
      })
    })
  } catch (error) {
    console.error('Error fetching parcels:', error)
  } finally {
    loading.value = false
  }
}

const handleLogin = (userData) => {
  userRole.value = userData.role
  isLoggedIn.value = true
  fetchParcels()
  WebSocketService.connect()
}

const handleLogout = () => {
  isLoggedIn.value = false
  userRole.value = null
  WebSocketService.disconnect()
}

const toggleTheme = () => {
  isDark.value = !isDark.value
  document.documentElement.classList.toggle('light-theme', !isDark.value)
}

onMounted(() => {
  const unsubscribe = WebSocketService.subscribe(handleLocationUpdate)
  onUnmounted(() => {
    unsubscribe()
    WebSocketService.disconnect()
  })
})
</script>

<template>
  <Transition name="page" mode="out-in">
    <!-- Login Screen -->
    <LoginView v-if="!isLoggedIn" @login="handleLogin" />

    <!-- Driver View -->
    <DriverView v-else-if="userRole === 'driver'" @logout="handleLogout" />

    <!-- Main Management Dashboard -->
    <div v-else class="app-layout" :class="{ 'light-theme': !isDark }">
      <Sidebar v-model="activeTab" :isDark="isDark" @toggle-theme="toggleTheme" @logout="handleLogout" />
      <main class="main-content">
        <Topbar @show-notifications="showModal = 'notifications'" @show-profile="showModal = 'profile'" />
        
        <Transition name="fade" mode="out-in">
          <div :key="activeTab" class="view-wrapper">
            <div v-if="activeTab === 0" class="dashboard-grid">
              <!-- Row 1: Stats -->
              <div class="grid-col-3">
                <StatsCard label="Total Active Shipments" value="275" trendValue="+6.3" trendDirection="up" color="#3b82f6">
                  <template #icon>🚛</template>
                </StatsCard>
              </div>
              <div class="grid-col-3">
                <StatsCard label="Pending Shipments" value="1.3k" trendValue="-3.5" trendDirection="down" color="#f59e0b">
                  <template #icon>🕒</template>
                </StatsCard>
              </div>
              <div class="grid-col-3">
                <StatsCard label="Delivered Shipments" value="5.7k" trendValue="-4.2" trendDirection="down" color="#22c55e">
                  <template #icon>📦</template>
                </StatsCard>
              </div>
              <div class="grid-col-3">
                <StatsCard label="Total Revenue" value="$5.3m" trendValue="+5.6" trendDirection="up" color="#ec4899">
                  <template #icon>💰</template>
                </StatsCard>
              </div>

              <!-- Row 2: Map and Shipment List -->
              <div class="grid-col-8 grid-row-span-2">
                <MapComponent :parcels="parcels" :latency="latency" />
              </div>
              <div class="grid-col-4 grid-row-span-2">
                <ShipmentList :parcels="parcels" />
              </div>

              <!-- Row 3: Revenue and Fleet Status -->
              <div class="grid-col-8">
                 <RevenueTrends />
              </div>
              <div class="grid-col-4">
                 <FleetStatus />
              </div>

              <!-- Row 4: Advanced Integrated Widgets -->
              <div class="grid-col-4">
                <PredictiveRisk />
              </div>
              <div class="grid-col-4">
                <ESGTracker />
              </div>
              <div class="grid-col-4">
                <RegionalThroughput />
              </div>
            </div>

            <div v-else-if="activeTab === 1" class="fleet-content">
              <FleetView />
            </div>

            <div v-else-if="activeTab === 2" class="routes-content">
              <RoutesView />
            </div>

            <div v-else-if="activeTab === 3" class="analytics-content">
              <AnalyticsView />
            </div>

            <div v-else-if="activeTab === 4" class="revenue-content">
              <RevenueView />
            </div>

            <div v-else-if="activeTab === 5" class="settings-content">
              <SettingsView />
            </div>

            <div v-else class="coming-soon">
              <div class="glass-panel">
                <h2>{{ ['Dashboard', 'Fleet', 'Routes', 'Analytics', 'Revenue', 'Settings'][activeTab] || 'Unknown' }} Module</h2>
                <p>Real-time data for this section is currently being synced from the cloud.</p>
                <button @click="activeTab = 0" class="back-btn">Return to Dashboard</button>
              </div>
            </div>
          </div>
        </Transition>

        <!-- Simple Modals -->
        <div v-if="showModal" class="modal-overlay" @click="showModal = null">
          <div class="modal-content glass-panel" @click.stop>
            <h3>{{ showModal === 'notifications' ? 'Notifications' : 'User Profile' }}</h3>
            <p v-if="showModal === 'notifications'">No new notifications.</p>
            <div v-else class="profile-info">
               <img src="https://i.pravatar.cc/150?u=daniel" />
               <h4>Daniel Richards</h4>
               <p>Fleet Manager</p>
            </div>
            <button @click="showModal = null" class="close-btn">Close</button>
          </div>
        </div>
      </main>
    </div>
  </Transition>
</template>

<style>
.app-layout {
  display: flex;
  height: 100vh;
  width: 100vw;
  background-color: var(--bg-primary);
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  padding-left: 100px; /* Base offset for sidebar margin + width */
}

.coming-soon {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.coming-soon .glass-panel {
  padding: 60px;
  text-align: center;
}

.back-btn {
  margin-top: 20px;
  padding: 10px 20px;
  background: var(--accent-primary);
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  padding: 40px;
  width: 400px;
}

.profile-info {
  text-align: center;
  margin: 20px 0;
}

.profile-info img {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  margin-bottom: 12px;
}

.close-btn {
  width: 100%;
  padding: 10px;
  background: var(--bg-tertiary);
  color: var(--text-primary);
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.app-layout.light-theme {
  --bg-primary: #f8fafc;
  --bg-secondary: #ffffff;
  --bg-tertiary: #f1f5f9;
  --text-primary: #0f172a;
  --text-secondary: #475569;
  --text-tertiary: #94a3b8;
  --glass-bg: rgba(255, 255, 255, 0.7);
  --glass-border: rgba(0, 0, 0, 0.05);
}

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  grid-template-rows: auto auto auto;
  gap: 20px;
  padding: 0 40px 40px 20px;
  overflow-y: auto;
  flex: 1;
}

.view-wrapper {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.fleet-content, .routes-content, .analytics-content, .revenue-content, .settings-content {
  flex: 1;
  overflow-y: auto;
}

.grid-col-3 { grid-column: span 3; }
.grid-col-4 { grid-column: span 4; }
.grid-col-8 { grid-column: span 8; }
.grid-row-span-2 { grid-row: span 2; }

@media (max-width: 1400px) {
  .dashboard-grid {
    grid-template-columns: repeat(6, 1fr);
  }
  .grid-col-3 { grid-column: span 3; }
  .grid-col-4 { grid-column: span 6; }
  .grid-col-8 { grid-column: span 6; }
}
</style>
