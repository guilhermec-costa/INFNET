import React from 'react';
import { TextInput as PaperInput, TextInputProps } from 'react-native-paper';
import { StyleSheet, ViewStyle } from 'react-native';

interface MyInputProps extends TextInputProps {
  containerStyle?: ViewStyle;
}

const MyInput: React.FC<MyInputProps> = ({ containerStyle, ...rest }) => {
  return (
    <PaperInput
      style={[styles.input, containerStyle]}
      mode="outlined"
      {...rest}
    />
  );
};

const styles = StyleSheet.create({
  input: {
    margin: 10,
  },
});

export default MyInput;
