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
val create_steam_n_rails_version: String by project
val ad_astra_version: String by project
val ae2_version: String by project
val create_enchantment_industry_version: String by project
val cc_tweaked_version: String by project
val numismatic_overhaul_version: String by project
val spelunkery_version: String by project
val extended_cogwheels_version: String by project
val moonlight_version: String by project
val cardinal_components_api_version: String by project
val owo_lib_version: String by project
val create_dragon_lib_version: String by project
val resourcefullib_version: String by project
val resourcefulconfig_version: String by project
val brrp_version: String by project
val tab_binder_version: String by project
val recipe_in_programming_version: String by project
val diesel_gen_version: String by project

version = project.property("mod_version") as String
group = project.property("maven_group") as String

repositories {
	maven(url = "https://api.modrinth.com/maven")
	maven(url = "https://maven.parchmentmc.org")
	maven(url = "https://mvn.devos.one/snapshots/")
	maven(url = "https://mvn.devos.one/releases/")
	maven(url = "https://maven.terraformersmc.com/")
	maven(url = "https://jitpack.io/")
	maven(url = "https://maven.ladysnake.org/releases")
	maven(url = "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	maven(url = "https://maven.tterrag.com/")
	maven(url = "https://maven.teamresourceful.com/repository/maven-public/")
	maven(url = "https://server.bbkr.space/artifactory/libs-release")
	maven(url = "https://maven.draylar.dev/releases")
	maven(url = "https://maven.dragons.plus/releases")
	maven(url = "https://maven.dragons.plus/snapshots")
	maven(url = "https://maven.ithundxr.dev/snapshots")
	maven(url = "https://modmaven.dev/")
	maven(url = "https://squiddev.cc/maven/")
	maven(url =" https://raw.githubusercontent.com/SolidBlock-cn/mvn-repo/main")
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

	//modImplementation("com.railwayteam.railways:Steam_Rails-fabric-1.20.1:${create_steam_n_rails_version}"){isTransitive = false}
	//modImplementation("maven.modrinth:ad-astra:${ad_astra_version}")
	modImplementation("earth.terrarium.adastra:ad_astra-fabric-${minecraft_version}:${ad_astra_version}")
	modImplementation("com.teamresourceful.resourcefullib:resourcefullib-fabric-${minecraft_version}:${resourcefullib_version}")
	modImplementation("com.teamresourceful.resourcefulconfig:resourcefulconfig-fabric-${minecraft_version}:${resourcefulconfig_version}")
	//modImplementation("maven.modrinth:ae2:${ae2_version}")
	modImplementation("appeng:appliedenergistics2-fabric:${ae2_version}")
	modImplementation("maven.modrinth:create-enchantment-industry-fabric:${create_enchantment_industry_version}")
	modImplementation("maven.modrinth:create-diesel-generators-fabric:${diesel_gen_version}")
	modCompileOnly("cc.tweaked:cc-tweaked-${minecraft_version}-fabric-api:${cc_tweaked_version}")
	modRuntimeOnly("cc.tweaked:cc-tweaked-${minecraft_version}-fabric:${cc_tweaked_version}")
	modImplementation("maven.modrinth:numismatic-overhaul:${numismatic_overhaul_version}")
	modImplementation("maven.modrinth:spelunkery:${spelunkery_version}")
	modImplementation("maven.modrinth:moonlight:${moonlight_version}")
	modImplementation("maven.modrinth:extended-cogwheels:${extended_cogwheels_version}")
	modImplementation("maven.modrinth:owo-lib:${owo_lib_version}")
	modImplementation("plus.dragons.createdragonlib:create_dragon_lib-fabric-${minecraft_version}:${create_dragon_lib_version}")

	modImplementation("maven.modrinth:brrp:${brrp_version}")
	//modImplementation("com.github.DM-Earth:Tags-Binder:${tab_binder_version}")

	modApi("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${cardinal_components_api_version}")
	modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${cardinal_components_api_version}")
	modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-world:${cardinal_components_api_version}")


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

	modImplementation("com.github.HO-Artisan:RecipeInProgramming:${recipe_in_programming_version}")
}

loom {
	accessWidenerPath = file("src/main/resources/fabricality.accesswidener")
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