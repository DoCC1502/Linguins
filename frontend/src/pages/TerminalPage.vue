<template>
  <section class="terminal-page">
    <header class="page-header">
      <div class="badge">Linguins Terminal</div>
      <h1>Wähle deine Umgebung</h1>
      <p>Lerne Linux in der sicheren Simulation oder auf echter Hardware.</p>
    </header>

    <div class="edition-toggle">
      <button
          :class="['toggle-btn', { active: currentMode === 'free' }]"
          @click="currentMode = 'free'"
      >
        <span class="icon">🎮</span> Free Simulator
      </button>
      <button
          :class="['toggle-btn pro', { active: currentMode === 'pro' }]"
          @click="currentMode = 'pro'"
      >
        <span class="icon">🚀</span> Pro Edition
      </button>
    </div>

    <div class="main-container">

      <div v-if="currentMode === 'free'" class="view-wrapper fade-in">
        <div class="terminal-card simulator-border">
          <div class="card-info">
            <span class="status-tag">Lokal im Browser</span>
            <h2>Linux Simulator</h2>
            <p>Perfekt für die ersten Schritte: ls, cd, mkdir und Pfade verstehen.</p>
          </div>
          <LinguinsSimulator />
        </div>
      </div>

      <div v-if="currentMode === 'pro'" class="view-wrapper fade-in">
        <div class="pro-grid">
          <div class="pro-info-card">
            <div class="pro-badge">🔥 Empfohlen</div>
            <h2>Echter Ubuntu Container</h2>
            <ul class="feature-list">
              <li><span class="check">✓</span> <strong>Echter Kernel:</strong> Kein Fake-Dateisystem.</li>
              <li><span class="check">✓</span> <strong>Paketverwaltung:</strong> <code>apt install python3</code> etc.</li>
              <li><span class="check">✓</span> <strong>Persistenz:</strong> Deine Dateien bleiben gespeichert.</li>
              <li><span class="check">✓</span> <strong>Networking:</strong> SSH-Zugriff möglich.</li>
            </ul>

            <div class="pricing-box">
              <div class="price">9,99€ <small>/ Monat</small></div>
              <p class="limit-offer">Early Bird: 3,99€ für die ersten 100 User!</p>
              <button class="buy-button">Jetzt Upgraden & Starten</button>
            </div>
          </div>

          <div class="comparison-table-wrapper">
            <table class="comparison-table">
              <thead>
              <tr>
                <th>Feature</th>
                <th>Free</th>
                <th>Pro</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>Technik</td>
                <td>JS Simulation</td>
                <td class="highlight">Docker Container</td>
              </tr>
              <tr>
                <td>Internet</td>
                <td>Nein</td>
                <td class="highlight">Vollzugriff</td>
              </tr>
              <tr>
                <td>Speicher</td>
                <td>Temporär</td>
                <td class="highlight">Permanent</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>

    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import LinguinsSimulator from "@/components/LinguinsSimulator.vue";

export default defineComponent({
  name: "TerminalPage",
  components: {
    LinguinsSimulator
  },
  setup() {
    const currentMode = ref<'free' | 'pro'>('free');
    return { currentMode };
  }
});
</script>

<style scoped>
/* Neue professionelle CSS Klassen */
.terminal-page {
  padding: 40px 20px;
  max-width: 1200px;
  margin: 0 auto;
  color: #e8e8e8;
  font-family: 'Space Grotesk', sans-serif;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
}

.badge {
  display: inline-block;
  background: rgba(74, 222, 128, 0.1);
  color: #4ade80;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: 600;
  text-transform: uppercase;
  margin-bottom: 10px;
}

/* Toggle Switch */
.edition-toggle {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-bottom: 40px;
}

.toggle-btn {
  background: #1e1e1e;
  border: 1px solid #333;
  padding: 12px 24px;
  border-radius: 10px;
  color: #aaa;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
}

.toggle-btn.active {
  background: #2a2a2a;
  border-color: #4ade80;
  color: #fff;
  box-shadow: 0 0 15px rgba(74, 222, 128, 0.2);
}

.toggle-btn.pro.active {
  border-color: #60a5fa;
  box-shadow: 0 0 15px rgba(96, 165, 250, 0.2);
}

/* Cards & Layout */
.terminal-card {
  background: #141414;
  border-radius: 16px;
  padding: 30px;
  border: 1px solid #2a2a2a;
}

.pro-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

.pro-info-card {
  background: linear-gradient(145deg, #1a1a1a 0%, #0c0c0c 100%);
  padding: 40px;
  border-radius: 16px;
  border: 1px solid #333;
  position: relative;
}

.pro-badge {
  position: absolute;
  top: 20px;
  right: 20px;
  background: #fbbf24;
  color: #000;
  padding: 4px 10px;
  border-radius: 5px;
  font-weight: bold;
  font-size: 0.7rem;
}

/* Feature List */
.feature-list {
  list-style: none;
  margin: 25px 0;
}

.feature-list li {
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.check { color: #4ade80; font-weight: bold; }

/* Pricing */
.pricing-box {
  margin-top: 30px;
  padding-top: 25px;
  border-top: 1px solid #333;
}

.price {
  font-size: 2.5rem;
  font-weight: 700;
  color: #fff;
}

.limit-offer {
  color: #fbbf24;
  font-size: 0.9rem;
  margin-top: 5px;
}

.buy-button {
  width: 100%;
  background: #60a5fa;
  color: white;
  border: none;
  padding: 15px;
  border-radius: 8px;
  font-weight: 700;
  margin-top: 20px;
  cursor: pointer;
  transition: transform 0.2s;
}

.buy-button:hover { transform: scale(1.02); background: #3b82f6; }

/* Table Style */
.comparison-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
}

.comparison-table th, .comparison-table td {
  padding: 15px;
  border-bottom: 1px solid #222;
}

.highlight { color: #60a5fa; font-weight: 600; }

.fade-in {
  animation: fadeIn 0.4s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

@media (max-width: 900px) {
  .pro-grid { grid-template-columns: 1fr; }
}
</style>