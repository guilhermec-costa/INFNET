import React from 'react';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Stack from '@mui/material/Stack';

const ButtonComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px', textAlign: 'center' }}>
      <Stack spacing={2} direction="row" justifyContent="center">
        <Button variant="contained" color="primary">
          Primary
        </Button>
        <Button variant="outlined" color="secondary">
          Secondary
        </Button>
        <Button variant="text" color="success">
          Success
        </Button>
      </Stack>
    </Container>
  );
};

export default ButtonComponent;
