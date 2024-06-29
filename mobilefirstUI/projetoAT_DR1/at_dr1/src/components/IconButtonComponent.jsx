import React from 'react';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';
import Container from '@mui/material/Container';

const IconButtonComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px', textAlign: 'center' }}>
      <IconButton aria-label="delete">
        <DeleteIcon />
      </IconButton>
    </Container>
  );
};

export default IconButtonComponent;
