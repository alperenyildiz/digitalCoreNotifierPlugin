plugins {
    id("java")    // Projenin Java kodlarını derleyebilmesi için temel Java desteği ekler
    id("org.jetbrains.intellij") version "1.17.2"    // IntelliJ plugin geliştirme araçlarını ekler, 1.17.2 bu araçların versiyonu
}

// Proje koordinatları
group = "com.ing"    // Maven koordinatlarında kullanılan grup ID'si,
version = "1.0-SNAPSHOT"     // Projenin versiyon numarası.

repositories {
    mavenCentral()    // Bağımlılıkların indirileceği merkezi Maven deposunu tanımlar
}

dependencies {
    // Test bağımlılıkları - JUnit 5 framework'ü kullanılıyor
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")    // Test yazabilmek için JUnit API'si
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")    // Testleri çalıştırabilmek için JUnit motoru
}

intellij {
    version.set("2023.3")           // Plugin'in hedeflediği IntelliJ IDEA versiyonu
    type.set("IC")                  // IC = Community Edition, IU = Ultimate Edition
    plugins.set(listOf("java"))     // Plugin'in ihtiyaç duyduğu IntelliJ modülleri, burada Java desteği ekleniyor
}

tasks {
    patchPluginXml {
        sinceBuild.set("231")       // Plugin'in çalışacağı minimum IntelliJ build numarası (2023.1)
        untilBuild.set("241.*")     // Plugin'in çalışacağı maximum IntelliJ build numarası (2024.1.x)
    }

    test {
        useJUnitPlatform()          // JUnit 5 test framework'ünü kullan
    }
}
