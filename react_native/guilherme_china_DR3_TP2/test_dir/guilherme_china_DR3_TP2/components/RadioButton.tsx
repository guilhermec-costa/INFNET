import React from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';

interface RadioButtonProps {
    selected: boolean;
    onPress: () => void;
    label: string;
    labelStyle?: object;
    buttonStyle?: object;
    circleStyle?: object;
}

const RadioButton: React.FC<RadioButtonProps> = ({
    selected,
    onPress,
    label,
    labelStyle,
    buttonStyle,
    circleStyle,
}) => {
    return (
        <TouchableOpacity style={[styles.container, buttonStyle]} onPress={onPress}>
            <View style={styles.circleContainer}>
                <View style={[styles.circle, selected && styles.selectedCircle, circleStyle]} />
            </View>
            <Text style={[styles.label, labelStyle]}>{label}</Text>
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        alignItems: 'center',
        marginVertical: 5,
    },
    circleContainer: {
        width: 24,
        height: 24,
        borderRadius: 12,
        borderWidth: 2,
        borderColor: '#3498db',
        justifyContent: 'center',
        alignItems: 'center',
        marginRight: 10,
    },
    circle: {
        width: 12,
        height: 12,
        borderRadius: 6,
        backgroundColor: 'transparent',
    },
    selectedCircle: {
        backgroundColor: '#3498db',
    },
    label: {
        fontSize: 16,
        color: '#333',
    },
});

export default RadioButton;
