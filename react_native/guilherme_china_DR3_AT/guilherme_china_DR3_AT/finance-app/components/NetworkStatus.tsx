import React, { useEffect, useState } from 'react';
import * as Network from 'expo-network';
import { View, Text, Alert } from 'react-native';

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
    <View>
      <Text>Network status: {isConnected ? "Online" : "Offline"}</Text>
    </View>
  );
}
