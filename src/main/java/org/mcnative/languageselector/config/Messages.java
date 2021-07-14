package org.mcnative.languageselector.config;

import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.components.MessageComponent;

public class Messages {

    public static final MessageComponent<?> ERROR_ONLY_PLAYER = Text.ofMessageKey("mcnativelanguageselector.error.onlyPlayer");
    public static final MessageComponent<?> ERROR_LANGUAGE_NOT_FOUND = Text.ofMessageKey("mcnativelanguageselector.error.languageNotFound");

    public static final MessageComponent<?> COMMAND_LANGUAGE_HELP = Text.ofMessageKey("mcnativelanguageselector.command.language.help");
    public static final MessageComponent<?> COMMAND_LANGUAGE = Text.ofMessageKey("mcnativelanguageselector.command.language");
}
