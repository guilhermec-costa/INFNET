import React from 'react';
import { View, StyleSheet, ViewStyle } from 'react-native';
import { Button } from 'react-native-paper';

interface MyButtonProps {
  onPress: () => void;
  title: string;
  style?: ViewStyle;
}

const MyButton: React.FC<MyButtonProps> = ({ onPress, title, style }) => {
  return (
    <View style={[styles.container, style]}>
      <Button mode="contained" onPress={onPress} style={styles.button}>
        {title}
      </Button>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    margin: 10,
  },
  button: {
    borderRadius: 8,
  },
});

export default MyButton;
