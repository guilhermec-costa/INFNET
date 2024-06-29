import React from 'react';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Container from '@mui/material/Container';

const FabComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <Fab color="primary" aria-label="add">
        <AddIcon />
      </Fab>
    </Container>
  );
};

export default FabComponent;
