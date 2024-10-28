import React from 'react';
import { View, StyleSheet } from 'react-native';
import { DataTable } from 'react-native-paper';

const TableComponent = () => {
	return (
		<View style={styles.container}>
			<DataTable>
				<DataTable.Header>
					<DataTable.Title>Nome</DataTable.Title>
					<DataTable.Title numeric>Idade</DataTable.Title>
					<DataTable.Title numeric>Cidade</DataTable.Title>
				</DataTable.Header>

				<DataTable.Row>
					<DataTable.Cell>Guilherme</DataTable.Cell>
					<DataTable.Cell numeric>25</DataTable.Cell>
					<DataTable.Cell numeric>Rio de Janeiro</DataTable.Cell>
				</DataTable.Row>

				<DataTable.Row>
					<DataTable.Cell>Ana</DataTable.Cell>
					<DataTable.Cell numeric>30</DataTable.Cell>
					<DataTable.Cell numeric>São Paulo</DataTable.Cell>
				</DataTable.Row>

				<DataTable.Row>
					<DataTable.Cell>João</DataTable.Cell>
					<DataTable.Cell numeric>28</DataTable.Cell>
					<DataTable.Cell numeric>Belo Horizonte</DataTable.Cell>
				</DataTable.Row>

				<DataTable.Pagination
					page={0}
					numberOfPages={3}
					onPageChange={(page) => console.log(page)}
					label="1-3 de 3"
				/>
			</DataTable>
		</View>
	);
};

const styles = StyleSheet.create({
	container: {
		padding: 16,
		width: "500px"
	},
});

export default TableComponent;
