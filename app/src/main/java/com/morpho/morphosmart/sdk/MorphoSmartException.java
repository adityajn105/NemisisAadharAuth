package com.morpho.morphosmart.sdk;


/**
 * This class manage runtime exceptions of the SDK MorphoSmart Android.
 * 
 */
public class MorphoSmartException extends RuntimeException{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String error_msg = "";

	/**
     * Default constructor.
     */
    public MorphoSmartException() {
        super();
    }

    public MorphoSmartException(final String message) {
        super(message);
    }

    public MorphoSmartException(final Throwable cause) {
        super(cause);
    }

    public MorphoSmartException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public MorphoSmartException(final int codeError, final String message, final Throwable cause) {
    	super(message, cause);
    	error_msg = ErrorCodes.getError(codeError, 0);
    }
    
    public String getErrorMessage()
    {
    	return error_msg;
    }
}
