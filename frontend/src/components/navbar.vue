<template>
  <nav class="navbar">
    <div class="brand-section">
      <RouterLink to="/" class="brand">
        <img
            src="@/assets/images/lingoLOGO_ohneSchrift.png"
            alt="Linguins Logo"
        />
      </RouterLink>
      <span v-if="currentUser" class="username-display">Hallo, {{ currentUser.username }} !</span>
    </div>

    <ul>
      <li><RouterLink to="/">Dashboard</RouterLink></li>
      <li><RouterLink to="/lessons">Lessons</RouterLink></li>
      <li><RouterLink to="/terminal">Pro</RouterLink></li>
      <li><RouterLink to="/free">Free</RouterLink></li>
      <li><RouterLink to="/collaboration">Collaboration</RouterLink></li>
      <li><RouterLink to="/about">About</RouterLink></li>
      <li><RouterLink to="/contact">Contact</RouterLink></li>
      <template v-if="currentUser">
        <li><a href="#" @click.prevent="handleLogout" class="signout">Sign out</a></li>
      </template>
      <template v-else>
        <li><RouterLink to="/signin">Sign in</RouterLink></li>
        <li><RouterLink to="/register" class="cta">Register</RouterLink></li>
      </template>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { RouterLink, useRouter, useRoute } from 'vue-router'
import { ref, onMounted, watch, onUnmounted } from 'vue'
import type { UserDto } from '@/types/types'

const router = useRouter()
const route = useRoute()
const currentUser = ref<UserDto | null>(null)

const loadUser = () => {
  const userData = localStorage.getItem('user')
  if (userData) {
    try {
      currentUser.value = JSON.parse(userData)
    } catch (e) {
      currentUser.value = null
    }
  } else {
    currentUser.value = null
  }
}

const handleLogout = () => {
  localStorage.removeItem('user')
  currentUser.value = null
  router.push('/')
}

// Watch for route changes to refresh user state after login/logout
watch(() => route.path, () => {
  loadUser()
})

// Listen for storage events to catch updates from the same tab
const handleStorageChange = (event: StorageEvent) => {
  if (event.key === 'user') {
    loadUser()
  }
}

// Custom event for same-tab updates (since StorageEvent doesn't fire in same tab)
const handleCustomUserUpdate = () => {
  loadUser()
}

onMounted(() => {
  loadUser()
  window.addEventListener('storage', handleStorageChange)
  window.addEventListener('user-updated', handleCustomUserUpdate)
})

onUnmounted(() => {
  window.removeEventListener('storage', handleStorageChange)
  window.removeEventListener('user-updated', handleCustomUserUpdate)
})
</script>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  z-index: 50;
  height: 64px;
  padding: 0 2.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;

  background: rgba(15, 23, 42, 0.75);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--border-soft);
}

.brand-wrapper {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.nav-username {
  font-size: 0.95rem;
  font-weight: 600;
  color: #42b883;
  white-space: nowrap;
  letter-spacing: 0.01em;
}

ul {
  display: flex;
  gap: 1.8rem;
  list-style: none;
  align-items: center;
}

a {
  color: var(--text-muted);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s ease;
}

a:hover {
  color: var(--text-main);
}

.cta {
  padding: 0.4rem 0.9rem;
  border-radius: 999px;
  background: var(--primary);
  color: #000;
  font-weight: 600;
}

.brand-section {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.brand {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.brand img {
  height: 80px;
  width: auto;
  opacity: 1;
  transition: transform 0.2s ease;
}

.brand img {
  filter: drop-shadow(0 4px 12px rgba(0, 0, 0, 0.4));
}

.username-display {
  color: var(--primary);
  font-weight: 600;
  font-size: 1rem;
}

.signout {
  color: var(--text-muted);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s ease;
}

.signout:hover {
  color: #ff4d4d;
}
</style>
