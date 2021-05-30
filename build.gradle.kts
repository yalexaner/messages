buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }

    dependencies {
        classpath(Libraries.Gradle.build)
        classpath(kotlin(Libraries.Gradle.kotlin, version = Versions.kotlin))
        classpath(Libraries.Gradle.hilt)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}