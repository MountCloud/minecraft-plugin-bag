package org.mountcloud.mcplugin.bag.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.bag.config.DefaultConfig;
import org.mountcloud.mcplugin.bag.config.PlayerBagConfig;
import org.mountcloud.mcplugin.bag.language.LanguageEnum;
import org.mountcloud.mcplugin.bag.service.BagService;
import org.mountcloud.mcplugin.common.command.BaseCommand;
import org.mountcloud.mcplugin.common.command.BaseCommandSenderType;
import org.mountcloud.mcplugin.common.service.language.BaseLanguageService;

import java.util.Arrays;
import java.util.List;

public class OpenCommand extends BaseCommand {


    public OpenCommand() {
        super("open", BaseCommandSenderType.PLAYER, 0);
        setUsage(BagPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.COMMAND_USEAGE_OPEN.name(), LanguageEnum.COMMAND_USEAGE_OPEN.getValue()));
        setDefault(true);
    }

    @Override
    public void run(CommandSender sender, String[] strings, BaseCommandSenderType commandSenderType) {

        Player player = (Player) sender;
        String playerId = BagPluginMain.getInstance().getUserUniqueNumber(player);

        DefaultConfig defaultConfig = BagPluginMain.getInstance().getBaseConfigService().getConfig(DefaultConfig.configName);
        BaseLanguageService languageService =BagPluginMain.getInstance().getBaseLanguageService();
        String boxTitle = languageService.getLanguage(LanguageEnum.TEXT_BOX_TITME.name(),LanguageEnum.TEXT_BOX_TITME.getValue());

        Integer num = boxCount(BagService.getInstance().getLineNum(playerId));
        List<ItemStack> itemStacks = BagService.getInstance().getPlayerItemStack(playerId);

        Inventory shopInv = Bukkit.createInventory(null,num , boxTitle);

        for(int i=0;itemStacks!=null&&i<itemStacks.size();i++){
            ItemStack itemStack = itemStacks.get(i);
            if(itemStack!=null){
                shopInv.addItem(itemStack);
            }
        }

//        if(prefixShopItems!=null&&prefixShopItems.size()>0){
//            for(int i=0;i<prefixShopItems.size();i++){
//                DefaultConfig.PrefixShopItem item = prefixShopItems.get(i);
//
//                ItemStack itemStack = new ItemStack(Material.getMaterial(itemType), 1);
//                ItemMeta itemMeta = itemStack.getItemMeta();
//
//                String prefix = getColorStr(item.getPrefix());
//                itemMeta.setDisplayName(prefix);
//
//                itemMeta.setLocalizedName(DefaultConfig.localizedName);
//
//                String priceStr = PrefixShopPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.TEXT_PREICE.name(),LanguageEnum.TEXT_PREICE.getValue(),String.valueOf(item.getPrice()));
//                String putMsg = PrefixShopPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.TEXT_PUT_PREFIX.name(),LanguageEnum.TEXT_PUT_PREFIX.getValue());
//
//                priceStr = getColorStr(priceStr);
//                putMsg = getColorStr(putMsg);
//
//                itemMeta.setLore(Arrays.asList(priceStr,putMsg));
//                itemStack.setItemMeta(itemMeta);
//
//                shopInv.addItem(itemStack);
//            }
//        }

        player.openInventory(shopInv);

    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    /**
     * 转颜色字符串
     * @param str
     * @return
     */
    private String getColorStr(String str){
        return ChatColor.translateAlternateColorCodes('&',str);
    }

    private Integer boxCount(Integer lineNum){
        return lineNum * 9;
    }
}
