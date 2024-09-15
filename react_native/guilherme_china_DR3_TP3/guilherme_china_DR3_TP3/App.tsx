import React from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import LoginScreen from './pages/LoginScreen';
import ForgotPasswordScreen from './pages/ForgotPasswordScreen';
import EditProfileScreen from './pages/EditProfileScreen';
import { ThemeProvider, useTheme } from './contexts/ThemeContext';
import { PaperProvider } from 'react-native-paper';
import SettingsScreen from './pages/SettingsScreen';
import AddItemScreen from './pages/AddItemScreen';
import ListItemsScreen from './pages/ListItemScreen';

const Stack = createStackNavigator();

const AppContent = () => {
  const { theme } = useTheme();

  if (!theme) {
    return null;
  }

  return (
    <PaperProvider theme={theme}>
      <NavigationContainer>
        <Stack.Navigator initialRouteName="login">
          <Stack.Screen name="login" component={LoginScreen} />
          <Stack.Screen name="forgot" component={ForgotPasswordScreen} />
          <Stack.Screen name="editprofile" component={EditProfileScreen} />
          <Stack.Screen name="settings" component={SettingsScreen} />
          <Stack.Screen name="add-item" component={AddItemScreen} />
          <Stack.Screen name="list-items" component={ListItemsScreen} />
        </Stack.Navigator>
      </NavigationContainer>
    </PaperProvider>
  );
};

const App = () => {
  return (
    <SafeAreaProvider>
      <ThemeProvider>
        <AppContent />
      </ThemeProvider>
    </SafeAreaProvider>
  );
};

export default App;