<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from 'vue-router'; // WICHTIG für den Seitenwechsel

const email = ref<string>('');
const password = ref<string>('');
const errorMessage = ref<string | null>(null); // Speichert die Fehlermeldung
const router = useRouter(); // Instanz des Routers

const handleLogin = async () => {
  errorMessage.value = null; // Fehler bei neuem Versuch zurücksetzen

  try {
    const response = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: email.value,
        password: password.value
      })
    });

    if (response.ok) {
      const user = await response.json();
      console.log('Login erfolgreich');

      // Optional: User-Daten im Browser merken
      localStorage.setItem('user', JSON.stringify(user));

      // REDIRECT zur Hauptseite (meistens "/" oder "/home")
      router.push('/');
    } else {
      // FEHLER: Nachricht vom Server (z.B. "Passwort falsch") anzeigen
      const message = await response.text();
      errorMessage.value = message || 'Anmeldung fehlgeschlagen.';
    }
  } catch (err) {
    console.error(err);
    errorMessage.value = 'Server nicht erreichbar. Bitte später versuchen.';
  }
};
</script>

<template>
  <section class="page">
    <div class="card">
      <h1>Sign In</h1>
      <input v-model="email" type="email" placeholder="Email address"/>
      <input v-model="password" type="password" placeholder="Password" @keyup.enter="handleLogin"/>

      <button @click="handleLogin">Sign In</button>

      <transition name="fade">
        <p v-if="errorMessage" class="error-text">
          {{ errorMessage }}
        </p>
      </transition>
    </div>
  </section>
</template>

<style scoped>
.error-text {
  color: #ff4d4d;
  font-size: 0.9rem;
  margin-top: 15px;
  text-align: center;
  font-weight: bold;
}

/* Ein kleiner Effekt für das Erscheinen der Meldung */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>