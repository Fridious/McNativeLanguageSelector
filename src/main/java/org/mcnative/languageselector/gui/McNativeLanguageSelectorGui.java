package org.mcnative.languageselector.gui;

import net.pretronic.libraries.message.bml.variable.VariableSet;
import org.mcnative.languageselector.config.LanguageConfiguration;
import org.mcnative.languageselector.config.McNativeLanguageSelectorConfig;
import org.mcnative.languageselector.config.Messages;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.profile.GameProfile;
import org.mcnative.runtime.api.player.profile.GameProfileLoader;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;
import org.mcnative.runtime.api.service.inventory.gui.builder.ElementList;
import org.mcnative.runtime.api.service.inventory.gui.context.EmptyScreenContext;
import org.mcnative.runtime.api.service.inventory.gui.context.GuiContext;
import org.mcnative.runtime.api.service.inventory.gui.pane.ArrayListPaginationSource;
import org.mcnative.runtime.api.service.inventory.gui.pane.ListPane;
import org.mcnative.runtime.api.service.inventory.item.ItemStack;
import org.mcnative.runtime.api.service.inventory.item.data.SkullItemData;
import org.mcnative.runtime.api.service.inventory.item.material.Material;
import org.mcnative.runtime.api.text.Text;

import java.net.URI;

public class McNativeLanguageSelectorGui {

    public static void register() {
        GuiManager manager = McNative.getInstance().getRegistry().getService(GuiManager.class);

        manager.createGui("mcnativelanguageselector", GuiContext.class, builder -> {
            builder.setDefaultPage("default");
            builder.createScreen("defaultScreen", McNativeLanguageSelectorConfig.GUI_SIZE, EmptyScreenContext.class,
                    context -> Text.parse(McNativeLanguageSelectorConfig.GUI_TITLE), McNativeLanguageSelectorGui::registerPage);
            builder.registerPage("default", "defaultScreen");
        });
    }

    private static void registerPage(ElementList<GuiContext, EmptyScreenContext> elements) {
        elements.addElement(new ListPane<GuiContext, EmptyScreenContext, LanguageConfiguration>(context -> new ArrayListPaginationSource<>(McNativeLanguageSelectorConfig.LANGUAGES, McNativeLanguageSelectorConfig.GUI_SIZE),
                createSlotArray(McNativeLanguageSelectorConfig.GUI_SIZE)){
            @Override
            protected ItemStack create(EmptyScreenContext context, int slot, LanguageConfiguration languageConfiguration) {
                GameProfileLoader profileLoader = McNative.getInstance().getRegistry().getService(GameProfileLoader.class);
                GameProfile gameProfile = profileLoader.getGameProfile(URI.create(languageConfiguration.getItemSkullUrl()));
                if(gameProfile == null) {
                    throw new IllegalArgumentException("Can't match GameProfile from url " + languageConfiguration.getItemSkullUrl());
                }
                return ItemStack.newItemStack(Material.PLAYER_HEAD)
                        .setDisplayName(languageConfiguration.getItemDisplayName())
                        .getData(SkullItemData.class, data -> data.setGameProfile(gameProfile));
            }

            @Override
            public void handleClick(EmptyScreenContext context, MinecraftPlayerInventoryClickEvent event, LanguageConfiguration languageConfiguration) {
                event.getPlayer().setLanguage(languageConfiguration.getLanguage());
                event.getPlayer().closeInventory();
                event.getPlayer().sendMessage(Messages.COMMAND_LANGUAGE, VariableSet.create().addDescribed("language", languageConfiguration));
            }
        });
    }

    private static int[] createSlotArray(int guiSize) {
        int[] slots = new int[guiSize];
        for (int i = 0; i < guiSize; i++) {
            slots[i] = i;
        }
        return slots;
    }
}
