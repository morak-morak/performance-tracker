plugins {
    id("java")
    id("java-library")
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

group = "com.morak"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.6")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.7.6")
    implementation("org.springframework.boot:spring-boot-starter-aop:2.7.6")
    implementation("org.springframework.boot:spring-boot-starter-test:2.7.6")

    // logging
    implementation("io.github.microutils:kotlin-logging:1.7.6")

    testRuntimeOnly("com.h2database:h2:1.4.200")
}

tasks {
    test {
        useJUnitPlatform()
    }
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            groupId = "com.github.morak-morak"
            artifactId = "performance-tracker"
            version = "0.0.1"
        }
    }
}
