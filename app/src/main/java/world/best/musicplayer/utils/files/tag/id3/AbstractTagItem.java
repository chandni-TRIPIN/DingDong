package world.best.musicplayer.utils.files.tag.id3;

import world.best.musicplayer.utils.files.tag.TagException;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 * This specifies a series of methods that have to be implemented by all structural subclasses,
 * required to support all copy constructors,iterative methods and so on.
 *
 * TODO Not sure if this is really correct, if really needed should probably be an interface
 */
public abstract class AbstractTagItem
{

    //Logger
    public static Logger logger = Logger.getLogger("world.best.musicplayer.utils.files.tag.id3");


    public AbstractTagItem()
    {
    }

    public AbstractTagItem(AbstractTagItem copyObject)
    {
        // no copy constructor in super class
    }

    /**
     * ID string that usually corresponds to the class name, but can be
     * displayed to the user. It is not indended to identify each individual
     * instance.
     *
     * @return ID string
     */
    abstract public String getIdentifier();

    /**
     * Return size of this item
     *
     * @return size of this item
     */
    abstract public int getSize();

    /**
     * @param byteBuffer file to read from
     * @throws TagException on any exception generated by this library.
     */
    abstract public void read(ByteBuffer byteBuffer) throws TagException;

    /**
     * Returns true if this datatype is a subset of the argument. This instance
     * is a subset if it is the same class as the argument.
     *
     * @param obj datatype to determine subset of
     * @return true if this instance and its entire datatype array list is a
     *         subset of the argument.
     */
    public boolean isSubsetOf(Object obj)
    {
        return obj instanceof AbstractTagItem;
    }

    /**
     * Returns true if this datatype and its body equals the argument and its
     * body. this datatype is equal if and only if they are the same class
     *
     * @param obj datatype to determine equality of
     * @return true if this datatype and its body are equal
     */
    public boolean equals(Object obj)
    {
        if ( this == obj ) return true;
        return obj instanceof AbstractTagItem;
    }
}
