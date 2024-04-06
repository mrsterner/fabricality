package dev.sterner.mixin.mods.applied_energistics;

import appeng.core.AppEngBase;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AppEngBase.class)
public class MixinAppEngBase {

    /**
     * Just qol, so I don't need to scroll so many tabs
     */
    @Inject(method = "registerCreativeTabs", at = @At(value = "INVOKE", target = "Lappeng/core/FacadeCreativeTab;init(Lnet/minecraft/core/Registry;)V"), cancellable = true)
    private void fabricality$removeFacadeTab(Registry<CreativeModeTab> registry, CallbackInfo ci) {
        ci.cancel();
    }
}
