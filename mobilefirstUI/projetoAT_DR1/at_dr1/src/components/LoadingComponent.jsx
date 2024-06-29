import React from 'react';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';

const LoadingComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    </Container>
  );
};

export default LoadingComponent;
