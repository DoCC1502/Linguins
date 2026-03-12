<script setup lang="ts">
import {ref} from 'vue';
import {userService} from '../services/userService'; // Pfad zu deiner .ts Datei prüfen
import type {UserDto } from '../types/types.ts'; // Pfad zu deinen Typen prüfen

// 1. Datenmodell initialisieren
const form = ref<UserDto>({
  username: '',
  email: '',
  password: '',
  role: 'USER' // Standardwert für die Datenbank
});

const isLoading = ref(false);
const message = ref('');

// 2. Registrierungs-Logik
async function handleRegister() {
  isLoading.value = true;
  message.value = '';

  try {
    const result = await userService.register(form.value);
    message.value = `Erfolg! Willkommen, ${result.username}.`;

    // Formular nach Erfolg leeren
    form.value = {username: '', email: '', password: '', role: 'USER'};
  } catch (error: any) {
    message.value = "Fehler: " + error.message;
  } finally {
    isLoading.value = false;
  }
}
</script>

<template>
  <section class="page">
    <div class="card">
      <h1>Create Account Test</h1>

      <input
          v-model="form.username"
          placeholder="Full name"
          :disabled="isLoading"
      />
      <input
          v-model="form.email"
          type="email"
          placeholder="Email address"
          :disabled="isLoading"
      />
      <input
          v-model="form.password"
          type="password"
          placeholder="Password"
          :disabled="isLoading"
      />

      <button @click="handleRegister" :disabled="isLoading">
        {{ isLoading ? 'Processing...' : 'Register' }}
      </button>

      <p v-if="message" :class="{ 'error': message.includes('Fehler') }">
        {{ message }}
      </p>
    </div>
  </section>
</template>

<style scoped>
/* Optional: Ein bisschen Feedback-Styling */
.error {
  color: #ff4d4d;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>