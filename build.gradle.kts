plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.0"
    id("org.jetbrains.dokka") version "1.9.20"
    `maven-publish`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val repoUser = project.properties["repoUser"] as String
val repoPassword = project.properties["repoPassword"] as String

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}


dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(kotlin("reflect"))
    compileOnly("dev.kord:kord-core:latest.release")
    compileOnly("org.slf4j:slf4j-simple:latest.release")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
    }


    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    create<Jar>("javadocJar") {
        archiveClassifier.set("javadoc")
        dependsOn("dokkaHtml")
        from("$buildDir/dokka/html")
    }

    jar { build() }

    shadowJar { build() }


    artifacts {
        publishing {
            repositories {
                maven {
                    val mavenUrl = "https://repo.blugon.kr/repository/maven-" +
                            if(project.version.toString().endsWith("SNAPSHOT")) "snapshots/"
                            else "releases/"
                    url = uri(mavenUrl)
                    credentials {
                        username = repoUser
                        password = repoPassword
                    }
                }
            }
            publications {
                create<MavenPublication>("maven") {
                    artifact("build/libs/${project.name}-${project.version}.jar") {
                        extension = "jar"
                    }
                }
            }
        }
    }
}

fun Jar.build() {
    archiveBaseName.set(project.name) //Project Name
    archiveFileName.set("${project.name}-${project.version}.jar") //Build File Name
    archiveVersion.set(project.version.toString()) //Version
    from(sourceSets["main"].output)
}