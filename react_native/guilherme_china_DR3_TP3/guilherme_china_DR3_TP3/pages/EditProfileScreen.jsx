import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text, Image } from 'react-native';
import { updateProfile } from 'firebase/auth';
import { getDatabase, ref, set } from 'firebase/database';
import { getStorage, ref as storageRef, uploadBytes, getDownloadURL } from 'firebase/storage';
import { auth, database, storage } from '../firebase';
import { launchCamera, launchImageLibrary } from 'react-native-image-picker';

const EditProfileScreen = () => {
	const [displayName, setDisplayName] = useState('');
	const [photoUri, setPhotoUri] = useState(null);
	const [error, setError] = useState('');
	const [message, setMessage] = useState('');

	const handleSave = async () => {
		console.log("saving picture photo")
		const user = auth.currentUser;

		if (user) {
			try {
				let photoURL = null;

				if (photoUri) {
					const response = await fetch(photoUri);
					const blob = await response.blob();
					const photoRef = storageRef(storage, `profile_pictures/${user.uid}`);

					await uploadBytes(photoRef, blob);
					photoURL = await getDownloadURL(photoRef);
				}

				await updateProfile(user, {
					displayName: displayName,
					photoURL: photoURL || user.photoURL,
				});

				const userRef = ref(database, `users/${user.uid}`);
				await set(userRef, {
					displayName: displayName,
					photoURL: photoURL || user.photoURL,
				});

				setMessage('Perfil atualizado com sucesso!');
				setError('');
			} catch (error) {
				setError(error.message);
				setMessage('');
			}
		}
	};

	const pickImage = async () => {
		const result = await launchImageLibrary({
			mediaType: 'photo',
			quality: 1,
		});

		if (!result.didCancel && result.assets && result.assets.length > 0) {
			setPhotoUri(result.assets[0].uri);
		}
	};

	const takePhoto = async () => {
		console.log("taking photo")
		const result = await launchCamera({
			mediaType: 'photo',
			quality: 1,
			includeBase64: false,
			maxHeight: 200,
			maxWidth: 200,
		});

		if (!result.didCancel && result.assets && result.assets.length > 0) {
			setPhotoUri(result.assets[0].uri);
		}
	};

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Editar Perfil</Text>
			{message ? <Text style={styles.message}>{message}</Text> : null}
			{error ? <Text style={styles.error}>{error}</Text> : null}

			<View style={styles.imageContainer}>
				{photoUri ? (
					<Image source={{ uri: photoUri }} style={styles.image} />
				) : (
					<View style={styles.imagePlaceholder}>
						<Text style={styles.imagePlaceholderText}>Sem foto</Text>
					</View>
				)}
			</View>

			<TextInput
				label="Nome"
				value={displayName}
				onChangeText={setDisplayName}
				style={styles.input}
			/>

			<Button mode="contained" onPress={pickImage} style={styles.button} labelStyle={styles.buttonText} title='Escolher Imagem da galeria'>
			</Button>
			<Button mode="contained" onPress={takePhoto} style={styles.button} labelStyle={styles.buttonText} title='Tirar foto'>
			</Button>
			<Button mode="contained" onPress={handleSave} style={styles.button} labelStyle={styles.buttonText} title='Salvar'>
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
		marginTop: 12,
	},
	buttonText: {
		color: '#ffffff', 
	},
	error: {
		color: 'red',
		marginBottom: 16,
		textAlign: 'center',
	},
	message: {
		color: 'green',
		marginBottom: 16,
		textAlign: 'center',
	},
	imageContainer: {
		alignItems: 'center',
		marginBottom: 16,
	},
	image: {
		width: 120,
		height: 120,
		borderRadius: 60,
		borderWidth: 2,
		borderColor: '#ddd',
		marginBottom: 16,
	},
	imagePlaceholder: {
		width: 120,
		height: 120,
		borderRadius: 60,
		backgroundColor: '#ddd',
		justifyContent: 'center',
		alignItems: 'center',
		marginBottom: 16,
	},
	imagePlaceholderText: {
		color: '#888',
		fontSize: 16,
	},
});

export default EditProfileScreen;
