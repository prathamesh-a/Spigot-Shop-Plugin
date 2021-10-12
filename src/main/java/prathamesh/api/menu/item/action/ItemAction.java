package prathamesh.api.menu.item.action;

import prathamesh.api.menu.action.ItemClickAction;

public interface ItemAction {

    public ItemActionPriority getPriority();

    public void onClick(ItemClickAction action);

}
