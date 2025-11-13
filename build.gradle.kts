plugins {
    kotlin("jvm") version "2.0.0" // or your Kotlin version
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    // ✅ JUnit + Kotlin Test
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}

tasks.test {
    useJUnitPlatform()
}

application {
    // Optional: specify your main class if you have one
    mainClass.set("MainKt")
}