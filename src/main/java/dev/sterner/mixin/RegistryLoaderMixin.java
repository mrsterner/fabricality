package dev.sterner.mixin;

import dev.sterner.ModCompatHelper;
import dev.sterner.tweak.RecipeTweaks;
import dev.sterner.util.MechAndSmithCraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

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
