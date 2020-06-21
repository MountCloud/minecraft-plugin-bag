package org.mountcloud.mcplugin.bag.command;

import org.bukkit.command.CommandSender;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.bag.language.LanguageEnum;
import org.mountcloud.mcplugin.bag.service.BagCommandService;
import org.mountcloud.mcplugin.common.command.BaseCommand;
import org.mountcloud.mcplugin.common.command.BaseCommandSenderType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HelpCommand extends BaseCommand {
    public HelpCommand() {
        super("help", BaseCommandSenderType.ARBITRARLIY, 0);
        setUsage(BagPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.COMMAND_USEAGE_HELP.name(), LanguageEnum.COMMAND_USEAGE_HELP.getValue()));
    }

    @Override
    public void run(CommandSender sender, String[] strings, BaseCommandSenderType baseCommandSenderType) {
        showHelp(sender);
    }

    public static void showHelp(CommandSender sender){
        String pluginTitle = BagPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.PLUGIN_TITLE.name(), LanguageEnum.PLUGIN_TITLE.getValue());
        BagPluginMain.getInstance().getBaseMessageService().sendMessage(sender, "&5[------------ &9"+pluginTitle+" &5------------]");
        List<String> helps = new ArrayList<String>();

        Map<String, BaseCommand> cmds = BagCommandService.getInstance().getCommands();
        Set<String> keys = cmds.keySet();
        for(String key : keys) {
            BaseCommand baseCommand = cmds.get(key);
            if(BagCommandService.getInstance().checkPermission(sender, baseCommand)&&BagCommandService.getInstance().checkCommandSenderType(sender, baseCommand)) {
                String uesAge = baseCommand.getUsage();
                if(!helps.contains(uesAge)) {
                    helps.add(uesAge);
                }
            }
        }

        for(String help : helps) {
            BagPluginMain.getInstance().getBaseMessageService().sendMessage(sender, help);
        }
    }
}
