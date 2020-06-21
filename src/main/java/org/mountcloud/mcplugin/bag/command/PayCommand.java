package org.mountcloud.mcplugin.bag.command;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.mountcloud.mcplugin.bag.BagPluginMain;
import org.mountcloud.mcplugin.bag.config.DefaultConfig;
import org.mountcloud.mcplugin.bag.config.PlayerBagConfig;
import org.mountcloud.mcplugin.bag.language.LanguageEnum;
import org.mountcloud.mcplugin.bag.service.BagService;
import org.mountcloud.mcplugin.common.command.BaseCommand;
import org.mountcloud.mcplugin.common.command.BaseCommandSenderType;

import java.util.regex.Pattern;

public class PayCommand extends BaseCommand {

    public PayCommand() {
        super("pay", BaseCommandSenderType.PLAYER, 1);
        setUsage(BagPluginMain.getInstance().getBaseLanguageService().getLanguage(LanguageEnum.COMMAND_USEAGE_PAY.name(),LanguageEnum.COMMAND_USEAGE_PAY.getValue()));
    }

    @Override
    public void run(CommandSender sender, String[] args, BaseCommandSenderType senderType) {
        RegisteredServiceProvider<Economy> economyReg = BagPluginMain.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        Player player = (Player) sender;

        //参数不对
        if(args.length==0){
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR.name(),LanguageEnum.MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR.getValue());
            return;
        }

        Integer expansionLine = null;
        String inputLevel = args[0].trim();
        boolean isInt = Pattern.compile("^[1-9]{1,}$").matcher(inputLevel).find();

        if(inputLevel.length()==0||!isInt){
            //输入的扩容次数格式不对
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR.name(),LanguageEnum.MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR.getValue());
            return;
        }

        try{
            expansionLine = Integer.parseInt(args[0]);
        }catch (Exception e){

        }

        //输入的扩容次数格式不对：转型失败
        if(expansionLine==null){
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR.name(),LanguageEnum.MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR.getValue());
            return;
        }

        String playerId = BagPluginMain.getInstance().getUserUniqueNumber(player);
        PlayerBagConfig playerBagConfig = BagService.getInstance().getPlayerBagConfig(playerId);
        DefaultConfig defaultConfig = BagPluginMain.getInstance().getBaseConfigService().getConfig(DefaultConfig.configName);

        //判断是不是超出了可以扩容的等级
        Integer maxLevel = defaultConfig.getMaxLine();

        Integer endExpLine = playerBagConfig.getLineNum()+expansionLine;
        //超出最大扩容限制
        if(endExpLine>maxLevel){
            Integer allowLine = maxLevel - playerBagConfig.getLineNum();
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_OUT_OF_LEVEL.name(),LanguageEnum.MESSAGE_BAG_PAY_OUT_OF_LEVEL.getValue(),playerBagConfig.getLineNum().toString(),allowLine.toString());
            return;
        }


        //现在钱包里的大洋
        double money = economyReg.getProvider().getBalance(player);

        //扩容需要花的钱
        Integer expansionMoney = expansionLine*defaultConfig.getUnitPrice();
        //钱包钱不够
        if(money<expansionMoney){
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_OUT_OF_MONEY.name(),LanguageEnum.MESSAGE_BAG_PAY_OUT_OF_MONEY.getValue(),expansionLine.toString(),expansionMoney.toString(),String.valueOf(money));
            return;
        }

        //花钱时刻来临了
        if(economyReg.getProvider().withdrawPlayer(player,expansionMoney).transactionSuccess()){
            //完成支付
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_SUCCESS.name(),LanguageEnum.MESSAGE_BAG_PAY_SUCCESS.getValue());
            BagService.getInstance().setLineNum(playerId,endExpLine);
        }else{
            //支付失败
            BagPluginMain.getInstance().getBaseMessageService().sendMessageByLanguage(sender,LanguageEnum.MESSAGE_BAG_PAY_FAIL.name(),LanguageEnum.MESSAGE_BAG_PAY_FAIL.getValue());
        }
    }
}
