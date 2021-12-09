# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile




#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类（如果应用程序引入的有jar包，并且想混淆jar包里面的class  ）
#-dontskipnonpubliclibraryclasses
#优化  不优化输入的类文件
-dontoptimize
#预校验（可去掉加快混淆速度）
-dontpreverify
#混淆时是否记录日志
-verbose
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#忽略警告
#-ignorewarning
#保护注解
-keepattributes *Annotation*


##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump proguard/class_files.txt
#未混淆的类和成员
-printseeds proguard/seeds.txt
#列出从 apk 中删除的代码
-printusage proguard/unused.txt
#混淆前后的映射
-printmapping proguard/mapping.txt
########记录生成的日志数据，gradle build时 在本项目根目录输出-end######

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable


#保持哪些类不被混淆,Activity 、Service ... 类的 子类
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService


#-libraryjars   libs/Android-support-v4.jar
# support v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
# support v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep public class android.support.v7.widget.** { *; }
-keep public class  android.support.v7.internal.widget.** { *; }
-keep public class  android.support.v7.internal.view.menu.** { *; }

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#  *****************自定义*************************** #
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#保持指定规则的方法不被混淆（Android layout 布局文件中为控件配置的onClick方法不能混淆）
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
#保持自定义控件指定规则的方法不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
#  *****************自定义 end*************************** #


#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep public class **.R$*{
   public static final int *;
}

#adapter也不能混淆
-keep public class * extends android.widget.Adapter {*;}
-keep public class * extends android.support.v7.widget.RecyclerView.Adapter {*;}

#避免混淆泛型 如果混淆报错建议关掉
#–keepattributes Signature

#移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用，另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
#-assumenosideeffects class android.util.Log {
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}



################################   第三方Library       #################################




####################### okhttp ################
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-dontwarn org.apache.**
-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**
-dontwarn rx.**
-dontwarn okio.**



###################### retrofit2 ##############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keep class retrofit2.adapter.rxjava.** { *; }
-keep interface retrofit2.adapter.rxjava.** { *; }
-keep class retrofit2.converter.gson.** { *; }
-keep interface retrofit2.converter.gson.** { *; }
-dontwarn retrofit2.Platform$Java8
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}



####################  rxjava  ################
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-dontnote rx.internal.util.PlatformDependent


#################### rxandroid  ###################
-keep class rx.android.** { *; }
-keep interface rx.android.** { *; }
-keep class rx.android.plugins.** { *; }
-keep interface rx.android.plugins.** { *; }
-keep class rx.android.schedulers.** { *; }
-keep interface rx.android.schedulers.** { *; }

####################  rxlifecycle  ####################
-keep class com.trello.rxlifecycle.** { *; }
-keep interface com.trello.rxlifecycle.** { *; }
-keep class com.trello.rxlifecycle.internal.** { *; }
-keep interface com.trello.rxlifecycle.internal.** { *; }
-keep class com.trello.rxlifecycle.android.** { *; }
-keep interface com.trello.rxlifecycle.android.** { *; }
-keep class com.trello.rxlifecycle.components.** { *; }
-keep interface com.trello.rxlifecycle.components.** { *; }
-keep class com.trello.rxlifecycle.components.support.** { *; }
-keep interface com.trello.rxlifecycle.components.support.** { *; }

####################  rxpermissions  ####################
-keep class com.tbruyelle.rxpermissions.** { *; }
-keep interface com.tbruyelle.rxpermissions.** { *; }

####################  mvp  #####################
-keepattributes Signature
-keepattributes *Annotation*
-keep class cn.droidlover.xdroidmvp.** { *; }
-keep interface cn.droidlover.xdroidmvp.** { *; }
-dontwarn cn.droidlover.xdroidmvp.**

#
########################  eventbus #######################
#
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}
#-keepclassmembers class ** {
#    public void onEvent*(**);
#}
#
########################  eventbus END  #######################
#


#
#################  gson #####################
#-keep public class com.google.gson.**
#-keep public class com.google.gson.** { *;}
## Gson uses generic type information stored in a class file when working with fields. Proguard
## removes such information by default, so configure it to keep all of it.
#-keepattributes Signature
#-keepattributes *Annotation*
## Gson specific classes
##-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
## Application classes that will be serialized/deserialized over Gson
#################  gson END #####################

###################   fastjson ################
-keep public class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
    public <methods>;
}
-keep class com.alibaba.fastjson.** {
    public <methods>;
}

################# ucrop  ######################
#-dontwarn com.yalantis.ucrop**
#-keep class com.yalantis.ucrop** { *; }
#-keep interface com.yalantis.ucrop** { *; }
################# ucrop END  ######################

######################### webview相关 ####################
-dontwarn android.webkit**
# WebView(可选)
-keepclassmembers class * extends android.webkit.WebView {
   public *;
}
# WebView的复杂操作
-keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
     public void *(android.webkit.WebView,java.lang.String);
}
# 与JS交互
-keepattributes SetJavaScriptEnabled
-keepattributes JavascriptInterface
# 保留与JS交互接口 , API17+
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
########################## webview相关 end ######################

############################    glide    ################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
###########################    glide END  ################################

#################   本项目 #################
-keep class com.ysarch.vmall.domain.bean.**{ *; }
-keep class com.ysarch.vmall.domain.bean.*$*{*;}                             # 保持自己定义的内部类不被混淆

# 穿山甲
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}

# talkingData
-dontwarn com.tendcloud.tenddata.**
-keep class com.tendcloud.** {*;}
-keep public class com.tendcloud.tenddata.** { public protected *;}
-keepclassmembers class com.tendcloud.tenddata.**{
public void *(***);
}
-keep class com.talkingdata.sdk.TalkingDataSDK {public *;}
-keep class com.apptalkingdata.** {*;}

-keep class com.bun.** {*;}
-keep class com.asus.msa.** {*;}
-keep class com.heytap.openid.** {*;}
-keep class com.huawei.android.hms.pps.** {*;}
-keep class com.meizu.flyme.openidsdk.** {*;}
-keep class com.samsung.android.deviceidservice.** {*;}
-keep class com.zui.** {*;}
-keep class com.huawei.hms.ads.** {*; }
-keep interface com.huawei.hms.ads.** {*; }
-keepattributes *Annotation*
-keep @android.support.annotation.Keep class **{
@android.support.annotation.Keep <fields>;
@android.support.annotation.Keep <methods>;
}

# baidu map
#-keep class com.baidu.** {*;}
-keep class com.baidu.** {*;}
-keep class mapsdkvi.com.** {*;}
-dontwarn com.baidu.**


# oss
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**


# 环信
#环信客服
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**
-keep class com.hianalytics.android.** {*;}
-dontwarn com.github.mikephil.charting.data.**

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}