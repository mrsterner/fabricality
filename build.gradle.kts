import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

plugins {
	id("idea")
	id("com.github.johnrengelman.shadow") version "7.1.0"
	id("maven-publish")
	id("org.jetbrains.kotlin.jvm") version "1.9.23"
	id("fabric-loom") version "1.6-SNAPSHOT"
}

val minecraft_version: String by project
val loader_version: String by project
val fabric_version: String by project
val fabric_kotlin_version: String by project
val archives_base_name: String by project
val vs2_version: String by project
val parchment_version: String by project
val create_version: String by project
val port_lib_modules: String by project
val emi_version: String by project
val flywheel_version: String by project
val registrate_version: String by project
val forge_config_api_port_version: String by project
val heracles_version: String by project
val indrev_version: String by project
val sodium_version: String by project
val libgui_version: String by project
val teamreborn_energy_version: String by project
val magna_version: String by project
val indium_version: String by project
val extra_mod_integrations_version: String by project

version = project.property("mod_version") as String
group = project.property("maven_group") as String

repositories {
	maven(url = "https://api.modrinth.com/maven")
	maven(url = "https://maven.parchmentmc.org")
	maven(url = "https://mvn.devos.one/snapshots/")
	maven(url = "https://mvn.devos.one/releases/")
	maven(url = "https://maven.terraformersmc.com/")
	maven(url = "https://jitpack.io/")
	maven(url = "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	maven(url = "https://maven.tterrag.com/")
	maven(url = "https://maven.teamresourceful.com/repository/maven-public/")
	maven(url = "https://server.bbkr.space/artifactory/libs-release")
	maven(url = "https://maven.draylar.dev/releases")
	maven(url = "https://maven.valkyrienskies.org")
}

dependencies {
	minecraft("com.mojang:minecraft:${minecraft_version}")

	mappings(loom.layered {
			parchment("org.parchmentmc.data:parchment-${minecraft_version}:${parchment_version}@zip")
			officialMojangMappings()
	})

	modImplementation("net.fabricmc:fabric-loader:${loader_version}")

	modImplementation("net.fabricmc.fabric-api:fabric-api:${fabric_version}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${fabric_kotlin_version}")

	modImplementation("com.simibubi.create:create-fabric-${minecraft_version}:${create_version}")
	modImplementation("com.jozufozu.flywheel:flywheel-fabric-${minecraft_version}:${flywheel_version}")
	modImplementation("com.tterrag.registrate_fabric:Registrate:${registrate_version}")
	modApi("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:$forge_config_api_port_version")

	port_lib_modules.split(",").forEach { module ->
		modApi("io.github.fabricators_of_create.Porting-Lib:$module:${project.properties["port_lib_version"]}")
	}

	modApi("org.valkyrienskies:valkyrienskies-120-fabric:${vs2_version}")

	modCompileOnly("dev.emi:emi-fabric:${emi_version}:api")
	modLocalRuntime("dev.emi:emi-fabric:${emi_version}")
	modLocalRuntime("maven.modrinth:extra-mod-integrations:${extra_mod_integrations_version}")

	modImplementation("earth.terrarium.heracles:heracles-fabric-${minecraft_version}:${heracles_version}") {
		exclude("me.shedaniel")
	}

	modImplementation("dev.draylar:magna:${magna_version}")
	modImplementation("teamreborn:energy:${teamreborn_energy_version}")
	modImplementation("io.github.cottonmc:LibGui:${libgui_version}")
	modImplementation("maven.modrinth:industrial-rezolution:${indrev_version}")

	modLocalRuntime("maven.modrinth:sodium:${sodium_version}")
	modLocalRuntime("maven.modrinth:indium:${indium_version}")
}

java {
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

tasks.named<Copy>("processResources") {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand(mapOf("version" to project.version))
	}
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = "17"
	}
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_$archives_base_name" }
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	repositories {
	}
}