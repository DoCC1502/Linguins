<script setup lang="ts">
import type { Task } from '@/types/types';

const tasks: Task[] = [
  { q: 'Change permissions', c: 'chmod', a: '', r: null },
  { q: 'Change owner', c: 'chown', a: '', r: null },
  { q: 'Read permission number', c: '4', a: '', r: null },
  { q: 'Write permission number', c: '2', a: '', r: null },
  { q: 'Execute permission number', c: '1', a: '', r: null }
];

const check = (t: Task): boolean => t.r = t.a.trim().toLowerCase() === t.c.toLowerCase();
</script>

<template>
  <section class="page">
    <div class="detail">
      <h1>File Permissions</h1>
      <p class="desc">Learn core Linux concepts and directory structure.</p>

      <div v-for="(t,i) in tasks" :key="i" class="task">
        <h3>Task {{ i+1 }}</h3>
        <p>{{ t.q }}</p>
        <input v-model="t.a" placeholder="Your answer" />
        <button @click="check(t)">Check</button>
        <p v-if="t.r !== null" :class="t.r ? 'ok':'bad'">
          {{ t.r ? 'Correct ✅' : 'Wrong ❌' }}
        </p>
      </div>
    </div>
  </section>
</template>

<style scoped>
.detail { max-width:900px;width:100% }
.desc { color:var(--text-muted);margin-bottom:2rem }
.task {
  background:var(--bg-card);
  padding:1.5rem;
  border-radius:12px;
  margin-bottom:1.5rem;
}
.ok { color:#42b883 }
.bad { color:#ef4444 }
</style>
