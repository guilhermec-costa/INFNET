// src/LoginScreen.js
import React, { useState } from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import { TextInput, Button, Text } from 'react-native-paper';
import { createUserWithEmailAndPassword, signInWithEmailAndPassword } from 'firebase/auth';
import { auth } from '../firebase';

const LoginScreen = ({ navigation }) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async () => {
        try {
            await signInWithEmailAndPassword(auth, email, password);
            console.log('Usuário logado com sucesso!');
        } catch (error) {
            setError(error.message);
        }
    };

    const handleRegister = async () => {
        try {
            await createUserWithEmailAndPassword(auth, email, password);
            console.log('Usuário registrado com sucesso!');
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Login</Text>
            {error ? <Text style={styles.error}>{error}</Text> : null}
            <TextInput
                label="Email"
                value={email}
                onChangeText={(text) => setEmail(text)}
                style={styles.input}
                autoCapitalize="none"
            />
            <TextInput
                label="Senha"
                value={password}
                onChangeText={(text) => setPassword(text)}
                secureTextEntry
                style={styles.input}
            />
            <Button mode="contained" onPress={handleLogin} style={styles.button}>
                Entrar
            </Button>
            <Button mode="outlined" onPress={handleRegister} style={styles.button}>
                Registrar
            </Button>
            <TouchableOpacity onPress={() => navigation.navigate('forgot')}>
                <Text style={styles.link}>Esqueci minha senha</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={() => navigation.navigate('editprofile')}>
                <Text style={styles.link}>Editar perfil</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={() => navigation.navigate('settings')}>
                <Text style={styles.link}>Settings</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={() => navigation.navigate('add-item')}>
                <Text style={styles.link}>Adicionar item</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={() => navigation.navigate('list-items')}>
                <Text style={styles.link}>Listar itens</Text>
            </TouchableOpacity>
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
  input: {
    marginBottom: 16,
  },
  button: {
    marginBottom: 16,
  },
  error: {
    color: 'red',
    marginBottom: 16,
    textAlign: 'center',
  },
});

export default LoginScreen;
