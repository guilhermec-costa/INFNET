import React, { createContext, useState, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Auth, getAuth, sendPasswordResetEmail, signInWithEmailAndPassword, signOut } from 'firebase/auth';
import { auth, firebaseConfig } from '@/app/firebaseConfig';
import { initializeApp } from 'firebase/app';
import { useRouter } from 'expo-router';

interface authType {
  login(email: string, password: string): any,
  logout(): any,
  recoverPassword(email: string): any,
  user: Object
  isAuthenticated: boolean
}

export const AuthContext = createContext({} as authType);

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);
  const [authHandler, setAuthHandler] = useState({} as Auth);
  const [user, setUser] = useState({});
  const router = useRouter();

  useEffect(() => {
    const checkAuthStatus = async () => {
      const token = await AsyncStorage.getItem('userToken');
      setIsAuthenticated(!!token);
      setLoading(false);
    };

    checkAuthStatus();
  }, []);

  const login = async (email: string, password: string) => {
    const loginResponse = await signInWithEmailAndPassword(auth, email, password)
    setUser(loginResponse);
    await AsyncStorage.setItem('userToken', "danese");
    setIsAuthenticated(true);
  };

  useEffect(() => {
      initializeApp(firebaseConfig);
      setAuthHandler(getAuth());
  }, []);

  const logout = async () => {
    await signOut(authHandler);
    await AsyncStorage.removeItem('userToken');
    setIsAuthenticated(false);
    router.navigate("/auth/login");
  };

  const recoverPassword = async (email: string) => {
    try {
        await sendPasswordResetEmail(authHandler, email)
    } catch (err) {
      console.log("fail to recover password: ", err.message)
    }
  }

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, loading, recoverPassword }}>
      {children}
    </AuthContext.Provider>
  );
};
