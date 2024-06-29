import React, { useState } from 'react';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import TextField from '../components/TextFieldComponent'; 
import Button from '../components/ButtonComponent'; 
import { Typography } from '@mui/material';

function FormPage() {
    const [formData, setFormData] = useState({
        nome: '',
        email: '',
        mensagem: '',
    });

    const handleChange = (event) => {
        setFormData({
            ...formData,
            [event.target.id]: event.target.value,
        });
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log('Dados do formulário:', formData);
    };

    return (
        <Grid container justifyContent="center">
            <Grid item xs={12} sm={8} md={6}>
                <Box mt={4}>
                    <Typography variant="h4" align="center" gutterBottom>
                        Formulário do Sistema
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <TextField
                            id="nome"
                            label="Nome"
                            value={formData.nome}
                            onChange={handleChange}
                            required
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            id="email"
                            label="Email"
                            type="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            fullWidth
                            margin="normal"
                        />
                        <TextField
                            id="mensagem"
                            label="Mensagem"
                            multiline
                            rows={4}
                            value={formData.mensagem}
                            onChange={handleChange}
                            required
                            fullWidth
                            margin="normal"
                        />
                        <Button
                            type="submit"
                            variant="contained"
                            color="primary"
                            fullWidth
                            size="large"
                            mt={2}
                        >
                            Enviar
                        </Button>
                    </form>
                </Box>
            </Grid>
        </Grid>
    );
}

export default FormPage;
