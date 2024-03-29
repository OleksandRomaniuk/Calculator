plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
    }
}

version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
    implementation(project(":fsm"))
    implementation(project(":tahiti"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("com.google.guava:guava-testlib:31.1-jre")
    testImplementation("org.slf4j:slf4j-log4j12:2.0.0-alpha7")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}