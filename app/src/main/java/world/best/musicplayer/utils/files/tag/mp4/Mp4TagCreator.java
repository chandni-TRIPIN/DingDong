package world.best.musicplayer.utils.files.tag.mp4;

import world.best.musicplayer.utils.files.audio.generic.AbstractTagCreator;
import world.best.musicplayer.utils.files.audio.generic.Utils;
import world.best.musicplayer.utils.files.audio.mp4.Mp4AtomIdentifier;
import world.best.musicplayer.utils.files.audio.mp4.atom.Mp4BoxHeader;
import world.best.musicplayer.utils.files.tag.FieldKey;
import world.best.musicplayer.utils.files.tag.KeyNotFoundException;
import world.best.musicplayer.utils.files.tag.Tag;
import world.best.musicplayer.utils.files.tag.TagField;
import world.best.musicplayer.utils.files.tag.mp4.field.Mp4TagCoverField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Iterator;

/**
 * Create raw content of mp4 tag data, concerns itself with atoms upto the ilst atom
 *
 * <p>This level is was selected because the ilst atom can be recreated without reference to existing mp4 fields
 * but fields above this level are dependent upon other information that is not held in the tag.
 *
 * <pre>
 * |--- ftyp
 * |--- moov
 * |......|
 * |......|----- mvdh
 * |......|----- trak
 * |......|----- udta
 * |..............|
 * |..............|-- meta
 * |....................|
 * |....................|-- hdlr
 * |....................|-- ilst
 * |....................|.. ..|
 * |....................|.....|---- @nam (Optional for each metadatafield)
 * |....................|.....|.......|-- data
 * |....................|.....|....... ecetera
 * |....................|.....|---- ---- (Optional for reverse dns field)
 * |....................|.............|-- mean
 * |....................|.............|-- name
 * |....................|.............|-- data
 * |....................|................ ecetere
 * |....................|-- free
 * |--- free
 * |--- mdat
 * </pre>
 */
public class Mp4TagCreator extends AbstractTagCreator
{
    /**
     * Convert tagdata to rawdata ready for writing to file
     *
     * @param tag
     * @param padding TODO padding parameter currently ignored
     * @return
     * @throws UnsupportedEncodingException
     */
    public ByteBuffer convert(Tag tag, int padding) throws UnsupportedEncodingException
    {
        try
        {
            //Add metadata raw content
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Iterator<TagField> it = tag.getFields();
            boolean processedArtwork = false;
            while (it.hasNext())
            {
                TagField frame = it.next();
                //To ensure order is maintained dont process artwork until iterator hits it.
                if (frame instanceof Mp4TagCoverField)
                {
                    if (processedArtwork)
                    {
                        //ignore
                    }
                    else
                    {
                        processedArtwork = true;

                        //Because each artwork image is held within the tag as a separate field, but when
                        //they are written they are all held under a single covr box we need to do some checks
                        //and special processing here if we have any artwork image (this code only necessary
                        //if we have more than 1 but do it anyway even if only have 1 image)
                        ByteArrayOutputStream covrDataBaos = new ByteArrayOutputStream();

                        try
                        {
                            for (TagField artwork : tag.getFields(FieldKey.COVER_ART))
                            {
                                covrDataBaos.write(((Mp4TagField) artwork).getRawContentDataOnly());
                            }
                        }
                        catch (KeyNotFoundException knfe)
                        {
                            //This cannot happen
                            throw new RuntimeException("Unable to find COVERART Key");
                        }

                        //Now create the parent Data
                        byte[] data = covrDataBaos.toByteArray();
                        baos.write(Utils.getSizeBEInt32(Mp4BoxHeader.HEADER_LENGTH + data.length));
                        baos.write(Utils.getDefaultBytes(Mp4FieldKey.ARTWORK.getFieldName(), "ISO-8859-1"));
                        baos.write(data);
                    }
                }
                else
                {                     
                    baos.write(frame.getRawContent());
                }
            }

            //Wrap into ilst box
            ByteArrayOutputStream ilst = new ByteArrayOutputStream();
            ilst.write(Utils.getSizeBEInt32(Mp4BoxHeader.HEADER_LENGTH + baos.size()));
            ilst.write(Utils.getDefaultBytes(Mp4AtomIdentifier.ILST.getFieldName(), "ISO-8859-1"));
            ilst.write(baos.toByteArray());

            //Put into ByteBuffer
            ByteBuffer buf = ByteBuffer.wrap(ilst.toByteArray());
            buf.rewind();
            return buf;
        }
        catch (IOException ioe)
        {
            //Should never happen as not writing to file at this point
            throw new RuntimeException(ioe);
        }
    }
}
