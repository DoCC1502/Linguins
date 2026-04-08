<template>
  <nav class="navbar">
    <RouterLink to="/" class="brand">
      <img
          src="@/assets/images/lingoLOGO_ohneSchrift.png"
          alt="Linguins Logo"
      />
    </RouterLink>

    <ul>
      <li><RouterLink to="/">Dashboard</RouterLink></li>
      <li><RouterLink to="/lessons">Lessons</RouterLink></li>
      <li><RouterLink to="/terminal">Pro</RouterLink></li>
      <li><RouterLink to="/free">Free</RouterLink></li>
      <li><RouterLink to="/collaboration">Collaboration</RouterLink></li>
      <li><RouterLink to="/about">About</RouterLink></li>
      <li><RouterLink to="/contact">Contact</RouterLink></li>
      <li><RouterLink to="/signin">Sign in</RouterLink></li>
      <li><RouterLink to="/register" class="cta">Register</RouterLink></li>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import { RouterLink } from 'vue-router'
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



ul {
  display: flex;
  gap: 1.8rem;
  list-style: none;
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
  height: 80px;        /* NOCH GRÖSSER */
  width: auto;
  opacity: 1;
  transition: transform 0.2s ease;
}

.brand img {
  filter: drop-shadow(0 4px 12px rgba(0, 0, 0, 0.4));
}





</style>
