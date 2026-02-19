<script setup>
const tasks = [
  { q:'Ping host', c:'ping', a:'', r:null },
  { q:'Download file', c:'wget', a:'', r:null },
  { q:'HTTP requests', c:'curl', a:'', r:null },
  { q:'Remote login', c:'ssh', a:'', r:null },
  { q:'Network config', c:'ifconfig', a:'', r:null }
]
const check = t => t.r = t.a === t.c
</script>

<template>
  <section class="page">
    <div class="detail">
      <h1>Network Commands</h1>
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
