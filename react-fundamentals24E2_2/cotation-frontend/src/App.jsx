import './App.css'
import { router } from './routes'
import { RouterProvider } from 'react-router-dom'
import { Suspense } from 'react'
import Loading from './components/Loading'
import { AuthProvider } from './hooks/useAuth'

function App() {
    return (
        <AuthProvider>
            <Suspense fallback={<Loading />}>
                <RouterProvider router={router} />
            </Suspense>
        </AuthProvider>
    )
}

export default App
