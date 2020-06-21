package org.mountcloud.mcplugin.bag.config;

import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.common.config.BaseConfig;

public class DefaultConfig extends BaseConfig {

    public static String configName = "config.yml";

    private Integer initLine;

    private Integer maxLine;

    private Integer lineSquareNum;

    private Integer unitPrice;

    public DefaultConfig(BagPluginMain plugin) {
        super(configName, plugin);
    }

    @Override
    protected boolean createConfig() {
        BagPluginMain.getInstance().getConfig();
        return true;
    }

    @Override
    public void loadConfig() {
        this.initLine = getConfig().getInt("bag.initLine");
        this.unitPrice = getConfig().getInt("bag.unitPrice");
        this.maxLine = getConfig().getInt("bag.maxLine");
        this.lineSquareNum = getConfig().getInt("bag.lineSquareNum");
    }

    public Integer getInitLine() {
        return initLine;
    }

    public Integer getMaxLine() {
        return maxLine;
    }

    public Integer getLineSquareNum() {
        return lineSquareNum;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }
}
