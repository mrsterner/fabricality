package dev.sterner.fluid

import dev.sterner.Fabricality
import dev.sterner.client.FluidColorRegistry
import dev.sterner.client.FluidRendererRegistry
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.joml.Vector3d
import javax.swing.text.html.BlockView


class BaseFluid(override val name: String) : Fluid(), IFluid {
    fun color(tint: Int): BaseFluid {
        FluidColorRegistry.register(name, tint)
        return this
    }

    override fun toBlockState(state: FluidState?): BlockState? {
        val block: Block = BuiltInRegistries.BLOCK.get((typical as IFluid).id)
        if (block === Blocks.AIR) return Blocks.AIR.defaultBlockState()
        return BuiltInRegistries.BLOCK.get(id).defaultBlockState().setValue(LiquidBlock.LEVEL, getLevel(state))
    }

    override fun toBlockState(): BlockState? {
        return toBlockState(defaultFluidState());
    }

    override fun toBlock(state: FluidState?): Block {
        return toBlockState(defaultFluidState())!!.block
    }

    override fun toBlock(): Block {
        return toBlockState()!!.block
    }

    override fun getBucket(): Item {
        val id: ResourceLocation = Fabricality.id(this.name + "_bucket")
        if (BuiltInRegistries.ITEM.containsKey(id)) return BuiltInRegistries.ITEM.get(id)
        return Items.AIR
    }

    override fun canBeReplacedWith(state: FluidState,
                                   level: BlockGetter,
                                   pos: BlockPos,
                                   fluid: Fluid,
                                   direction: Direction): Boolean {
        return true
    }

    override fun getFlow(blockReader: BlockGetter, pos: BlockPos, fluidState: FluidState): Vec3 {
        return Vec3.ZERO
    }

    override fun getTickDelay(level: LevelReader): Int {
        return 0
    }

    override fun getExplosionResistance(): Float {
        return 0f
    }

    override fun getHeight(state: FluidState, level: BlockGetter, pos: BlockPos): Float {
        return 0f
    }

    override fun getOwnHeight(state: FluidState): Float {
        return 0f
    }

    override fun createLegacyBlock(state: FluidState): BlockState {
        TODO("Not yet implemented")
    }

    override fun isSource(state: FluidState?): Boolean {
        return true
    }

    override fun getAmount(state: FluidState): Int {
        TODO("Not yet implemented")
    }

    override fun getShape(state: FluidState, level: BlockGetter, pos: BlockPos): VoxelShape {
        TODO("Not yet implemented")
    }

    fun getLevel(state: FluidState?): Int {
        return 8
    }

    fun getShape(state: FluidState?, world: BlockView?, pos: BlockPos?): VoxelShape {
        return Shapes.empty()
    }

    override val id: ResourceLocation
        get() = Fabricality.id(this.name)

    override val flowing: Fluid
        get() = this.typical



    override fun setupRendering() {
        FluidRendererRegistry.register(this.name, this.textureName!!,
            typical,
            flowing,
            false)
    }

    override fun hasBucketItem(): Boolean {
        return true
    }

    override val typical: Fluid
        get() = this

}