<template>
  <div class="terminal-window">
    <div class="terminal-header">
      <div class="buttons">
        <span class="close"></span>
        <span class="minimize"></span>
        <span class="maximize"></span>
      </div>
      <div class="title">Linguins Terminal — ubuntu@docker</div>
    </div>

    <div ref="terminalContainer" class="terminal-body"></div>

    <div class="terminal-footer">
      <span :class="['status-dot', isConnected ? 'online' : 'offline']"></span>
      {{ isConnected ? 'Verbunden' : 'Verbindung wird neu aufgebaut...' }}
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, onUnmounted, ref } from "vue";
import { Terminal } from "xterm";
import { FitAddon } from "xterm-addon-fit"; // Wichtig für automatisches Resizing
import "xterm/css/xterm.css";

export default defineComponent({
  name: "TerminalComponent",
  setup() {
    const terminalContainer = ref<HTMLDivElement | null>(null);
    const isConnected = ref(false);

    const terminal = new Terminal({
      cursorBlink: true,
      fontSize: 15,
      fontFamily: "'Fira Code', 'Courier New', monospace",
      theme: {
        background: "#1e1e1e",
        foreground: "#d4d4d4",
        cursor: "#00ff00",
        selectionBackground: "rgba(255, 255, 255, 0.3)",
        black: "#000000",
        red: "#cd3131",
        green: "#0dbc79",
        yellow: "#e5e510",
        blue: "#2472c8",
        magenta: "#bc3fbc",
        cyan: "#11a8cd",
        white: "#e5e5e5"
      },
      allowProposedApi: true
    });

    const fitAddon = new FitAddon();
    terminal.loadAddon(fitAddon);

    let socket: WebSocket | null = null;

    function connectWebSocket() {
      const wsUrl = "ws://localhost:8080/ws/terminal";
      socket = new WebSocket(wsUrl);

      socket.onopen = () => {
        isConnected.value = true;
        terminal.write("\x1b[32m\r\n✅ SYSTEM ONLINE: Verbindung hergestellt.\x1b[0m\r\n\r\n");
      };

      socket.onmessage = (event) => {
        terminal.write(event.data);
      };

      socket.onerror = () => {
        isConnected.value = false;
        terminal.write("\x1b[31m\r\n❌ VERBINDUNGSFEHLER: Server nicht erreichbar.\x1b[0m\r\n");
      };

      socket.onclose = () => {
        isConnected.value = false;
        setTimeout(connectWebSocket, 3000);
      };
    }

    onMounted(() => {
      if (terminalContainer.value) {
        terminal.open(terminalContainer.value);
        fitAddon.fit(); // Passt das Terminal an die Größe an

        terminal.write("\x1b[1;36mLinguins Terminal v1.0.0\x1b[0m\r\n");
        terminal.write("Initialisiere Shell...\r\n");

        connectWebSocket();

        terminal.onData((data) => {
          if (socket && socket.readyState === WebSocket.OPEN) {
            socket.send(data);
          }
        });

        // Resize-Listener
        window.addEventListener('resize', () => fitAddon.fit());
      }
    });

    onUnmounted(() => {
      if (socket) socket.close();
      terminal.dispose();
      window.removeEventListener('resize', () => fitAddon.fit());
    });

    return { terminalContainer, isConnected };
  }
});
</script>

<style scoped>
.terminal-window {
  margin: 20px auto;
  max-width: 900px;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.5);
  border: 1px solid #333;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif;
}

.terminal-header {
  background-color: #333;
  height: 35px;
  display: flex;
  align-items: center;
  padding: 0 15px;
  position: relative;
}

.buttons {
  display: flex;
  gap: 8px;
}

.buttons span {
  width: 12px;
  height: 12px;
  border-radius: 50%;
}

.close { background-color: #ff5f56; }
.minimize { background-color: #ffbd2e; }
.maximize { background-color: #27c93f; }

.title {
  color: #bbb;
  font-size: 13px;
  position: absolute;
  width: 100%;
  text-align: center;
  left: 0;
  pointer-events: none;
}

.terminal-body {
  height: 450px;
  background-color: #1e1e1e;
  padding: 10px;
}

.terminal-footer {
  background-color: #252526;
  color: #888;
  font-size: 11px;
  padding: 4px 15px;
  display: flex;
  align-items: center;
  gap: 8px;
  border-top: 1px solid #333;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.online { background-color: #27c93f; box-shadow: 0 0 5px #27c93f; }
.offline { background-color: #ff5f56; box-shadow: 0 0 5px #ff5f56; }

/* Scrollbar Styling */
:deep(.xterm-viewport::-webkit-scrollbar) {
  width: 8px;
}
:deep(.xterm-viewport::-webkit-scrollbar-thumb) {
  background: #444;
  border-radius: 4px;
}
</style>