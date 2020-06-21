package org.mountcloud.mcplugin.bag.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.bag.language.LanguageEnum;
import org.mountcloud.mcplugin.bag.service.BagService;
import org.mountcloud.mcplugin.common.listener.BaseListener;

import java.util.Arrays;
import java.util.List;

public class BagListener extends BaseListener {

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent inventoryCloseEvent) {
        InventoryView inventoryView = inventoryCloseEvent.getView();String title = inventoryView.getTitle();
        String pluginTitle = BagPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.TEXT_BOX_TITME.name(), LanguageEnum.TEXT_BOX_TITME.getValue());
        if (!title.equals(pluginTitle)) {
            return;
        }
        Player player = (Player) inventoryCloseEvent.getPlayer();
        String playerId = BagPluginMain.getInstance().getUserUniqueNumber(player);

        Inventory inventory = inventoryView.getTopInventory();
        ItemStack[] itemStacks = inventory.getContents();
        //BagPluginMain.getInstance().getBaseLogService().info("item statcks length " + itemStacks.length);
        List<ItemStack> itemStackList = Arrays.asList(itemStacks);
        BagService.getInstance().saveBag(playerId,itemStackList);
    }

}