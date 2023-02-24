import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization") version "1.7.20"
}

group = "xyz.liusui"
version = "1.0"

repositories {
//    maven("https://maven.aliyun.com/repository/central")
//    maven("https://maven.aliyun.com/repository/public")
//    maven("https://maven.aliyun.com/repository/google")
//    maven("https://maven.aliyun.com/repository/gradle-plugin")
    mavenLocal()
    mavenCentral()

    google()
    jcenter()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("io.ktor:ktor-client-core-jvm:2.2.3")
                implementation("io.ktor:ktor-client-serialization-jvm:2.2.3")
                implementation("io.ktor:ktor-client-cio-jvm:2.2.3")
                implementation("io.ktor:ktor-client-content-negotiation-jvm:2.2.3")
                implementation("io.ktor:ktor-client-websockets-jvm:2.2.3")
                runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")
                implementation("org.xerial:sqlite-jdbc:3.41.0.0")
                implementation("io.ktor:ktor-client-auth:2.2.3")
                implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
            }
        }
        val jvmTest by getting

    }

}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "withQian"
            packageVersion = "1.0.0"
        }
    }
}
