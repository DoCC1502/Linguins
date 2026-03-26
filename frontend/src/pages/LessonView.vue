<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { lessonService } from '../services/lessonService';
import { lessonContentService } from '../services/lessonContentService.ts';
import type { LessonDto, LessonContentDto } from '../types/types';

const route = useRoute();
const router = useRouter();
const lesson = ref<LessonDto | null>(null);
const content = ref<LessonContentDto | null>(null);
const isLoading = ref(true);

onMounted(async () => {
  const id = Number(route.params.id);
  try {
    // 1. Basis-Infos der Lektion laden
    lesson.value = await lessonService.getLessonById(id);
    // 2. Den eigentlichen Inhalt (lesson_content) laden
    content.value = await lessonContentService.getByLessonId(id);
  } catch (error) {
    console.error("Inhalt konnte nicht geladen werden:", error);
  } finally {
    isLoading.value = false;
  }
});
</script>

<template>
  <div class="lesson-page">
    <button @click="router.back()" class="back-btn">← Zurück</button>

    <div v-if="isLoading" class="loader">Lade Lektion...</div>

    <div v-else-if="lesson && content" class="content-container">
      <header>
        <span class="badge">Level {{ lesson.difficulty }}</span>
        <h1>{{ lesson.title }}</h1>
      </header>

      <section class="lesson-body">
        <div class="text-content">
          {{ content.content }}
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
.lesson-page { padding: 2rem; color: white; max-width: 800px; margin: 0 auto; }
.back-btn { background: none; border: 1px solid #00dc82; color: #00dc82; padding: 0.5rem 1rem; border-radius: 8px; cursor: pointer; margin-bottom: 2rem; }
.badge { color: #00dc82; font-weight: bold; }
h1 { font-size: 2.5rem; margin-top: 0.5rem; }
.lesson-body { background: #1e293b; padding: 2rem; border-radius: 15px; line-height: 1.6; margin-top: 2rem; }
.text-content { white-space: pre-wrap; } /* Wichtig, damit Zeilenumbrüche aus der DB angezeigt werden */
</style>