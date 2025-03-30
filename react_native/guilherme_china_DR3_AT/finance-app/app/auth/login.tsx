import React, { useContext, useState } from 'react';
import { View, TextInput, Button, Text, StyleSheet, ActivityIndicator } from 'react-native';
import { useRouter } from 'expo-router';
import { AuthContext } from '@/context/AuthContext';

export default function LoginScreen() {
  const { login } = useContext(AuthContext);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const router = useRouter();

  const handleLogin = async () => {
    setLoading(true);
    setError('');
    try {
      await login(email, password);
      router.replace('/(tabs)');
    } catch (error) {
      setError('Login falhou. Verifique suas credenciais e tente novamente.');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Login</Text>

      <TextInput
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        autoCapitalize="none"
        style={styles.input}
      />
      
      <TextInput
        placeholder="Senha"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
        style={styles.input}
      />
      
      {error ? <Text style={styles.errorText}>{error}</Text> : null}

      {loading ? (
        <ActivityIndicator size="large" color="#4caf50" />
      ) : (
        <Button title="Login" onPress={handleLogin} />
      )}

      <Text style={styles.forgotPassword} onPress={() => router.push('/auth/forgot')}>
        Esqueci minha senha
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  input: {
    borderBottomWidth: 1,
    borderColor: '#ccc',
    padding: 10,
    marginBottom: 20,
    fontSize: 16,
  },
  errorText: {
    color: '#f44336',
    textAlign: 'center',
    marginBottom: 20,
  },
  forgotPassword: {
    color: '#1e90ff',
    textAlign: 'center',
    marginTop: 10,
    textDecorationLine: 'underline',
  },
});
