package org.mountcloud.mcplugin.bag;

import org.bukkit.command.PluginCommand;
import org.mountcloud.mcplugin.bag.command.HelpCommand;
import org.mountcloud.mcplugin.bag.command.InfoCommand;
import org.mountcloud.mcplugin.bag.command.OpenCommand;
import org.mountcloud.mcplugin.bag.command.PayCommand;
import org.mountcloud.mcplugin.bag.config.DefaultConfig;
import org.mountcloud.mcplugin.bag.listener.BagListener;
import org.mountcloud.mcplugin.bag.service.BagCommandService;
import org.mountcloud.mcplugin.common.BasePlugin;
import org.mountcloud.mcplugin.common.config.BaseLanguageConfig;

/**
 * 背包插件
 */
public class BagPluginMain extends BasePlugin {

    private static BagPluginMain bagPluginMain;

    @Override
    public boolean enable() {
        bagPluginMain = this;
        getBaseLogService().info("init config");
        initConfig();
        getBaseLogService().info("init command");
        initCommand();
        getBaseLogService().info("init listener");
        initListener();
        return true;
    }


    private void initConfig(){
        BaseLanguageConfig languageConfig = new BaseLanguageConfig(this);
        getBaseConfigService().registerConfig(languageConfig);

        DefaultConfig defaultConfig = new DefaultConfig(this);
        getBaseConfigService().registerConfig(defaultConfig);
    }

    private void initCommand(){
        BagCommandService bagCommandService = BagCommandService.getInstance();

        HelpCommand helpCommand = new HelpCommand();
        bagCommandService.registerCommand(helpCommand);
        OpenCommand openCommand = new OpenCommand();
        bagCommandService.registerCommand(openCommand);
        InfoCommand infoCommand = new InfoCommand();
        bagCommandService.registerCommand(infoCommand);
        PayCommand payCommand = new PayCommand();
        bagCommandService.registerCommand(payCommand);

        this.registerCommand(bagCommandService);
    }

    private void initListener(){
        BagListener bagListener = new BagListener();
        this.registerListener(bagListener);
    }
    public static BagPluginMain getInstance(){
        return bagPluginMain;
    }
}
