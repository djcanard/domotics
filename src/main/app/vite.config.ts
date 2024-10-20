import {defineConfig} from 'vite'
import react from '@vitejs/plugin-react-swc'

export default defineConfig({
    plugins: [react()],
    server: {
        open: true,
        port: 3000,
        proxy: {
            "/api": {
                changeOrigin: true,
                target: "http://localhost:8080/",
                rewrite: path => path.replace(/^\/api/, '')
            }
        }
    },
})
