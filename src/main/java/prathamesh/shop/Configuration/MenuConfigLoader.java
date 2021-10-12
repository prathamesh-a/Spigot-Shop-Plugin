package prathamesh.shop.Configuration;

import prathamesh.api.menu.ItemMenu;
import prathamesh.shop.Inventories.AbstractMenu;
import prathamesh.shop.Main;
import prathamesh.shop.Utils.ChatUtils;
import org.bukkit.entity.Player;

import java.io.File;

public class MenuConfigLoader {

    Main plugin;

    public MenuConfigLoader(Main plugin){
        this.plugin = plugin;
    }

    public void loadFromConfig(String config, Player player, ItemMenu parent){

        if (new File( plugin.getDataFolder() + File.separator + "Shops", config+".yml").exists()){
            AbstractMenu menu = new AbstractMenu(plugin, config+".yml");
            menu.open(player, parent);
        }
        else {
            player.sendMessage(ChatUtils.color("&eShop: &fUnknown shop name, use &a/shop help"));
        }

    }

}
