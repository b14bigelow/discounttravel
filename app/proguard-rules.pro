# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\ysych\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
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
-libraryjars libs

-dontwarn com.sun.mail.**
-dontwarn javax.activation.**
-dontwarn org.apache.harmony.awt.**
-dontwarn android.support.**
-dontwarn com.google.code.**
-dontwarn retrofit.**
-dontwarn com.j256.**
-dontwarn com.squareup.**
-dontwarn com.github.bumptech.**
-dontwarn com.parse**

-keep class com.sun.mail.** {*;}
-keep class javax.activation.** {*;}
-keep class org.apache.harmony.awt.** {*;}
-keep class android.support.** {*;}
-keep class com.google.code.** {*;}
-keep class retrofit.** {*;}
-keep class com.j256.** {*;}
-keep class com.squareup.** {*;}
-keep class com.github.bumptech.** {*;}
-keep class com.parse** {*;}