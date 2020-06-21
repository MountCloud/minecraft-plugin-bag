package org.mountcloud.mcplugin.bag.language;

public enum LanguageEnum {

    PLUGIN_TITLE("Bag"),
    COMMAND_USEAGE_HELP("&7/bag &bhelp  &2| Show help"),
    COMMAND_USEAGE_OPEN("&7/bag &b[open]  &2| Open bage"),
    COMMAND_USEAGE_INFO("&7/bag &binfo  &2| show bag info"),
    COMMAND_USEAGE_PAY("&7/bag &bpay [1]  &2| uplevel bag,demo: [/bag pay 2] is add 2 level"),
    MESSAGE_BAG_INFO("&aThe bag level &6{0}&a,you can also be expanded &6{1}&a, and each expansion must cost &6{2}."),

    MESSAGE_BAG_PAY_OUT_OF_LEVEL("&cExpansion failed:&acurrent bag level is:&6{0}&a,max input level is &6{1}."),
    MESSAGE_BAG_PAY_OUT_OF_MONEY("&cExpansion failed:&aexpansion&6{0}&aneed payment&6{1}&a,you money is &6{2}."),
    MESSAGE_BAG_PAY_SUCCESS("&aExpansion success,Please use：&f/bag &a或 &f/bag open &ato view."),
    MESSAGE_BAG_PAY_FORMAGE_LINENUM_ERROR("The number of expansion times you entered is not a number. Please enter it correctly."),
    MESSAGE_BAG_PAY_FAIL("&cExpansion failed:&apayment failed."),
    TEXT_BOX_TITME("Bag");

    private String value;

    LanguageEnum(String s) {
        this.value = s;
    }

    public String getValue() {
        return value;
    }
}
