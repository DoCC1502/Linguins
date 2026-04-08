<script setup lang="ts">
// Der Simulator füllt jetzt das gesamte Browserfenster aus
</script>

<template>
  <div class="simulator-wrapper">
    <iframe
        src="/linux-simulator.html"
        class="linux-iframe"
        title="Linux Terminal Simulator"
        allow="fullscreen"
    ></iframe>
  </div>
</template>

<style scoped>
.simulator-wrapper {
  /* Nutzt die gesamte Breite und Höhe des Viewports */
  width: 100vw;
  height: 100vh;

  /* Verhindert Scrollbalken der Hauptseite */
  overflow: hidden;

  /* Fixiert die Position, falls du eine Navigationsleiste hast */
  position: fixed;
  top: 30px;
  left: 0;

  background-color: #000; /* Schwarzer Hintergrund für den Übergang */
}

.linux-iframe {
  width: 100%;
  height: 100%;
  border: none;

  /* Sorgt dafür, dass das Iframe sauber skaliert */
  display: block;
}
</style>