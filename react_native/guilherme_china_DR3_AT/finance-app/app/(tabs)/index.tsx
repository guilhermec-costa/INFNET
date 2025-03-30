import React, { useContext } from 'react';
import { View, Text, Button, FlatList, StyleSheet, TouchableOpacity } from 'react-native';
import { AuthContext } from '@/context/AuthContext';
import NetworkStatus from '@/components/NetworkStatus';
import { useTheme } from '@/context/ThemeContext';

const mockTransactions = [
  { id: '1', title: 'Compra no supermercado', amount: -150.00, date: '2024-09-10' },
  { id: '2', title: 'Salário', amount: 3000.00, date: '2024-09-05' },
  { id: '3', title: 'Aluguel', amount: -800.00, date: '2024-09-01' },
];

const TransactionItem = ({ item }) => {
  const { theme } = useTheme();
  return (
    <TouchableOpacity activeOpacity={0.7} style={[styles.transaction, { backgroundColor: theme.cardBackground }]}>
      <Text style={[styles.transactionTitle, { color: theme.text }]}>{item.title}</Text>
      <Text style={item.amount > 0 ? styles.income : styles.expense}>
        {item.amount > 0 ? `+ R$${item.amount}` : `- R$${Math.abs(item.amount)}`}
      </Text>
      <Text style={styles.transactionDate}>{item.date}</Text>
    </TouchableOpacity>
  );
};

export default function HomeScreen() {
  const { logout, isAuthenticated } = useContext(AuthContext);
  const { theme } = useTheme();

  return (
    <View style={[styles.container, { backgroundColor: theme.background }]}>
      <NetworkStatus />
      <Text style={[styles.balanceTitle, { color: theme.text }]}>Saldo Atual</Text>
      <Text style={[styles.balance, { color: theme.success }]}>R$ 2.050,00</Text>

      <Text style={[styles.transactionsTitle, { color: theme.text }]}>Últimas Transações</Text>
      <FlatList
        data={mockTransactions}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => <TransactionItem item={item} />}
        showsVerticalScrollIndicator={false}
      />

      {isAuthenticated && (
        <Button
          title="Logout"
          onPress={logout}
          color={theme.primary}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  balanceTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 8,
  },
  balance: {
    fontSize: 32,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 16,
  },
  transactionsTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 8,
  },
  transaction: {
    padding: 16,
    borderRadius: 8,
    marginBottom: 10,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 2 },
    elevation: 2,
  },
  transactionTitle: {
    fontSize: 18,
    fontWeight: '600',
  },
  income: {
    fontSize: 18,
    color: '#4caf50',
  },
  expense: {
    fontSize: 18,
    color: '#f44336',
  },
  transactionDate: {
    fontSize: 14,
    color: '#888',
    marginTop: 4,
  },
});
