package prathamesh.api.menu.item.voidaction;

import prathamesh.api.menu.Item;
import prathamesh.api.menu.action.ItemClickAction;
import org.bukkit.inventory.ItemStack;

public class VoidActionItem extends Item {

    public VoidActionItem(String name, ItemStack icon, String... lore) {
        super(name, icon, lore);
    }

    @Override
    public final void onClick(ItemClickAction action) {
        /* do nothing */
    }

}
