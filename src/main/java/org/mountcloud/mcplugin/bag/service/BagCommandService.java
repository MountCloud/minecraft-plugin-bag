package org.mountcloud.mcplugin.bag.service;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.bag.command.HelpCommand;
import org.mountcloud.mcplugin.common.BasePlugin;
import org.mountcloud.mcplugin.common.command.BaseCommand;
import org.mountcloud.mcplugin.common.command.BaseCommandSenderType;
import org.mountcloud.mcplugin.common.language.BaseLanguageEnum;
import org.mountcloud.mcplugin.common.service.command.BaseCommandeSercvice;

public class BagCommandService extends BaseCommandeSercvice {

    private static BagCommandService bagCommandService;

    public BagCommandService(String execCommand) {
        super(execCommand, BagPluginMain.getInstance());
    }

    @Override
    public boolean execCommand(CommandSender sender, BaseCommand command, String[] args, BaseCommandSenderType commandSenderType) {
        if(command instanceof HelpCommand) {
            if(args.length>0) {
                BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_ARGS_ERROR;
                BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender, languageEnum.name(), languageEnum.getValue(),command.getCommand());
            }
        }
        command.run(sender, args, commandSenderType);
        return true;
    }

    @Override
    public boolean notFoundCommand(CommandSender sender, Command command, String[] args) {
        BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_NOT_FOUND_COMMAND;
        BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender, languageEnum.name(), languageEnum.getValue(),command.getName(),getCommandName(args));
        HelpCommand.showHelp(sender);
        return false;
    }

    public static BagCommandService getInstance(){
        if(bagCommandService == null){
            bagCommandService = new BagCommandService("bag");
        }
        return bagCommandService;
    }
}
