import React from 'react';
import { StyleSheet, View } from 'react-native';
import { FAB, Provider as PaperProvider } from 'react-native-paper';

const FabButton = ({ onPress }) => {
  return (
    <PaperProvider>
      <View style={styles.container}>
        <FAB
          style={styles.fab}
          icon="plus"
          onPress={onPress}
          color="white"
          label="Add"
          accessibilityLabel="Add Button"
        />
      </View>
    </PaperProvider>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  fab: {
    position: 'absolute',
    margin: 16,
    right: 0,
    bottom: 0,
    backgroundColor: '#6200ee',
  },
});

export default FabButton;
