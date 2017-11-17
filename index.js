/* @flow */

import React, { Component } from 'react';
import { NativeModules } from 'react-native';
import { Platform } from 'react-native';

export default class KeepAwake extends Component {
  static isScreenLocked(locked) {
    if (Platform.OS === 'android') {
      return NativeModules.KCKeepAwake.isLockedScreen(locked)
    }
  }

  static activateAll() {
    if (Platform.OS === 'android') {
      NativeModules.KCKeepAwake.activateAll();
    }
  }

  static deactivateAll() {
    if (Platform.OS === 'android') {
      NativeModules.KCKeepAwake.deactivateAll();
    }
  }

  static activate() {
    NativeModules.KCKeepAwake.activate();
  }

  static deactivate() {
    NativeModules.KCKeepAwake.deactivate();
  }

  componentWillMount() {
    //KeepAwake.activate();
  }

  componentWillUnmount() {
   //KeepAwake.deactivate();
  }

  render() {
    return this.props.children || null;
  }
}
