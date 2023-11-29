import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetpack.compose)
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }
        withJava()
    }
    val osName = System.getProperty("os.name")
    val targetOs = when {
        osName == "Mac OS X" -> "macos"
        osName.startsWith("Win") -> "windows"
        osName.startsWith("Linux") -> "linux"
        else -> error("Unsupported OS: $osName")
    }

    val targetArch = when (val osArch = System.getProperty("os.arch")) {
        "x86_64", "amd64" -> "x64"
        "aarch64" -> "arm64"
        else -> error("Unsupported arch: $osArch")
    }

    val version = libs.versions.skiko.get()
    val target = "${targetOs}-${targetArch}"

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.common)
                implementation(compose.desktop.currentOs)
                implementation("org.jetbrains.skiko:skiko-awt-runtime-$target:$version")
                api(compose.preview)
                implementation("io.ultreia:bluecove:2.1.1")
                implementation(project(":lib:FilledSliderCompose:filled-slider-compose"))
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
            packageName = "STONE Manager"
            packageVersion = "1.0.0"
            macOS {
                iconFile.set(project.file("icon.icns"))
                installationPath = "/Applications/StoneManager"
                bundleID = "io.github.irack.stonemanager.desktop"
            }
            windows {
                iconFile.set(project.file("icon.ico"))
                dirChooser = true
                installationPath = "C:\\Program Files\\StoneManager"
                perUserInstall = true
            }
            linux {
                iconFile.set(project.file("icon.png"))
                installationPath = "/usr/local/bin/stonemanager"
            }
        }
    }
}
