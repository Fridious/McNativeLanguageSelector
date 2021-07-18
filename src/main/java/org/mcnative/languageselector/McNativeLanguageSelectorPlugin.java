package org.mcnative.languageselector;

import net.pretronic.libraries.plugin.lifecycle.Lifecycle;
import net.pretronic.libraries.plugin.lifecycle.LifecycleState;
import org.mcnative.languageselector.commands.LanguageCommand;
import org.mcnative.languageselector.commands.McNativeLanguageSelectorCommand;
import org.mcnative.languageselector.config.McNativeLanguageSelectorConfig;
import org.mcnative.languageselector.gui.McNativeLanguageSelectorGui;
import org.mcnative.languageselector.listener.ServiceListener;
import org.mcnative.licensing.context.platform.McNativeLicenseIntegration;
import org.mcnative.licensing.exceptions.CloudNotCheckoutLicenseException;
import org.mcnative.licensing.exceptions.LicenseNotValidException;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.plugin.MinecraftPlugin;

public class McNativeLanguageSelectorPlugin extends MinecraftPlugin {

    public static final String RESOURCE_ID = "415e443a-e655-11eb-8ba0-0242ac180002";
    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApIr07y+zTUsWQgQvL3v4AZd8HtJtkpSlMDDqy6Jp81mGn83lfta17kFRTc2INZeCs7yN82pHOePmLhpcd9RJMx/GItDJGCmCnrVw2VFw6STv0OFBMZZj24C37v0/mvup8CRteMk3FKRcwItGqIuZAt/oRyxFanhfB856uGkYw1YPSbn0rCe0teQsCGbgIyjxO3D9gIOTp5Utf+fOmww7oxC4cf0D6ZOH97j5QrU1qt1sTfqnDJH+Xdzl5Z5IDgzYjDC7P0atMBiRPI8UeWnMsRu44TcWGKB0+xIozDmRBDClDhDcfZwqQG7naqMEPXmIa8n1RbBbTlaakEDSgsCeewIDAQAB";

    @Lifecycle(state = LifecycleState.LOAD)
    public void onLoad(LifecycleState state){
        getLogger().info("McNativeLanguageSelector is starting, please wait..");

        try{
            McNativeLicenseIntegration.newContext(this,RESOURCE_ID,PUBLIC_KEY).verifyOrCheckout();
        }catch (LicenseNotValidException | CloudNotCheckoutLicenseException e){
            getLogger().error("--------------------------------");
            getLogger().error("-> Invalid license");
            getLogger().error("-> Error: "+e.getMessage());
            getLogger().error("--------------------------------");
            getLogger().info("McNativeLanguageSelector is shutting down");
            getLoader().shutdown();
            return;
        }
        getConfiguration().load(McNativeLanguageSelectorConfig.class);

        getRuntime().getLocal().getCommandManager().registerCommand(new LanguageCommand(this, McNativeLanguageSelectorConfig.COMMAND_LANGUAGE));
        getRuntime().getLocal().getCommandManager().registerCommand(new McNativeLanguageSelectorCommand(this));

        if(McNative.getInstance().getPlatform().isService() && McNativeLanguageSelectorConfig.GUI_ENABLED) {
            McNativeLanguageSelectorGui.register();
            getRuntime().getLocal().getEventBus().subscribe(this, new ServiceListener());
        }

        getLogger().info("McNativeLanguageSelector started successfully");
    }
}
