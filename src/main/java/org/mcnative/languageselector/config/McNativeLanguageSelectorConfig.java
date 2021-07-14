package org.mcnative.languageselector.config;

import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;

import java.util.Arrays;
import java.util.List;

public class McNativeLanguageSelectorConfig {

    public static List<LanguageConfiguration> LANGUAGES = Arrays.asList(
            new LanguageConfiguration("deutsch", "Deutsch", "German", "http://textures.minecraft.net/texture/5e7899b4806858697e283f084d9173fe487886453774626b24bd8cfecc77b3f", "&7Deutsch"),
            new LanguageConfiguration("english", "English", "English", "http://textures.minecraft.net/texture/4cac9774da1217248532ce147f7831f67a12fdcca1cf0cb4b3848de6bc94b4", "&7English"));

    public static CommandConfiguration COMMAND_LANGUAGE = CommandConfiguration.newBuilder()
            .name("language")
            .aliases("l")
            .permission("mcnativelanguageselector.command.language")
            .create();

    public static String GUI_TITLE = "&2Select your language";
    public static int GUI_SIZE = 27;

    public static boolean GUI_ENABLED = true;

    public static boolean LANGUAGE_SELECTOR_ITEM_ENABLED = true;
    public static String LANGUAGE_SELECTOR_ITEM_DISPLAY_NAME = "&2LanguageSelector";
    public static String LANGUAGE_SELECTOR_ITEM_MATERIAL = "BLAZE_ROD";
    public static int LANGUAGE_SELECTOR_ITEM_SLOT = 2;

    public static ItemStack buildLanguageSelectorItem() {
        return ItemStack.newItemStack(Material.getMaterial(LANGUAGE_SELECTOR_ITEM_MATERIAL))
                .setDisplayName(Text.parse(LANGUAGE_SELECTOR_ITEM_DISPLAY_NAME));
    }
}
