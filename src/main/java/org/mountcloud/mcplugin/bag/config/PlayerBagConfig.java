package org.mountcloud.mcplugin.bag.config;

import org.bukkit.inventory.ItemStack;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.common.BasePlugin;
import org.mountcloud.mcplugin.common.config.BaseConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerBagConfig extends BaseConfig {
    private String fileName;
    private String playerId;

    private Integer lineNum;

    private List<ItemStack> itemStackList;

    public PlayerBagConfig(String uuid) {
        super("player/"+uuid+".yml", BagPluginMain.getInstance());
    }

    @Override
    protected boolean createConfig() {
        boolean state = false;

        if(!this.createNewConfigFile()) {
            return false;
        }
        DefaultConfig defaultConfig = BagPluginMain.getInstance().getBaseConfigService().getConfig(DefaultConfig.configName);
        getConfig().set("player.lineNum",defaultConfig.getInitLine());
        getConfig().set("player.itemStackList",new ArrayList<>());
        try {
            this.getConfig().save(getFile());
            state = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }

    @Override
    public void loadConfig() {
        this.lineNum = getConfig().getInt("player.lineNum");
        this.itemStackList = (List<ItemStack>) getConfig().getList("player.itemStackList");
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        getConfig().set("player.lineNum",lineNum);
        this.lineNum = lineNum;
    }

    public List<ItemStack> getItemStackList() {
        return itemStackList;
    }

    public void setItemStackList(List<ItemStack> itemStackList) {
        getConfig().set("player.itemStackList",itemStackList);
        this.itemStackList = itemStackList;
    }
}
