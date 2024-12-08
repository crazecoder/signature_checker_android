import 'package:flutter/material.dart';
import 'package:signature_checker_android/signature_checker_android.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _signature = 'Unknown';
  String _result = 'Unknown';

  @override
  void initState() {
    super.initState();
    SignatureCheckerAndroid.getApkSignature().then((_signature) {
      setState(() {
        this._signature = _signature;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: <Widget>[
            Text('signature info: $_signature'),
            Text('result: $_result'),
            MaterialButton(
              onPressed: () {
                SignatureCheckerAndroid.verifyInstallerId().then((_b) {
                  setState(() {
                    this._result = "verifyInstallerId=$_b";
                  });
                });
              },
              child: Text("verifyInstallerId"),
            ),
            MaterialButton(
              onPressed: () {
                SignatureCheckerAndroid.verifySignature("478yYkKAQF+KST8y4ATKvHkYibo=").then((_b) {
                  setState(() {
                    this._result = "verifySignature=$_b";
                  });
                });
              },
              child: Text("verifySignature"),
            ),
            MaterialButton(
              onPressed: () {
                SignatureCheckerAndroid.verifyUnauthorizedApps().then((_b) {
                  setState(() {
                    this._result = "verifyUnauthorizedApps=$_b";
                  });
                });
              },
              child: Text("verifyUnauthorizedApps"),
            ),
            MaterialButton(
              onPressed: () {
                SignatureCheckerAndroid.verifyStores().then((_b) {
                  setState(() {
                    this._result = "verifyStores=$_b";
                  });
                });
              },
              child: Text("verifyStores"),
            ),
            MaterialButton(
              onPressed: () {
                SignatureCheckerAndroid.verifyDebug().then((_b) {
                  setState(() {
                    this._result = "verifyDebug=$_b";
                  });
                });
              },
              child: Text("verifyDebug"),
            ),
            MaterialButton(
              onPressed: () {
                SignatureCheckerAndroid.verifyEmulator().then((_b) {
                  setState(() {
                    this._result = "verifyEmulator=$_b";
                  });
                });
              },
              child: Text("verifyEmulator"),
            ),
          ],
        ),
      ),
    );
  }
}
