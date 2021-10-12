package prathamesh.shop.Commands;

import prathamesh.api.menu.ItemMenu;
import prathamesh.shop.Configuration.MenuConfigLoader;
import prathamesh.shop.Inventories.MenuManager;
import prathamesh.shop.Main;
import prathamesh.shop.Utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    private final Main plugin;
    MenuConfigLoader loader;

    public MainCommand(Main plugin) {
        this.plugin = plugin;
        this.loader = new MenuConfigLoader(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        MenuManager menuManager = plugin.getMenuManager();
        ItemMenu mainMenu = menuManager.getMainMenu().getMainMenuInstance();

        if (sender instanceof Player){
            Player player = (Player) sender;
            if (args.length == 0){plugin.getMenuManager().getMainMenu().openMainMenu((Player) sender);}
            else if (args.length == 1){

                if (args[0].equalsIgnoreCase("reload")){
                    if (player.hasPermission("shop.reload") || player.isOp()){
                        executeReload((Player) sender);
                    }
                    else {player.sendMessage(ChatUtils.color("&f(&c!&f) &cYou do not have permission to do this."));}
                }
                else if (args[0].equalsIgnoreCase("help")){sendHelpMessages((Player) sender);}
                else if (args[0].equalsIgnoreCase("farmer")){menuManager.getFarmerMenu().open((Player) sender, mainMenu);}
                else if (args[0].equalsIgnoreCase("builder")){menuManager.getBuilderMenu().open((Player) sender, mainMenu);}
                else if (args[0].equalsIgnoreCase("decorator")){menuManager.getDecoratorMenu().open((Player) sender, mainMenu);}
                else if (args[0].equalsIgnoreCase("fisherman")){menuManager.getFishermanMenu().open((Player) sender, mainMenu);}
                else if (args[0].equalsIgnoreCase("miner")){menuManager.getMinerMenu().open((Player) sender, mainMenu);}
                else if (args[0].equalsIgnoreCase("adventurer")){menuManager.getMiscMenu().open((Player) sender, mainMenu);}
                else if (args[0].equalsIgnoreCase("info")){sender.sendMessage(ChatUtils.color("&eShop &7vBETA-0.1.7"));}
                else {
                    loader.loadFromConfig(args[0], player, null);
                }
            }
        }
        else {
            plugin.getLogger().info("This command is executable only by player.");
        }
        return true;
    }

    private void sendHelpMessages(Player sender){
        sender.sendMessage(ChatUtils.color("&7==========  Shop &7&n=========="));
        sender.sendMessage("");
        sender.sendMessage(ChatUtils.color("  &e/shop reload &8- &freload the plugin."));
        sender.sendMessage(ChatUtils.color("  &e/shop info &8- &fversion of the plugin."));
        sender.sendMessage(ChatUtils.color("  &e/shop &8- &fopen the main menu."));
        sender.sendMessage(ChatUtils.color("  &e/shop <name> &8- &fopen custom shop"));
        sender.sendMessage("");
        sender.sendMessage(ChatUtils.color("&7=========================="));
    }

    private void executeReload(Player player){
        ReloadCommand reloadCommand = new ReloadCommand(plugin, player);
        player.sendMessage(ChatUtils.color("&eShop: &aplugin reloaded."));
    }
}
