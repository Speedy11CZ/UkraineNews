plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("java")
}

group = "cz.speedy11"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.sps.pushover.net:pushover-client:1.0.0")
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("com.moandjiezana.toml:toml4j:0.7.2")
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("org.xerial:sqlite-jdbc:3.39.3.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "cz.speedy11.ukrainenewsnotifier.UkraineNewsNotifier"
    }
}