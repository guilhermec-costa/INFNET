import React from 'react';
import Alert from '@mui/material/Alert';
import Container from '@mui/material/Container';

const AlertComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <Alert severity="success">This is a success alert — check it out!</Alert>
      <Alert severity="error">This is an error alert — check it out!</Alert>
      <Alert severity="warning">This is a warning alert — check it out!</Alert>
      <Alert severity="info">This is an info alert — check it out!</Alert>
    </Container>
  );
};

export default AlertComponent;
