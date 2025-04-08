import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
    proxy: {
      '/api': {
        // target: 'http://vcm-45176.vm.duke.edu:8080',
        target: 'http://ece651project.gli81.com:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    },
    allowedHosts: ['vcm-45176.vm.duke.edu', 'ece651project.gli81.com'],
  }
})
