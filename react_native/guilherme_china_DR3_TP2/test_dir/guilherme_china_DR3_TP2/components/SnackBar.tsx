import React, { useState } from 'react';
import { View, Text, StyleSheet, Animated, TouchableOpacity } from 'react-native';

interface SnackbarProps {
    message: string;
    visible: boolean;
    onClose: () => void;
    duration?: number;
    backgroundColor?: string;
    textColor?: string;
}

const Snackbar: React.FC<SnackbarProps> = ({
    message,
    visible,
    onClose,
    duration = 3000,
    backgroundColor = '#333',
    textColor = '#fff',
}) => {
    const [fadeAnim] = useState(new Animated.Value(0));

    React.useEffect(() => {
        if (visible) {
            Animated.timing(fadeAnim, {
                toValue: 1,
                duration: 300,
                useNativeDriver: true,
            }).start();

            const timer = setTimeout(() => {
                Animated.timing(fadeAnim, {
                    toValue: 0,
                    duration: 300,
                    useNativeDriver: true,
                }).start(() => onClose());
            }, duration);

            return () => clearTimeout(timer);
        }
    }, [visible, fadeAnim, onClose, duration]);

    return (
        <Animated.View
            style={[
                styles.container,
                { opacity: fadeAnim, backgroundColor },
            ]}
        >
            <Text style={[styles.text, { color: textColor }]}>{message}</Text>
            <TouchableOpacity onPress={onClose} style={styles.closeButton}>
                <Text style={[styles.closeText, { color: textColor }]}>×</Text>
            </TouchableOpacity>
        </Animated.View>
    );
};

const styles = StyleSheet.create({
    container: {
        position: 'absolute',
        bottom: 0,
        left: 0,
        right: 0,
        padding: 16,
        borderRadius: 4,
        margin: 16,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        elevation: 5,
    },
    text: {
        fontSize: 16,
        flex: 1,
    },
    closeButton: {
        marginLeft: 16,
        padding: 4,
    },
    closeText: {
        fontSize: 20,
        fontWeight: 'bold',
    },
});

export default Snackbar;
