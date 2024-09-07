import React, { useEffect, useState } from 'react';
import { View, FlatList, Text, StyleSheet, Button, Alert, Image, ScrollView } from 'react-native';
import { collection, getDocs, doc, updateDoc, deleteDoc, setDoc } from 'firebase/firestore';
import { db, storage } from '../firebase';
import { Dialog, Portal, TextInput } from 'react-native-paper';
import { launchImageLibrary } from 'react-native-image-picker';
import { ref, uploadBytes, getDownloadURL } from 'firebase/storage';

const ListItemsScreen = () => {
	const [items, setItems] = useState([]);
	const [visible, setVisible] = useState(false);
	const [selectedItem, setSelectedItem] = useState(null);
	const [newName, setNewName] = useState('');
	const [images, setImages] = useState([]);
	const [uploadedImages, setUploadedImages] = useState([]);

	const fetchItems = async () => {
		const listSnapshot = await getDocs(collection(db, "itens"));
		const itemList = listSnapshot.docs.map(doc => ({ id: doc.id, ...doc.data() }));
		setItems(itemList);
	};

	useEffect(() => {
		fetchItems();
	}, []);

	const handleImagePicker = () => {
		launchImageLibrary({ selectionLimit: 5, mediaType: 'photo' }, async (response) => {
			if (!response.didCancel && response.assets) {
				const selectedImages = response.assets;
				setImages(selectedImages);
			}
		});
	};

	const uploadImages = async (itemId) => {
		const imageUrls = [];
		for (const image of images) {
			const imageRef = ref(storage, `item_images/${itemId}/${image.fileName}`);
			const response = await fetch(image.uri);
			const blob = await response.blob();
			await uploadBytes(imageRef, blob);
			const url = await getDownloadURL(imageRef);
			imageUrls.push(url);
		}
		return imageUrls;
	};

	const updateItem = async () => {
		if (selectedItem) {
			const itemRef = doc(db, 'itens', selectedItem.id);
			const imageUrls = await uploadImages(selectedItem.id);
			await updateDoc(itemRef, {
				name: newName,
				images: imageUrls
			});
			const updatedItems = items.map(item =>
				item.id === selectedItem.id ? { ...item, name: newName, images: imageUrls } : item
			);
			setItems(updatedItems);
			setVisible(false);
		}
	};

	const deleteItem = async (item) => {
		Alert.alert(
			"Confirmar",
			"Tem certeza de que deseja excluir este item?",
			[
				{ text: "Cancelar", style: "cancel" },
				{
					text: "Excluir", onPress: async () => {
						try {
							const itemRef = doc(db, 'itens', item.id);
							await deleteDoc(itemRef);
							fetchItems();
						} catch (error) {
							console.error("Erro ao excluir item: ", error);
						}
					}
				}
			]
		);
	};

	const showDialog = (item) => {
		setSelectedItem(item);
		setNewName(item.name);
		setUploadedImages(item.images || []);
		setVisible(true);
	};

	const hideDialog = () => {
		setVisible(false);
		setSelectedItem(null);
		setImages([]);
	};

	return (
		<View style={styles.container}>
			<Text style={styles.title}>Lista de Itens</Text>
			<FlatList
				data={items}
				keyExtractor={(item) => item.id}
				renderItem={({ item }) => (
					<View style={styles.itemContainer}>
						<Text style={styles.itemName}>{item.name}</Text>
						{item.images && item.images.length > 0 && (
							<ScrollView horizontal>
								{item.images.map((imageUrl, index) => (
									<Image
										key={index}
										source={{ uri: imageUrl }}
										style={styles.image}
									/>
								))}
							</ScrollView>
						)}
						<Button title="Atualizar" onPress={() => showDialog(item)} />
						<Button title="Excluir" onPress={() => deleteItem(item)} />
					</View>
				)}
			/>
			<Portal>
				<Dialog visible={visible} onDismiss={hideDialog}>
					<Dialog.Title>Atualizar Item</Dialog.Title>
					<Dialog.Content>
						<TextInput
							label="Nome do Item"
							value={newName}
							onChangeText={setNewName}
						/>
						<Button onPress={handleImagePicker} title='Selecionar imagens'></Button>
						<ScrollView horizontal>
							{images.map((image, index) => (
								<Image
									key={index}
									source={{ uri: image.uri }}
									style={styles.selectedImage}
								/>
							))}
						</ScrollView>
					</Dialog.Content>
					<Dialog.Actions>
						<Button onPress={hideDialog} title='Cancelar'></Button>
						<Button onPress={updateItem} title='Salvar'></Button>
					</Dialog.Actions>
				</Dialog>
			</Portal>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		flex: 1,
		padding: 20,
	},
	title: {
		fontSize: 24,
		marginBottom: 16,
		textAlign: 'center',
	},
	itemContainer: {
		padding: 16,
		borderBottomWidth: 1,
		borderBottomColor: '#ccc',
	},
	itemName: {
		fontSize: 18,
	},
	image: {
		width: 100,
		height: 100,
		marginRight: 10,
	},
	selectedImage: {
		width: 100,
		height: 100,
		marginRight: 10,
		borderColor: '#ccc',
		borderWidth: 1,
	},
});

export default ListItemsScreen;
