import React from 'react';
import { View, StyleSheet, Image, ImageStyle, TextStyle, ViewStyle } from 'react-native';
import { Card, Title, Paragraph } from 'react-native-paper';

interface ImageCardProps {
  imageSource: string; // URL ou caminho da imagem
  title: string;
  description: string;
  containerStyle?: ViewStyle;
  imageStyle?: ImageStyle;
  titleStyle?: TextStyle;
  descriptionStyle?: TextStyle;
}

const ImageCard: React.FC<ImageCardProps> = ({
  imageSource,
  title,
  description,
  containerStyle,
  imageStyle,
  titleStyle,
  descriptionStyle,
}) => {
  return (
    <Card style={[styles.card, containerStyle]}>
      <Card.Cover source={{ uri: imageSource }} style={imageStyle} />
      <Card.Content>
        <Title style={titleStyle}>{title}</Title>
        <Paragraph style={descriptionStyle}>{description}</Paragraph>
      </Card.Content>
    </Card>
  );
};

const styles = StyleSheet.create({
  card: {
    margin: 10,
  },
});

export default ImageCard;
