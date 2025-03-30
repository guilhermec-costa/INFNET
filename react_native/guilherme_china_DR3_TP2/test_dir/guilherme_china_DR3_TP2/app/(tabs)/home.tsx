import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, ScrollView } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import Card from '@/components/Card';
import IconButton from '@/components/Icon';
import List from '@/components/List';
import ProgressBar from '@/components/ProgressBar';
import RadioButton from '@/components/RadioButton';
import Snackbar from '@/components/SnackBar';
import Tab from '@/components/Tab';
import Menu from '@/components/Menu';

const Home = () => {
  const navigation = useNavigation();
  const [selected, setSelected] = useState<string | null>(null);

  const handlePress = (value: string) => {
    setSelected(value);
  };

  const tabs = [
    {
      label: 'Tab 1',
      content: <Text style={styles.contentText}>Content of Tab 1</Text>,
    },
    {
      label: 'Tab 2',
      content: <Text style={styles.contentText}>Content of Tab 2</Text>,
    },
  ];

  const [snackbarVisible, setSnackbarVisible] = useState(false);

  const showSnackbar = () => {
    setSnackbarVisible(true);
  };

  const hideSnackbar = () => {
    setSnackbarVisible(false);
  };

  const data = [
    { id: '1', title: 'Item 1' },
    { id: '2', title: 'Item 2' },
    { id: '3', title: 'Item 3' },
  ];

  const listData = [
    { id: '1', title: 'Item 1', description: 'Description of Item 1' },
    { id: '2', title: 'Item 2', description: 'Description of Item 2' },
    { id: '3', title: 'Item 3', description: 'Description of Item 3' },
  ];

  const handleLogout = () => {
    console.log('Logout');
  };

  const handleMenuItemPress = (item: string) => {
    console.log(`${item} pressed`);
  };

  const menuItems = [
    { id: '1', title: 'Menu Item 1', onPress: () => handleMenuItemPress('Menu Item 1') },
    { id: '2', title: 'Menu Item 2', onPress: () => handleMenuItemPress('Menu Item 2') },
    { id: '3', title: 'Menu Item 3', onPress: () => handleMenuItemPress('Menu Item 3') },
  ];

  const renderItem = ({ item }) => (
    <View style={styles.item}>
      <Text style={styles.itemText}>{item.title}</Text>
    </View>
  );

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Home Screen</Text>
      <FlatList
        data={data}
        renderItem={renderItem}
        keyExtractor={(item) => item.id}
        style={styles.list}
      />
      <Text style={styles.sectionTitle}>Card Component</Text>
      <Card description='Card Description' onPress={() => console.log("card clicked")} title='Card title' imageUrl='' />
      <Text style={styles.sectionTitle}>Icon Component</Text>
      <IconButton label='Icon button label' color='#ff0000' onPress={() => console.log("icon button pressed")} icon='home' />
      <Text style={styles.sectionTitle}>List Component</Text>
      <List data={listData} onItemPress={() => console.log("item pressed")} />
      <Text style={styles.sectionTitle}>Progress Bar Component</Text>
      <ProgressBar
        progress={60}
        color='#3498db'
        backgroundColor='#e0e0e0'
        width={300}
        height={20}
        label='60%'
      />
      <ProgressBar
        progress={90}
        color='#e74c3c'
        backgroundColor='#f0f0f0'
        width={250}
        height={15}
        label='90%'
      />
      <Text style={styles.sectionTitle}>Radio Button Component</Text>
      <RadioButton
        selected={selected === 'option1'}
        onPress={() => handlePress('option1')}
        label="Option 1"
      />
      <Text style={styles.sectionTitle}>Menu Component</Text>
      <Menu
        items={menuItems}
        menuStyle={styles.menu}
        itemStyle={styles.menuItem}
        itemTextStyle={styles.menuItemText}
      />
      <Snackbar
        message="This is a Snackbar message!"
        visible={snackbarVisible}
        onClose={hideSnackbar}
      />
      <Text style={styles.sectionTitle}>Tab Component</Text>
      <Tab
        tabs={tabs}
        tabStyle={styles.tabHeader}
        labelStyle={styles.tabLabel}
        activeTabStyle={styles.activeTab}
        activeLabelStyle={styles.activeLabel}
      />
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    padding: 16,
  },
  title: {
    fontSize: 32,
    marginBottom: 16,
    textAlign: 'center',
  },
  list: {
    marginBottom: 16,
  },
  item: {
    padding: 16,
    marginVertical: 8,
    marginHorizontal: 16,
    backgroundColor: '#f9f9f9',
    borderRadius: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  itemText: {
    fontSize: 18,
  },
  sectionTitle: {
    fontSize: 24,
    marginVertical: 10,
    fontWeight: 'bold',
  },
  tabHeader: {
    backgroundColor: '#fff',
  },
  tabLabel: {
    color: '#333',
  },
  activeTab: {
    borderBottomColor: '#3498db',
  },
  activeLabel: {
    color: '#3498db',
  },
  contentText: {
    fontSize: 18,
  },
  menu: {
    marginBottom: 16,
  },
  menuItem: {
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  menuItemText: {
    fontSize: 16,
  },
});

export default Home;
