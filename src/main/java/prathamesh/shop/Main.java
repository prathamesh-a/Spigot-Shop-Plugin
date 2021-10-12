package prathamesh.shop;

import prathamesh.shop.Commands.MainCommand;
import prathamesh.shop.Configuration.ConfigLoader;
import prathamesh.shop.Inventories.MenuManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private MenuManager menuManager;
    private Economy economy;

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.YELLOW+"============= Shop =============");
        getLogger().info("");

        if (!isEconSet()){
            sendVaultMessages();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        plugin = this;
        ConfigLoader configLoader = new ConfigLoader(this);
        configLoader.loadConfigurations();
        getLogger().info(ChatColor.GREEN+"  Loaded Configurations.");
        menuManager = new MenuManager(this);
        getLogger().info(ChatColor.GREEN+"  Loaded Menus.");
        registerCommands();
        sendStartupMessages();
    }

    @Override
    public void onDisable() {}

    public static Main getPlugin(){return plugin;}
    public MenuManager getMenuManager(){return menuManager;}

    private void registerCommands(){
        getCommand("Shop").setExecutor(new MainCommand(this));
    }

    private boolean isEconSet(){
        if (Bukkit.getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public Economy getEconomy(){return economy;}

    private void sendStartupMessages() {
        getLogger().info(ChatColor.GREEN+"   Shop has been enabled.");
        getLogger().info(ChatColor.AQUA+"  Thank you for using this plugin :)");
        getLogger().info("");
        getLogger().info(ChatColor.YELLOW+"======================================");
    }

    private void sendVaultMessages() {
        getLogger().info(ChatColor.RED+"  Vault is required to run plugin.");
        getLogger().info(ChatColor.RED+"  Disabling the plugin now.");
        getLogger().info("  ");
        getLogger().info(ChatColor.YELLOW+ "======================================");
    }

}
