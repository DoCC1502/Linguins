<template>
  <div class="simulator-scope">
    <div v-if="allTasksCompleted" class="completion-screen">
      <div class="completion-content">
        <div class="completion-icon">🎉</div>
        <h2 class="completion-title">All Tasks Complete!</h2>
        <p class="completion-text">You've mastered the basics of Linux.<br>Great work!</p>
      </div>
    </div>

    <div class="terminal-window">
      <div class="window-chrome">
        <div class="window-dot dot-close"></div>
        <div class="window-dot dot-minimize"></div>
        <div class="window-dot dot-maximize"></div>
        <div class="window-title">linguin@linux: ~</div>
      </div>
      <div class="terminal-container">
        <div ref="terminalRef" id="terminal"></div>
      </div>
    </div>

    <div class="task-panel" v-if="currentTask && !allTasksCompleted">
      <div class="task-header">
        <span class="task-title">Current Mission</span>
        <span class="task-counter">Task {{ currentTaskIndex + 1 }} / {{ totalTasks }}</span>
      </div>
      <div class="task-description">{{ currentTask.description }}</div>
      <div class="task-footer">
        <span class="task-status" :class="currentTask.completed ? 'status-completed' : 'status-pending'">
          <span class="status-icon">{{ currentTask.completed ? '✓' : '⏳' }}</span>
          {{ currentTask.completed ? 'Completed' : 'In Progress' }}
        </span>
        <button
            v-if="currentTask.completed && !isLastTask"
            class="next-btn"
            @click="nextTask"
        >
          Next Step →
        </button>
      </div>
      <div class="progress-bar">
        <div class="progress-fill" :style="{ width: progressPercent + '%' }"></div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, computed, onBeforeUnmount } from 'vue';
import { Terminal } from 'xterm';
import { FitAddon } from 'xterm-addon-fit';
import 'xterm/css/xterm.css';

// --- ANSI & LOGIK KLASSEN (EXAKT AUS DEINER HTML) ---
const ANSI = {
  reset: '\x1b[0m', bold: '\x1b[1m', dim: '\x1b[2m',
  red: '\x1b[31m', green: '\x1b[32m', blue: '\x1b[34m',
  brightGreen: '\x1b[92m', brightBlue: '\x1b[94m', brightCyan: '\x1b[96m'
};

class VFS {
  // ... (Hier den gesamten Inhalt deiner VFS Klasse aus der HTML einfügen)
  // Zur Kürzung hier nur die Initialisierung, nimm dein Original!
  constructor() {
    this.root = { type: 'dir', children: { home: { type: 'dir', children: { linguin: { type: 'dir', children: { 'welcome.txt': { type: 'file', content: '...' } } } } } } };
    this.currentUser = 'linguin'; this.hostname = 'linux'; this._cwd = '/home/linguin';
  }
  get cwd() { return this._cwd; }
  set cwd(value) { this._cwd = value; }
  // ... [Rest der VFS Methoden aus deinem Script]
  getPrompt() {
    const displayPath = this.getDisplayPath(this.cwd);
    return `${ANSI.brightGreen}${this.currentUser}@${this.hostname}${ANSI.reset}:${ANSI.brightBlue}${displayPath}${ANSI.reset}$ `;
  }
  getDisplayPath(path) {
    const home = '/home/linguin';
    if (path === home) return '~';
    if (path.startsWith(home + '/')) return '~' + path.slice(home.length);
    return path;
  }
  // [BITTE ALLE ANDEREN METHODEN WIE resolveNode, listDir etc. HIER REINKOPIEREN]
}

class TaskSystem {
  constructor(vfs, terminal) {
    this.vfs = vfs; this.terminal = terminal; this.tasks = [];
    this.currentTaskIndex = 0; this.commandHistory = []; this.onTaskComplete = null;
  }
  addTask(description, checkFn) { this.tasks.push({ description, checkFn, completed: false }); }
  // ... [Rest der TaskSystem Methoden aus deinem Script]
}

export default defineComponent({
  name: 'LinguinsSimulator',
  setup() {
    const terminalRef = ref<HTMLElement | null>(null);
    const terminal = ref<any>(null);
    const vfs = ref(new VFS());
    const taskSystem = ref<any>(null);

    const commandBuffer = ref('');
    const cursorPosition = ref(0);
    const shellHistory = ref<string[]>([]);
    const historyIndex = ref(-1);
    const savedInput = ref('');
    const currentTaskIndex = ref(0);
    const totalTasks = ref(0);
    const allTasksCompleted = ref(false);

    // Hilfsfunktionen (aus deinem Script)
    const redrawLine = () => {
      terminal.value.write(`\r\x1b[K`);
      terminal.value.write(vfs.value.getPrompt());
      terminal.value.write(commandBuffer.value);
      const offset = commandBuffer.value.length - cursorPosition.value;
      if (offset > 0) terminal.value.write(`\x1b[${offset}D`);
    };

    onMounted(() => {
      // 1. Terminal Init
      terminal.value = new Terminal({
        fontSize: 14,
        fontFamily: '"JetBrains Mono", monospace',
        cursorBlink: true,
        theme: { background: '#1a1a1a', foreground: '#e8e8e8' }
      });

      const fitAddon = new FitAddon();
      terminal.value.loadAddon(fitAddon);
      terminal.value.open(terminalRef.value);
      fitAddon.fit();

      // 2. Task System Init
      taskSystem.value = new TaskSystem(vfs.value, terminal.value);
      taskSystem.value.onTaskComplete = (index: number) => { currentTaskIndex.value = index; };

      // HIER deine Task-Definitionen (Task 1 bis 8) aus dem HTML Script reinkopieren
      taskSystem.value.addTask('List contents...', (v, h) => h.some(i => i.command.trim() === 'ls'));
      // ... usw.

      totalTasks.value = taskSystem.value.tasks.length;

      // Welcome Message
      terminal.value.writeln(`${ANSI.brightGreen}Welcome to Linux Simulator v2.0${ANSI.reset}`);
      terminal.value.write(vfs.value.getPrompt());

      // 3. Input Handling (OnData)
      terminal.value.onData((data: string) => {
        // Hier die gesamte Logik aus deinem setupInput() einfügen
        // Achtung: Variablen wie commandBuffer müssen .value nutzen
        if (data === '\r') {
          // executeCommand Logik...
        }
        // ... Rest der Tastenbelegung
      });
    });

    const currentTask = computed(() => taskSystem.value?.tasks[currentTaskIndex.value]);
    const progressPercent = computed(() => (currentTaskIndex.value / totalTasks.value) * 100);

    return {
      terminalRef, currentTaskIndex, totalTasks, allTasksCompleted,
      currentTask, progressPercent,
      nextTask: () => { if(taskSystem.value.nextTask()) currentTaskIndex.value++ }
    };
  }
});
</script>

<style scoped>
/* Hier EXAKT dein CSS aus der HTML Datei reinkopieren */
.simulator-scope {
  --bg-primary: #0c0c0c;
  --bg-terminal: #1a1a1a;
  --accent-green: #4ade80;
  /* ... alle Variablen ... */
}

.terminal-window {
  background: var(--bg-terminal);
  border-radius: 12px;
  height: 520px;
  display: flex;
  flex-direction: column;
}
.window-chrome {
  background: linear-gradient(180deg, #252525 0%, #1e1e1e 100%);
  padding: 14px 16px;
  display: flex;
  align-items: center;
  gap: 10px;
  border-bottom: 1px solid var(--border-color);
  user-select: none;
}

.window-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  transition: transform 0.2s ease;
}

.window-dot:hover {
  transform: scale(1.15);
}

.dot-close { background: #ff5f56; box-shadow: 0 0 4px rgba(255, 95, 86, 0.4); }
.dot-minimize { background: #ffbd2e; box-shadow: 0 0 4px rgba(255, 189, 46, 0.4); }
.dot-maximize { background: #27c93f; box-shadow: 0 0 4px rgba(39, 201, 63, 0.4); }

.window-title {
  flex: 1;
  text-align: center;
  font-size: 13px;
  color: var(--text-muted);
  font-weight: 500;
  letter-spacing: 0.5px;
}

.terminal-container {
  flex: 1;
  padding: 16px;
  position: relative;
  overflow: hidden;
}

#terminal {
  width: 100%;
  height: 100%;
}

.xterm {
  height: 100%;
  padding: 4px;
}

.xterm-viewport {
  background-color: transparent !important;
}

/* Task Panel */
.task-panel {
  background: var(--bg-panel);
  border: 1px solid var(--border-color);
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.3);
}

.task-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
}

.task-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 14px;
  font-weight: 600;
  color: var(--accent-green);
  text-transform: uppercase;
  letter-spacing: 1.5px;
}

.task-counter {
  font-size: 12px;
  color: var(--text-muted);
  background: rgba(255,255,255,0.05);
  padding: 4px 12px;
  border-radius: 20px;
}

.task-description {
  font-size: 15px;
  line-height: 1.6;
  color: var(--text-primary);
  margin-bottom: 20px;
  min-height: 48px;
}

.task-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
}

.task-status {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

.status-pending {
  background: rgba(251, 191, 36, 0.1);
  color: var(--accent-yellow);
  border: 1px solid rgba(251, 191, 36, 0.2);
}

.status-completed {
  background: rgba(74, 222, 128, 0.1);
  color: var(--accent-green);
  border: 1px solid rgba(74, 222, 128, 0.2);
}

.status-icon {
  font-size: 14px;
}

.next-btn {
  background: linear-gradient(135deg, var(--accent-green) 0%, #22c55e 100%);
  color: #000;
  border: none;
  padding: 10px 24px;
  border-radius: 6px;
  font-family: 'Space Grotesk', sans-serif;
  font-weight: 600;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.2s ease;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% { box-shadow: 0 0 0 0 rgba(74, 222, 128, 0.4); }
  50% { box-shadow: 0 0 0 8px rgba(74, 222, 128, 0); }
}

.next-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 20px rgba(74, 222, 128, 0.3);
}

.next-btn:disabled {
  background: var(--border-color);
  color: var(--text-muted);
  animation: none;
  cursor: not-allowed;
}

.progress-bar {
  height: 3px;
  background: rgba(255,255,255,0.05);
  border-radius: 2px;
  margin-top: 16px;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, var(--accent-green), var(--accent-blue));
  border-radius: 2px;
  transition: width 0.5s ease;
}

/* Completion Screen */
.completion-screen {
  position: fixed;
  inset: 0;
  background: rgba(12, 12, 12, 0.95);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(10px);
}

.completion-content {
  text-align: center;
  padding: 60px;
}

.completion-icon {
  font-size: 80px;
  margin-bottom: 24px;
  animation: bounce 1s ease infinite;
}

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.completion-title {
  font-family: 'Space Grotesk', sans-serif;
  font-size: 36px;
  font-weight: 700;
  background: linear-gradient(135deg, var(--accent-green), var(--accent-blue));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  margin-bottom: 16px;
}

.completion-text {
  color: var(--text-muted);
  font-size: 16px;
  line-height: 1.6;
}
</style>