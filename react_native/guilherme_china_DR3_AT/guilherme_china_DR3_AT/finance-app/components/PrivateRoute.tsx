import React, { useContext, useEffect } from 'react';
import { AuthContext } from '@/context/AuthContext';
import { useRouter } from 'expo-router';

const PrivateRoute = ({ children }) => {
    const { isAuthenticated } = useContext(AuthContext);
    const router = useRouter();

    useEffect(() => {
        if (!isAuthenticated) {
            router.replace('/auth/login');
        }
    }, [isAuthenticated]);

    if (!isAuthenticated) {
        return null;
    }

    return children;
};

export default PrivateRoute;
