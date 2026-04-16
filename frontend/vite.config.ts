import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue' // <--- Achte auf das 'js' im Namen!
import vueDevTools from 'vite-plugin-vue-devtools'

export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  server: {
    port: 5173, // Wir lassen das Frontend auf 5173 während der Entwicklung
    proxy: {
      '/api': {
        target: 'http://localhost:8080', // Hier läuft dein Java/Gradle Backend
        changeOrigin: true,
        secure: false,
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})