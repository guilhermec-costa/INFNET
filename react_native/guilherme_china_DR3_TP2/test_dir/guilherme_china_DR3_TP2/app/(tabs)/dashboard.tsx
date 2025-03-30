// pages/Dashboard.js
import React from 'react';
import { View, Text, StyleSheet, FlatList, Button } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const Dashboard = () => {
    const navigation = useNavigation();

    const metrics = [
        { id: '1', title: 'Revenue', value: '$5,000' },
        { id: '2', title: 'Users', value: '1,200' },
        { id: '3', title: 'Sales', value: '320' },
    ];

    const handleViewDetails = (metric) => {
        console.log('View details for:', metric);
    };

    const renderItem = ({ item }) => (
        <View style={styles.metricItem}>
            <Text style={styles.metricTitle}>{item.title}</Text>
            <Text style={styles.metricValue}>{item.value}</Text>
            <Button
                title="View Details"
                onPress={() => handleViewDetails(item)}
            />
        </View>
    );

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Dashboard</Text>
            <FlatList
                data={metrics}
                renderItem={renderItem}
                keyExtractor={(item) => item.id}
                style={styles.list}
            />
            <Button
                title="Go to Settings"
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
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
    metricItem: {
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
    metricTitle: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    metricValue: {
        fontSize: 16,
        marginVertical: 8,
    },
});

export default Dashboard;
