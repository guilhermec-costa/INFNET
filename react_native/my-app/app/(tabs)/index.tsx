import { Image, StyleSheet, Platform, Alert } from 'react-native';

import ParallaxScrollView from '@/components/ParallaxScrollView';
import MyButton from '@/components/ButtonComponent';
import { SafeAreaView } from 'react-native-safe-area-context';
import MyInput from '@/components/InputComponent';
import { useState } from 'react';
import { Text } from 'react-native-paper';
import Grid from '@/components/GridComponent';
import ImageCard from '@/components/ImageComponent';

export default function HomeScreen() {
  const handlePress = () => {
    Alert.alert('Button Pressed!', 'You pressed the button.');
  }
  const [text, setText] = useState('');

  const data = [
    { id: '1', title: 'Item 1', description: 'Descrição do item 1' },
    { id: '2', title: 'Item 2', description: 'Descrição do item 2' },
    { id: '3', title: 'Item 3', description: 'Descrição do item 3' },
    { id: '4', title: 'Item 4', description: 'Descrição do item 4' },
  ];

  return (
    <ParallaxScrollView
      headerBackgroundColor={{ light: '#A1CEDC', dark: '#1D3D47' }}
      headerImage={
        <Image
          source={require('@/assets/images/partial-react-logo.png')}
          style={styles.reactLogo}
        />
      }>
      <SafeAreaView>
        <MyButton onPress={handlePress} title="Clique Aqui" />
        <SafeAreaView style={styles.container}>
          <MyInput
            label="Digite algo"
            value={text}
            onChangeText={setText}
            placeholder="Digite aqui..."
          />
          <Text style={styles.text}>Texto digitado: {text}</Text>
          <Grid data={data} />
          <ImageCard
            imageSource="assets/images/react-logo.png" 
            title="React logo"
            description="React logo's image"
          />
        </SafeAreaView>
      </SafeAreaView>
    </ParallaxScrollView>
  );
}

const styles = StyleSheet.create({
  titleContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
  },
  stepContainer: {
    gap: 8,
    marginBottom: 8,
  },
  reactLogo: {
    height: 178,
    width: 290,
    bottom: 0,
    left: 0,
    position: 'absolute',
  },
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 16,
  },

  text: {
    color: '#ffffff'
  }
});
