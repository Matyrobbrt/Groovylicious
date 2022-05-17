package ga.ozli.minecraftmods.groovylicious.api.gui.colours

import groovy.transform.AutoFinal
import groovy.transform.CompileStatic
import groovy.transform.stc.POJO
import net.minecraft.ChatFormatting
import org.jetbrains.annotations.NotNull

/**
 * This class has built-in names for all colours used by Minecraft that you can use for consistency.
 * You can also define your own in here for shared usage across mods.
 *
 * Usage:
 * <pre>
 * {@code
 * import static ga.ozli.minecraftmods.groovylicious.api.gui.colours.ColoursRegistry.instance as Colours
 *
 * Colours.WHITE // returns the Colour WHITE
 * Colours.SUBTITLE.alpha // returns the alpha channel of the Colour SUBTITLE
 * }
 * </pre>
 *
 * You can also define your own colours and use the features provided by this library, for example:
 * <pre>
 * {@code
 * Colours.MAGENTA = Colour.of(red: 255, green: 0, blue: 255) // creates a new Colour and stores it as MAGENTA in the Colours class
 * Colours.MAGENTA.packed // returns 0xFF00FF
 *
 * // More Java-esque syntax is also available if you prefer:
 * Colours.define("GroovyBlue", new Colour(-13272429))
 * Colours.get("GroovyBlue").getARGB() // returns [255, 53, 122, 147]
 * }
 * </pre>
 *
 * @see {@link Colour}
 *
 * @author Paint_Ninja
 * @author lukebemish
 */
@AutoFinal
@POJO
@CompileStatic
@Singleton(strict = false)
class ColoursRegistry {

    // Used by Vanilla GUIs
    Colour WHITE = new Colour(ChatFormatting.WHITE)
    Colour LIGHT_GREY = new Colour(10526880)
    Colour SUBTITLE = new Colour(16711680)
    Colour WARNING = new Colour(8421504)

    Colour EDITBOX_TEXT = new Colour(14737632)
    Colour EDITBOX_TEXTUNEDITABLE = new Colour(7368816)
    Colour EDITBOX_CURSORINSERT = new Colour(-3092272)
    Colour EDITBOX_BORDER = new Colour(-6250336)
    Colour EDITBOX_BORDERFOCUSED = new Colour(-1)
    Colour EDITBOX_BACKGROUND = new Colour(16777216)
    Colour EDITBOX_SUGGESTION = new Colour(-8355712)

    // For completeness
    Colour GREY = new Colour(ChatFormatting.GRAY)
    Colour DARK_GREY = new Colour(ChatFormatting.DARK_GRAY)
    Colour BLACK = new Colour(ChatFormatting.BLACK)

    Colour RED = new Colour(ChatFormatting.RED)
    Colour DARK_RED = new Colour(ChatFormatting.DARK_RED)
    Colour YELLOW = new Colour(ChatFormatting.YELLOW)
    Colour GOLD = new Colour(ChatFormatting.GOLD)
    Colour GREEN = new Colour(ChatFormatting.GREEN)
    Colour DARK_GREEN = new Colour(ChatFormatting.DARK_GREEN)
    Colour AQUA = new Colour(ChatFormatting.AQUA)
    Colour DARK_AQUA = new Colour(ChatFormatting.DARK_AQUA)
    Colour BLUE = new Colour(ChatFormatting.BLUE)
    Colour DARK_BLUE = new Colour(ChatFormatting.DARK_BLUE)
    Colour LIGHT_PURPLE = new Colour(ChatFormatting.LIGHT_PURPLE)
    Colour DARK_PURPLE = new Colour(ChatFormatting.DARK_PURPLE)

    @Delegate(excludes = ['containsKey', 'put', 'putAll'])
    private final HashMap<String, Colour> backingMap =
            this.getProperties().findAll({ it.value instanceof Colour }) as HashMap<String, Colour>

    // Custom handling for colour names in the backing map to use case-standard names (e.g. "RED" instead of "red")
    // TODO: handle conversion from PascalCase/camelCase to SCREAMING_CAMEL_CASE instead of just making it uppercase
    // TODO: get() implementation
    @Override
    boolean containsKey(Object key) {
        String keyStr = null
        if (key instanceof String)
            keyStr = (key as String).toUpperCase(Locale.ROOT)
        return backingMap.containsKey(keyStr ?: key)
    }

    @Override
    Colour put(String key, Colour value) {
        return backingMap[key.toUpperCase(Locale.ROOT)] = value
    }

    @Override
    void putAll(@NotNull Map<? extends String, ? extends Colour> m) {
        m.each { key, value ->
            backingMap[key.toUpperCase(Locale.ROOT)] = value
        }
    }

    // Convenience methods
    void define(String name, Colour colour) {
        backingMap[name.toUpperCase(Locale.ROOT)] = colour
    }
}
