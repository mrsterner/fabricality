package dev.sterner.mixin;

import dev.sterner.util.CobbleGenUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.LavaFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LavaFluid.class)
public class LavaFluidMixin {

    @Redirect(method = "spreadTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    protected boolean setState(LevelAccessor instance, BlockPos pos, BlockState blockState, int i) {
        return instance.getBlockState(pos.relative(Direction.DOWN)).is(Blocks.BEDROCK)
                ? instance.setBlock(pos, CobbleGenUtil.getBlock(instance, pos), i)
                : instance.setBlock(pos, blockState, i);
    }
}
