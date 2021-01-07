package dev.necro.coyotelib.client.debug.overlay;

import dev.necro.coyotelib.CoyoteLib;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.client.debug.overlay.components.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = CoyoteLib.MODID, value = Dist.CLIENT)
public class DebugOverlayTextComponents {

    @SubscribeEvent
    public static void registerComponents(RegistryEvent.Register<DebugOverlayTextComponent> event){
        IForgeRegistry<DebugOverlayTextComponent> registry = event.getRegistry();

        registry.register(new MinecraftVersionDebugComponent().setRegistryName("minecraft_version"));
        registry.register(new PerformanceDebugComponent().setRegistryName("performance"));
        registry.register(new RendererRendersDebugComponent().setRegistryName("renderer_renders"));
        registry.register(new RendererEntitiesDebugComponent().setRegistryName("renderer_entities"));
        registry.register(new ParticlesEntitiesCountDebugComponent().setRegistryName("particles_entities_count"));
        registry.register(new ChunkStatsDebugComponent().setRegistryName("chunk_stats"));
        registry.register(new ServerInfoDebugComponent().setRegistryName("server_info"));
        registry.register(new DimensionDebugComponent().setRegistryName("dimension"));
        registry.register(new PositionDebugComponent().setRegistryName("position"));
        registry.register(new FacingDebugComponent().setRegistryName("facing"));
        registry.register(new LightDebugComponent().setRegistryName("light"));
        registry.register(new HeightmapDebugComponent().setRegistryName("heightmap"));
        registry.register(new BiomeDebugComponent().setRegistryName("biome"));
        registry.register(new LocalDifficultyDebugComponent().setRegistryName("local_difficulty"));
        registry.register(new LookingAtBlockPositionDebugComponent().setRegistryName("looking_at_block"));
        registry.register(new LookingAtFluidPositionDebugComponent().setRegistryName("looking_at_fluid"));
        registry.register(new ShaderDebugComponent().setRegistryName("shader"));
        registry.register(new EntityCountsDebugComponent().setRegistryName("entity_counts"));
        registry.register(new SoundsDebugComponent().setRegistryName("sounds"));

        registry.register(new MemoryDebugComponent().setRegistryName("memory"));
        registry.register(new CPUDebugComponent().setRegistryName("cpu"));
        registry.register(new DisplayDebugComponent().setRegistryName("display"));
        registry.register(new BlockDebugComponent().setRegistryName("block"));
        registry.register(new TileEntityDebugComponent().setRegistryName("tile_entity"));
        registry.register(new FluidDebugComponent().setRegistryName("fluid"));
        registry.register(new EntityDebugComponent().setRegistryName("entity"));
        registry.register(new ItemStackDebugComponent().setRegistryName("item_stack"));

        registry.register(new PlayerDebugComponent().setRegistryName("player"));
        registry.register(new VitalParametersDebugComponent().setRegistryName("vital_parameters"));
        registry.register(new PotionEffectsDebugComponent().setRegistryName("potion_effects"));
        registry.register(new PlayingSoundsDebugComponent().setRegistryName("playing_sounds"));
        registry.register(new MidairJumpDebugComponent().setRegistryName("midair_jump"));

        registry.register(new SpacerDebugComponent().setRegistryName("spacer"));
        registry.register(new OptionalSpacerDebugComponent().setRegistryName("optional_spacer"));
    }
}
