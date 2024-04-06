package dev.sterner;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import ho.artisan.lib.recipe.api.RecipeLoadingEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ModCompatHelper {

    public static RegistryAccess registryAccess;

    public enum Entry {
        MC("minecraft"),

        C("c"),

        CREATE("create"),

        FAB(Fabricality.INSTANCE.getMODID()),

        INDREV("indrev"),

        AE2("ae2"),

        AD_ASTRA("ad_astra"),
        
        CC("computercraft");

        final String modid;

        Entry(String modid) {
            this.modid = modid;
        }

        public String modid() {
            return modid;
        }

        public boolean isLoaded() {
            return FabricLoader.getInstance().isModLoaded(modid());
        }

        public boolean checkContains(@Nullable ResourceLocation id) {
            return id != null && id.getNamespace().equals(modid());
        }

        public boolean checkContains(@NotNull Item item) {
            return checkContains(BuiltInRegistries.ITEM.getKey(item));
        }

        public boolean checkContains(RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler, @NotNull Recipe<?> recipe) {
            return handler.getRecipes().get(recipe.getType()).entrySet().stream()
                    .filter(entry -> entry.getValue().equals(recipe))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .map(this::checkContains)
                    .orElse(false);
        }

        public ResourceLocation id(String... path) {
            return new ResourceLocation(modid(), String.join("/", path));
        }

        public Item asItem(String... paths) {
            return BuiltInRegistries.ITEM.get(id(paths));
        }

        public TagKey<Item> asItemTag(String... paths) {
            return TagKey.create(Registries.ITEM, id(paths));
        }

        public ItemStack asStack(int count, String... paths) {
            return new ItemStack(asItem(paths), count);
        }

        public ItemStack asStack(String... paths) {
            return new ItemStack(asItem(paths), 1);
        }

        public Ingredient asIngredient(String... paths) {
            return Ingredient.of(asItem(paths));
        }

        public ProcessingOutput asProcessingOutput(String... paths) {
            return asProcessingOutput(1, paths);
        }

        public ProcessingOutput asProcessingOutput(float chance, String... paths) {
            return new ProcessingOutput(asStack(paths), chance);
        }

        public ProcessingOutput asProcessingOutput(float chance, int count, String... paths) {
            return new ProcessingOutput(asStack(count, paths), chance);
        }

        public Fluid asFluid(String... paths) {
            return BuiltInRegistries.FLUID.get(id(paths));
        }

        public Block asBlock(String... paths) {
            return BuiltInRegistries.BLOCK.get(id(paths));
        }

        public SoundEvent asSoundEvent(String... paths) {
            return BuiltInRegistries.SOUND_EVENT.get(id(paths));
        }

        public Predicate<Recipe<?>> predicateOutput(
                RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler,
                boolean containsCabf, int count, String... paths
        ) {
            return recipe -> recipe.getResultItem(registryAccess).equals(asStack(count, paths))
                    && (containsCabf || !FAB.checkContains(handler, recipe));
        }

        public Predicate<Recipe<?>> predicateOutput(
                RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler,
                boolean containsCabf, String... paths
        ) {
            return predicateOutput(handler, containsCabf, 1, paths);
        }

        public Predicate<Recipe<?>> predicateOutput(
                RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler,
                String... paths
        ) {
            return predicateOutput(handler, false, paths);
        }

        public static Predicate<Recipe<?>> predicateOutput(
                RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler,
                ItemStack stack
        ) {
            return recipe -> recipe.getResultItem(registryAccess).equals(stack);
        }

        public Predicate<Recipe<?>> predicateIngredient(
                RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler,
                boolean containsCabf, String... paths
        ) {
            return recipe -> recipe.getIngredients().stream().anyMatch(asIngredient(paths)::equals)
                    && (containsCabf || !FAB.checkContains(handler, recipe));
        }

        public Predicate<Recipe<?>> predicateIngredient(
                RecipeLoadingEvents.RemoveRecipesCallback.RecipeHandler handler,
                String... paths
        ) {
            return predicateIngredient(handler, false, paths);
        }

        public static Predicate<Recipe<?>> predicateIngredient(Item item) {
            return recipe -> recipe.getIngredients().stream().anyMatch(Ingredient.of(item)::equals);
        }
    }

    public enum Dependency {
        /*
        FTB_LIBRARY("ftblibrary", Text.translatable("mod.ftblibrary.name"),
                UrlUtil.generateCurseForgeModFileUrl("ftb-library-fabric", 4720055),
                false, false),

        FTB_QUESTS("ftbquests", Text.translatable("mod.ftbquests.name"),
                UrlUtil.generateCurseForgeModFileUrl("ftb-quests-fabric", 4774299),
                false, false),

        FTB_TEAMS("ftbteams", Text.translatable("mod.ftbteams.name"),
                UrlUtil.generateCurseForgeModFileUrl("ftb-teams-fabric", 4623115),
                false, false),

         */

		/* TODO: Waiting for Quests Additions
		QUESTS_ADDITIONS("questsadditions", Text.translatable("mod.questsadditions.name"),
				LinkUtil.generateCurseForgeModFileUrl("quests-additions-fabric", 4774299),
				false, false),

		 */
        ;

        final String modid;
        final Component name;
        @Nullable final URL url;
        final boolean required;
        final boolean isClient;

        Dependency(String id, Component name, String url, boolean required, boolean isClient) {
            this.modid = id;
            this.name = name;
            this.required = required;
            this.url = getUrl(url);
            this.isClient = isClient;
        }

        public String getModid() {
            return modid;
        }

        public Component getName() {
            return name;
        }

        public String getNameAsString() {
            return name.getString();
        }

        public boolean hasUrl() {
            return url != null;
        }

        @Nullable
        public URL getUrl() {
            return url;
        }

        public boolean isRequired() {
            return required;
        }

        public boolean isClient() {
            return isClient;
        }

        public boolean isLoaded() {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        public boolean matchesSide(boolean isServer) {
            return !isServer || !isClient;
        }

        private static URL getUrl(String spec) {
            try {
                return new URL(spec);
            } catch (MalformedURLException e) {
                return null;
            }
        }
    }

    public enum Conflict {
        ESSENTIAL("essential-loader");

        final String modid;

        Conflict(String modid) {
            this.modid = modid;
        }

        public String getModid() {
            return modid;
        }

        public boolean isLoaded() {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        public static boolean isAnyLoaded() {
            return Stream.of(values()).anyMatch(Conflict::isLoaded);
        }

        public static void checkAndExit() {
            if (isAnyLoaded())
                throw new RuntimeException();
        }
    }
}
