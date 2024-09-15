import React, { useContext, useEffect, useState } from 'react';
import { View, Text, StyleSheet, Image, Button, Alert } from 'react-native';
import { AuthContext } from '@/context/AuthContext';
import * as ImagePicker from 'expo-image-picker';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Camera } from 'expo-camera';
import { CameraType } from 'expo-camera/build/legacy/Camera.types';

export default function ProfileScreen() {
  const { user, logout } = useContext(AuthContext);
  const [profileImage, setProfileImage] = useState(user?.profileImage || null);
  const [image, setImage] = useState(null);

  const pickImage = async () => {
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });

    if (!result.canceled) {
      const imageUri = result.assets[0].uri;
      setProfileImage(imageUri);

      try {
        await AsyncStorage.setItem('@profileImage', imageUri);
        console.log('Imagem de perfil salva com sucesso!');
      } catch (error) {
        console.error('Erro ao salvar a imagem de perfil:', error);
      }
    }
  };

  const captureImage = async () => {
    const { status } = await Camera.requestCameraPermissionsAsync();
    if (status === 'granted') {
      const result = await ImagePicker.launchCameraAsync({
        allowsEditing: true,
        quality: 1,
        cameraType: CameraType.front
      });

      if (!result.canceled) {
        setImage(result.uri);
      }
    } else {
      alert("Permission to access camera is required!");
    }
  };

  useEffect(() => {
    const loadProfileImage = async () => {
      try {
        const savedImage = await AsyncStorage.getItem('@profileImage');
        if (savedImage) {
          setProfileImage(savedImage);
        }
      } catch (error) {
        console.error('Erro ao carregar a imagem de perfil:', error);
      }
    };

    loadProfileImage();
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Perfil</Text>

      <View style={styles.profileInfo}>
        <Image
          source={profileImage ? { uri: profileImage } : require("@/assets/images/favicon.png")}
          style={styles.profileImage}
        />
        <Button title="Alterar foto de perfil" onPress={pickImage} />
        <Button title="Capture Image" onPress={captureImage} />

        <Text style={styles.username}>{user?.name || 'Nome do Usuário'}</Text>
        <Text style={styles.email}>{user?.email || 'email@exemplo.com'}</Text>
      </View>

      <View style={styles.stats}>
        <Text style={styles.statTitle}>Estatísticas financeiras:</Text>
        <Text style={styles.statItem}>Total Gasto: R$ {user?.totalSpent || 0}</Text>
        <Text style={styles.statItem}>Total Economizado: R$ {user?.totalSaved || 0}</Text>
      </View>

      <View style={styles.actions}>
        <Button title="Sair" color="#f44336" onPress={logout} />
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 20,
  },
  profileInfo: {
    alignItems: 'center',
    marginBottom: 20,
  },
  profileImage: {
    width: 100,
    height: 100,
    borderRadius: 50,
    marginBottom: 10,
  },
  username: {
    fontSize: 20,
    fontWeight: '600',
    marginVertical: 10,
  },
  email: {
    fontSize: 16,
    color: '#888',
    marginBottom: 20,
  },
  stats: {
    marginVertical: 20,
  },
  statTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  statItem: {
    fontSize: 16,
    marginBottom: 5,
  },
  actions: {
    marginTop: 30,
  },
});
