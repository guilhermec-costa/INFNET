import React, { useState, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import { items } from '../utils/fakeList';
import { TableContainer, Typography } from '@mui/material';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

function ListPage() {
    const [data, setData] = useState([]);

    useEffect(() => {
        setData(items);
    }, []);

    return (
        <Grid container justifyContent="center">
            <Grid item xs={12} sm={10} md={8}>
                <Box mt={4}>
                    <Typography variant="h4" align="center" gutterBottom>
                        Lista do Sistema
                    </Typography>
                    <TableContainer component={Paper}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>ID</TableCell>
                                    <TableCell>Nome</TableCell>
                                    <TableCell>Descrição</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {data.map(item => (
                                    <TableRow key={item.id}>
                                        <TableCell>{item.id}</TableCell>
                                        <TableCell>{item.name}</TableCell>
                                        <TableCell>{item.description}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            </Grid>
        </Grid>
    );
}

export default ListPage;
