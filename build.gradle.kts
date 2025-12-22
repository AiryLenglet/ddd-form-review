plugins {
    id("java")
    id("io.freefair.aspectj") version "9.1.0"
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

    implementation("org.aspectj:aspectjrt:1.9.25.1")
    compileOnly("org.aspectj:aspectjtools:1.9.25.1")

    implementation("com.alibaba:fastjson:2.0.28")

    implementation("org.slf4j:slf4j-simple:1.7.25")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("net.javacrumbs.json-unit:json-unit-assertj:5.1.0")
    testImplementation("org.json:json:20250517")
}

tasks.test {
    useJUnitPlatform()
}