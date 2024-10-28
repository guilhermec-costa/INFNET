import React from 'react';
import { View, Text, StyleSheet, Button } from 'react-native';
import { useTheme } from '../contexts/ThemeContext';

const SettingsScreen = ({ navigation }) => {
    const { setAppTheme } = useTheme();

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Configurações de Tema</Text>
            <Button title="Claro" onPress={() => setAppTheme('light')} />
            <Button title="Escuro" onPress={() => setAppTheme('dark')} />
            <Button title="Automático" onPress={() => setAppTheme('auto')} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 20,
    },
    title: {
        fontSize: 24,
        marginBottom: 16,
        textAlign: 'center',
    },
});

export default SettingsScreen;
