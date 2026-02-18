<template>
  <!-- HERO -->
  <section class="hero">
    <img
        class="hero-logo"
        src="@/assets/images/lingoLOGO_ohneSchrift.png"
        alt=""
    />

    <h1 class="brand">Linguins</h1>
  </section>



  <!-- MODULES -->
  <section class="modules">
    <h2>Learning Modules</h2>

    <div class="modules-grid">
      <div class="module-card">
        <h3>Linux Fundamentals</h3>
        <div class="progress"><div class="bar" style="width: 80%"></div></div>
      </div>

      <div class="module-card">
        <h3>Essential Commands</h3>
        <div class="progress"><div class="bar" style="width: 55%"></div></div>
      </div>

      <div class="module-card">
        <h3>File Permissions</h3>
        <div class="progress"><div class="bar" style="width: 40%"></div></div>
      </div>

      <div class="module-card">
        <h3>Process Management</h3>
        <div class="progress"><div class="bar" style="width: 25%"></div></div>
      </div>

      <div class="module-card">
        <h3>Network Commands</h3>
        <div class="progress"><div class="bar" style="width: 10%"></div></div>
      </div>

      <div class="module-card">
        <h3>Shell Scripting</h3>
        <div class="progress"><div class="bar" style="width: 5%"></div></div>
      </div>
    </div>
  </section>

  <!-- TRUST -->
  <section class="trust">
    <p>Used by professionals on</p>

    <div class="logos">
      <img src="https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/linkedin.svg" />
      <img src="https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/youtube.svg" />
      <img src="https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/instagram.svg" />
      <img src="https://cdn.jsdelivr.net/npm/simple-icons@v9/icons/x.svg" />
    </div>
  </section>

</template>

<style scoped>
.hero {
  position: relative;
  min-height: 80vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  overflow: hidden;
}

/* HINTERGRUND LOGO */
.hero-logo {
  position: absolute;
  width: 900px;          /* DEUTLICH GRÖSSER */
  max-width: 95vw;
  opacity: 0.045;        /* sichtbar aber edel */
  filter: grayscale(1);
  pointer-events: none;
}



.brand {
  font-size: clamp(4.5rem, 9vw, 6.5rem);
  font-weight: 800;
  letter-spacing: -0.04em;
  color: #42b883; /* DAS Vue-/Linguins-Grün */
}





.modules {
  margin-top: 8rem; /* DAS ist der Schlüssel */
  padding: 4rem 2rem;
  max-width: 1300px;
  margin-left: auto;
  margin-right: auto;
}


.modules-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 2.5rem; /* mehr Luft */
}


.module-card {
  background: var(--bg-card);
  padding: 2.5rem; /* MEHR FLÄCHE */
  border-radius: 16px;
  min-height: 160px;

  display: flex;
  flex-direction: column;
  justify-content: space-between;

  transition: transform 0.25s ease, box-shadow 0.25s ease;
}

.module-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 25px 50px rgba(0, 0, 0, 0.45);
}

.module-card h3 {
  font-size: 1.25rem;
}


.progress {
  height: 8px;
  background: var(--border-soft);
  border-radius: 999px;
  overflow: hidden;
}

.bar {
  height: 100%;
  background: linear-gradient(90deg, #42b883, #7cfac6);
}


.trust {
  padding: 3rem 2rem;
  text-align: center;
  color: var(--text-muted);
}

.logos {
  margin-top: 1.5rem;
  display: flex;
  justify-content: center;
  gap: 2.5rem;
}

.logos img {
  width: 32px;
  height: 32px;
  filter: invert(1);
  opacity: 0.8;
  transition: opacity 0.2s ease, transform 0.2s ease;
}

.logos img:hover {
  opacity: 1;
  transform: scale(1.1);
}

</style>
