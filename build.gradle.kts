plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("jvm") version "1.4.10"
}

group = "com.tnt_man_inc"
version = "1.1.0"

application {
    mainClassName = "Main"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    jcenter()
}

dependencies {
    implementation("net.dv8tion:JDA:4.2.0_211")
    implementation(group = "com.konghq", name = "unirest-java", version = "3.11.02")
    implementation("com.jagrosh:jda-utilities:3.0.4")
    implementation(group = "net.oneandone.reflections8", name = "reflections8", version = "0.11.7")
    implementation(kotlin("stdlib", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))
    implementation("org.mongodb:mongodb-driver-sync:4.0.5")
    implementation("dev.minn:jda-ktx:0.1.0")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}