package com.example.signature_checker_android;

import android.app.Activity;

import com.github.javiersantos.piracychecker.PiracyChecker;
import com.github.javiersantos.piracychecker.callbacks.AllowCallback;
import com.github.javiersantos.piracychecker.callbacks.PiracyCheckerCallback;
import com.github.javiersantos.piracychecker.enums.Display;
import com.github.javiersantos.piracychecker.enums.InstallerID;
import com.github.javiersantos.piracychecker.enums.PiracyCheckerError;
import com.github.javiersantos.piracychecker.enums.PirateApp;
import com.github.javiersantos.piracychecker.utils.LibraryUtilsKt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.List;

import io.flutter.Log;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * SignatureCheckerAndroidPlugin
 */
public class SignatureCheckerAndroidPlugin implements MethodCallHandler {
    private Activity activity;

    private SignatureCheckerAndroidPlugin(Activity activity) {
        this.activity = activity;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "crazecoder/signature_checker_android");
        channel.setMethodCallHandler(new SignatureCheckerAndroidPlugin(registrar.activity()));
    }

    @Override
    public void onMethodCall(MethodCall call, final Result result) {
        if (call.method.equals("getApkSignature")) {
            String signature = LibraryUtilsKt.getApkSignature(activity);
            result.success(signature);
        } else if (call.method.equals("verifySignature")) {
            String signature = call.argument("signature");
            new PiracyChecker(activity)
                    .enableSigningCertificate(signature)
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(@NotNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                            Log.e("SignatureCheckerAndroidPlugin",piracyCheckerError.toString());
                            if(pirateApp!=null)
                            Log.e("SignatureCheckerAndroidPlugin",pirateApp.getName()+":----:"+pirateApp.getType().name());
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

            new PiracyChecker(activity)
                    .enableInstallerId(installerIDS)
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(@NotNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyUnauthorizedApps")) {
            new PiracyChecker(activity)
                    .enableUnauthorizedAppsCheck()
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(@NotNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyStores")) {
            new PiracyChecker(activity)
                    .enableStoresCheck()
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(@NotNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyDebug")) {
            new PiracyChecker(activity)
                    .enableDebugCheck()
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(@NotNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else if (call.method.equals("verifyEmulator")) {
            new PiracyChecker(activity)
                    .enableEmulatorCheck(false)
                    .callback(new PiracyCheckerCallback() {
                        @Override
                        public void allow() {
                            result.success(true);
                        }

                        @Override
                        public void doNotAllow(@NotNull PiracyCheckerError piracyCheckerError, @Nullable PirateApp pirateApp) {
                            result.success(false);
                        }
                    })
                    .start();
        } else {
            result.notImplemented();
        }
    }
}
