package dev.sterner.mixin.mods.industrial_revolution;

import kotlin.jvm.functions.Function0;
import me.steven.indrev.fluids.BaseFluid;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFluid.class)
public class MixinBaseFluid {

    @Mutable
    @Shadow @Final private int color;

    /**
     * Indrev is using the wrong color code for its fluids, so we switch r and b
     */
    @Inject(method = "<init>", at = @At("TAIL"))
    private void fabricality$invertColor(ResourceLocation identifier, Function0 block, Function0 bucketItem, int color, CallbackInfo ci){
        //this.color = ColorHelper.toVanillaColor(color);
    }
}
