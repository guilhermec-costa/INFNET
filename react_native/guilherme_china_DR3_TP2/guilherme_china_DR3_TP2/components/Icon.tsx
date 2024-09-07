import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import { MaterialIcons } from '@expo/vector-icons';

interface IconButtonProps {
    icon: string;
    label: string;
    onPress: () => void;
    color?: string;
}

const IconButton: React.FC<IconButtonProps> = ({ icon, label, onPress, color = '#000' }) => {
    return (
        <TouchableOpacity style={styles.button} onPress={onPress}>
            <View style={styles.iconContainer}>
                <MaterialIcons name={icon} size={24} color={color} />
            </View>
            <Text style={[styles.label, { color }]}>{label}</Text>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    button: {
        flexDirection: 'row',
        alignItems: 'center',
        padding: 10,
        borderRadius: 5,
        backgroundColor: '#f0f0f0',
        marginBottom: 10,
    },
    iconContainer: {
        marginRight: 10,
    },
    label: {
        fontSize: 16,
        fontWeight: '600',
    },
});

export default IconButton;
