plugins {
    id("java")
}

group = "ch.lenglet"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    implementation(platform("org.mongodb:mongodb-driver-bom:5.6.1"))
    implementation("org.mongodb:mongodb-driver-sync")

    implementation("com.alibaba:fastjson:2.0.28")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:5.1.0")
    testImplementation("org.json:json:20250517")
}

tasks.test {
    useJUnitPlatform()
}