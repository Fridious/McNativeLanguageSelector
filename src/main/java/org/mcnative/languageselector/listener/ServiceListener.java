package org.mcnative.languageselector.listener;

import net.pretronic.libraries.event.Listener;
import org.mcnative.languageselector.config.McNativeLanguageSelectorConfig;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.service.entity.living.Player;
import org.mcnative.runtime.api.service.event.player.MinecraftPlayerDropItemEvent;
import org.mcnative.runtime.api.service.event.player.MinecraftPlayerInteractEvent;
import org.mcnative.runtime.api.service.event.player.MinecraftPlayerJoinEvent;
import org.mcnative.runtime.api.service.event.player.inventory.MinecraftPlayerInventoryClickEvent;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;
import org.mcnative.runtime.api.service.world.block.BlockAction;

public class ServiceListener {

    @Listener
    public void onJoin(MinecraftPlayerJoinEvent event) {
        if(McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_ENABLED) {
            Player player = event.getPlayer();

            player.getInventory().setItem(McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_SLOT, McNativeLanguageSelectorConfig.buildLanguageSelectorItem());
        }
    }

    @Listener
    public void onInteract(MinecraftPlayerInteractEvent event) {
        if(event.getAction() == BlockAction.RIGHT_CLICK_AIR || event.getAction() == BlockAction.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            int slot = player.getInventory().getHeldItemSlot();
            if(McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_ENABLED && slot == McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_SLOT) {
                McNative.getInstance().getRegistry().getService(GuiManager.class).getGui("mcnativelanguageselector").open(player, "default");
            }
        }
    }

    @Listener
    public void onInventoryClick(MinecraftPlayerInventoryClickEvent event) {
        if((McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_ENABLED && event.getSlot() == McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_SLOT)) {
            event.setCancelled(true);
        }
    }

    @Listener
    public void onItemDrop(MinecraftPlayerDropItemEvent event) {
        int slot = event.getPlayer().getInventory().getHeldItemSlot();
        if((McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_ENABLED && slot == McNativeLanguageSelectorConfig.LANGUAGE_SELECTOR_ITEM_SLOT)) {
            event.setCancelled(true);
        }
    }
}