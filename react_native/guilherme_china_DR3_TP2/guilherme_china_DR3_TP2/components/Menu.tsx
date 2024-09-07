import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity, FlatList, StyleProp, ViewStyle, TextStyle } from 'react-native';

interface MenuItem {
  id: string;
  title: string;
  onPress: () => void;
}

interface MenuProps {
  items: MenuItem[];
  menuStyle?: StyleProp<ViewStyle>;
  itemStyle?: StyleProp<ViewStyle>;
  itemTextStyle?: StyleProp<TextStyle>;
}

const Menu: React.FC<MenuProps> = ({
  items,
  menuStyle,
  itemStyle,
  itemTextStyle,
}) => {
  return (
    <View style={[styles.menuContainer, menuStyle]}>
      <FlatList
        data={items}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => (
          <TouchableOpacity
            style={[styles.menuItem, itemStyle]}
            onPress={item.onPress}
          >
            <Text style={[styles.itemText, itemTextStyle]}>
              {item.title}
            </Text>
          </TouchableOpacity>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  menuContainer: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#ddd',
    padding: 16,
    width: '100%',
  },
  menuItem: {
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  itemText: {
    fontSize: 16,
    color: '#333',
  },
});

export default Menu;
