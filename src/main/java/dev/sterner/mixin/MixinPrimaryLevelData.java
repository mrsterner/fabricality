package dev.sterner.mixin;

import com.mojang.serialization.Lifecycle;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PrimaryLevelData.class)
public class MixinPrimaryLevelData {
    @Shadow
    @Final
    private Lifecycle worldGenSettingsLifecycle;

    /**
     * Disables experimental settings warning screen
     */
    @Inject(method = "worldGenSettingsLifecycle", at = @At("HEAD"), cancellable = true)
    private void fabricality$removeExperimentalWorldSettingScreen(CallbackInfoReturnable<Lifecycle> cir) {
        if (worldGenSettingsLifecycle == Lifecycle.experimental()) {
            cir.setReturnValue(Lifecycle.stable());
            cir.cancel();
        }
    }
}
