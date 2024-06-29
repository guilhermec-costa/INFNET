import React from 'react';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import Container from '@mui/material/Container';

const GridComponent = () => {
  return (
    <Container maxWidth="lg" style={{ marginTop: '20px' }}>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6} md={4}>
          <Paper style={{ padding: '20px', textAlign: 'center' }}>Item 1</Paper>
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <Paper style={{ padding: '20px', textAlign: 'center' }}>Item 2</Paper>
        </Grid>
        <Grid item xs={12} sm={6} md={4}>
          <Paper style={{ padding: '20px', textAlign: 'center' }}>Item 3</Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default GridComponent;
