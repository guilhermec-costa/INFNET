import { Navigate } from "react-router-dom"
const PrivateRouter = ({ children }) => {
    const user = "Churros";

    if (!user) return <Navigate to={"/"} />
    return children;
}

export default PrivateRouter;
