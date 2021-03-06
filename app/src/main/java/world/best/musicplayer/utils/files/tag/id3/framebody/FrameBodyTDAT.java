package world.best.musicplayer.utils.files.tag.id3.framebody;

import world.best.musicplayer.utils.files.tag.InvalidTagException;
import world.best.musicplayer.utils.files.tag.id3.ID3v23Frames;

import java.nio.ByteBuffer;

/**
 * Date Text information frame.
 * <p>The 'Date' frame is a numeric string in the DDMM format containing the date for the recording. This field is always four characters long.
 * <p>Deprecated in v2.4.0
 *
 * <p>For more details, please refer to the ID3 specifications:
 * <ul>
 * <li><a href="http://www.id3.org/id3v2.3.0.txt">ID3 v2.3.0 Spec</a>
 * </ul>
 */
public class FrameBodyTDAT extends AbstractFrameBodyTextInfo implements ID3v23FrameBody
{
    private boolean monthOnly;

    /**
     * Creates a new FrameBodyTDAT datatype.
     */
    public FrameBodyTDAT()
    {
    }

    public FrameBodyTDAT(FrameBodyTDAT body)
    {
        super(body);
    }

    /**
     * Creates a new FrameBodyTDAT datatype.
     *
     * @param textEncoding
     * @param text
     */
    public FrameBodyTDAT(byte textEncoding, String text)
    {
        super(textEncoding, text);
    }

    /**
     * Creates a new FrameBodyTDAT datatype.
     *
     * @param byteBuffer
     * @param frameSize
     * @throws InvalidTagException
     */
    public FrameBodyTDAT(ByteBuffer byteBuffer, int frameSize) throws InvalidTagException
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
        return ID3v23Frames.FRAME_ID_V3_TDAT;
    }

    public boolean isMonthOnly()
    {
        return monthOnly;
    }

    public void setMonthOnly(boolean monthOnly)
    {
        this.monthOnly = monthOnly;
    }
}