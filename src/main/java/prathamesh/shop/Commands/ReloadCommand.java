package prathamesh.shop.Commands;

import prathamesh.shop.Main;
import org.bukkit.entity.Player;

public class ReloadCommand{

    private Main plugin;
    private Player player;

    public ReloadCommand(Main plugin, Player player) {
        this.player = player;
        this.plugin = plugin;
        plugin.getMenuManager().reloadMenus();
    }
}
