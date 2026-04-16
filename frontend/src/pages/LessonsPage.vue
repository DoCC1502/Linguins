<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import { lessonService } from '../services/lessonService';
import type { LessonDto, UserDto } from '../types/types';

/**
 * LOGIK BEREICH
 */
const router = useRouter();
const lessons = ref<LessonDto[]>([]);
const isLoading = ref(true);
const showDropdown = ref(false);
const dropdownRef = ref<HTMLElement | null>(null);

// Simulierter Admin-Status (In einer echten App aus Pinia/Auth-Store)
const currentUser = ref<UserDto | null>({
  username: 'Admin',
  email : 'admin@linguins',
  role: 'ADMIN'
});

// Dropdown schließen, wenn man außerhalb klickt (Unternehmen-Standard UX)
const handleClickOutside = (event: MouseEvent) => {
  if (dropdownRef.value && !dropdownRef.value.contains(event.target as Node)) {
    showDropdown.value = false;
  }
};

onMounted(async () => {
  window.addEventListener('click', handleClickOutside);
  try {
    // Daten vom Backend laden
    const data = await lessonService.getAllLessons();
    lessons.value = data;
  } catch (error) {
    console.error("Fehler beim Laden der Lektionen:", error);
  } finally {
    // Kleiner Delay für geschmeidigeres UI-Feeling
    setTimeout(() => {
      isLoading.value = false;
    }, 400);
  }
});

onUnmounted(() => {
  window.removeEventListener('click', handleClickOutside);
});

const toggleDropdown = (e: Event) => {
  e.stopPropagation();
  showDropdown.value = !showDropdown.value;
};

// Navigation zu den spezifischen Lektions-Files (Templates)
async function selectTemplate(routePath: string) {
  showDropdown.value = false;
  router.push(`/lessons/${routePath}`);
}

// Navigation zur Lektion (mit ID für Progress Sync)
function navigateToLesson(lessonId: number | undefined) {
  if (lessonId === undefined) return;
  router.push(`/lessons/${lessonId}`);
}
</script>

<template>
  <section class="dashboard">
    <header class="top-bar">
      <div class="header-content">
        <div class="brand-zone">
          <h1 class="brand-title">Linguins <span class="accent">Academy</span></h1>
          <p class="subtitle">Meistere die Linux-Kommandozeile interaktiv.</p>
        </div>

        <div v-if="currentUser?.role === 'ADMIN'" class="admin-zone" ref="dropdownRef">
          <button @click="toggleDropdown" class="action-button primary">
            <span class="icon">＋</span>
            Template hinzufügen
          </button>

          <transition name="slide-fade">
            <div v-if="showDropdown" class="glass-dropdown">
              <div class="dropdown-header">Lektions-Vorlagen</div>
              <div class="dropdown-grid">
                <button @click="selectTemplate('linux-fundamentals')">Fundamentals</button>
                <button @click="selectTemplate('essential-commands')">Commands</button>
                <button @click="selectTemplate('file-permissions')">Permissions</button>
                <button @click="selectTemplate('process-management')">Processes</button>
                <button @click="selectTemplate('network-commands')">Network</button>
                <button @click="selectTemplate('shell-scripting')">Scripting</button>
              </div>
            </div>
          </transition>
        </div>
      </div>
    </header>

    <div class="container">
      <hr class="separator" />

      <div v-if="isLoading" class="lessons-grid">
        <div v-for="i in 6" :key="i" class="skeleton-card"></div>
      </div>

      <div v-else class="lessons-grid">
        <div
            v-for="lesson in lessons"
            :key="lesson.id"
            class="modern-card"
            @click="navigateToLesson(lesson.id)"
        >
          <div class="card-inner">
            <div class="card-badge">Level {{ lesson.difficulty }}</div>
            <h3>{{ lesson.title }}</h3>
            <p>Interaktive Übung zur Vertiefung deiner Linux-Kenntnisse im Terminal.</p>

            <div class="card-footer">
              <div class="progress-info">
                <span>Dein Fortschritt</span>
                <span>0%</span>
              </div>
              <div class="progress-track">
                <div class="progress-fill" style="width: 0%"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* VARIABLE DEFINITIONEN */
.dashboard {
  --primary: #00dc82;
  --bg-dark: #0f172a;
  --card-bg: #1e293b;
  --text-main: #f8fafc;
  --text-muted: #94a3b8;

  min-height: 100vh;
  background-color: var(--bg-dark);
  color: var(--text-main);
  font-family: 'Inter', system-ui, sans-serif;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem 4rem 2rem;
}

/* HEADER STYLES */
.top-bar {
  padding: 4rem 0 2rem 0;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.brand-title {
  font-size: 3rem;
  font-weight: 800;
  letter-spacing: -2px;
  margin: 0;
}

.accent { color: var(--primary); }
.subtitle { color: var(--text-muted); margin-top: 0.5rem; font-size: 1.1rem; }

.separator {
  border: 0;
  height: 1px;
  background: linear-gradient(to right, rgba(255,255,255,0.1), rgba(255,255,255,0.05));
  margin: 2rem 0 3rem 0;
}

/* ADMIN BUTTON & GLASS DROPDOWN */
.admin-zone { position: relative; }

.action-button.primary {
  background: var(--primary);
  color: #0f172a;
  padding: 0.8rem 1.4rem;
  border-radius: 10px;
  font-weight: 700;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.6rem;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.action-button.primary:hover {
  transform: translateY(-3px);
  box-shadow: 0 10px 25px rgba(0, 220, 130, 0.4);
}

.glass-dropdown {
  position: absolute;
  right: 0;
  top: calc(100% + 15px);
  background: rgba(30, 41, 59, 0.7);
  backdrop-filter: blur(16px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  padding: 1.5rem;
  width: 340px;
  z-index: 1000;
  box-shadow: 0 25px 50px rgba(0,0,0,0.6);
}

.dropdown-header {
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: var(--text-muted);
  margin-bottom: 1rem;
  font-weight: 700;
}

.dropdown-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 0.6rem;
}

.dropdown-grid button {
  background: rgba(255,255,255,0.03);
  border: 1px solid rgba(255,255,255,0.05);
  color: white;
  padding: 0.7rem;
  border-radius: 8px;
  font-size: 0.85rem;
  cursor: pointer;
  transition: 0.2s ease;
  text-align: center;
}

.dropdown-grid button:hover {
  background: var(--primary);
  color: #0f172a;
  border-color: var(--primary);
  font-weight: 600;
}

/* MODERN LESSON CARDS */
.lessons-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(340px, 1fr));
  gap: 2.5rem;
}

.modern-card {
  background: linear-gradient(135deg, rgba(255,255,255,0.05) 0%, rgba(255,255,255,0) 100%);
  border-radius: 20px;
  padding: 1px;
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid rgba(255,255,255,0.05);
}

.card-inner {
  background: #1e293b;
  border-radius: 19px;
  padding: 2.5rem;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.modern-card:hover {
  transform: translateY(-12px);
  border-color: var(--primary);
  box-shadow: 0 20px 40px rgba(0,0,0,0.4);
}

.card-badge {
  background: rgba(0, 220, 130, 0.1);
  color: var(--primary);
  padding: 0.3rem 0.8rem;
  border-radius: 30px;
  font-size: 0.75rem;
  font-weight: 800;
  align-self: flex-start;
  margin-bottom: 1.5rem;
}

h3 { font-size: 1.6rem; margin: 0 0 0.8rem 0; font-weight: 700; }
p { color: var(--text-muted); font-size: 0.95rem; line-height: 1.6; margin: 0; }

.card-footer { margin-top: auto; padding-top: 2.5rem; }

.progress-info {
  display: flex;
  justify-content: space-between;
  font-size: 0.8rem;
  color: var(--text-muted);
  margin-bottom: 0.6rem;
}

.progress-track {
  height: 8px;
  background: #0f172a;
  border-radius: 10px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: var(--primary);
  box-shadow: 0 0 15px rgba(0, 220, 130, 0.5);
  border-radius: 10px;
}

/* SKELETON ANIMATION */
.skeleton-card {
  height: 280px;
  background: rgba(30, 41, 59, 0.5);
  border-radius: 20px;
  animation: pulse 1.8s infinite ease-in-out;
}

@keyframes pulse {
  0% { opacity: 0.5; }
  50% { opacity: 0.2; }
  100% { opacity: 0.5; }
}

/* TRANSITIONS */
.slide-fade-enter-active { transition: all 0.3s ease-out; }
.slide-fade-leave-active { transition: all 0.2s ease-in; }
.slide-fade-enter-from, .slide-fade-leave-to {
  opacity: 0;
  transform: translateY(-15px);
}
</style>