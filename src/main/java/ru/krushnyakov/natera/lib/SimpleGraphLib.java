/**
 * 
 */
package ru.krushnyakov.natera.lib;

/**
 * @author kkrushnyakov
 *
 */
public class SimpleGraphLib {

    private static final UnsynchronizedGraphFactory UNSYNCHRONIZED_FACTORY_INSTANCE = new UnsynchronizedGraphFactory();

    private static final SynchronizedGraphFactory SYNCHRONIZED_FACTORY_INSTANCE = new SynchronizedGraphFactory();
   
    /**
     * Produces unsynchronized graph factory
     * 
     * @return GraphFactory unsynchronized instance
     */
    
    public static GraphFactory getGraphFactory() {
        return UNSYNCHRONIZED_FACTORY_INSTANCE;
    }
   
    /**
     * Produces synchronized graph factory
     * 
     * @return GraphFactory synchronized instance
     */

    public static GraphFactory getSynchronyzedGraphFactory() {
        return SYNCHRONIZED_FACTORY_INSTANCE;
    }
}
