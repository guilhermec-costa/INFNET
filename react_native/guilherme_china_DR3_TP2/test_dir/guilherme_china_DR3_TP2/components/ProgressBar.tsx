import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

interface ProgressBarProps {
    progress: number; 
    color?: string;   
    backgroundColor?: string; 
    width?: number;   
    height?: number;  
    label?: string;   
}

const ProgressBar: React.FC<ProgressBarProps> = ({
    progress,
    color = '#3498db',
    backgroundColor = '#e0e0e0',
    width = 300,
    height = 20,
    label,
}) => {
    const progressPercentage = Math.min(Math.max(progress, 0), 100); 
    const progressWidth = (progressPercentage / 100) * width;

    return (
        <View style={[styles.container, { width, height }]}>
            <View style={[styles.barBackground, { backgroundColor, width, height }]}>
                <View style={[styles.barProgress, { width: progressWidth, backgroundColor: color }]} />
            </View>
            {label && (
                <Text style={[styles.label, { width, height }]}>{label}</Text>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        position: 'relative',
        marginVertical: 10,
    },
    barBackground: {
        borderRadius: 10,
        overflow: 'hidden',
        justifyContent: 'center', 
    },
    barProgress: {
        height: '100%',
        borderRadius: 10,
    },
    label: {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: [{ translateX: "-50%" }, { translateY: "-50%" }],
        color: '#fff',
        fontWeight: 'bold',
        textAlign: 'center',
        lineHeight: 20, 
    },
});

export default ProgressBar;
