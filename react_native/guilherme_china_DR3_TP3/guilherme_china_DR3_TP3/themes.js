// themes.js
import { Appearance } from 'react-native';
import { DefaultTheme, MD3DarkTheme } from 'react-native-paper';

const themes = {
  light: {
    ...DefaultTheme,
    colors: {
      ...DefaultTheme.colors,
      primary: '#6200ee',
      accent: '#03dac4',
    },
  },
  dark: {
    ...MD3DarkTheme,
    colors: {
      ...MD3DarkTheme.colors,
      primary: '#bb86fc',
      accent: '#03dac6',
    },
  },
};

const getSystemTheme = () => {
  const colorScheme = Appearance.getColorScheme();
  return colorScheme === 'dark' ? themes.dark : themes.light;
};

export { themes, getSystemTheme };
