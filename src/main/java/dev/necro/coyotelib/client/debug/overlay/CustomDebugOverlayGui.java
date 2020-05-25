package dev.necro.coyotelib.client.debug.overlay;

import dev.necro.coyotelib.api.debug.overlay.DebugOverlayTextComponent;
import dev.necro.coyotelib.client.debug.overlay.components.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.overlay.DebugOverlayGui;

import java.util.ArrayList;
import java.util.List;

public class CustomDebugOverlayGui extends DebugOverlayGui {

    public DebugOverlayTextComponent MEMORY = new MemoryDebugComponent();
    public DebugOverlayTextComponent CPU = new CPUDebugComponent();
    public DebugOverlayTextComponent DISPLAY = new DisplayDebugComponent();
    public DebugOverlayTextComponent BLOCK = new BlockDebugComponent();
    public DebugOverlayTextComponent TILE_ENTITY = new TileEntityDebugComponent();
    public DebugOverlayTextComponent FLUID = new FluidDebugComponent();
    public DebugOverlayTextComponent ENTITY = new EntityDebugComponent();
    public DebugOverlayTextComponent ITEM_STACK = new ItemStackDebugComponent();
    public DebugOverlayTextComponent SOUNDS = new SoundsDebugComponent();
    public DebugOverlayTextComponent SERVER_INFO = new ServerInfoDebugComponent();
    public DebugOverlayTextComponent MINECRAFT_VERSION = new MinecraftVersionDebugComponent();
    public DebugOverlayTextComponent POSITION = new PositionDebugComponent();
    public DebugOverlayTextComponent FACING = new FacingDebugComponent();
    public DebugOverlayTextComponent CLIENT_LIGHT = new ClientLightComponent();

    protected Minecraft mc;

    public CustomDebugOverlayGui(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    @Override
    protected List<String> getDebugInfoLeft() {
        ArrayList<String> ret = new ArrayList<>();
        combineDebugOverlayComponents(ret, MINECRAFT_VERSION, SERVER_INFO, POSITION, FACING, CLIENT_LIGHT);
        return ret;
    }

    @Override
    protected List<String> getDebugInfoRight(){
        ArrayList<String> ret = new ArrayList<>();
        combineDebugOverlayComponents(ret, MEMORY, CPU, DISPLAY, BLOCK, TILE_ENTITY, FLUID, ENTITY, ITEM_STACK);
        return ret;
    }

    private void combineDebugOverlayComponents(List<String> list, DebugOverlayTextComponent...components){
        for(DebugOverlayTextComponent component: components){
            component.addInformation(list, this.mc, this.rayTraceBlock, this.rayTraceFluid);
            if(!list.isEmpty() && !list.get(list.size()-1).isEmpty()){
                list.add("");
            }
        }
    }
}
