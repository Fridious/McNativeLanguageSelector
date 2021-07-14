package org.mcnative.languageselector.config;

import net.pretronic.libraries.message.language.Language;
import net.pretronic.libraries.utility.Iterators;
import net.pretronic.libraries.utility.Validate;
import org.mcnative.runtime.api.text.Text;
import org.mcnative.runtime.api.text.components.MessageComponent;

public class LanguageConfiguration {

    private final String name;
    private final String displayName;
    private final String languageCode;

    private final String itemSkullUrl;
    private final String itemDisplayName;

    private transient Language language;
    private transient MessageComponent<?> parsedItemDisplayName;

    public LanguageConfiguration(String name, String displayName, String languageCode, String skullUrl, String itemDisplayName) {
        Validate.notNull(name);
        Validate.notNull(displayName);
        Validate.notNull(languageCode);

        this.name = name;
        this.displayName = displayName;
        this.languageCode = languageCode;
        this.itemSkullUrl = skullUrl;
        this.itemDisplayName = itemDisplayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public Language getLanguage() {
        if(language == null) {
            this.language = Iterators.findOne(Language.REGISTRY, language1 -> language1.getCode().equalsIgnoreCase(languageCode));
            if(this.language == null) {
                throw new IllegalArgumentException("Can't match language for code " + languageCode);
            }
        }
        return this.language;
    }

    public String getItemSkullUrl() {
        return itemSkullUrl;
    }

    public MessageComponent<?> getItemDisplayName() {
        if(this.parsedItemDisplayName == null) {
            this.parsedItemDisplayName = Text.parse(this.itemDisplayName);
        }
        return this.parsedItemDisplayName;
    }
}
