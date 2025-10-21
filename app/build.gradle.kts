plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.aplicacoesgraf"
    compileSdk = 34 // Mantemos o 34, que é estável para sua versão do Gradle

    defaultConfig {
        applicationId = "com.example.aplicacoesgraf"
        minSdk = 24
        targetSdk = 34 // É boa prática manter o targetSdk igual ao compileSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        // A configuração do Compose não é necessária para este projeto, mas não atrapalha
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --- VERSÕES CORRIGIDAS E COMPATÍVEIS COM SDK 34 ---
    implementation("androidx.core:core-ktx:1.9.0") // VERSÃO CORRIGIDA
    implementation("androidx.appcompat:appcompat:1.6.1") // VERSÃO CORRIGIDA
    implementation("com.google.android.material:material:1.10.0") // VERSÃO CORRIGIDA
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // VERSÃO CORRIGIDA

    // ViewModel e LiveData
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    // Biblioteca do Gráfico (está correta)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Dependências do Compose (mantidas como estavam, pois são um grupo separado)
    implementation("androidx.activity:activity-compose:1.8.0") // VERSÃO CORRIGIDA para alinhar
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Testes
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // VERSÃO CORRIGIDA
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // VERSÃO CORRIGIDA
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
