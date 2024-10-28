import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, ViewStyle, TextStyle, StyleProp } from 'react-native';

interface TabProps {
  tabs: { label: string; content: React.ReactNode }[];
  tabStyle?: StyleProp<ViewStyle>;
  labelStyle?: StyleProp<TextStyle>;
  activeTabStyle?: StyleProp<ViewStyle>;
  activeLabelStyle?: StyleProp<TextStyle>;
}

const Tab: React.FC<TabProps> = ({
  tabs,
  tabStyle,
  labelStyle,
  activeTabStyle,
  activeLabelStyle,
}) => {
  const [activeTab, setActiveTab] = useState(0);

  return (
    <View style={styles.container}>
      <View style={[styles.tabHeader, tabStyle]}>
        {tabs.map((tab, index) => (
          <TouchableOpacity
            key={index}
            style={[
              styles.tab,
              index === activeTab && [styles.activeTab, activeTabStyle],
            ]}
            onPress={() => setActiveTab(index)}
          >
            <Text
              style={[
                styles.label,
                index === activeTab && [styles.activeLabel, activeLabelStyle],
                labelStyle,
              ]}
            >
              {tab.label}
            </Text>
          </TouchableOpacity>
        ))}
      </View>
      <View style={styles.tabContent}>
        {tabs[activeTab]?.content}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  tabHeader: {
    flexDirection: 'row',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  tab: {
    flex: 1,
    padding: 16,
    alignItems: 'center',
  },
  activeTab: {
    borderBottomWidth: 2,
    borderBottomColor: '#3498db',
  },
  label: {
    fontSize: 16,
    color: '#333',
  },
  activeLabel: {
    fontWeight: 'bold',
    color: '#3498db',
  },
  tabContent: {
    flex: 1,
    padding: 16,
  },
});

export default Tab;
