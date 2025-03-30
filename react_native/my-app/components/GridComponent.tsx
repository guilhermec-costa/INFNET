import React from 'react';
import { View, StyleSheet, FlatList, ViewStyle, TextStyle } from 'react-native';
import { Card, Title, Paragraph } from 'react-native-paper';

interface GridItem {
  id: string;
  title: string;
  description: string;
}

interface GridProps {
  data: GridItem[];
  containerStyle?: ViewStyle;
  cardStyle?: ViewStyle;
  titleStyle?: TextStyle;
  descriptionStyle?: TextStyle;
}

const Grid: React.FC<GridProps> = ({ data, containerStyle, cardStyle, titleStyle, descriptionStyle }) => {
  const renderItem = ({ item }: { item: GridItem }) => (
    <Card style={[styles.card, cardStyle]}>
      <Card.Content>
        <Title style={titleStyle}>{item.title}</Title>
        <Paragraph style={descriptionStyle}>{item.description}</Paragraph>
      </Card.Content>
    </Card>
  );

  return (
    <View style={[styles.container, containerStyle]}>
      <FlatList
        data={data}
        renderItem={renderItem}
        keyExtractor={item => item.id}
        numColumns={2} 
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 10,
  },
  card: {
    margin: 10,
    flex: 1,
  },
});

export default Grid;
