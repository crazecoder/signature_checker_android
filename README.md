# signature_checker_android

A new flutter plugin project for check android app signature base on [PiracyChecker](https://github.com/javiersantos/PiracyChecker), It may help you verify the use of pirate apps.

## Getting Started
```yaml
dependencies:
  signature_checker_android:
    git:
      url: git://github.com/crazecoder/signature_checker_android.git
```
method support
```dart
SignatureCheckerAndroid.getApkSignature();
SignatureCheckerAndroid.verifyInstallerId();
SignatureCheckerAndroid.verifySignature();
SignatureCheckerAndroid.verifyUnauthorizedApps();
SignatureCheckerAndroid.verifyDebug();
SignatureCheckerAndroid.verifyEmulator();
```
