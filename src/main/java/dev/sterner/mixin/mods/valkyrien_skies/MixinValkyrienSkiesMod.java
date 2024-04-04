package dev.sterner.mixin.mods.valkyrien_skies;

import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.valkyrienskies.mod.common.ValkyrienSkiesMod;

@Mixin(ValkyrienSkiesMod.class)
public class MixinValkyrienSkiesMod {

    @Inject(method = "createCreativeTab$lambda$2", at = @At("HEAD"), cancellable = true)
    private static void fabricality$removeVS2ItemGroup(CreativeModeTab.ItemDisplayParameters output, CreativeModeTab.Output par2, CallbackInfo ci){
        ci.cancel();
    }
}
