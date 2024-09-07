import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Alert } from 'react-native';

const NewItemScreen = () => {
    const [itemName, setItemName] = useState('');
    const [itemDescription, setItemDescription] = useState('');

    const handleSaveItem = () => {
        if (!itemName || !itemDescription) {
            Alert.alert('Error', 'Please fill in all fields');
            return;
        }
        Alert.alert('Success', 'New item has been added');
        setItemName('');
        setItemDescription('');
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>New Item</Text>

            <TextInput
                style={styles.input}
                placeholder="Item Name"
                value={itemName}
                onChangeText={setItemName}
            />

            <TextInput
                style={[styles.input, styles.textArea]}
                placeholder="Item Description"
                multiline
                numberOfLines={4}
                value={itemDescription}
                onChangeText={setItemDescription}
            />

            <TouchableOpacity style={styles.button} onPress={handleSaveItem}>
                <Text style={styles.buttonText}>Save Item</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.backButton} onPress={() => { }}>
                <Text style={styles.backButtonText}>Back to List</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        justifyContent: 'center',
        backgroundColor: '#fff',
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 20,
        textAlign: 'center',
    },
    input: {
        height: 50,
        borderColor: '#ddd',
        borderWidth: 1,
        borderRadius: 5,
        paddingHorizontal: 15,
        marginBottom: 20,
    },
    textArea: {
        height: 100,
        textAlignVertical: 'top',
    },
    button: {
        padding: 15,
        borderRadius: 5,
        backgroundColor: '#007BFF',
        alignItems: 'center',
        marginBottom: 20,
    },
    buttonText: {
        color: '#fff',
        fontSize: 18,
    },
    backButton: {
        alignItems: 'center',
    },
    backButtonText: {
        fontSize: 16,
        color: '#007BFF',
    },
});

export default NewItemScreen;
