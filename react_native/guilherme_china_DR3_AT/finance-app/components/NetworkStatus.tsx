import React, { useEffect, useState } from 'react';
import * as Network from 'expo-network';
import { View, Text, Alert, StyleSheet } from 'react-native';

export default function NetworkStatus() {
  const [isConnected, setIsConnected] = useState(true);

  useEffect(() => {
    const checkNetwork = async () => {
      const networkState = await Network.getNetworkStateAsync();
      setIsConnected(networkState.isConnected);

      if (!networkState.isConnected) {
        Alert.alert("No Internet Connection", "Please connect to the internet.");
      }
    };

    checkNetwork();

    const interval = setInterval(() => {
      checkNetwork();
    }, 5000);

    return () => clearInterval(interval);
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.statusBox}>
        <Text
          style={[
            styles.statusText,
            { color: isConnected ? '#4CAF50' : '#F44336' } 
          ]}
        >
          {isConnected ? "You are Online" : "You are Offline"}
        </Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5F5F5',
  },
  statusBox: {
    padding: 20,
    borderRadius: 10,
    backgroundColor: '#fff',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 5,
  },
  statusText: {
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center',
  },
});
