package nl.han.ica.OOPDProcessingEngineHAN.Exceptions;

/**
 * Used by the TileType object.
 */
@SuppressWarnings("serial")
public class TileNotFoundException extends RuntimeException {

    public TileNotFoundException(String message) {
        super(message);
    }
}
