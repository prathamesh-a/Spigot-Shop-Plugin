package prathamesh.shop.Inventories;

import prathamesh.shop.Inventories.Menus.*;
import prathamesh.shop.Inventories.Menus.*;
import prathamesh.shop.Main;

public class MenuManager {

    private final Main plugin;
    private FarmerMenu farmerMenu;
    private BuilderMenu builderMenu;
    private DecoratorMenu decoratorMenu;
    private FishermanMenu fishermanMenu;
    private MinerMenu minerMenu;
    private MiscMenu miscMenu;
    private MainMenu mainMenu;
    private SubMenu subMenu;

    public MenuManager(Main plugin){
        this.plugin = plugin;
        registerMenus();
    }

    private void registerMenus(){
        farmerMenu = new FarmerMenu(plugin);
        mainMenu = new MainMenu(plugin);
        subMenu = new SubMenu(plugin);
        builderMenu = new BuilderMenu(plugin);
        decoratorMenu = new DecoratorMenu(plugin);
        fishermanMenu = new FishermanMenu(plugin);
        minerMenu = new MinerMenu(plugin);
        miscMenu = new MiscMenu(plugin);
    }

    public FarmerMenu getFarmerMenu(){return farmerMenu;}
    public MainMenu getMainMenu(){return mainMenu;}
    public SubMenu getSubMenu(){return subMenu;}
    public BuilderMenu getBuilderMenu(){return builderMenu;}
    public DecoratorMenu getDecoratorMenu(){return decoratorMenu;}
    public FishermanMenu getFishermanMenu(){return fishermanMenu;}
    public MinerMenu getMinerMenu(){return minerMenu;}
    public MiscMenu getMiscMenu(){return miscMenu;}

    public void reloadMenus(){
        mainMenu.reloadMainMenu();
        builderMenu.reloadMenu();
        decoratorMenu.reloadMenu();
        farmerMenu.reloadMenu();
        fishermanMenu.reloadMenu();
        minerMenu.reloadMenu();
        miscMenu.reloadMenu();
        subMenu.reloadMessageConfig();
    }
}
