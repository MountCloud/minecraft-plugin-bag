package org.mountcloud.mcplugin.bag.service;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mountcloud.mcplugin.bag.config.PlayerBagConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BagService {

    private static BagService bagService;

    public static BagService getInstance(){
        if(bagService==null){
            bagService = new BagService();
        }
        return bagService;
    }

    private Map<String,PlayerBagConfig> configMap = new HashMap<>();

    public List<ItemStack> getPlayerItemStack(String playerId){
        return  getPlayerBagConfig(playerId).getItemStackList();
    }

    public Integer getLineNum(String playerId){
        return getPlayerBagConfig(playerId).getLineNum();
    }

    public void setLineNum(String playerId,Integer line){
        PlayerBagConfig playerBagConfig = getPlayerBagConfig(playerId);
        playerBagConfig.setLineNum(line);
        playerBagConfig.saveConfig();
    }

    public void saveBag(String playerId,List<ItemStack> itemStacks){
        PlayerBagConfig playerBagConfig = getPlayerBagConfig(playerId);
        playerBagConfig.setItemStackList(itemStacks);
        playerBagConfig.saveConfig();
    }

    public PlayerBagConfig getPlayerBagConfig(String playerId){
        PlayerBagConfig bagConfig = configMap.get(playerId);
        if(bagConfig==null){
            bagConfig = new PlayerBagConfig(playerId);
            configMap.put(playerId,bagConfig);
        }
        return bagConfig;
    }

}
