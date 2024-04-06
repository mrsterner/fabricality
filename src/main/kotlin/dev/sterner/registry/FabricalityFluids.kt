package dev.sterner.registry

import com.simibubi.create.AllTags
import com.tterrag.registrate.fabric.SimpleFlowableFluid
import com.tterrag.registrate.util.entry.FluidEntry
import dev.sterner.Fabricality
import dev.sterner.fluid.CreateAttributeHandler
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage.CombinedItemApiProvider
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.minecraft.core.registries.Registries
import net.minecraft.tags.FluidTags
import net.minecraft.world.item.Items


object FabricalityFluids {

    init {
        Fabricality.REGISTRATE.setCreativeTab(Fabricality.GENERAL_KEY)
    }

    val SOUL: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("soul")
        .lang("Soul")
        .tag(AllTags.forgeFluidTag("soul"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(1)
                .tickRate(50)
                .flowSpeed(6)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.soul",
                500,
                400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val RAW_LOGIC: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("raw_logic")
        .lang("Raw Logic")
        .tag(AllTags.forgeFluidTag("raw_logic"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(1)
                .tickRate(25)
                .flowSpeed(4)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.raw_logic",
                1000,
                1000)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val REDSTONE: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("redstone")
        .lang("Redstone")
        .tag(AllTags.forgeFluidTag("redstone"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.redstone",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val SKY_STONE: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("sky_stone")
        .lang("Sky Stone")
        .tag(AllTags.forgeFluidTag("sky_stone"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.sky_stone",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val RESIN: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("resin")
        .lang("Resin")
        .tag(AllTags.forgeFluidTag("resin"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.resin",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val COKE: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("coke")
        .lang("Coke")
        .tag(AllTags.forgeFluidTag("coke"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.coke",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val FINE_SAND: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("fine_sand")
        .lang("Fine Sand")
        .tag(AllTags.forgeFluidTag("fine_sand"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.fine_sand",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val WASTE: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("waste")
        .lang("Waste")
        .tag(AllTags.forgeFluidTag("waste"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.waste",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val MOLTEN_ZINC: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("molten_zinc")
        .lang("Molten Zinc")
        .tag(AllTags.forgeFluidTag("molten_zinc"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.molten_zinc",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val MOLTEN_DESH: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("molten_desh")
        .lang("Molten Desh")
        .tag(AllTags.forgeFluidTag("molten_desh"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.molten_desh",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val MOLTEN_OSTRUM: FluidEntry<SimpleFlowableFluid.Flowing> = Fabricality.REGISTRATE.standardFluid("molten_ostrum")
        .lang("Molten Ostrum")
        .tag(AllTags.forgeFluidTag("molten_ostrum"), FluidTags.WATER) // fabric: water tag controls physics
        .fluidProperties { p: SimpleFlowableFluid.Properties ->
            p.levelDecreasePerBlock(2)
                .tickRate(25)
                .flowSpeed(3)
                .blastResistance(100f)
        }
        .fluidAttributes {
            CreateAttributeHandler("block.fabricality.molten_ostrum",
                1500,
                1400)
        }
        .onRegisterAfter(Registries.ITEM
        ) { redstone: SimpleFlowableFluid.Flowing ->
            val source = redstone.source
            // transfer values
            FluidStorage.combinedItemApiProvider(source.bucket).register(
                CombinedItemApiProvider { context: ContainerItemContext? ->
                    FullItemFluidStorage(context,
                        { bucket: ItemVariant? ->
                            ItemVariant.of(Items.BUCKET)
                        },
                        FluidVariant.of(source),
                        FluidConstants.BUCKET)
                })
            FluidStorage.combinedItemApiProvider(Items.BUCKET)
                .register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        EmptyItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(source.bucket)
                            },
                            source,
                            FluidConstants.BUCKET)
                    })
        }
        .register()

    val MOLTEN_CALORITE: FluidEntry<SimpleFlowableFluid.Flowing> =
        Fabricality.REGISTRATE.standardFluid("molten_calorite")
            .lang("Molten Calorite")
            .tag(AllTags.forgeFluidTag("molten_calorite"), FluidTags.WATER) // fabric: water tag controls physics
            .fluidProperties { p: SimpleFlowableFluid.Properties ->
                p.levelDecreasePerBlock(2)
                    .tickRate(25)
                    .flowSpeed(3)
                    .blastResistance(100f)
            }
            .fluidAttributes {
                CreateAttributeHandler("block.fabricality.molten_calorite",
                    1500,
                    1400)
            }
            .onRegisterAfter(Registries.ITEM
            ) { redstone: SimpleFlowableFluid.Flowing ->
                val source = redstone.source
                // transfer values
                FluidStorage.combinedItemApiProvider(source.bucket).register(
                    CombinedItemApiProvider { context: ContainerItemContext? ->
                        FullItemFluidStorage(context,
                            { bucket: ItemVariant? ->
                                ItemVariant.of(Items.BUCKET)
                            },
                            FluidVariant.of(source),
                            FluidConstants.BUCKET)
                    })
                FluidStorage.combinedItemApiProvider(Items.BUCKET)
                    .register(
                        CombinedItemApiProvider { context: ContainerItemContext? ->
                            EmptyItemFluidStorage(context,
                                { bucket: ItemVariant? ->
                                    ItemVariant.of(source.bucket)
                                },
                                source,
                                FluidConstants.BUCKET)
                        })
            }
            .register()

    fun register() {}
}