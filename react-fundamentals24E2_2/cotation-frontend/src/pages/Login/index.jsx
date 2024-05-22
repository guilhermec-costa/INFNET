import { memo } from "react"
import { useAuth } from "../../hooks/useAuth"

const Login = () => {
    const auth = useAuth();
    return (
        <h2>Login page</h2>
    )
}

export default memo(Login)
