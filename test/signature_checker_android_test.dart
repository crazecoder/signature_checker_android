import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:signature_checker_android/signature_checker_android.dart';

void main() {
  const MethodChannel channel = MethodChannel('signature_checker_android');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getApkSignature', () async {
    expect(await SignatureCheckerAndroid.getApkSignature(), '42');
  });
}
