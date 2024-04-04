package dev.sterner.data

import net.fabricmc.api.EnvType
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.loot.packs.VanillaBlockLoot
import net.minecraft.data.models.blockstates.BlockStateGenerator
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.storage.loot.BuiltInLootTables
import net.minecraft.world.level.storage.loot.LootTable
import org.jetbrains.annotations.ApiStatus
import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.Nullable
import pers.solid.brrp.v1.BRRPUtils
import pers.solid.brrp.v1.annotations.PreferredEnvironment
import pers.solid.brrp.v1.api.RuntimeResourcePack
import pers.solid.brrp.v1.generator.ItemResourceGenerator
import pers.solid.brrp.v1.generator.TextureRegistry
import pers.solid.brrp.v1.model.ModelJsonBuilder


interface FBlockResourceGenerator : ItemResourceGenerator {

    @Nullable
    fun getBaseBlock(): Block? {
        return null
    }

    @Contract(pure = true)
    fun getBlockId(): ResourceLocation {
        return BuiltInRegistries.BLOCK.getKey(this as Block)
    }

    @Contract(pure = true)
    override fun getItemId(): ResourceLocation? {
        if (this is Block) {
            val block = this as Block
            val item: Item = block.asItem()
            return if (item === Items.AIR) {
                if (BlockItem.BY_BLOCK.containsKey(block)) BuiltInRegistries.ITEM.getKey(BlockItem.BY_BLOCK[block]) else null
            } else {
                BuiltInRegistries.ITEM.getKey(item)
            }
        } else {
            return null
        }
    }

    fun getBlockModelId(): ResourceLocation {
        return getBlockId().brrp_prefixed("block/")
    }

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(pure = true)
    fun getTextureId(textureKey: TextureSlot): ResourceLocation {
        if (this is Block) {
            val thisBlock = this as Block
            val texture = TextureRegistry.getTexture(thisBlock, textureKey)
            if (texture != null) {
                return texture
            }

            val baseBlock = this.getBaseBlock()
            if (baseBlock != null) {
                return BRRPUtils.getTextureId(baseBlock, textureKey)
            }
        }

        return getBlockId().brrp_prefixed("block/")
    }

    fun getBlockStates(): BlockStateGenerator? {
        return null
    }

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(mutates = "param1")
    fun writeBlockStates(pack: RuntimeResourcePack) {
        val blockStates = this.getBlockStates()
        if (blockStates != null) {
            pack.addBlockState(this.getBlockId(), blockStates)
        }
    }

    @get:Contract(pure = true)
    @get:PreferredEnvironment(EnvType.CLIENT)
    val blockModel: ModelJsonBuilder?
        get() = null

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(mutates = "param1")
    fun writeBlockModel(pack: RuntimeResourcePack) {
        val model = this.blockModel
        if (model != null) {
            pack.addModel(this.getBlockModelId(), model)
        }
    }

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(pure = true)
    override fun getItemModelId(): ResourceLocation? {
        val itemId = this.itemId
        return if (itemId == null) null else itemId.brrp_prefixed("item/")
    }

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(pure = true)
    override fun getItemModel(): ModelJsonBuilder {
        return ModelJsonBuilder.create(this.getBlockModelId())
    }

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(mutates = "param1")
    override fun writeItemModel(pack: RuntimeResourcePack) {
        val itemModelId = this.itemModelId
        if (itemModelId != null) {
            val model = this.itemModel
            if (model != null) {
                pack.addModel(itemModelId, model)
            }
        }
    }

    @PreferredEnvironment(EnvType.CLIENT)
    @Contract(mutates = "param1")
    override fun writeAssets(pack: RuntimeResourcePack) {
        this.writeBlockStates(pack)
        this.writeBlockModel(pack)
        this.writeItemModel(pack)
    }

    @get:ApiStatus.NonExtendable
    @get:Contract(pure = true)
    val lootTableId: ResourceLocation
        get() = if (this is BlockBehaviour) {
            val thisBlock = this as BlockBehaviour
            thisBlock.getLootTable()
        } else {
            getBlockId().brrp_prefixed("blocks/")
        }

    @get:Contract(pure = true)
    val lootTableBuilder: LootTable.Builder?
        get() = VanillaBlockLoot().createSingleItemTable(this as ItemLike)

    @Contract(mutates = "param1")
    fun writeLootTable(pack: RuntimeResourcePack) {
        val lootTableId = this.lootTableId
        if (lootTableId != BuiltInLootTables.EMPTY) {
            val lootTable = this.lootTableBuilder
            if (lootTable != null) {
                pack.addLootTable(lootTableId, lootTable)
            }
        }
    }

    @get:Contract(pure = true)
    val stonecuttingRecipe: SingleItemRecipeBuilder?
        get() = null

    @Contract(pure = true)
    fun shouldWriteStonecuttingRecipe(): Boolean {
        return false
    }



    override fun writeRecipes(pack: RuntimeResourcePack) {
        val recipe = this.craftingRecipe
        if (recipe != null) {
            val recipeId = this.recipeId
            pack.addRecipeAndAdvancement(recipeId, recipe)
        }

        if (this.shouldWriteStonecuttingRecipe()) {
            val stonecuttingRecipe = this.stonecuttingRecipe
            if (stonecuttingRecipe != null) {
                pack.addRecipeAndAdvancement(this.stonecuttingRecipeId, stonecuttingRecipe)
            }
        }
    }



    @get:Contract(pure = true)
    val stonecuttingRecipeId: ResourceLocation
        get() = this.recipeId.brrp_suffixed("_from_stonecutting")

    override fun writeData(pack: RuntimeResourcePack) {
        this.writeLootTable(pack)
        this.writeRecipes(pack)
    }
}