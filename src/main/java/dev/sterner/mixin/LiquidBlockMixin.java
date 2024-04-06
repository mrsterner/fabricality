package dev.sterner.mixin;

import dev.sterner.util.CobbleGenUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class LiquidBlockMixin {

    @Shadow @Final protected FlowingFluid fluid;

    @Inject(method = "shouldSpreadLiquid", at = @At("HEAD"), cancellable = true)
    private void cabfReceiveNeighborFluids(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.is(FluidTags.LAVA) && world.getBlockState(pos.relative(Direction.DOWN)).is(Blocks.BEDROCK)) {
            for (Direction direction : LiquidBlock.POSSIBLE_FLOW_DIRECTIONS) {
                FluidState targetState = world.getFluidState(pos.relative(direction));
                if (targetState.is(Fluids.WATER) || targetState.is(Fluids.FLOWING_WATER)) {
                    world.setBlockAndUpdate(pos, CobbleGenUtil.getBlock(world, pos));
                    world.levelEvent(1501, pos, 0);
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
