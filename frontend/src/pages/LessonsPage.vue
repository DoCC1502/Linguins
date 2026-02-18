<template>
  <section class="page">
    <div class="lessons-container">
      <h1>Lessons</h1>

      <div class="lessons-grid">
        <div class="lesson-card" @click="$router.push('/lessons/linux-fundamentals')">
          <h3>Linux Fundamentals</h3>
          <span>Beginner</span>
          <div class="progress"><div class="bar" style="width:70%"></div></div>
        </div>

        <div class="lesson-card" @click="$router.push('/lessons/essential-commands')">
          <h3>Essential Commands</h3>
          <span>Beginner</span>
          <div class="progress"><div class="bar" style="width:55%"></div></div>
        </div>

        <div class="lesson-card" @click="$router.push('/lessons/file-permissions')">
          <h3>File Permissions</h3>
          <span>Intermediate</span>
          <div class="progress"><div class="bar" style="width:40%"></div></div>
        </div>

        <div class="lesson-card" @click="$router.push('/lessons/process-management')">
          <h3>Process Management</h3>
          <span>Intermediate</span>
          <div class="progress"><div class="bar" style="width:30%"></div></div>
        </div>

        <div class="lesson-card" @click="$router.push('/lessons/network-commands')">
          <h3>Network Commands</h3>
          <span>Advanced</span>
          <div class="progress"><div class="bar" style="width:15%"></div></div>
        </div>

        <div class="lesson-card" @click="$router.push('/lessons/shell-scripting')">
          <h3>Shell Scripting</h3>
          <span>Advanced</span>
          <div class="progress"><div class="bar" style="width:5%"></div></div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.lessons-container {
  max-width: 1200px;
  width: 100%;
}

.lessons-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 2rem;
}

.lesson-card {
  background: var(--bg-card);
  padding: 2rem;
  border-radius: 16px;
  cursor: pointer;
  transition: transform .2s ease, box-shadow .2s ease;
}

.lesson-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 20px 40px rgba(0,0,0,.4);
}

.progress {
  margin-top: 1rem;
  height: 8px;
  background: var(--border-soft);
  border-radius: 999px;
}

.bar {
  height: 100%;
  background: var(--primary);
  border-radius: 999px;
}
</style>
