package prathamesh.shop.Configuration;

import prathamesh.shop.Main;

import java.io.File;

public class ConfigLoader {

    private final Main plugin;

    public ConfigLoader(Main plugin){
        this.plugin = plugin;
    }

    public void loadConfigurations(){
        if (!new File(plugin.getDataFolder(), "config.yml").exists()){plugin.saveResource("config.yml", false);}
        if (!new File(plugin.getDataFolder(), "messages.yml").exists()){plugin.saveResource("messages.yml", false);}
        if (!new File(plugin.getDataFolder() + File.separator + "Shops", "Farmer.yml").exists()){plugin.saveResource("Shops" + File.separator + "Farmer.yml", false);}
        if (!new File(plugin.getDataFolder() + File.separator + "Shops", "Builder.yml").exists()){plugin.saveResource("Shops" + File.separator + "Builder.yml", false);}
        if (!new File(plugin.getDataFolder() + File.separator + "Shops", "Miner.yml").exists()){plugin.saveResource("Shops" + File.separator + "Miner.yml", false);}
        if (!new File(plugin.getDataFolder() + File.separator + "Shops", "Decorator.yml").exists()){plugin.saveResource("Shops" + File.separator + "Decorator.yml", false);}
        if (!new File(plugin.getDataFolder() + File.separator + "Shops", "Misc.yml").exists()){plugin.saveResource("Shops" + File.separator + "Misc.yml", false);}
        if (!new File(plugin.getDataFolder() + File.separator + "Shops", "Fisherman.yml").exists()){plugin.saveResource("Shops" + File.separator + "Fisherman.yml", false);}
    }
}
