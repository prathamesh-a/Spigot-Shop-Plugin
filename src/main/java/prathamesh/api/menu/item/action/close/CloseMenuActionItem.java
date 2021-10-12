package prathamesh.api.menu.item.action.close;

import prathamesh.api.ReflectionUtils;
import prathamesh.api.menu.action.ItemClickAction;
import prathamesh.api.menu.item.action.ActionItem;
import prathamesh.api.menu.item.action.ItemAction;
import prathamesh.api.menu.item.action.ItemActionPriority;
import prathamesh.api.itemstack.safe.SafeItemStack;
import prathamesh.api.itemstack.stainedglass.StainedGlassColor;
import prathamesh.api.itemstack.stainedglass.StainedGlassItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CloseMenuActionItem extends ActionItem {

    @Deprecated // Compatibility with server versions <= 1.8
    public static final ItemStack DEFAULT_ICON = ReflectionUtils.getEnumConstant(Material.class, "BARRIER") != null
            ? new SafeItemStack(Material.BARRIER)
            : new StainedGlassItemStack(StainedGlassColor.RED, true);

    public CloseMenuActionItem(String... lore) {
        this(ChatColor.RED + "Close", lore);
    }

    public CloseMenuActionItem(String name, String... lore) {
        this(name, DEFAULT_ICON, lore);
    }

    public CloseMenuActionItem(String name, ItemStack icon, String... lore) {
        super(name, icon, lore);
        addAction(new ItemAction() {

            @Override
            public ItemActionPriority getPriority() {
                return ItemActionPriority.LOW;
            }

            @Override
            public void onClick(ItemClickAction action) {
                action.setClose(true);
            }
        });
    }

}
