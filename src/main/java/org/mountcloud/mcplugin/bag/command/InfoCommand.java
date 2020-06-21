package org.mountcloud.mcplugin.bag.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.bag.config.DefaultConfig;
import org.mountcloud.mcplugin.bag.config.PlayerBagConfig;
import org.mountcloud.mcplugin.bag.language.LanguageEnum;
import org.mountcloud.mcplugin.bag.service.BagService;
import org.mountcloud.mcplugin.common.command.BaseCommand;
import org.mountcloud.mcplugin.common.command.BaseCommandSenderType;
import org.mountcloud.mcplugin.common.service.language.BaseLanguageService;

public class InfoCommand extends BaseCommand {
    public InfoCommand() {
        super("info", BaseCommandSenderType.PLAYER, 0);
        setUsage(BagPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.COMMAND_USEAGE_INFO.name(),LanguageEnum.COMMAND_USEAGE_INFO.getValue()));
    }

    @Override
    public void run(CommandSender sender, String[] strings, BaseCommandSenderType baseCommandSenderType) {
        Player player = (Player) sender;
        String playerId = BagPluginMain.getInstance().getUserUniqueNumber(player);
        PlayerBagConfig playerBagConfig = BagService.getInstance().getPlayerBagConfig(playerId);
        DefaultConfig defaultConfig = BagPluginMain.getInstance().getBaseConfigService().getConfig(DefaultConfig.configName);

        Integer lineNum = playerBagConfig.getLineNum();
        Integer maxLineNum = defaultConfig.getMaxLine();
        Integer price = defaultConfig.getUnitPrice();

        BaseLanguageService languageService = BagPluginMain.getInstance().getBaseLanguageService();
        BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_INFO.name(),LanguageEnum.MESSAGE_BAG_INFO.getValue(),lineNum.toString(),String.valueOf((maxLineNum-lineNum)),price.toString());
    }
}
