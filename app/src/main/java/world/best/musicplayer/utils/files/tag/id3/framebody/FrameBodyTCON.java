package world.best.musicplayer.utils.files.tag.id3.framebody;

import world.best.musicplayer.utils.files.tag.InvalidTagException;
import world.best.musicplayer.utils.files.tag.datatype.DataTypes;
import world.best.musicplayer.utils.files.tag.datatype.NumberHashMap;
import world.best.musicplayer.utils.files.tag.datatype.TCONString;
import world.best.musicplayer.utils.files.tag.id3.ID3v24Frames;
import world.best.musicplayer.utils.files.tag.id3.valuepair.ID3V2ExtendedGenreTypes;
import world.best.musicplayer.utils.files.tag.id3.valuepair.TextEncoding;
import world.best.musicplayer.utils.files.tag.reference.GenreTypes;

import java.nio.ByteBuffer;

/**
 * Content type Text information frame.
 *
 * <p>The 'Content type', which previously was
 * stored as a one byte numeric value only, is now a numeric string. You
 * may use one or several of the types as ID3v1.1 did or, since the
 * category list would be impossible to maintain with accurate and up to
 * date categories, define your own.
 * <p>
 * ID3V23:References to the ID3v1 genres can be made by, as first byte, enter
 * "(" followed by a number from the genres list (appendix A) and
 * ended with a ")" character. This is optionally followed by a
 * refinement, e.g. "(21)" or "(4)Eurodisco". Several references can be
 * made in the same frame, e.g. "(51)(39)". If the refinement should
 * begin with a "(" character it should be replaced with "((", e.g. "((I
 * can figure out any genre)" or "(55)((I think...)". The following new
 * content types is defined in ID3v2 and is implemented in the same way
 * as the numeric content types, e.g. "(RX)".
 * <p><table border=0 width="70%">
 * <tr><td>RX</td><td width="100%">Remix</td></tr>
 * <tr><td>CR</td><td>Cover</td></tr>
 * </table>
 *
 * <p>For more details, please refer to the ID3 specifications:
 * <ul>
 * <li><a href="http://www.id3.org/id3v2.3.0.txt">ID3 v2.3.0 Spec</a>
 * </ul>
 *
 * ID3V24:The 'Content type', which ID3v1 was stored as a one byte numeric
 * value only, is now a string. You may use one or several of the ID3v1
 * types as numerical strings, or, since the category list would be
 * impossible to maintain with accurate and up to date categories,
 * define your own. Example: "21" $00 "Eurodisco" $00
 *
 * You may also use any of the following keywords:
 * <p><table border=0 width="70%">
 * <tr><td>RX</td><td width="100%">Remix</td></tr>
 * <tr><td>CR</td><td>Cover</td></tr>
 * </table>
 */
public class FrameBodyTCON extends AbstractFrameBodyTextInfo implements ID3v24FrameBody, ID3v23FrameBody
{
    /**
     * Creates a new FrameBodyTCON datatype.
     */
    public FrameBodyTCON()
    {
    }

    public FrameBodyTCON(FrameBodyTCON body)
    {
        super(body);
    }

    /**
     * Creates a new FrameBodyTCON datatype.
     *
     * @param textEncoding
     * @param text
     */
    public FrameBodyTCON(byte textEncoding, String text)
    {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTCON datatype.
     *
     * @param byteBuffer
     * @param frameSize
     * @throws InvalidTagException
     */
    public FrameBodyTCON(ByteBuffer byteBuffer, int frameSize) throws InvalidTagException
    {
        super(byteBuffer, frameSize);
    }


    /**
     * The ID3v2 frame identifier
     *
     * @return the ID3v2 frame identifier  for this frame type
     */
    public String getIdentifier()
    {
        return ID3v24Frames.FRAME_ID_GENRE;
    }


    /**
     * Convert value to internal genre value
     *
     * @param value
     * @return
     */
    public static String convertGenericToID3v24Genre(String value)
    {
        try
        {
            int genreId = Integer.parseInt(value);
            if (genreId < GenreTypes.getMaxGenreId())
            {
                return String.valueOf(genreId);
            }
            else
            {
                return value;
            }
        }
        catch (NumberFormatException nfe)
        {
            Integer genreId = GenreTypes.getInstanceOf().getIdForName(value);
            if (genreId != null)
            {
                return String.valueOf(genreId);
            }

            if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.RX.getDescription()))
            {
                value = ID3V2ExtendedGenreTypes.RX.name();
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.CR.getDescription()))
            {
                value = ID3V2ExtendedGenreTypes.CR.name();
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.RX.name()))
            {
                value = ID3V2ExtendedGenreTypes.RX.name();
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.CR.name()))
            {
                value = ID3V2ExtendedGenreTypes.CR.name();
            }
        }
        return value;
    }

    /**
     * Convert value to internal genre value
     *
     * @param value
     * @return
     */
    public static String convertGenericToID3v23Genre(String value)
    {
        try
        {
            int genreId = Integer.parseInt(value);
            if (genreId < GenreTypes.getMaxGenreId())
            {
                return bracketWrap(String.valueOf(genreId));
            }
            else
            {
                return value;
            }
        }
        catch (NumberFormatException nfe)
        {
            Integer genreId = GenreTypes.getInstanceOf().getIdForName(value);
            if (genreId != null)
            {
                return bracketWrap(String.valueOf(genreId));
            }

            if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.RX.getDescription()))
            {
                value = bracketWrap(ID3V2ExtendedGenreTypes.RX.name());
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.CR.getDescription()))
            {
                value = bracketWrap(ID3V2ExtendedGenreTypes.CR.name());
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.RX.name()))
            {
                value = bracketWrap(ID3V2ExtendedGenreTypes.RX.name());
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.CR.name()))
            {
                value = bracketWrap(ID3V2ExtendedGenreTypes.CR.name());
            }
        }
        return value;
    }

    public static String convertGenericToID3v22Genre(String value)
    {
        return convertGenericToID3v23Genre(value);
    }

    private static String bracketWrap(Object value)
    {
        return "(" + value + ')';
    }

    /**
     * Convert internal v24 genre value to generic genre
     *
     * @param value
     * @return
     */
    public static String convertID3v24GenreToGeneric(String value)
    {
        try
        {
            int genreId = Integer.parseInt(value);
            if (genreId < GenreTypes.getMaxStandardGenreId())
            {
                return GenreTypes.getInstanceOf().getValueForId(genreId);
            }
            else
            {
                return value;
            }
        }
        catch (NumberFormatException nfe)
        {
            if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.RX.name()))
            {
                value = ID3V2ExtendedGenreTypes.RX.getDescription();
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.CR.name()))
            {
                value = ID3V2ExtendedGenreTypes.CR.getDescription();
            }
            else
            {
                return value;
            }
        }
        return value;
    }

    private static String checkBracketed(String value)
    {
        value=value.replace("(", "");
        value=value.replace(")", "");
        try
        {
            int genreId = Integer.parseInt(value);
            if (genreId < GenreTypes.getMaxStandardGenreId())
            {
                return GenreTypes.getInstanceOf().getValueForId(genreId);
            }
            else
            {
                return value;
            }
        }
        catch (NumberFormatException nfe)
        {
            if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.RX.name()))
            {
                value = ID3V2ExtendedGenreTypes.RX.getDescription();
            }
            else if (value.equalsIgnoreCase(ID3V2ExtendedGenreTypes.CR.name()))
            {
                value = ID3V2ExtendedGenreTypes.CR.getDescription();
            }
            else
            {
                return value;
            }
        }
        return value;
    }

    /**
     * Convert V23 format to Generic
     *
     * i.e.
     *
     * (2)         -> Country
     * (RX)        -> Remix
     * Shoegaze    -> Shoegaze
     * (2)Shoegaze -> Country Shoegaze
     *
     * Note only handles one field so if the frame stored (2)(3) this would be two separate fields
     * and would manifest itself as two different calls to this method once for (2) and once for (3)
     * @param value
     * @return
     */
    public static String convertID3v23GenreToGeneric(String value)
    {
        if(value.contains(")") && value.lastIndexOf(')')<value.length()-1)
        {
            return checkBracketed(value.substring(0,value.lastIndexOf(')'))) + ' ' + value.substring(value.lastIndexOf(')')+1);
        }
        else
        {
            return checkBracketed(value);
        }
    }

    public static String convertID3v22GenreToGeneric(String value)
    {
        return convertID3v23GenreToGeneric(value);
    }

    public void setV23Format()
    {
        TCONString text = (TCONString) getObject(DataTypes.OBJ_TEXT);
        text.setNullSeperateMultipleValues(false);
    }

    protected void setupObjectList()
    {
        objectList.add(new NumberHashMap(DataTypes.OBJ_TEXT_ENCODING, this, TextEncoding.TEXT_ENCODING_FIELD_SIZE));
        objectList.add(new TCONString(DataTypes.OBJ_TEXT, this));
    }


}
