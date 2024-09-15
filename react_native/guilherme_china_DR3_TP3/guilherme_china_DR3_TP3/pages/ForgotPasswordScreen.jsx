import React, { useState } from 'react';
import { View, StyleSheet } from 'react-native';
import { TextInput, Button, Text } from 'react-native-paper';
import { auth } from '../firebase';
import { sendPasswordResetEmail } from 'firebase/auth';

const ForgotPasswordScreen = ({ navigation }) => {
	const [email, setEmail] = useState('');
	const [message, setMessage] = useState('');
	const [error, setError] = useState('');

	const handlePasswordReset = async () => {
		try {
			await sendPasswordResetEmail(auth, email);
			setMessage('E-mail de redefinição de senha enviado!');
			setError('');
		} catch (error) {
			setError(error.message);
			setMessage('');
		}
	};

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Recuperar Senha</Text>
			{message ? <Text style={styles.message}>{message}</Text> : null}
			{error ? <Text style={styles.error}>{error}</Text> : null}
			<TextInput
				label="Email"
				value={email}
				onChangeText={(text) => setEmail(text)}
				style={styles.input}
				autoCapitalize="none"
			/>
			<Button mode="contained" onPress={handlePasswordReset} style={styles.button}>
				Enviar Link de Redefinição
			</Button>
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
	message: {
		color: 'green',
		marginBottom: 16,
		textAlign: 'center',
	},
	error: {
		color: 'red',
		marginBottom: 16,
		textAlign: 'center',
	},
});

export default ForgotPasswordScreen;
