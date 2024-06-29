import React from 'react';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import Container from '@mui/material/Container';
import FormGroup from '@mui/material/FormGroup';

const CheckboxComponent = () => {
  return (
    <Container maxWidth="sm" style={{ marginTop: '20px' }}>
      <FormGroup>
        <FormControlLabel control={<Checkbox />} label="Option 1" />
        <FormControlLabel control={<Checkbox />} label="Option 2" />
        <FormControlLabel control={<Checkbox />} label="Option 3" />
      </FormGroup>
    </Container>
  );
};

export default CheckboxComponent;
