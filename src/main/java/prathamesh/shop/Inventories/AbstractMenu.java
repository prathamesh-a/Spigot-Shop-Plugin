package prathamesh.shop.Inventories;

import prathamesh.api.material.XMaterial;
import prathamesh.api.menu.Item;
import prathamesh.api.menu.ItemMenu;
import prathamesh.api.menu.action.ItemClickAction;
import prathamesh.api.menu.custom.book.BookItemMenu;
import prathamesh.api.menu.custom.book.item.AlternateBookPageActionItem;
import prathamesh.api.menu.item.action.ActionItem;
import prathamesh.api.menu.item.action.ItemAction;
import prathamesh.api.menu.item.action.ItemActionPriority;
import prathamesh.api.menu.size.ItemMenuSize;
import prathamesh.shop.Main;
import prathamesh.shop.Utils.ChatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class AbstractMenu {

    protected Main plugin;
    protected BookItemMenu menu;
    protected int buyPrice;
    protected File file;
    protected YamlConfiguration config;
    protected Set<String> items;
    protected String configName;
    Item FILLER = new ActionItem(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());

    public AbstractMenu(Main plugin, String configName){
        this.plugin = plugin;
        this.configName = configName;
        this.file = new File(this.plugin.getDataFolder() + File.separator + "Shops", configName);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.items = config.getConfigurationSection("items").getKeys(false);
        loadMenu();
    }

    private void loadMenu(){
        this.menu = new BookItemMenu(config.getString("title"), ItemMenuSize.SIX_LINE, BookItemMenu.DEFAULT_BUTTONS_BAR_SIZE, null);

        organize();

        for (String i : items){
            String materialString = config.getString("items."+i+".material");
            this.buyPrice = config.getInt("items."+i+".buyPrice");
            Material material = Material.getMaterial(materialString);
            ActionItem item = new ActionItem(new ItemStack(material));
            item.setName(ChatUtils.color("&f"+item.getIcon().getType().name()));
            item.setLore(lore());

            addBorder(i);
            item.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {return ItemActionPriority.NORMAL;}
                @Override
                public void onClick(ItemClickAction action) {
                        if (action.getClickType().equals(ClickType.LEFT)){
                            plugin.getMenuManager().getSubMenu().addItems(action.getClickedItem(), action.getMenu(), config);
                            plugin.getMenuManager().getSubMenu().openSubMenu(action.getPlayer());
                        }
                }
            });
            menu.addItem(item);
        }
        for (int i=0; i<9; i++){menu.addBarButton(FILLER);}

        if (items.size() >= 29){
            menu.setBarButton(3, new AlternateBookPageActionItem(ChatUtils.color("&ePrevious Page"), XMaterial.ARROW.parseItem(), ChatUtils.color("&7Go to previous page.")).setGoNext(false).setBookMenu(this.menu));
            menu.setBarButton(5, new AlternateBookPageActionItem(ChatUtils.color("&eNext Page"), XMaterial.ARROW.parseItem(), ChatUtils.color("&7Go to next page.")).setGoNext(true).setBookMenu(this.menu));
        }
        ActionItem mainMenuButton = new ActionItem(ChatUtils.color("&aMain Menu"), XMaterial.NETHER_STAR.parseItem(), ChatUtils.color("&7Go back to main menu."));
        mainMenuButton.addAction(new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {return ItemActionPriority.NORMAL;}
            @Override
            public void onClick(ItemClickAction action) {plugin.getMenuManager().getMainMenu().openMainMenu(action.getPlayer());}
        });
        menu.setBarButton(4, mainMenuButton);
        menu.registerListener(this.plugin);
    }

    public void open(Player player, @Nullable ItemMenu parent){
        this.menu.setParent(parent);
        this.menu.open(player);
    }

    public void reloadMenu(){
        menu.clear();
        this.file = new File(this.plugin.getDataFolder() + File.separator + "Shops", configName);
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.items = Objects.requireNonNull(config.getConfigurationSection("items")).getKeys(false);
        loadMenu();
    }

    private List<String> lore(){
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("");
        loreList.add(ChatUtils.color("&7Cost"));
        loreList.add(ChatUtils.color("&6"+buyPrice+" Coins"));
        loreList.add("");
        loreList.add(ChatUtils.color("&eClick to trade!"));
        return loreList;
    }

    private void organize(){
        for (int i=0; i<10; i++){
            this.menu.addItem(FILLER);
        }
    }

    private void addBorder(String i){
        switch (i){
            case "8":
            case "15":
            case "22":
            case "36":
            case "43":
            case "50":
            case "64":
            case "71":
            case "78":
            case "92":
            case "99":
            case "106":
            case "120":
            case "127":
            case "134":
            case "148":
            case "155":
            case "162":
                menu.addItem(FILLER);
                menu.addItem(FILLER);
                break;
            case "29":
            case "57":
            case "85":
            case "113":
            case "141":
            case "169":
                for (int i2=0; i2 < 11 ; i2++){
                    menu.addItem(FILLER);
                }
                break;
            default:
                break;
        }
    }
}
