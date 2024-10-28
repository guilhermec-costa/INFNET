import React, { useContext, useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, Alert } from 'react-native';
import { useRouter } from 'expo-router';
import { AuthContext } from '@/context/AuthContext';

export default function ForgotPasswordScreen({navigation}) {
    const {recoverPassword} = useContext(AuthContext);
    const [email, setEmail] = useState('');
    const router = useRouter();

    const handleForgotPassword = () => {
        if (!email) {
            Alert.alert('Erro', 'Por favor, insira seu email.');
            return;
        }

        recoverPassword(email);
        Alert.alert('Sucesso', 'Instruções de recuperação de senha foram enviadas para seu email.');
        router.replace('/auth/login');
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Esqueci Minha Senha</Text>
            <TextInput
                style={styles.input}
                placeholder="Email"
                value={email}
                onChangeText={setEmail}
                keyboardType="email-address"
                autoCapitalize="none"
            />
            <Button title="Enviar Instruções" onPress={handleForgotPassword} />
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 16,
    },
    title: {
        fontSize: 24,
        marginBottom: 16,
        textAlign: 'center',
    },
    input: {
        height: 40,
        borderColor: '#ccc',
        borderWidth: 1,
        marginBottom: 16,
        paddingHorizontal: 8,
        borderRadius: 4,
    },
});
