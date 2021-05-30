object Libraries {

    const val hiltVersion = "2.36"

    object Gradle {

        const val build = "com.android.tools.build:gradle:7.1.0-alpha01"
        const val kotlin = "gradle-plugin"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    }

    object External {

        const val permissions = "com.sagar:coroutinespermission:2.0.3"
    }

    object Android {

        const val core = "androidx.core:core-ktx:1.5.0"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0"
        const val material = "com.google.android.material:material:1.3.0"
    }

    object Compose {

        private const val version = Versions.compose

        const val ui = "androidx.compose.ui:ui:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val material = "androidx.compose.material:material:$version"
        const val runtime = "androidx.compose.runtime:runtime:$version"
        const val livedata = "androidx.compose.runtime:runtime-livedata:$version"
        const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha01"
    }

    object Hilt {

        const val core = "com.google.dagger:hilt-android:$hiltVersion"
        const val compiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val viewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
        const val navigation = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha02"
        const val androidx = "androidx.hilt:hilt-compiler:1.0.0"
    }

    object Lifecycle {

        private const val version = "2.3.1"

        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    }

    object Test {

        const val core = "junit:junit:4.13.2"
        const val junit = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }
}