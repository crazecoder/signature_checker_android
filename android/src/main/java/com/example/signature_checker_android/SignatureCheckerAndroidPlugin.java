package com.example.signature_checker_android;

import android.content.Context;

import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.callbacks.PiracyCheckerCallback;
import com.github.javiersantos.piracychecker.enums.InstallerID;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerError;
import com.github.javiersantos.piracychecker.enums.PirateApp;
import com.github.javiersantos.piracychecker.utils.LibraryUtilsKt;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.embedding.engine.plugins.FlutterPlugin;

import androidx.annotation.NonNull;

/**
 * SignatureCheckerAndroidPlugin
 */
public class SignatureCheckerAndroidPlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;
    private Context context;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        context = binding.getApplicationContext();
        channel = new MethodChannel(binding.getBinaryMessenger(), "crazecoder/signature_checker_android");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        if (call.method.equals("getApkSignature")) {
            String signature = LibraryUtilsKt.getApkSignatures(context)[0];
            result.success(signature);
        } else if (call.method.equals("verifySignature")) {
            String signature = call.argument("signature");
            new PiracyChecker(context)
                    .enableSigningCertificates(signature)
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyInstallerId")) {
            List<String> ids = call.argument("installerIds");
            List<InstallerID> installerIDList = new ArrayList<>();
            for (String id : ids) {
                installerIDList.add(InstallerID.valueOf(id));
            }
            installerIDList.add(InstallerID.GOOGLE_PLAY);
            installerIDList.add(InstallerID.GALAXY_APPS);
            installerIDList.add(InstallerID.AMAZON_APP_STORE);
            InstallerID[] installerIDS = new InstallerID[installerIDList.size()];
            installerIDList.toArray(installerIDS);

            new PiracyChecker(context)
                    .enableInstallerId(installerIDS)
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyUnauthorizedApps")) {
            new PiracyChecker(context)
                    .enableUnauthorizedAppsCheck()
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyStores")) {
            new PiracyChecker(context)
                    .enableStoresCheck()
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyDebug")) {
            new PiracyChecker(context)
                    .enableDebugCheck()
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyEmulator")) {
            new PiracyChecker(context)
                    .enableEmulatorCheck(false)
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(PiracyCheckerError piracyCheckerError, PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
