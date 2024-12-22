pluginManagement {
    repositories {
        gradlePluginPortal()    // Gradle plugin'lerinin indirileceği resmi Gradle plugin portalı
        mavenCentral()          // Ek plugin'lerin indirilebileceği Maven merkezi deposu
    }
}

// Projenin root (kök) ismi - IDE'de ve build çıktılarında bu isim kullanılır
rootProject.name = "digitalCoreNotifierPlugin"