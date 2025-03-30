import React from 'react';
import { Appbar } from 'react-native-paper';

const AppBarComponent = ({ title, subtitle, onBackPress, onMorePress }) => {
  return (
    <Appbar.Header>
      {onBackPress && <Appbar.BackAction onPress={onBackPress} />}
      <Appbar.Content title={title} subtitle={subtitle} />
      {onMorePress && <Appbar.Action icon="dots-vertical" onPress={onMorePress} />}
    </Appbar.Header>
  );
};

export default AppBarComponent;