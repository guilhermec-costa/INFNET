import { createBrowserRouter } from "react-router-dom"
import { lazy } from "react"
import ErrorBoundary from "../pages/ErrorBoundary"
import PrivateRouter from "./PrivateRouter"

const LoginPage = lazy(() => import("./../pages/Login"))

const router = createBrowserRouter([
    {
        path: "/",
        element: (
            <PrivateRouter>
                <LoginPage />
            </PrivateRouter>
        ),
        errorElement: < ErrorBoundary />
    }
])

export {
    router
}
