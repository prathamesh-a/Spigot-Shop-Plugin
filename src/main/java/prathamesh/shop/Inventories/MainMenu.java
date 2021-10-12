package prathamesh.shop.Inventories;

import prathamesh.api.menu.ItemMenu;
import prathamesh.api.menu.action.ItemClickAction;
import prathamesh.api.menu.item.action.ActionItem;
import prathamesh.api.menu.item.action.ItemAction;
import prathamesh.api.menu.item.action.ItemActionPriority;
import prathamesh.api.menu.item.action.close.CloseMenuActionItem;
import prathamesh.api.menu.size.ItemMenuSize;
import prathamesh.shop.Configuration.MenuConfigLoader;
import prathamesh.shop.Main;
import prathamesh.shop.Utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MainMenu {

    private final Main plugin;
    private File file;
    private YamlConfiguration config;
    private ItemMenu mainMenu;
    private Set<String> mainMenuItems;
    private MenuConfigLoader loader;

    public MainMenu(Main plugin){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.mainMenuItems = Objects.requireNonNull(config.getConfigurationSection("mainMenuItems")).getKeys(false);
        this.loader = new MenuConfigLoader(plugin);
        loadMainMenu();
    }

    private void loadMainMenu(){
        this.mainMenu = new ItemMenu(config.getString("shopMenuName"), ItemMenuSize.fitOf(config.getInt("shopMenuSize")), null);
        addFiller();
        for (String i : mainMenuItems){

            int slot = config.getInt("mainMenuItems."+i+".slot");
            String materialString = config.getString("mainMenuItems."+i+".material");
            Material material = Material.getMaterial(materialString);
            ActionItem item = new ActionItem(new ItemStack(material));

            item.setName(ChatUtils.color(config.getString("mainMenuItems."+i+".name")));
            item.setLore(getColoredLore(config.getStringList("mainMenuItems."+i+".lore")));
            mainMenu.setItem(slot, item);
            item.addAction(getItemClickAction(i));
        }
        mainMenu.registerListener(plugin);
        mainMenu.setItem(config.getInt("closeButton.slot"), new CloseMenuActionItem(ChatUtils.color(config.getString("closeButton.name")), new ItemStack(Material.getMaterial(config.getString("closeButton.material")))));
    }

    public void openMainMenu(Player player){mainMenu.open(player);}
    public ItemMenu getMainMenuInstance(){return mainMenu;}
    public void reloadMainMenu(){
        mainMenu.clear();
        this.file = new File(plugin.getDataFolder(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.mainMenuItems = Objects.requireNonNull(config.getConfigurationSection("mainMenuItems")).getKeys(false);
        loadMainMenu();
    }

    private ItemAction getItemClickAction(String i){
        return new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {return ItemActionPriority.NORMAL;}
            @Override
            public void onClick(ItemClickAction clickAction) {
                if (clickAction.getClickType().equals(ClickType.LEFT)){
                    String shopName = config.getString("mainMenuItems."+i+".shop");

                    switch (Objects.requireNonNull(shopName)){
                        case "builder":
                            plugin.getMenuManager().getBuilderMenu().open(clickAction.getPlayer(), clickAction.getMenu());
                            break;
                        case "farmer":
                            plugin.getMenuManager().getFarmerMenu().open(clickAction.getPlayer(), clickAction.getMenu());
                            break;
                        case "decorator":
                            plugin.getMenuManager().getDecoratorMenu().open(clickAction.getPlayer(), clickAction.getMenu());
                            break;
                        case "fisherman":
                            plugin.getMenuManager().getFishermanMenu().open(clickAction.getPlayer(), clickAction.getMenu());
                            break;
                        case "miner":
                            plugin.getMenuManager().getMinerMenu().open(clickAction.getPlayer(), clickAction.getMenu());
                            break;
                        case "misc":
                            plugin.getMenuManager().getMiscMenu().open(clickAction.getPlayer(), clickAction.getMenu());
                            break;
                        default:
                            loader.loadFromConfig(shopName , clickAction.getPlayer(), clickAction.getMenu());
                            break;
                    }
                }
            }
        };
    }

    private void addFiller(){
        Material material = Material.getMaterial(config.getString("shopMenuFiller"));
        mainMenu.fillToAll(new ActionItem(" ", new ItemStack(material)));
    }

    private List<String> getColoredLore(List<String> list){
        List<String> newList = new ArrayList<>();
        for (String s : list){
            newList.add(ChatUtils.color(s));
        }
        return newList;
    }

}
