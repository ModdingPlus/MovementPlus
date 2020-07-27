package dev.necro.coyotelib.client.debug.overlay;

import com.google.common.base.Strings;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.DataFixUtils;
import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.api.debug.overlay.IDebugOverlayScreen;
import dev.necro.coyotelib.client.config.ClientConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.overlay.DebugOverlayGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CustomDebugOverlayGui extends DebugOverlayGui implements IDebugOverlayScreen {

    protected Minecraft mc;
    protected World cachedIntegratedServerWorld = null;
    protected BlockPos cachedRenderViewBlockPos = null;
    protected Optional<BlockPos> cachedTargetedBlock;
    protected Optional<BlockPos> cachedTargetedFluid;
    protected Optional<Chunk> cachedChunk;
    protected Optional<Chunk> cachedServerChunk;
    protected FontRenderer fontRenderer;

    public CustomDebugOverlayGui(Minecraft mc) {
        super(mc);
        this.mc = mc;
        this.fontRenderer = mc.fontRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack) {
        ChunkPos chunkPos = new ChunkPos(this.getRenderViewBlockPos());
        if (!Objects.equals(this.chunkPos, chunkPos)) {
            this.chunkPos = chunkPos;
            this.resetChunk();
        }
        super.render(matrixStack);
        this.clearCachedFields();
    }

    @Override
    protected void renderDebugInfoLeft(MatrixStack matrixStack) {
        List<String> list = this.getDebugInfoLeft();
        int line_height = 9;

        for(int line = 0; line < list.size(); ++line) {
            String s = list.get(line);
            if (!Strings.isNullOrEmpty(s)) {
                int width = this.fontRenderer.getStringWidth(s);
                int y = 2 + line_height * line;
                fill(matrixStack,1, y - 1, 2 + width + 1, y + line_height - 1, -1873784752);
                this.fontRenderer.drawString(matrixStack, s, 2.0F, (float)y, 14737632);
            }
        }
    }

    @Override
    @Nonnull
    protected List<String> getDebugInfoLeft() {
        NonNullList<String> ret = NonNullList.create();
        combineDebugOverlayComponents(
                ret,
                ClientConfiguration.CONFIGURATION.debugoverlay_left.get()
                        .stream()
                        .map(s -> DebugOverlayTextComponentsRegistry.INSTANCE.getValue(new ResourceLocation(s)))
                        .toArray(DebugOverlayTextComponent[]::new)
        );
        return ret;
    }

    @Override
    @Nonnull
    protected List<String> getDebugInfoRight(){
        NonNullList<String> ret = NonNullList.create();
        combineDebugOverlayComponents(
                ret,
                ClientConfiguration.CONFIGURATION.debugoverlay_right.get()
                        .stream()
                        .map(s -> DebugOverlayTextComponentsRegistry.INSTANCE.getValue(new ResourceLocation(s)))
                        .toArray(DebugOverlayTextComponent[]::new)
        );
        return ret;
    }

    protected void combineDebugOverlayComponents(NonNullList<String> list, DebugOverlayTextComponent...components){
        for(DebugOverlayTextComponent component: components){
            component.addInformation(list, this.mc, this);
        }
    }

    @SuppressWarnings("OptionalAssignedToNull")
    protected void clearCachedFields(){
        this.cachedIntegratedServerWorld=null;
        this.cachedRenderViewBlockPos=null;
        this.cachedTargetedBlock=null;
        this.cachedTargetedFluid=null;
        this.cachedChunk=null;
        this.cachedServerChunk=null;
    }

    @Override
    public PlayerEntity getPlayerEntity() {
        return this.mc.player;
    }

    @Override
    public Entity getRenderViewEntity(){
        return this.mc.renderViewEntity;
    }

    @Override
    public BlockPos getRenderViewBlockPos(){
        if(this.cachedRenderViewBlockPos == null) {
            this.cachedRenderViewBlockPos = this.getRenderViewEntity().getPosition();
        }
        return this.cachedRenderViewBlockPos;
    }

    @Override
    public World getWorld() {
        return this.mc.world;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public World getIntegratedServerWorld() {
        if(this.cachedIntegratedServerWorld == null) {
            this.cachedIntegratedServerWorld = DataFixUtils.orElse(Optional.ofNullable(this.mc.getIntegratedServer())
                    .map((integratedServer) -> integratedServer.getWorld(this.mc.world.func_234923_W_())), this.mc.world); // @TODO: func_234923_W_ -> getRegistryKey ?
        }
        return this.cachedIntegratedServerWorld;
    }

    @Override
    public Minecraft getMinecraft(){
        return this.mc;
    }

    @Override
    public RayTraceResult getTargetedBlockRayTrace(){
        return this.rayTraceBlock;
    }

    @Override
    public RayTraceResult getTargetedFluidRayTrace(){
        return this.rayTraceFluid;
    }

    @Override
    @SuppressWarnings("OptionalAssignedToNull")
    public Optional<BlockPos> getTargetedBlock() {
        if(this.cachedTargetedBlock == null){
            RayTraceResult rayTraceResult = this.getTargetedBlockRayTrace();
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
                this.cachedTargetedBlock = Optional.of(((BlockRayTraceResult)rayTraceResult).getPos());
            else
                this.cachedTargetedBlock = Optional.empty();
        }
        return this.cachedTargetedBlock;
    }

    @Override
    @SuppressWarnings("OptionalAssignedToNull")
    public Optional<BlockPos> getTargetedFluid() {
        if(this.cachedTargetedFluid == null){
            RayTraceResult rayTraceResult = this.getTargetedFluidRayTrace();
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK)
                this.cachedTargetedBlock = Optional.of(((BlockRayTraceResult)rayTraceResult).getPos());
            else
                this.cachedTargetedBlock = Optional.empty();
        }
        return this.cachedTargetedBlock;
    }

    @Override
    @SuppressWarnings({"OptionalAssignedToNull", "ConstantConditions"})
    public Optional<Chunk> getChunkIfAvailable() {
        if(this.cachedChunk == null){
            Chunk chunk = this.getChunk();
            if(chunk!=null)
                this.cachedChunk = Optional.of(chunk);
            else
                this.cachedChunk = Optional.empty();
        }
        return this.cachedChunk;
    }

    @Override
    @SuppressWarnings("OptionalAssignedToNull")
    public Optional<Chunk> getServerChunkIfAvailable() {
        if(this.cachedServerChunk == null){
            Chunk serverChunk = this.getServerChunk();
            if(serverChunk!=null)
                this.cachedServerChunk = Optional.of(serverChunk);
            else
                this.cachedServerChunk = Optional.empty();
        }
        return this.cachedServerChunk;
    }
}
