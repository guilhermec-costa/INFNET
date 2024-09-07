// ThemeContext.js
import React, { createContext, useState, useContext, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { themes, getSystemTheme } from '../themes';

const ThemeContext = createContext();

const ThemeProvider = ({ children }) => {
    const [theme, setTheme] = useState(getSystemTheme());

    useEffect(() => {
        const loadTheme = async () => {
            try {
                const savedTheme = await AsyncStorage.getItem('theme');
                if (savedTheme) {
                    setTheme(themes[savedTheme]);
                } else {
                    setTheme(getSystemTheme());
                }
            } catch (error) {
                console.error('Failed to load theme:', error);
                setTheme(getSystemTheme());
            }
        };
        loadTheme();
    }, []);

    const setAppTheme = async (themeName) => {
        const selectedTheme = themes[themeName] || getSystemTheme();
        setTheme(selectedTheme);
        try {
            await AsyncStorage.setItem('theme', themeName);
        } catch (error) {
            console.error('Failed to save theme:', error);
        }
    };

    return (
        <ThemeContext.Provider value={{ theme, setAppTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};

const useTheme = () => useContext(ThemeContext);

export { ThemeProvider, useTheme };
