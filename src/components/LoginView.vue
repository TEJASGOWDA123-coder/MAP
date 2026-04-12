<script setup>
import { ref } from 'vue'

const emit = defineEmits(['login'])

const username = ref('')
const password = ref('')
const role = ref('manager') // 'manager' or 'driver'
const isLoading = ref(false)

const handleLogin = () => {
    if (!username.value || !password.value) return
    
    isLoading.ref = true
    // Simulate API call
    setTimeout(() => {
        isLoading.value = false
        emit('login', {
            username: username.value,
            role: role.value
        })
    }, 1200)
}
</script>

<template>
  <div class="login-page">
    <div class="background-blobs">
      <div class="blob blob-1"></div>
      <div class="blob blob-2"></div>
      <div class="blob blob-3"></div>
    </div>

    <div class="login-container glass-panel">
      <div class="brand">
        <div class="logo-box">
          <svg width="40" height="40" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <rect width="32" height="32" rx="8" fill="url(#login_logo_grad)" />
            <path d="M8 12C8 10.8954 8.89543 10 10 10H22C23.1046 10 24 10.8954 24 12V20C24 21.1046 23.1046 22 22 22H10C8.89543 22 8 21.1046 8 20V12Z" stroke="white" stroke-width="2" />
            <path d="M8 14H24" stroke="white" stroke-width="2" />
            <defs>
              <linearGradient id="login_logo_grad" x1="0" y1="0" x2="32" y2="32" gradientUnits="userSpaceOnUse">
                <stop stop-color="#3b82f6" />
                <stop offset="1" stop-color="#6366f1" />
              </linearGradient>
            </defs>
          </svg>
        </div>
        <h1>Logistics Pro</h1>
        <p>Enterprise Fleet Management</p>
      </div>

      <form @submit.prevent="handleLogin" class="login-form">
        <div class="role-selector">
          <button type="button" 
                  :class="{ active: role === 'manager' }" 
                  @click="role = 'manager'">
            Fleet Manager
          </button>
          <button type="button" 
                  :class="{ active: role === 'driver' }" 
                  @click="role = 'driver'">
            Driver
          </button>
        </div>

        <div class="input-group">
          <label>Username</label>
          <div class="input-wrapper glass-panel">
            <span class="input-icon">👤</span>
            <input v-model="username" type="text" placeholder="Enter username" required />
          </div>
        </div>

        <div class="input-group">
          <label>Password</label>
          <div class="input-wrapper glass-panel">
            <span class="input-icon">🔒</span>
            <input v-model="password" type="password" placeholder="Enter password" required />
          </div>
        </div>

        <button type="submit" class="login-btn" :disabled="isLoading">
          <span v-if="!isLoading">Login to Dashboard</span>
          <span v-else class="loader"></span>
        </button>
      </form>

      <div class="footer">
        <p>© 2025 Logistics Pro Systems</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #050505;
  color: white;
  overflow: hidden;
  position: relative;
}

.background-blobs {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 0;
}

.blob {
  position: absolute;
  filter: blur(80px);
  border-radius: 50%;
  opacity: 0.4;
  animation: move 20s infinite alternate;
}

.blob-1 { width: 500px; height: 500px; background: rgba(59, 130, 246, 0.4); top: -100px; left: -100px; }
.blob-2 { width: 400px; height: 400px; background: rgba(99, 102, 241, 0.3); bottom: -100px; right: -100px; animation-delay: -5s; }
.blob-3 { width: 300px; height: 300px; background: rgba(236, 72, 153, 0.2); top: 50%; left: 50%; animation-delay: -10s; }

@keyframes move {
  from { transform: translate(0, 0) scale(1); }
  to { transform: translate(50px, 50px) scale(1.1); }
}

.login-container {
  width: 100%;
  max-width: 440px;
  padding: 48px;
  z-index: 1;
  position: relative;
  animation: entrance 0.8s cubic-bezier(0.16, 1, 0.3, 1);
}

@keyframes entrance {
  from { opacity: 0; transform: translateY(20px) scale(0.95); }
  to { opacity: 1; transform: translateY(0) scale(1); }
}

.brand {
  text-align: center;
  margin-bottom: 40px;
}

.logo-box {
  margin-bottom: 16px;
  display: flex;
  justify-content: center;
}

.brand h1 {
  font-size: 2rem;
  font-weight: 700;
  margin: 0;
  background: linear-gradient(to right, #fff, #a1a1aa);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.brand p {
  color: #71717a;
  font-size: 0.95rem;
  margin-top: 8px;
}

.role-selector {
  display: flex;
  background: rgba(255, 255, 255, 0.05);
  padding: 4px;
  border-radius: 12px;
  margin-bottom: 32px;
}

.role-selector button {
  flex: 1;
  padding: 10px;
  border: none;
  background: transparent;
  color: #71717a;
  font-weight: 600;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.role-selector button.active {
  background: var(--accent-primary, #3b82f6);
  color: white;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.input-group {
  margin-bottom: 24px;
}

.input-group label {
  display: block;
  font-size: 0.85rem;
  color: #a1a1aa;
  margin-bottom: 8px;
}

.input-wrapper {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  border-radius: 12px;
}

.input-icon {
  margin-right: 12px;
  font-size: 1.1rem;
  opacity: 0.6;
}

.input-wrapper input {
  background: transparent;
  border: none;
  color: white;
  width: 100%;
  outline: none;
  font-family: inherit;
}

/* Autofill Fix */
.input-wrapper input:-webkit-autofill,
.input-wrapper input:-webkit-autofill:hover, 
.input-wrapper input:-webkit-autofill:focus {
  -webkit-text-fill-color: white;
  -webkit-box-shadow: 0 0 0px 1000px #1a1a1c inset;
  transition: background-color 5000s ease-in-out 0s;
}

.login-btn {
  width: 100%;
  padding: 14px;
  background: var(--accent-primary, #3b82f6);
  color: white;
  border: none;
  border-radius: 12px;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s;
  margin-top: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  filter: brightness(1.1);
}

.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.footer {
  text-align: center;
  margin-top: 32px;
  color: #3f3f46;
  font-size: 0.8rem;
}

.loader {
  width: 20px;
  height: 20px;
  border: 3px solid rgba(255,255,255,0.3);
  border-radius: 50%;
  border-top-color: #fff;
  animation: spin 1s ease-in-out infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
