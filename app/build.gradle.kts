plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id ("com.google.devtools.ksp")
}

android {
    namespace = "com.github.compose.chat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.compose.chat"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val key="708184285520-qd6ifuu7ocf99o4duqh0g6qmqc7dlvsj.apps.googleusercontent.com"
        val messageUrl="https://fcm.googleapis.com/v1/projects/fir-chat-63ac8/messages:send"
        debug {
            buildConfigField(
                "String",
                "ServerKey",
                "\"${key}\""
            )
            buildConfigField(
                "String",
                "MessageUrl",
                "\"${messageUrl}\""
            )
        }
        release {
            isMinifyEnabled = false
            buildConfigField(
                "String",
                "ServerKey",
                "\"${key}\""
            )
            buildConfigField(
                "String",
                "MessageUrl",
                "\"${messageUrl}\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packagingOptions.apply {
        resources {
            excludes.addAll(
                listOf(
                    "META-INF/DEPENDENCIES",
                    "META-INF/LICENSE",
                    "META-INF/LICENSE.txt",
                    "META-INF/license.txt",
                    "META-INF/NOTICE",
                    "META-INF/NOTICE.txt",
                    "META-INF/notice.txt",
                    "META-INF/ASL2.0",
                    "META-INF/INDEX.LIST"
                )
            )
        }
        jniLibs {
            useLegacyPackaging = false
        }
    }
    androidResources {
        noCompress.add("json")
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
    //////////////////////ROOM//////////////////////////////
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    ///////////////////////HILT////////////////////////////////
    val hiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    //////////////////////////COMPOSE///////////////////////////////////
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation ("com.airbnb.android:lottie-compose:6.3.0")
    /////////////////////////FIREBASE///////////////////////////////
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.23.0")

}