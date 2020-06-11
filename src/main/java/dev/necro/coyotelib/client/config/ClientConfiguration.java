package dev.necro.coyotelib.client.config;

import com.google.common.collect.Lists;
import dev.necro.coyotelib.client.debug.overlay.DebugOverlayTextComponentsRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ClientConfiguration {

    public static class Configuration {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> debugoverlay_left;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> debugoverlay_right;

        public Configuration(ForgeConfigSpec.Builder builder) {
            debugoverlay_left = builder
                    .comment("A list of components to be shown on the left-hand side of the CoyoteLib debug overlay.")
                    .defineList(
                            "debugoverlay.left",
                            Lists.newArrayList(
                                    "coyotelib:minecraft_version",
                                    "coyotelib:performance",
                                    "coyotelib:server_info",
                                    "coyotelib:renderer_renders",
                                    "coyotelib:renderer_entities",
                                    "coyotelib:particles_entities_count",
                                    "coyotelib:chunk_stats",
                                    "coyotelib:dimension",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:position",
                                    "coyotelib:facing",
                                    "coyotelib:light",
                                    "coyotelib:heightmap",
                                    "coyotelib:biome",
                                    "coyotelib:local_difficulty",
                                    "coyotelib:looking_at_block",
                                    "coyotelib:looking_at_fluid",
                                    "coyotelib:shader",
                                    "coyotelib:sounds"
                            ),
                            s -> s instanceof String && DebugOverlayTextComponentsRegistry.INSTANCE.containsKey(new ResourceLocation((String)s))
                    );

            debugoverlay_right = builder
                    .comment("A list of components to be shown on the right-hand side of the CoyoteLib debug overlay.")
                    .defineList(
                            "debugoverlay.right",
                            Lists.newArrayList(
                                    "coyotelib:memory",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:cpu",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:display",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:block",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:tile_entity",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:fluid",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:entity",
                                    "coyotelib:optional_spacer",
                                    "coyotelib:item_stack"
                            ),
                            s -> s instanceof String && DebugOverlayTextComponentsRegistry.INSTANCE.containsKey(new ResourceLocation((String)s))
                    );
        }
    }

    public static final Configuration CONFIGURATION;
    public static final ForgeConfigSpec CONFIGURATION_SPEC;
    static {
        final Pair<Configuration, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);
        CONFIGURATION_SPEC = specPair.getRight();
        CONFIGURATION = specPair.getLeft();
    }
}
