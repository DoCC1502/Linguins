<template>
  <section class="page">
    <div class="detail">
      <h1>Linux Fundamentals</h1>
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

<script setup>
const tasks = [
  { q:'List files', c:'ls', a:'', r:null },
  { q:'Show current dir', c:'pwd', a:'', r:null },
  { q:'Change directory', c:'cd', a:'', r:null },
  { q:'Clear terminal', c:'clear', a:'', r:null },
  { q:'Root directory symbol', c:'/', a:'', r:null }
]

const check = t => t.r = t.a.trim().toLowerCase() === t.c
</script>

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
