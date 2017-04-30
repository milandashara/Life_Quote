package lifequote.security;

/**
 * Created by milanashara on 1/3/17.
 */
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;

/**
 *
 * @author Rob Winch
 *
 */
public class OwaspCharacterEscapes extends CharacterEscapes {
    private final int[] ESCAPES;

    public OwaspCharacterEscapes() {
        ESCAPES = standardAsciiEscapesForJSON();
        for(int i=0;i<ESCAPES.length;i++) {
            if(!(Character.isAlphabetic(i) || Character.isDigit(i))) {
                ESCAPES[i] = CharacterEscapes.ESCAPE_CUSTOM;
            }
        }
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        String unicode = String.format("\\u%04x", ch);
        return new SerializedString(unicode);
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return ESCAPES;
    }

    private static final long serialVersionUID = 8140493311454723880L;
}
