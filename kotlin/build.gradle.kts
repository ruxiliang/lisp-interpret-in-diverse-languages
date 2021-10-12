import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
}

group = "me.okitasan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.antlr:antlr4-master:4.9.2")
	implementation("org.antlr:antlr4-runtime:4.9.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
	implementation("org.junit.jupiter:junit-jupiter:5.7.0")
	implementation("org.junit.jupiter:junit-jupiter:5.7.0")
	testImplementation(kotlin("test"))
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}