package com.morpho.morphosmart.sdk;


/**
 * 
 * This store different error codes of SDK MorphoSmart Android.
 * 
 */
public class ErrorCodes {

	/** No error. */
	public static final int MORPHO_OK = 0;
	
	/** Biometrics device performed an internal error. */
	public static final int MORPHOERR_INTERNAL = -1;
	
	/** Communication protocole error. */
	public static final int MORPHOERR_PROTOCOLE = -2;
	
	/** Can not connect biometrics device. */
	public static final int MORPHOERR_CONNECT = -3;
	
	/** Error while closing communication port. */
	public static final int MORPHOERR_CLOSE_COM = -4;
	
	/** Invalid parameter. */
	public static final int MORPHOERR_BADPARAMETER = -5;
	
	/** Not enough memory (in the PC). */
	public static final int MORPHOERR_MEMORY_PC = -6;
	
	/** Not enough memory for the creation of a database in the MSO. */
	public static final int MORPHOERR_MEMORY_DEVICE = -7;
	
	/** Authentication or Identification failed. */
	public static final int MORPHOERR_NO_HIT = -8;
	
	/** MSO returned an unknown status error. */
	public static final int MORPHOERR_STATUS = -9;
	
	/** The database is full. */
	public static final int MORPHOERR_DB_FULL = -10;
	
	/** The database is empty. */
	public static final int MORPHOERR_DB_EMPTY = -11;
	
	/** User has already been enrolled. */
	public static final int MORPHOERR_ALREADY_ENROLLED = -12;
	
	/** The specified base does not exist. */
	public static final int MORPHOERR_BASE_NOT_FOUND = -13;
	
	/** The specified base already exist. */
	public static final int MORPHOERR_BASE_ALREADY_EXISTS = -14;
	
	/** User object has been instanciated without MorphoDatabase.GetUser. */
	public static final int MORPHOERR_NO_ASSOCIATED_DB = -15;
	
	/**
	 * Database object has been instanciated without
	 * C_MORPHO_Device::GetDatabase.
	 */
	public static final int MORPHOERR_NO_ASSOCIATED_DEVICE = -16;
	
	/** The template is not valid. */
	public static final int MORPHOERR_INVALID_TEMPLATE = -17;
	
	/** Command not yet implemented in this release. */
	public static final int MORPHOERR_NOT_IMPLEMENTED = -18;
	
	/** No response after defined time. */
	public static final int MORPHOERR_TIMEOUT = -19;
	
	/** No templates have been registered. */
	public static final int MORPHOERR_NO_REGISTERED_TEMPLATE = -20;
	
	/** Field does not exist. */
	public static final int MORPHOERR_FIELD_NOT_FOUND = -21;
	
	/** Class has been corrupted. */
	public static final int MORPHOERR_CORRUPTED_CLASS = -22;
	
	/** There are too many templates. */
	public static final int MORPHOERR_TO_MANY_TEMPLATE = -23;
	
	/** There are too many fields. */
	public static final int MORPHOERR_TO_MANY_FIELD = -24;
	
	/** Templates with differents formats are mixed. */
	public static final int MORPHOERR_MIXED_TEMPLATE = -25;
	
	/** Command has been aborted. */
	public static final int MORPHOERR_CMDE_ABORTED = -26;
	
	/** Invalid PK format. */
	public static final int MORPHOERR_INVALID_PK_FORMAT = -27;
	
	/** User gave twice the same finger. */
	public static final int MORPHOERR_SAME_FINGER = -28;
	
	/** The number of the additional field is more than 128. */
	public static final int MORPHOERR_OUT_OF_FIELD = -29;
	
	/** UserID is not valid. */
	public static final int MORPHOERR_INVALID_USER_ID = -30;
	
	/** The user data are not valid. */
	public static final int MORPHOERR_INVALID_USER_DATA = -31;
	
	/** Additional field name length is more than MORPHO_FIELD_NAME_LEN. */
	public static final int MORPHOERR_FIELD_INVALID = -32;
	
	/** User is not found. */
	public static final int MORPHOERR_USER_NOT_FOUND = -33;
	
	/** Serial COM has not been opened. */
	public static final int MORPHOERR_COM_NOT_OPEN = -34;
	
	/** This element is already present in the list. */
	public static final int MORPHOERR_ELT_ALREADY_PRESENT = -35;
	
	/**
	 * You have to call C_MORPHO_Database::DbQueryFirst to initialize the
	 * querry.
	 */
	public static final int MORPHOERR_NOCALLTO_DBQUERRYFIRST = -36;
	
	/**
	 * The communication callback functions returns error between -10000 and
	 * -10499.
	 */
	public static final int MORPHOERR_USER = -37;
	
	/** The Compression is not valid. */
	public static final int MORPHOERR_BAD_COMPRESSION = -38;
	
	/** Security error. */
	public static final int MORPHOERR_SECU = -39;
	
	/** The MSO has not the certificate necessary to verify the signature. */
	public static final int MORPHOERR_CERTIF_UNKNOW = -40;
	
	/** The class has been destroyed. */
	public static final int MORPHOERR_INVALID_CLASS = -41;
	
	/** The specified Usb device is not plugged. */
	public static final int MORPHOERR_USB_DEVICE_NAME_UNKNOWN = -42;
	
	/** The certificate is not valid. */
	public static final int MORPHOERR_CERTIF_INVALID = -43;
	
	/**
	 * The certificate identity is not the same than the X984 certificate
	 * identity.
	 */
	public static final int MORPHOERR_SIGNER_ID = -44;
	
	/**
	 * The X984 certificate identity size is different to 20 octets (SHA_1
	 * size).
	 */
	public static final int MORPHOERR_SIGNER_ID_INVALID = -45;
	
	/** False Finger Detected. */
	public static final int MORPHOERR_FFD = -46;
	
	/** The finger can be too moist or the scanner is wet. */
	public static final int MORPHOERR_MOIST_FINGER = -47;
	
	/**
	 * The Morpho MorphoSmart Service Provider Usb Server is stopped or not
	 * installed.
	 */
	public static final int MORPHOERR_NO_SERVER = -48;
	
	/** No parameter has been initialized. */
	public static final int MORPHOERR_OTP_NOT_INITIALIZED = -49;
	
	/** Code pin is needed : it is the first enrollment. */
	public static final int MORPHOERR_OTP_PIN_NEEDED = -50;
	
	/** User is not allowed to be reenrolled. */
	public static final int MORPHOERR_OTP_REENROLL_NOT_ALLOWED = -51;
	
	/** Enrollment failed. */
	public static final int MORPHOERR_OTP_ENROLL_FAILED = -52;
	
	/** Identification failed. */
	public static final int MORPHOERR_OTP_IDENT_FAILED = -53;
	
	/** No more OTP available (sequence number = 0). */
	public static final int MORPHOERR_NO_MORE_OTP = -54;
	
	/** Authentication or Identification failed. */
	public static final int MORPHOERR_OTP_NO_HIT = -55;
	
	/** Enrollment needed before generating OTP. */
	public static final int MORPHOERR_OTP_ENROLL_NEEDED = -56;
	
	/** The device is locked. */
	public static final int MORPHOERR_DEVICE_LOCKED = -57;
	
	/** The device is not locked. */
	public static final int MORPHOERR_DEVICE_NOT_LOCK = -58;
	
	/** ILV_OTP_GENERATE Locked. */
	public static final int MORPHOERR_OTP_LOCK_GEN_OTP = -59;
	
	/** ILV_OTP_SET_PARAMETERS Locked. */
	public static final int MORPHOERR_OTP_LOCK_SET_PARAM = -60;
	
	/** ILV_OTP_ENROLL_USER Locked. */
	public static final int MORPHOERR_OTP_LOCK_ENROLL = -61;
	
	/**
	 * Security level mismatch: attempt to match fingerprint template in high
	 * security level (MorphoSmart™ FINGER VP only).
	 */
	public static final int MORPHOERR_FVP_MINUTIAE_SECURITY_MISMATCH = -62;
	
	/**
	 * Misplaced or withdrawn finger has been detected during acquisition
	 * (MorphoSmart™ FINGER VP only).
	 */
	public static final int MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN = -63;
	
	/** A required license is missing. */
	public static final int MORPHOERR_LICENSE_MISSING = -64;

	/** A required license is missing. */
	public static final int MORPHOERR_CANT_GRAN_PERMISSION_USB = -99;
	
	/** A required license is missing. */
	public static final int CLASS_NOT_INSTANTIATED = -98;
	
	/** USB Permission denied. */
	public static final int MORPHOERR_USB_PERMISSION_DENIED = -97;
	
	
	/**
	 * Private constructor, do not use.
	 */
	private ErrorCodes() {
	}
	
	/**
	 * Get error message corresponding to given error code  and internal error code
	 * @param codeError : error code
	 * @param internalError : internal error code
	 * @return corresponding error message
	 */
	public static String getError(int codeError,int internalError ) {
		switch (codeError) {
		case MORPHO_OK:
			return "No error.";
		case MORPHOERR_INTERNAL:
			return "Biometrics device performed an internal error.";
		case MORPHOERR_PROTOCOLE:
			return "Communication protocole error.";
		case MORPHOERR_CONNECT:
			return "Can not connect biometrics device.";
		case MORPHOERR_CLOSE_COM:
			return "Error while closing communication port.";
		case MORPHOERR_BADPARAMETER:
			return "Invalid parameter.";
		case MORPHOERR_MEMORY_PC:
			return "Not enough memory (in the PC).";
		case MORPHOERR_MEMORY_DEVICE:
			return "Not enough memory for the creation of a database in the MSO.";
		case MORPHOERR_NO_HIT:
			return "Authentication or Identification failed.";
		case MORPHOERR_STATUS:
			return "MSO returned an unknown status error.";
		case MORPHOERR_DB_FULL:
			return "The database is full.";
		case MORPHOERR_DB_EMPTY:
			return "The database is empty.";
		case MORPHOERR_ALREADY_ENROLLED:
			return "User has already been enrolled.";
		case MORPHOERR_BASE_NOT_FOUND:
			return "The specified base does not exist.";
		case MORPHOERR_BASE_ALREADY_EXISTS:
			return "The specified base already exist.";
		case MORPHOERR_NO_ASSOCIATED_DB:
			return "User object has been instanciated without C_MORPHO_Database::GetUser.";
		case MORPHOERR_NO_ASSOCIATED_DEVICE:
			return "Database object has been instanciated without C_MORPHO_Device::GetDatabase.";
		case MORPHOERR_INVALID_TEMPLATE:
			return "The template is not valid.";
		case MORPHOERR_NOT_IMPLEMENTED:
			return "Command not yet implemented in this release.";
		case MORPHOERR_TIMEOUT:
			return "No response after defined time.";
		case MORPHOERR_NO_REGISTERED_TEMPLATE:
			return "No templates have been registered.";
		case MORPHOERR_FIELD_NOT_FOUND:
			return "Field does not exist.";
		case MORPHOERR_CORRUPTED_CLASS:
			return "Class has been corrupted.";
		case MORPHOERR_TO_MANY_TEMPLATE:
			return "There are too many templates.";
		case MORPHOERR_TO_MANY_FIELD:
			return "There are too many fields.";
		case MORPHOERR_MIXED_TEMPLATE:
			return "Templates with differents formats are mixed.";
		case MORPHOERR_CMDE_ABORTED:
			return "Command has been aborted.";
		case MORPHOERR_INVALID_PK_FORMAT:
			return "Invalid PK format.";
		case MORPHOERR_SAME_FINGER:
			return "User gave twice the same finger.";
		case MORPHOERR_OUT_OF_FIELD:
			return "The number of the additional field is more than 128.";
		case MORPHOERR_INVALID_USER_ID:
			return "UserID is not valid.";
		case MORPHOERR_INVALID_USER_DATA:
			return "The user data are not valid.";
		case MORPHOERR_FIELD_INVALID:
			return "Additional field name length is more than MORPHO_FIELD_NAME_LEN.";
		case MORPHOERR_USER_NOT_FOUND:
			return "User is not found.";
		case MORPHOERR_COM_NOT_OPEN:
			return "Serial COM has not been opened.";
		case MORPHOERR_ELT_ALREADY_PRESENT:
			return "This element is already present in the list.";
		case MORPHOERR_NOCALLTO_DBQUERRYFIRST:
			return "You have to call C_MORPHO_Database::DbQueryFirst to initialize the querry.";
		case MORPHOERR_USER:
			return "The communication callback functions returns error between -10000 and -10499.";
		case MORPHOERR_BAD_COMPRESSION:
			return "The Compression is not valid.";
		case MORPHOERR_SECU:
			return "Security error.";
		case MORPHOERR_CERTIF_UNKNOW:
			return "The MSO has not the certificate necessary to verify the signature.";
		case MORPHOERR_INVALID_CLASS:
			return "The class has been destroyed.";
		case MORPHOERR_USB_DEVICE_NAME_UNKNOWN:
			return "The specified Usb device is not plugged.";
		case MORPHOERR_CERTIF_INVALID:
			return "The certificate is not valid.";
		case MORPHOERR_SIGNER_ID:
			return "The certificate identity is not the same than the X984 certificate identity.";
		case MORPHOERR_SIGNER_ID_INVALID:
			return "The X984 certificate identity size is different to 20 octets (SHA_1 size).";
		case MORPHOERR_FFD:
			return "False Finger Detected.";
		case MORPHOERR_MOIST_FINGER:
			return "The finger can be too moist or the scanner is wet.";
		case MORPHOERR_NO_SERVER:
			return "The Morpho MorphoSmart Service Provider Usb Server is stopped or not installed.";
		case MORPHOERR_OTP_NOT_INITIALIZED:
			return "No parameter has been initialized.";
		case MORPHOERR_OTP_PIN_NEEDED:
			return "Code pin is needed : it is the first enrollment.";
		case MORPHOERR_OTP_REENROLL_NOT_ALLOWED:
			return "User is not allowed to be reenrolled.";
		case MORPHOERR_OTP_ENROLL_FAILED:
			return "Enrollment failed.";
		case MORPHOERR_OTP_IDENT_FAILED:
			return "Identification failed.";
		case MORPHOERR_NO_MORE_OTP:
			return "No more OTP available (sequence number = 0).";
		case MORPHOERR_OTP_NO_HIT:
			return "Authentication or Identification failed.";
		case MORPHOERR_OTP_ENROLL_NEEDED:
			return "Enrollment needed before generating OTP.";
		case MORPHOERR_DEVICE_LOCKED:
			return "The device is locked.";
		case MORPHOERR_DEVICE_NOT_LOCK:
			return "The device is not locked.";
		case MORPHOERR_OTP_LOCK_GEN_OTP:
			return "ILV_OTP_GENERATE Locked.";
		case MORPHOERR_OTP_LOCK_SET_PARAM:
			return "ILV_OTP_SET_PARAMETERS  Locked.";
		case MORPHOERR_OTP_LOCK_ENROLL:
			return "ILV_OTP_ENROLL_USER Locked.";
		case MORPHOERR_FVP_MINUTIAE_SECURITY_MISMATCH:
			return "Security level mismatch: attempt to match fingerprint template in high security level (MorphoSmart FINGER VP only).";
		case MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN:
			return "Misplaced or withdrawn finger has been detected during acquisition (MorphoSmart FINGER VP only).";
		case MORPHOERR_LICENSE_MISSING:
			return "A required license is missing.";
		case MORPHOERR_CANT_GRAN_PERMISSION_USB:
			return "Could not grant permissions to USB";
		case MORPHOERR_USB_PERMISSION_DENIED:
			return "USB permission denied.";
		default:
			return String.format("Unknown error %d, Internal Error = %d",codeError,internalError);
		}
	}
}
