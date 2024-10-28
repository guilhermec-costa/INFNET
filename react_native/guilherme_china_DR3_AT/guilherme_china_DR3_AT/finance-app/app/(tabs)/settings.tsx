import React, { useContext, useEffect, useState } from 'react';
import { View, Text, Switch, Button, Alert, StyleSheet, TouchableOpacity } from 'react-native';
import { useRouter } from 'expo-router';
import { AuthContext } from '@/context/AuthContext';
import { useColorScheme } from '@/components/useColorScheme';
import * as Notifications from 'expo-notifications';
import { useTheme } from '@/context/ThemeContext';

export default function SettingsScreen() {
  const {setAppTheme, theme} = useTheme();
  const { logout } = useContext(AuthContext);
  const router = useRouter();
  const [darkModeEnabled, setDarkModeEnabled] = useState(theme=== 'dark');
  const [notificationsEnabled, setNotificationsEnabled] = useState(true);

  const toggleDarkMode = () => {
    setDarkModeEnabled(!darkModeEnabled);
  };

  useEffect(() => {
    setAppTheme(darkModeEnabled ? "dark" : "light");
  }, [darkModeEnabled])

  const toggleNotifications = async () => {
    setNotificationsEnabled(!notificationsEnabled);
    if (notificationsEnabled) {
      await Notifications.setNotificationChannelAsync('default', {
        name: 'default',
        importance: Notifications.AndroidImportance.MAX,
        sound: 'default',
      });
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Configurações</Text>

      <View style={styles.settingItem}>
        <Text style={styles.settingText}>Ativar Notificações</Text>
        <Switch value={notificationsEnabled} onValueChange={toggleNotifications} />
      </View>

      <View style={styles.settingItem}>
        <Text style={styles.settingText}>Modo Escuro</Text>
        <Switch value={darkModeEnabled} onValueChange={toggleDarkMode} />
      </View>

      <TouchableOpacity style={styles.settingButton} onPress={() => router.push('/profile')}>
        <Text style={styles.settingButtonText}>Editar Perfil</Text>
      </TouchableOpacity>

      <Button title="Sair" color="red" onPress={logout} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  settingItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
  },
  settingText: {
    fontSize: 16,
  },
  settingButton: {
    marginTop: 20,
    padding: 15,
    backgroundColor: '#1E90FF',
    borderRadius: 8,
    alignItems: 'center',
  },
  settingButtonText: {
    color: 'white',
    fontSize: 16,
  },
});
