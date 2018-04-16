# Android
-dontwarn android.test.**
-dontwarn android.support.test.**

# Hamcrest
-dontwarn org.hamcrest.**
-keep class org.hamcrest.** {
   *;
}

# Mockito
-dontwarn org.mockito.**
-keep class org.mockito.** {
   *;
}

# Byte Buddy
-dontwarn net.bytebuddy.**
-keep class net.bytebuddy.** {
   *;
}

# Junit
-keep class org.junit.** { *; }
-dontwarn org.junit.**
-keep class junit.** { *; }
-dontwarn junit.**

# JavaWriter
-dontwarn com.squareup.javawriter.JavaWriter

# Internal
-keep class sun.misc.** { *; }
-dontwarn sun.misc.**