<template>
  <nav class="navbar">
    <div class="brand-wrapper">
      <RouterLink to="/" class="brand">
        <img
            src="@/assets/images/lingoLOGO_ohneSchrift.png"
            alt="Linguins Logo"
        />
      </RouterLink>
      <span v-if="isLoggedIn" class="nav-username">{{ username }}</span>
    </div>

    <ul>
      <li><RouterLink to="/">Dashboard</RouterLink></li>
      <li><RouterLink to="/lessons">Lessons</RouterLink></li>
      <li><RouterLink to="/terminal">Terminal</RouterLink></li>
      <li><RouterLink to="/collaboration">Collaboration</RouterLink></li>
      <li><RouterLink to="/about">About</RouterLink></li>
      <li><RouterLink to="/contact">Contact</RouterLink></li>
      <template v-if="!isLoggedIn">
        <li><RouterLink to="/signin">Sign in</RouterLink></li>
        <li><RouterLink to="/register" class="cta">Register</RouterLink></li>
      </template>
      <template v-else>
        <li><button class="sign-out" @click="handleLogout">Sign out</button></li>
      </template>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { RouterLink, useRouter } from 'vue-router'
import { useUser } from '@/composables/useUser'

const { username, isLoggedIn, clearUser } = useUser()
const router = useRouter()

function handleLogout() {
  clearUser()
  router.push('/signin')
}
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

.sign-out {
  background: transparent;
  border: none;
  cursor: pointer;
  color: var(--text-muted);
  font-size: 1rem;
  font-weight: 500;
  padding: 0;
  transition: color 0.2s ease;
}

.sign-out:hover {
  color: var(--text-main);
}
</style>