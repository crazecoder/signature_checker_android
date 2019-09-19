import 'dart:async';

import 'package:flutter/services.dart';

class SignatureCheckerAndroid {
  static const MethodChannel _channel =
      const MethodChannel('crazecoder/signature_checker_android');

  static Future<String> getApkSignature() async {
    final String signature = await _channel.invokeMethod('getApkSignature');
    return signature;
  }

  ///If you only plan to distribute the app on a particular store this technique will block from installing the app using any another store.
  ///Supported stores: Google Play, Amazon App Store and Samsung Galaxy Apps.
  static Future<bool> verifyInstallerId(
      {List<String> installerIds = const []}) async {
    Map map = {
      "installerIds": installerIds,
    };
    return await _channel.invokeMethod('verifyInstallerId', map);
  }

  static Future<bool> verifySignature(String signature) async {
    Map map = {
      "signature": signature,
    };
    return await _channel.invokeMethod('verifySignature', map);
  }
  ///If you want to check if user has pirate apps installed, you can use this code.
  ///It will check for: Lucky Patcher, Uret Patcher, Freedom, CreeHack and HappyMod.
  ///pirate return false
  static Future<bool> verifyUnauthorizedApps() async {
    return await _channel.invokeMethod('verifyUnauthorizedApps');
  }

  static Future<bool> verifyStores() async {
    return await _channel.invokeMethod('verifyStores');
  }

  ///Debug return false
  static Future<bool> verifyDebug() async {
    return await _channel.invokeMethod('verifyDebug');
  }

  ///Emulator return false
  static Future<bool> verifyEmulator() async {
    return await _channel.invokeMethod('verifyEmulator');
  }
}
