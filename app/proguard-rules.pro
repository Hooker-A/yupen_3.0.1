# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\software\adt-bundle-windows-x86_64-20140702\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontpreverify

-repackageclasses ''

-allowaccessmodification

-optimizations !code/simplification/arithmetic

-keepattributes *Annotation*

-keep public class * extends android.app.Activity

-keep public class * extends android.app.Application

-keep public class * extends android.app.Service

-keep public class * extends android.content.BroadcastReceiver

-keep public class * extends android.content.ContentProvider

-keep public class * extends android.view.View {

    public <init>(android.content.Context);

    public <init>(android.content.Context, android.util.AttributeSet);

    public <init>(android.content.Context, android.util.AttributeSet, int);

    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {

    public <init>(android.content.Context, android.util.AttributeSet, int);

}

-keepclassmembers class * implements android.os.Parcelable {

    static android.os.Parcelable$Creator CREATOR;

}


-keepclassmembers class **.R$* {

    public static <fields>;

}

-keep public class * {
    public protected *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }



#ksoap2混淆
-ignorewarnings
-keep class org.kobjects.** { *; }
-keep class org.ksoap2.** { *; }
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }
#ksoap混淆

-keep class com.huaop2p.module.Base.entity.**{*;}
-keep class com.huaop2p.module.Crowdfunding.Entity.**{*;}
-keep class com.huaop2p.module.Settings.entity.**{*;}
-keep class com.huaop2p.module.CusWealth.entity.**{*;}


#微信
-keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}

-keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
#微信

#微博
-dontwarn android.net.http.**
-keep public class android.webkit.WebView {*;}
-keep public class android.webkit.WebViewClient {*;}
-keep class com.weibo.net.** {*;}
#微博


-dontwarn com.google.zxing.**
-keep class com.google.zxing.** {
    *;
}
-dontwarn com.sina.**
-keep class com.sina.** {
    *;
}
-dontwarn com.tencent.**
-keep class com.tencent.** {
    *;
}

#xutils
-dontwarn com.lidroid.xutils.**
-keep class com.lidroid.xutils.**{
*;
}
-keep class * extends java.lang.annotation.Annotation { *; }
-keep class com.google.gson.** { *; }
-keep class com.ibm.mqtt.**{*;}
-keepattributes EnclosingMethod
#-keepattributes Exceptions,InnerClasses,Deprecated,SourceFile,LineNumberTable,,EnclosingMethod
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#协议
-keep class com.huaop2p.**{*;}