#import "SignatureCheckerAndroidPlugin.h"
#import <signature_checker_android/signature_checker_android-Swift.h>

@implementation SignatureCheckerAndroidPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftSignatureCheckerAndroidPlugin registerWithRegistrar:registrar];
}
@end
