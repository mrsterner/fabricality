package dev.sterner.mixin;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.mojang.datafixers.util.Pair;
import dev.sterner.ModCompatHelper;
import dev.sterner.tweak.RecipeTweaks;
import dev.sterner.util.MechAndSmithCraft;
import ho.artisan.lib.recipe.impl.RecipeManagerImpl;
import net.fabricmc.fabric.api.event.registry.DynamicRegistryView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.impl.registry.sync.DynamicRegistriesImpl;
import net.fabricmc.fabric.impl.registry.sync.DynamicRegistryViewImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

@Mixin(RegistryDataLoader.class)
public class RegistryLoaderMixin {
    @Inject(
            method = "load",
            at = @At(
                    value = "HEAD"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void beforeLoad(ResourceManager resourceManager, RegistryAccess registryAccess, List<RegistryDataLoader.RegistryData<?>> registryData, CallbackInfoReturnable<RegistryAccess.Frozen> cir) {

        MechAndSmithCraft.registryAccess = registryAccess;
        RecipeTweaks.registryAccess = registryAccess;
        ModCompatHelper.registryAccess = registryAccess;
    }
}
