plugins {
    java
}



group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(15))
    }
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    testImplementation("com.google.guava:guava-testlib:31.1-jre")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    implementation ("io.github.microutils:kotlin-logging-jvm:2.0.11")
    testImplementation ("org.junit.platform:junit-platform-commons:1.5.2")

}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}