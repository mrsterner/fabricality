package dev.sterner.util

import net.minecraft.core.Direction
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape


object VoxelShapeMath {
    fun simpleBox(x1: Double, y1: Double, z1: Double, x2: Double, y2: Double, z2: Double): VoxelShape {
        val box: AABB = AABB(x1, y1, z1, x2, y2, z2)
        return Shapes.box(box.minX / 16.0,
            box.minY / 16.0,
            box.minZ / 16.0,
            box.maxX / 16.0,
            box.maxY / 16.0,
            box.maxZ / 16.0)
    }

    fun simpleBox(direction: Direction,
                  x1: Double,
                  y1: Double,
                  z1: Double,
                  x2: Double,
                  y2: Double,
                  z2: Double): VoxelShape {
        return when (direction) {
            Direction.NORTH -> simpleBox(x1, y1, z1, x2, y2, z2)
            Direction.WEST -> simpleBox(z1, y1, 16 - x1, z2, y2, 16 - x2)
            Direction.SOUTH -> simpleBox(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2)
            Direction.EAST -> simpleBox(16 - z1, y1, x1, 16 - z2, y2, x2)
            Direction.UP, Direction.DOWN -> throw UnsupportedOperationException("Unimplemented case: $direction")
        }
    }
}