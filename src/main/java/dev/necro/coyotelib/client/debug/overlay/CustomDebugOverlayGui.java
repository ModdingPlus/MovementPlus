package dev.necro.coyotelib.client.debug.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.overlay.DebugOverlayGui;

import java.util.ArrayList;
import java.util.List;

public class CustomDebugOverlayGui extends DebugOverlayGui {

    public DebugOverlayComponent MEMORY = new DebugOverlayComponent.Memory();
    public DebugOverlayComponent CPU = new DebugOverlayComponent.CPU();
    public DebugOverlayComponent DISPLAY = new DebugOverlayComponent.Display();
    public DebugOverlayComponent BLOCK = new DebugOverlayComponent.Block();
    public DebugOverlayComponent TILE_ENTITY = new DebugOverlayComponent.TileEntity();
    public DebugOverlayComponent FLUID = new DebugOverlayComponent.Fluid();
    public DebugOverlayComponent ENTITY = new DebugOverlayComponent.Entity();
    public DebugOverlayComponent ITEM_STACK = new DebugOverlayComponent.ItemStack();
    public DebugOverlayComponent SOUNDS = new DebugOverlayComponent.Sounds();

    protected Minecraft mc;

    public CustomDebugOverlayGui(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    @Override
    protected List<String> getDebugInfoRight(){
        ArrayList<String> ret = new ArrayList<>();
        combineDebugOverlayComponents(ret, MEMORY, CPU, DISPLAY, BLOCK, TILE_ENTITY, FLUID, ENTITY, ITEM_STACK);
        return ret;
    }

    private void combineDebugOverlayComponents(List<String> list, DebugOverlayComponent ...components){
        for(DebugOverlayComponent component: components){
            component.addInformation(list, this.mc, this.rayTraceBlock, this.rayTraceFluid);
            if(!list.isEmpty() && !list.get(list.size()-1).isEmpty()){
                list.add("");
            }
        }
    }
}
