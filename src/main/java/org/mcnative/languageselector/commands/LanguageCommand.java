package org.mcnative.languageselector.commands;

import net.pretronic.libraries.command.command.BasicCommand;
import net.pretronic.libraries.command.command.configuration.CommandConfiguration;
import net.pretronic.libraries.command.sender.CommandSender;
import net.pretronic.libraries.message.bml.variable.VariableSet;
import net.pretronic.libraries.message.language.Language;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.interfaces.ObjectOwner;
import org.mcnative.languageselector.config.LanguageConfiguration;
import org.mcnative.languageselector.config.McNativeLanguageSelectorConfig;
import org.mcnative.languageselector.config.Messages;
import org.mcnative.runtime.api.McNative;
import org.mcnative.runtime.api.player.ConnectedMinecraftPlayer;
import org.mcnative.runtime.api.player.MinecraftPlayer;
import org.mcnative.runtime.api.service.inventory.gui.GuiManager;

public class LanguageCommand extends BasicCommand {

    public LanguageCommand(ObjectOwner owner, CommandConfiguration configuration) {
        super(owner, configuration);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof MinecraftPlayer)) {
            sender.sendMessage(Messages.ERROR_ONLY_PLAYER);
            return;
        }
        if(McNative.getInstance().getPlatform().isService() && McNativeLanguageSelectorConfig.GUI_ENABLED) {
            McNative.getInstance().getRegistry().getService(GuiManager.class).getGui("mcnativelanguageselector")
                    .open((ConnectedMinecraftPlayer) sender, "default");
            return;
        }
        if(args.length != 1) {
            sender.sendMessage(Messages.COMMAND_LANGUAGE_HELP,
                    VariableSet.create().addDescribed("languageConfigurations", McNativeLanguageSelectorConfig.LANGUAGES));
            return;
        }
        String languageName = args[0];

        LanguageConfiguration languageConfiguration = Iterators.findOne(McNativeLanguageSelectorConfig.LANGUAGES,
                languageConfiguration1 -> languageConfiguration1.getName().equalsIgnoreCase(languageName));
        if(languageConfiguration == null) {
            sender.sendMessage(Messages.ERROR_LANGUAGE_NOT_FOUND, VariableSet.create().add("language", languageName));
            return;
        }

        Language language = languageConfiguration.getLanguage();
        ((MinecraftPlayer)sender).setLanguage(language);
        sender.sendMessage(Messages.COMMAND_LANGUAGE, VariableSet.create().addDescribed("language", languageConfiguration));
    }
}
