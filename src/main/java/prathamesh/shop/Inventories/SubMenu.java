package prathamesh.shop.Inventories;

import prathamesh.api.menu.ItemMenu;
import prathamesh.api.menu.action.ItemClickAction;
import prathamesh.api.menu.item.action.ActionItem;
import prathamesh.api.menu.item.action.ItemAction;
import prathamesh.api.menu.item.action.ItemActionPriority;
import prathamesh.api.menu.item.action.back.BackActionItem;
import prathamesh.api.menu.item.action.close.CloseMenuActionItem;
import prathamesh.api.menu.size.ItemMenuSize;
import prathamesh.shop.Main;
import prathamesh.shop.Utils.ChatUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class SubMenu {

    private final Main plugin;
    private ItemMenu subMenu;
    private File file;
    private YamlConfiguration messageConfig;
    private int buyPrice;
    protected Economy eco;

    public SubMenu(Main plugin){
        this.plugin = plugin;
        loadSecondaryMenu();
        this.eco = plugin.getEconomy();
    }

    public void openSubMenu(Player player){subMenu.open(player);}

    private void loadSecondaryMenu(){
        subMenu = new ItemMenu("Select amount to buy", ItemMenuSize.SIX_LINE, null);
        addFillerAndButtons();
        subMenu.registerListener(plugin);
    }

    private void addFillerAndButtons(){
        subMenu.fillToAll(new ActionItem(" ", new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
        subMenu.setItem(48, new BackActionItem(ChatUtils.color("&eBack"), new ItemStack(Material.ARROW), new String[0]));
        subMenu.setItem(49, new CloseMenuActionItem(ChatUtils.color("&cClose"), new ItemStack(Material.BARRIER)));
    }

    public void addItems(ItemStack item, ItemMenu parent, YamlConfiguration config){
        this.buyPrice = getPrice(config, item);
        int slot = 20;
        int[] amount = {1,5,10,32,64};

        subMenu.setParent(parent);

        for (int a : amount){
            ItemStack itemStack = item.clone();
            itemStack.setAmount(a);
            ActionItem actionItem = new ActionItem(itemStack.getItemMeta().getDisplayName(), itemStack);
            actionItem.setLore(lore(buyPrice, a));
            actionItem.addAction(getItemAction(new ItemStack(itemStack.getType()), a));
            subMenu.setItem(slot, actionItem);
            slot++;
        }
    }

    private int getPrice(YamlConfiguration config, ItemStack item){
        String material = item.getType().toString();
        Set<String> items = Objects.requireNonNull(config.getConfigurationSection("items")).getKeys(false);

        for (String i : items){
            String materialString = config.getString("items."+i+".material");

            if (material.equalsIgnoreCase(materialString)){
                return config.getInt("items."+i+".buyPrice");
            }
        }
        return 0;
    }

    private ItemAction getItemAction(ItemStack item, int amount){
        return new ItemAction() {
            @Override
            public ItemActionPriority getPriority() {return ItemActionPriority.HIGH;}
            @Override
            public void onClick(ItemClickAction action) {
                if (action.getPlayer().getInventory().firstEmpty() == -1){
                    action.getPlayer().closeInventory();
                    action.getPlayer().sendTitle(" ", ChatUtils.color("&cInventory Full!"), 10, 30, 10);
                }
                else {
                    double balance = eco.getBalance(action.getPlayer());
                    if (balance >= (buyPrice*amount)){
                        eco.withdrawPlayer(action.getPlayer(), buyPrice*amount);
                        item.setAmount(amount);
                        action.getPlayer().getInventory().addItem(item);
                        action.getPlayer().sendMessage(getPurchaseSuccessMessage(item, amount));
                    }
                    else {
                        action.getPlayer().sendMessage(getMoneyMessage(buyPrice*amount, balance));
                    }
                }
            }
        };
    }

    private ArrayList<String> lore(int buyPrice, int amount){
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add("");
        loreList.add(ChatUtils.color("&7Cost"));
        loreList.add(ChatUtils.color("&6"+buyPrice*amount+" Coins"));
        loreList.add("");
        loreList.add(ChatUtils.color("&eClick to trade!"));
        return loreList;
    }

    private YamlConfiguration getMessageConfig(){
        this.file = new File(plugin.getDataFolder(), "messages.yml");
        this.messageConfig = YamlConfiguration.loadConfiguration(file);
        return messageConfig;
    }

    public void reloadMessageConfig(){
        this.file = new File(plugin.getDataFolder(), "messages.yml");
        this.messageConfig = YamlConfiguration.loadConfiguration(file);
    }

    private String getPurchaseSuccessMessage(ItemStack item, int amount){
        String msg = ChatUtils.color(Objects.requireNonNull(getMessageConfig().getString("buyMessage")));
        msg = msg.replace("%item%", item.getAmount()+" x "+item.getType().name());
        msg = msg.replace("%cost%", String.valueOf(buyPrice*amount));
        return msg;
    }

    private String getMoneyMessage(int price, double worth){
        String msg = ChatUtils.color(getMessageConfig().getString("getMoneyMessage"));
        msg = msg.replace("%needMoney%",String.valueOf(price-worth));
        return msg;
    }
}
