<script setup lang="ts">
import {ref} from 'vue';
import {useRouter} from 'vue-router';
import {lessonService} from '@/services/lessonService';

const router = useRouter();
const isSubmitting = ref(false);

const lessonData = ref({
  title: '',
  description: '',
  difficulty: 1
});

const tasks = ref([{q: '', c: ''}]);

function addTask() {
  tasks.value.push({q: '', c: ''});
}

function removeTask(index: number) {
  if (tasks.value.length > 1) {
    tasks.value.splice(index, 1);
  }
}

async function handleSave() {
  if (!lessonData.value.title || tasks.value.some(t => !t.q || !t.c)) {
    alert("Bitte fülle alle Felder aus.");
    return;
  }

  isSubmitting.value = true;
  try {
    // 1. Lektion (Metadaten) anlegen
    const createdLesson = await lessonService.createLesson(lessonData.value);

    // 2. Content (Fragen als JSON) anlegen
    if (createdLesson.id !== undefined) {
      await lessonService.createLessonContent({
        lessonId: createdLesson.id,
        content: JSON.stringify(tasks.value)
      });
    }

    alert("Lektion erfolgreich erstellt!");
    router.push('/lessons');
  } catch (error) {
    console.error("Fehler beim Speichern:", error);
    alert("Fehler beim Speichern der Lektion.");
  } finally {
    isSubmitting.value = false;
  }
}
</script>

<template>
  <section class="admin-page">
    <div class="container">
      <header class="admin-header">
        <button @click="router.back()" class="btn-back">← Abbrechen</button>
        <h1>Neue <span class="accent">Lektion</span> erstellen</h1>
      </header>

      <div class="editor-grid">
        <div class="card editor-card">
          <h3>Allgemeine Infos</h3>
          <div class="form-group">
            <label>Titel</label>
            <input v-model="lessonData.title" type="text" placeholder="z.B. Linux Dateisystem"/>
          </div>
          <div class="form-group">
            <label>Beschreibung</label>
            <textarea v-model="lessonData.description" placeholder="Was lernt man hier?"></textarea>
          </div>
          <div class="form-group">
            <label>Schwierigkeit (1-3)</label>
            <input v-model.number="lessonData.difficulty" type="number" min="1" max="3"/>
          </div>
        </div>

        <div class="card editor-card">
          <div class="tasks-header">
            <h3>Aufgaben (JSON-Content)</h3>
            <button @click="addTask" class="btn-add">＋ Frage hinzufügen</button>
          </div>

          <div class="tasks-list">
            <div v-for="(task, index) in tasks" :key="index" class="task-item">
              <div class="task-number">{{ index + 1 }}</div>
              <div class="task-inputs">
                <input v-model="task.q" type="text" placeholder="Frage / Befehl-Anforderung"/>
                <input v-model="task.c" type="text" placeholder="Korrekte Antwort (Befehl)"/>
              </div>
              <button @click="removeTask(index)" class="btn-delete">×</button>
            </div>
          </div>
        </div>
      </div>

      <footer class="admin-footer">
        <button @click="handleSave" class="btn-save" :disabled="isSubmitting">
          {{ isSubmitting ? 'Wird gespeichert...' : 'Lektion in Datenbank veröffentlichen' }}
        </button>
      </footer>
    </div>
  </section>
</template>

<style scoped>
.admin-page {
  min-height: 100vh;
  background-color: #0f172a;
  color: #f8fafc;
  padding: 4rem 2rem;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
}

.accent {
  color: #00dc82;
}

.admin-header {
  display: flex;
  align-items: center;
  gap: 2rem;
  margin-bottom: 3rem;
}

.btn-back {
  background: transparent;
  border: 1px solid #334155;
  color: #94a3b8;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
}

.editor-grid {
  display: grid;
  grid-template-columns: 1fr 1.5fr;
  gap: 2rem;
}

.card {
  background: #1e293b;
  padding: 2rem;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.form-group {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

label {
  font-size: 0.85rem;
  color: #94a3b8;
  font-weight: 600;
  text-transform: uppercase;
}

input, textarea {
  background: #0f172a;
  border: 1px solid #334155;
  border-radius: 8px;
  padding: 0.8rem;
  color: white;
}

.tasks-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.btn-add {
  background: rgba(0, 220, 130, 0.1);
  color: #00dc82;
  border: 1px solid #00dc82;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  cursor: pointer;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
  background: rgba(15, 23, 42, 0.5);
  padding: 1rem;
  border-radius: 12px;
}

.task-inputs {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  flex: 1;
}

.btn-delete {
  background: #ef4444;
  color: white;
  border: none;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  cursor: pointer;
}

.admin-footer {
  margin-top: 3rem;
  display: flex;
  justify-content: flex-end;
}

.btn-save {
  background: #00dc82;
  color: #0f172a;
  padding: 1rem 2rem;
  border-radius: 12px;
  font-weight: 700;
  border: none;
  cursor: pointer;
  font-size: 1.1rem;
}
</style>