import React, { useEffect, useState } from 'react';
import { Container, Typography, Box, Grid, Paper, Button } from '@mui/material';
import { fakeList } from '../utils/fakeList';
import { fakeCreate } from '../utils/fakeCreate';

const HomePage = () => {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    fakeList().then((data) => {
      setItems(data);
      setLoading(false);
    });
  }, []);

  const handleCreate = () => {
    fakeCreate('New Item', 'Description for new item').then((newItem) => {
      setItems((prevItems) => [...prevItems, newItem]);
    });
  };

  return (
    <Container maxWidth="lg" style={{ marginTop: '20px' }}>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4">Home Page</Typography>
        <Button variant="contained" color="primary" onClick={handleCreate}>
          Add New Item
        </Button>
      </Box>
      {loading ? (
        <Typography>Loading...</Typography>
      ) : (
        <Grid container spacing={4}>
          {items.map((item) => (
            <Grid item xs={12} sm={6} md={4} key={item.id}>
              <Paper style={{ padding: '20px' }}>
                <Typography variant="h6">{item.name}</Typography>
                <Typography>{item.description}</Typography>
              </Paper>
            </Grid>
          ))}
        </Grid>
      )}
    </Container>
  );
};

export default HomePage;
