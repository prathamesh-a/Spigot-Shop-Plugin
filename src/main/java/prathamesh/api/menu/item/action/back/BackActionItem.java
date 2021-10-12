package prathamesh.api.menu.item.action.back;

import prathamesh.api.menu.action.ItemClickAction;
import prathamesh.api.menu.item.action.ActionItem;
import prathamesh.api.menu.item.action.ItemAction;
import prathamesh.api.menu.item.action.ItemActionPriority;
import org.bukkit.inventory.ItemStack;

public class BackActionItem extends ActionItem {

    public BackActionItem(String name, ItemStack icon, String[] lore) {
        super(name, icon, lore);
        addAction(new ItemAction() {

            @Override
            public ItemActionPriority getPriority() {
                return ItemActionPriority.LOW;
            }

            @Override
            public void onClick(ItemClickAction action) {
                action.setGoBack(true);
            }
        });
    }

}
