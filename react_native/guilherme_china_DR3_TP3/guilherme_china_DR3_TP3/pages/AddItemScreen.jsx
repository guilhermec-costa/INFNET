import React, { useState } from 'react';
import { View, TextInput, Button, StyleSheet, Text } from 'react-native';
import { ref, push } from 'firebase/database';
import { database, db } from '../firebase';
import { collection, addDoc, getDocs, doc, deleteDoc, updateDoc, getDoc } from "firebase/firestore";

const AddItemScreen = () => {
    const [itemName, setItemName] = useState('');
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');

    const handleAddItem = async () => {
        if (itemName.trim() === '') {
            setError('Nome do item é obrigatório.');
            return;
        }

        try {
            const itemCollection = collection(db, "itens");
            await addDoc(itemCollection, {
                name: itemName,
                createdAt: new Date().toISOString(),
            })

            setItemName('');
            setMessage('Item adicionado com sucesso!');
            setError('');
        } catch (error) {
            setError('Erro ao adicionar o item: ' + error.message);
            setMessage('');
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Adicionar Item</Text>
            {message ? <Text style={styles.message}>{message}</Text> : null}
            {error ? <Text style={styles.error}>{error}</Text> : null}
            <TextInput
                placeholder="Nome do Item"
                value={itemName}
                onChangeText={setItemName}
                style={styles.input}
            />
            <Button title="Adicionar" onPress={handleAddItem} />
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
        marginTop: 16,
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
});

export default AddItemScreen;
