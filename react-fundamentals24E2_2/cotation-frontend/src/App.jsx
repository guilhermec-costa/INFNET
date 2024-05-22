import './App.css'
import { router } from './routes'
import { RouterProvider } from 'react-router-dom'
import { Suspense } from 'react'
import Loading from './components/Loading'

function App() {
    return (
        <Suspense fallback={<Loading />}>
            <RouterProvider router={router} />
        </Suspense>
    )
}

export default App
