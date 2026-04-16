<template>
  <section class="quiz-page">
    <div class="quiz-container">
      <!-- Loading State -->
      <div v-if="isLoading" class="loading-state">
        <div class="spinner"></div>
        <p>Lektion wird geladen...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="error-state">
        <h2>Fehler</h2>
        <p>{{ error }}</p>
        <button @click="goBack" class="btn-secondary">Zurück</button>
      </div>

      <!-- Quiz Content -->
      <template v-else-if="lessonContent">
        <!-- Header mit großer Fortschrittsleiste -->
        <header class="quiz-header">
          <div class="header-top">
            <span class="lesson-category">Lektion {{ lessonContent.id }}</span>
            <span v-if="!isLoggedIn" class="guest-badge">Gast-Modus</span>
          </div>
          <h1 class="lesson-title">{{ lessonContent.title }}</h1>
          <p class="lesson-description">{{ lessonContent.description }}</p>

          <!-- Große Fortschrittsleiste -->
          <div class="progress-section">
            <div class="progress-header">
              <span class="progress-label">Fortschritt</span>
              <span class="progress-percentage">{{ localProgressPercentage }}%</span>
            </div>
            <div class="progress-bar-large">
              <div
                class="progress-fill"
                :style="{ width: localProgressPercentage + '%' }"
                :class="{ completed: allTasksAnswered }"
              ></div>
            </div>
            <div class="progress-stats">
              <span>{{ correctAnswers }} von {{ lessonContent.tasks.length }} richtig</span>
              <span v-if="isLoggedIn && lastSynced" class="sync-status">
                {{ syncError ? '⚠️ Sync-Fehler' : '✓ Gespeichert' }}
              </span>
            </div>
          </div>
        </header>

        <!-- Frage-Box -->
        <div class="question-card">
          <div class="question-header">
            <span class="question-number">Frage {{ currentQuestionIndex + 1 }} / {{ lessonContent.tasks.length }}</span>
            <div class="question-dots">
              <span
                v-for="(task, idx) in lessonContent.tasks"
                :key="idx"
                class="dot"
                :class="{
                  completed: task.r === true,
                  wrong: task.r === false,
                  current: idx === currentQuestionIndex
                }"
              ></span>
            </div>
          </div>

          <div class="question-content">
            <h2 class="question-text">{{ currentTask?.q }}</h2>
            <p class="question-hint">Gib den entsprechenden Linux-Befehl oder Wert ein</p>
          </div>

          <!-- Input-Bereich -->
          <div class="answer-section">
            <div class="input-wrapper" :class="{ 'has-feedback': hasFeedback }">
              <input
                ref="answerInput"
                :value="currentTask?.a"
                @input="updateAnswer(($event.target as HTMLInputElement).value)"
                type="text"
                class="answer-input"
                :class="{
                  correct: currentTask?.r === true,
                  wrong: currentTask?.r === false
                }"
                placeholder="Deine Antwort..."
                :disabled="currentTask?.r === true"
                @keyup.enter="checkAnswer"
              />
              <div v-if="hasFeedback" class="feedback-icon">
                <span v-if="currentTask?.r === true">✓</span>
                <span v-else>✗</span>
              </div>
            </div>

            <button
              v-if="currentTask?.r !== true"
              @click="checkAnswer"
              class="btn-primary"
              :disabled="!canSubmit"
            >
              Antwort prüfen
            </button>
            <button
              v-else-if="!allTasksAnswered"
              @click="nextQuestion"
              class="btn-primary"
            >
              Nächste Frage →
            </button>
          </div>

          <!-- Sofort-Feedback -->
          <div
            v-if="hasFeedback"
            class="feedback-message"
            :class="{ correct: currentTask?.r === true, wrong: currentTask?.r === false }"
          >
            <div class="feedback-content">
              <span class="feedback-icon-large">
                {{ currentTask?.r === true ? '✓' : '✗' }}
              </span>
              <div class="feedback-text">
                <p v-if="currentTask?.r === true" class="feedback-title">Richtig!</p>
                <p v-else class="feedback-title">Falsch</p>
                <p v-if="currentTask?.r === false" class="feedback-hint">
                  Die richtige Antwort wäre: <strong>{{ currentTask?.c }}</strong>
                </p>
              </div>
            </div>
          </div>
        </div>

        <!-- Navigation zwischen Fragen -->
        <div v-if="lessonContent.tasks.length > 1" class="question-navigation">
          <button
            v-for="(task, idx) in lessonContent.tasks"
            :key="idx"
            class="nav-dot"
            :class="{
              completed: task.r === true,
              wrong: task.r === false,
              current: idx === currentQuestionIndex
            }"
            @click="goToQuestion(idx)"
          >
            {{ idx + 1 }}
          </button>
        </div>

        <!-- Abschluss-Bereich -->
        <div v-if="allTasksAnswered && !lessonCompleted" class="completion-section">
          <div class="completion-card">
            <h3>🎉 Alle Fragen beantwortet!</h3>
            <p>Du hast {{ correctAnswers }} von {{ lessonContent.tasks.length }} Fragen richtig beantwortet.</p>
            <div class="completion-actions">
              <button @click="finishLesson" class="btn-success" :disabled="isSyncing">
                {{ isSyncing ? 'Wird gespeichert...' : 'Lektion abschließen' }}
              </button>
            </div>
          </div>
        </div>

        <!-- Erfolgsmeldung nach Abschluss -->
        <div v-if="lessonCompleted" class="success-banner">
          <div class="success-content">
            <span class="success-icon">🏆</span>
            <div class="success-text">
              <h3>Lektion abgeschlossen!</h3>
              <p>Score: {{ finalScore }}% ({{ passed ? 'Bestanden' : 'Nicht bestanden' }})</p>
            </div>
          </div>
        </div>

        <!-- Sync Error -->
        <div v-if="syncError" class="sync-error">
          <span>⚠️ Fortschritt konnte nicht synchronisiert werden</span>
          <button @click="retrySync" class="btn-retry">Erneut versuchen</button>
        </div>

        <!-- Footer Actions -->
        <div class="quiz-footer">
          <button @click="goBack" class="btn-secondary">← Zurück zur Übersicht</button>
        </div>
      </template>
    </div>
  </section>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch, nextTick } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { lessonService } from '@/services/lessonService';
import type { LessonContent, Task, UserDto } from '@/types/types';

const route = useRoute();
const router = useRouter();
const answerInput = ref<HTMLInputElement | null>(null);

// State
const isLoading = ref(true);
const error = ref<string | null>(null);
const lessonContent = ref<LessonContent | null>(null);
const currentQuestionIndex = ref(0);
const isLoggedIn = ref(false);
const currentUser = ref<UserDto | null>(null);
const isSyncing = ref(false);
const syncError = ref(false);
const lastSynced = ref(false);
const lessonCompleted = ref(false);
const finalScore = ref<number | null>(null);
const passed = ref(false);

// Computed
const currentTask = computed<Task | null>(() => {
  if (!lessonContent.value) return null;
  return lessonContent.value.tasks[currentQuestionIndex.value] || null;
});

const correctAnswers = computed(() => {
  if (!lessonContent.value) return 0;
  return lessonContent.value.tasks.filter(t => t.r === true).length;
});

const localProgressPercentage = computed(() => {
  if (!lessonContent.value || lessonContent.value.tasks.length === 0) return 0;
  return Math.round((correctAnswers.value / lessonContent.value.tasks.length) * 100);
});

const allTasksAnswered = computed(() => {
  if (!lessonContent.value) return false;
  return lessonContent.value.tasks.every(t => t.r !== null);
});

const hasFeedback = computed(() => currentTask.value?.r !== null);

const canSubmit = computed(() => {
  const task = currentTask.value;
  if (!task) return false;
  return task.a.trim().length > 0;
});

// Watch für DB-Sync bei jeder richtigen Antwort
let syncTimeout: ReturnType<typeof setTimeout> | null = null;

watch(correctAnswers, async (newVal, oldVal) => {
  if (!lessonContent.value || !isLoggedIn.value || !currentUser.value?.id) return;
  if (newVal > oldVal) {
    // Sofort sync bei korrekter Antwort
    await syncProgress();
  }
});

// Auth Status prüfen
function checkAuthStatus() {
  const storedUser = localStorage.getItem('user');
  if (storedUser) {
    try {
      currentUser.value = JSON.parse(storedUser);
      isLoggedIn.value = true;
    } catch {
      isLoggedIn.value = false;
    }
  } else {
    isLoggedIn.value = false;
  }
}

// Lektion laden
async function loadLesson() {
  isLoading.value = true;
  error.value = null;

  try {
    const lessonIdParam = route.params.id || route.params.lessonId;
    const lessonId = typeof lessonIdParam === 'string'
      ? parseInt(lessonIdParam, 10)
      : Array.isArray(lessonIdParam) && lessonIdParam.length > 0 && lessonIdParam[0]
        ? parseInt(lessonIdParam[0], 10)
        : null;

    if (!lessonId || isNaN(lessonId)) {
      error.value = 'Ungültige Lektions-ID';
      return;
    }

    const content = lessonService.getStaticContent(lessonId);

    if (!content) {
      error.value = `Lektion ${lessonId} wurde nicht gefunden`;
      return;
    }

    lessonContent.value = content;

    if (isLoggedIn.value && currentUser.value?.id) {
      await loadExistingProgress(currentUser.value.id, lessonId);
    }

  } catch (err) {
    error.value = err instanceof Error ? err.message : 'Unbekannter Fehler';
    console.error('Fehler beim Laden der Lektion:', err);
  } finally {
    isLoading.value = false;
  }
}

async function loadExistingProgress(userId: number, lessonId: number) {
  // Optional: Vorhandenen Fortschritt laden
  // Für jetzt starten wir bei 0
}

// Antwort aktualisieren
function updateAnswer(value: string) {
  if (currentTask.value) {
    currentTask.value.a = value;
  }
}

// Antwort prüfen
function checkAnswer() {
  if (!currentTask.value) return;

  const task = currentTask.value;
  const userAnswer = task.a.trim().toLowerCase();
  const correctAnswer = task.c.trim().toLowerCase();

  task.r = userAnswer === correctAnswer;

  // Focus auf Input für bessere UX
  nextTick(() => {
    if (!task.r) {
      answerInput.value?.focus();
      answerInput.value?.select();
    }
  });
}

// Nächste Frage
function nextQuestion() {
  if (!lessonContent.value) return;

  const nextIndex = currentQuestionIndex.value + 1;
  if (nextIndex < lessonContent.value.tasks.length) {
    currentQuestionIndex.value = nextIndex;
    // Focus auf Input für nächste Frage
    nextTick(() => {
      answerInput.value?.focus();
    });
  }
}

// Zu spezifischer Frage springen
function goToQuestion(index: number) {
  if (!lessonContent.value || index < 0 || index >= lessonContent.value.tasks.length) return;
  currentQuestionIndex.value = index;
  nextTick(() => {
    answerInput.value?.focus();
  });
}

// Fortschritt synchronisieren
async function syncProgress() {
  if (!isLoggedIn.value || !currentUser.value?.id || !lessonContent.value) {
    return;
  }

  syncError.value = false;

  try {
    const status = allTasksAnswered.value ? 'COMPLETED' : 'IN_PROGRESS';
    await lessonService.updateUserProgress(
      currentUser.value.id,
      lessonContent.value.id,
      localProgressPercentage.value,
      status
    );
    lastSynced.value = true;
  } catch (err) {
    console.error('Sync fehlgeschlagen:', err);
    syncError.value = true;
  }
}

// Lektion abschließen
async function finishLesson() {
  if (!lessonContent.value) return;

  const score = localProgressPercentage.value;
  finalScore.value = score;
  passed.value = score >= 60;

  lessonCompleted.value = true;

  if (isLoggedIn.value && currentUser.value?.id) {
    isSyncing.value = true;
    syncError.value = false;

    try {
      await lessonService.completeLesson(
        currentUser.value.id,
        lessonContent.value.id,
        correctAnswers.value,
        lessonContent.value.tasks.length
      );
      lastSynced.value = true;
    } catch (err) {
      console.error('Abschluss-Sync fehlgeschlagen:', err);
      syncError.value = true;
    } finally {
      isSyncing.value = false;
    }
  }
}

// Sync erneut versuchen
async function retrySync() {
  await syncProgress();
}

// Zurück navigieren
function goBack() {
  router.push('/lessons');
}

// Lifecycle
onMounted(async () => {
  checkAuthStatus();
  await loadLesson();
  // Focus auf Input beim Start
  nextTick(() => {
    answerInput.value?.focus();
  });
});
</script>

<style scoped>
.quiz-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
  padding: 2rem 1rem;
}

.quiz-container {
  max-width: 800px;
  margin: 0 auto;
}

/* Header */
.quiz-header {
  background: white;
  border-radius: 24px;
  padding: 2rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.lesson-category {
  font-size: 0.875rem;
  font-weight: 600;
  color: #64748b;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.guest-badge {
  background: #fef3c7;
  color: #92400e;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 600;
}

.lesson-title {
  font-size: 2rem;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 0.5rem 0;
}

.lesson-description {
  color: #64748b;
  font-size: 1rem;
  margin: 0 0 1.5rem 0;
}

/* Progress Section */
.progress-section {
  margin-top: 1.5rem;
}

.progress-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.75rem;
}

.progress-label {
  font-weight: 600;
  color: #475569;
}

.progress-percentage {
  font-size: 1.5rem;
  font-weight: 700;
  color: #00dc82;
}

.progress-bar-large {
  height: 12px;
  background: #e2e8f0;
  border-radius: 10px;
  overflow: hidden;
  margin-bottom: 0.75rem;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #00dc82, #00b4d8);
  border-radius: 10px;
  transition: width 0.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.progress-fill.completed {
  background: linear-gradient(90deg, #22c55e, #16a34a);
}

.progress-stats {
  display: flex;
  justify-content: space-between;
  font-size: 0.875rem;
  color: #64748b;
}

.sync-status {
  color: #22c55e;
  font-weight: 500;
}

/* Question Card */
.question-card {
  background: white;
  border-radius: 24px;
  padding: 2.5rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.question-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.question-number {
  font-size: 0.875rem;
  font-weight: 600;
  color: #64748b;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.question-dots {
  display: flex;
  gap: 0.5rem;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #e2e8f0;
  transition: all 0.3s ease;
}

.dot.completed {
  background: #22c55e;
}

.dot.wrong {
  background: #ef4444;
}

.dot.current {
  background: #00dc82;
  transform: scale(1.5);
}

/* Question Content */
.question-content {
  margin-bottom: 2.5rem;
}

.question-text {
  font-size: 1.5rem;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 0.75rem 0;
  line-height: 1.4;
}

.question-hint {
  color: #94a3b8;
  font-size: 0.875rem;
  margin: 0;
}

/* Answer Section */
.answer-section {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.answer-input {
  width: 100%;
  padding: 1rem 1.25rem;
  font-size: 1.125rem;
  border: 2px solid #e2e8f0;
  border-radius: 12px;
  background: white;
  color: #1e293b;
  transition: all 0.2s ease;
}

.answer-input:focus {
  outline: none;
  border-color: #00dc82;
  box-shadow: 0 0 0 4px rgba(0, 220, 130, 0.1);
}

.answer-input.correct {
  border-color: #22c55e;
  background: #f0fdf4;
  padding-right: 3rem;
}

.answer-input.wrong {
  border-color: #ef4444;
  background: #fef2f2;
  padding-right: 3rem;
}

.answer-input:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.feedback-icon {
  position: absolute;
  right: 1rem;
  font-size: 1.5rem;
  font-weight: 700;
}

.feedback-icon span:first-child {
  color: #22c55e;
}

.feedback-icon span:last-child {
  color: #ef4444;
}

/* Buttons */
.btn-primary {
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #00dc82, #00b4d8);
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-primary:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(0, 220, 130, 0.4);
}

.btn-primary:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-secondary {
  padding: 0.75rem 1.5rem;
  background: transparent;
  border: 2px solid #e2e8f0;
  color: #64748b;
  border-radius: 12px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-secondary:hover {
  border-color: #00dc82;
  color: #00dc82;
}

.btn-success {
  padding: 1rem 2rem;
  background: linear-gradient(135deg, #22c55e, #16a34a);
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-success:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(34, 197, 94, 0.4);
}

.btn-success:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-retry {
  padding: 0.5rem 1rem;
  background: white;
  border: 1px solid #f59e0b;
  color: #f59e0b;
  border-radius: 8px;
  font-size: 0.875rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-retry:hover {
  background: #f59e0b;
  color: white;
}

/* Feedback Message */
.feedback-message {
  margin-top: 1.5rem;
  padding: 1.25rem;
  border-radius: 12px;
  animation: slideIn 0.3s ease;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.feedback-message.correct {
  background: #f0fdf4;
  border: 1px solid #22c55e;
}

.feedback-message.wrong {
  background: #fef2f2;
  border: 1px solid #ef4444;
}

.feedback-content {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.feedback-icon-large {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  font-weight: 700;
  flex-shrink: 0;
}

.feedback-message.correct .feedback-icon-large {
  background: #22c55e;
  color: white;
}

.feedback-message.wrong .feedback-icon-large {
  background: #ef4444;
  color: white;
}

.feedback-title {
  font-weight: 600;
  margin: 0 0 0.25rem 0;
}

.feedback-message.correct .feedback-title {
  color: #166534;
}

.feedback-message.wrong .feedback-title {
  color: #991b1b;
}

.feedback-hint {
  margin: 0;
  color: #64748b;
  font-size: 0.875rem;
}

.feedback-hint strong {
  color: #1e293b;
}

/* Question Navigation */
.question-navigation {
  display: flex;
  justify-content: center;
  gap: 0.5rem;
  margin-bottom: 1.5rem;
  flex-wrap: wrap;
}

.nav-dot {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  border: 2px solid #e2e8f0;
  background: white;
  color: #64748b;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.nav-dot:hover {
  border-color: #00dc82;
  color: #00dc82;
}

.nav-dot.current {
  border-color: #00dc82;
  background: #00dc82;
  color: white;
}

.nav-dot.completed {
  border-color: #22c55e;
  background: #22c55e;
  color: white;
}

.nav-dot.wrong {
  border-color: #ef4444;
  background: #ef4444;
  color: white;
}

/* Completion Section */
.completion-section {
  margin-bottom: 1.5rem;
}

.completion-card {
  background: white;
  border-radius: 24px;
  padding: 2rem;
  text-align: center;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  border: 2px solid #00dc82;
}

.completion-card h3 {
  font-size: 1.5rem;
  color: #1e293b;
  margin: 0 0 0.5rem 0;
}

.completion-card p {
  color: #64748b;
  margin: 0 0 1.5rem 0;
}

.completion-actions {
  display: flex;
  justify-content: center;
}

/* Success Banner */
.success-banner {
  background: linear-gradient(135deg, #22c55e, #16a34a);
  border-radius: 16px;
  padding: 1.5rem 2rem;
  margin-bottom: 1.5rem;
  color: white;
}

.success-content {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.success-icon {
  font-size: 2.5rem;
}

.success-text h3 {
  font-size: 1.25rem;
  margin: 0 0 0.25rem 0;
}

.success-text p {
  margin: 0;
  opacity: 0.9;
}

/* Sync Error */
.sync-error {
  background: #fef3c7;
  border: 1px solid #f59e0b;
  border-radius: 12px;
  padding: 1rem;
  margin-bottom: 1.5rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #92400e;
}

/* Quiz Footer */
.quiz-footer {
  display: flex;
  justify-content: center;
  padding: 1rem 0;
}

/* Loading State */
.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem;
  color: #64748b;
}

.spinner {
  width: 48px;
  height: 48px;
  border: 4px solid #e2e8f0;
  border-top-color: #00dc82;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* Error State */
.error-state {
  text-align: center;
  padding: 4rem;
  background: white;
  border-radius: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

.error-state h2 {
  color: #ef4444;
  margin-bottom: 0.5rem;
}

.error-state p {
  color: #64748b;
  margin-bottom: 1.5rem;
}

/* Responsive */
@media (max-width: 640px) {
  .quiz-page {
    padding: 1rem;
  }

  .quiz-header,
  .question-card {
    padding: 1.5rem;
  }

  .lesson-title {
    font-size: 1.5rem;
  }

  .question-text {
    font-size: 1.25rem;
  }

  .progress-percentage {
    font-size: 1.25rem;
  }
}
</style>
