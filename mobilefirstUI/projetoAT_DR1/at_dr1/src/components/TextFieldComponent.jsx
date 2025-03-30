import React from 'react';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Stack from '@mui/material/Stack';

const TextFieldComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <Stack spacing={2}>
        <TextField label="Outlined" variant="outlined" />
        <TextField label="Filled" variant="filled" />
        <TextField label="Standard" variant="standard" />
      </Stack>
    </Container>
  );
};

export default TextFieldComponent;
