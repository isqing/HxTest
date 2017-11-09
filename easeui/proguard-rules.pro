# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\work\android_sdk\android-sdk-windows/tools/proguard/proguard-android.txt
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


-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
#保留support库
-keep class android.support.** {
    *;
}

##2D地图
#-keep class com.amap.api.maps2d.*{*;}
#-keep class com.amap.api.mapcore2d.*{*;}
#-keep class com.amap.api.interfaces.*{*;}
#-keep class com.autonavi.amap.mapcore2d.*{*;}
#
#
##定位
#-keep class com.amap.api.location.*{*;}
#-keep class com.amap.api.fence.*{*;}
#-keep class com.autonavi.aps.amapapi.model.*{*;}
#-keep class com.loc.*{*;}


 #定位
    -keep class com.amap.api.location.**{*;}
    -keep class com.amap.api.fence.**{*;}
    -keep class com.autonavi.aps.amapapi.model.**{*;}
   #2D地图
     -keep class com.amap.api.maps2d.**{*;}
     -keep class com.amap.api.mapcore2d.**{*;}

   # 搜索
     -keep   class com.amap.api.services.**{*;}
    -dontwarn com.amap.**
    -dontwarn com.amap.api.**
#    -dontwarn com.github.lzyzsd.jsbridge.**{*;}
#    -dontwarn comg.amap.api.mapcore2d.MapMessage
#    -keepclassmembers class fqcn.of.javascript.interface.for.webview {
#       public *;
#    }
#    -keep class com.hyphenate.** {*;}
#    -dontwarn  com.hyphenate.**
#    -keep class com.hyphenate.easeui.utils.EaseSmileUtils {*;}

#    -dontwarn com.squareup.okhttp3.**
#    -keep class com.squareup.okhttp3.** { *;}
#    -dontwarn okio.**
#
-dontwarn com.parse.**
-keep class com.parse.** { *; }

#环信部分混淆规则代码
-dontwarn  com.easemob.**
-keep class com.easemob.** {*;}
-keep class org.xmlpull.** {*;}
-keep class com.squareup.picasso.* {*;}
-keep class com.hyphenate.* {*;}
-keep class com.hyphenate.chat.** {*;}
-keep class org.jivesoftware.** {*;}
-keep class org.apache.** {*;}
#如果使用easeui库，需要这么写
-keep class com.hyphenate.easeui.utils.EaseSmileUtils {*;}
#2.0.9后加入语音通话功能，如需使用此功能的api，加入以下keep
-dontwarn ch.imvs.**
-dontwarn org.slf4j.**
-keep class org.ice4j.** {*;}
-keep class net.java.sip.** {*;}
-keep class org.webrtc.voiceengine.** {*;}
-keep class org.bitlet.** {*;}
-keep class org.slf4j.** {*;}
-keep class ch.imvs.** {*;}
-keep class com.easemob.** {*;}
#环信3.0
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.superrtc.** {*;}

-dontwarn android.webkit.WebView**
-keep public class android.webkit** { *; }

-dontwarn org.apache.http.conn.ssl.SSLSocketFactory**
-keep public class org.apache.http.conn.ssl** { *; }