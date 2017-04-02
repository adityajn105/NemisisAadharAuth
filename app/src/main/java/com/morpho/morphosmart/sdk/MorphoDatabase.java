package com.morpho.morphosmart.sdk;

import java.util.Observer;

/**
 * Class performing operations on the embedded database
 * This class performs operations related to the biometric device local database.
 * As a database is always associated to a device, MorphoDatabase has to be instantiated
 * with MorphoDevice.GetDatabase().
 */

public class MorphoDatabase implements Cloneable
{
	private static MorphoDatabaseNative morphoDatabaseNative = new MorphoDatabaseNative();
	private Long morphoDatabasePointerCPP  = Long.valueOf(0);
	protected boolean cppMemOwn = false;
	
	/** constructor */
	public MorphoDatabase()
	{
		long cppPtr = morphoDatabaseNative.getCPPInstance();
		
		if (cppPtr != 0)
		{
			this.cppMemOwn = true;
			this.morphoDatabasePointerCPP = cppPtr;			
		}
		else
		{
			try{
				throw new MorphoSmartException("cppPtr is null");
			} catch (MorphoSmartException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/** copy constructor */
	public MorphoDatabase(MorphoDatabase database)
	{
		if(!database.cppMemOwn)
		{
			long cppPtr = morphoDatabaseNative.getCPPInstance(database.morphoDatabasePointerCPP);
			
			if (cppPtr != 0)
			{
				this.cppMemOwn = true;
				this.morphoDatabasePointerCPP = cppPtr;			
			}
			else
			{
				try{
					throw new MorphoSmartException("cppPtr is null");
				} catch (MorphoSmartException e) {
					e.printStackTrace();
				}	
			}
		}
		else
		{
			//TODO : trow Exception		
		}
	}
	
	/** destructor */
	protected void finalize()
	{
		if(this.cppMemOwn)
		{
			morphoDatabaseNative.deleteInstance(this.morphoDatabasePointerCPP);
			this.cppMemOwn = false;
		}
	}

	public Object clone()
	{
		return new MorphoDatabase(this);
	}
	
	public long getMorphoDatabasePointerCPP()
	{
		return morphoDatabasePointerCPP;
	}
	
	public void setMorphoDatabasePointerCPP(long morphoDatabasePointerCPP)
	{
		this.morphoDatabasePointerCPP = morphoDatabasePointerCPP;
		this.cppMemOwn = true;
	}
	/** This function returns a user instance.
 	@param user ID
 	@param MorphoUser 
 	@return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range.  
 */
	public int getUser(String userID,MorphoUser morphoUser)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		
		return morphoDatabaseNative.getUser(this.morphoDatabasePointerCPP,userID,morphoUser);
	}
	
	public int putField(MorphoField morphoField, Integer index)
	{		
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.putField(this.morphoDatabasePointerCPP,morphoField,index);
	}
	
	/** This method overloads the one inherited from MorphoFieldDescriptor. This function returns one-field characteristics. 
	 * @param  Index Field index to retrieve characteristics of. 0 is used for the UserID. First supplementary field index is 1. 
	 * 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice::GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_FIELD_NOT_FOUND Field not found.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/	
	public int getField(int index, MorphoField morphoField)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getField(this.morphoDatabasePointerCPP,index,morphoField);
	}
	
	/** This function creates a biometric database in flash memory. Before calling this method, the database internal structure has to be defined (see MorphoFieldDescriptor for more details). Once a database is created, its internal structure can not be modified. 
	 * 
	 * @param maxRecord Number of records reserved in the database (i.e. maximum number of persons enrolled). 
	 * @param maxNbFinger Maximum number of fingerprints which can be stored in each record of the database (1, 2, 10 or 20). At record generation, the terminal allows the Host System to save less fingerprints per user than specified in the Create Database command (i.e. 1 in case of 2 fingerprints maximum per record).
- In case of MorphoSmart™ FINGER VP, the maximum number of fingerprints per record cannot be more than two. 
	 * @param templateType The only allowed values are MORPHO_PK_COMP and MORPHO_PK_COMP_NORM. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice::GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_ALREADY_EXISTS This database already exists.  
		- MORPHOERR_NO_SPACE_LEFT The database can not be created because there is not enough memory left.  
		- MORPHOERR_BADPARAMETER - Wrong number of fingers.- No UserID found in array i_px_FieldDescriptorArray.  
		- MORPHOERR_OUT_OF_FIELD The number of additional fields is greater than MORPHO_NB_FIELD_MAX.  
		- MORPHOERR_FIELD_INVALID Additional field name length is greater than MORPHO_FIELD_NAME_LEN  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_INVALID_PK_FORMAT Incorrect value for i_x_TemplateType.  
*/
	public int dbCreate(int maxRecord, int maxNbFinger,TemplateType templateType)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.dbCreate(this.morphoDatabasePointerCPP, maxRecord,  maxNbFinger, templateType.getCode());
	}
	/** This function retrieves a list of public fields from all database records. Private fields can not be retrieved.
	 * @param FieldIndexDescriptor Bit array that represents fields that must be retrieved. Bit 0 is reserved for the UserID (unique identifier) field. 
		- Example: 10100000 00000001 00000000 00000000 designates field #0 (UserID), field #2 and field #15. To avoid errors, it is recommended to fill this parameter with FillIndexDescriptor() method.
     * @param UserList Users list. Field content is retrieved by  GetField() method (see MorphoFieldList). 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND There is no Database corresponding to the Identifier specified in the Request.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_FIELD_INVALID One or more desired fields are not found.  
		- MORPHOERR_USER_NOT_FOUND User does not exist.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range.  
 	 * */
	public int readPublicFields(int [] fieldsIndex,MorphoUserList morphoUserList)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.readPublicFields(this.morphoDatabasePointerCPP, fieldsIndex,  morphoUserList);
	}
	/**This function erases all records in the local database or deletes the local database.
	 * @param morphoTypeDeletion 
	 * 		- MORPHO_ERASE_BASE: This option erases all records in a local database without deleting the database. 
	 * 		- MORPHO_DESTROY_BASE: This option deletes the local database. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted. 
*/
	
	public int dbDelete(MorphoTypeDeletion morphoTypeDeletion)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.dbDelete(this.morphoDatabasePointerCPP,morphoTypeDeletion.ordinal());
	}
	/** This function identifies a live finger against the local database, and returns the associated user. 
	* @param Timeout Finger detection timeout in seconds. Its value must be between 0 and 0xFFFF. 0 corresponds to an infinite timeout.  
	* @param FAR This parameter specifies how tight the matching threshold is. Morpho recommends MORPHO_FAR_5 (see paragraph "T_ MORPHO_FAR"). 
	* @param CoderChoice Contains the biometric coder to use (MORPHO_MSO_V9_CODER or MORPHO_MSO_V9_JUV_CODER). Morpho recommends using MORPHO_MSO_V9_CODER. Please refer to the MorphoSmartHostInterface document for details.  
	* @param detectModeChoice : Bitmask of the following:
		- MORPHO_VERIF_DETECT_MODE: more permissive mode than default; MorphoSmart™ detects more easily finger presence, but might issue lower quality templates. 
		- MORPHO_ENROLL_DETECT_MODE: strongest detection mode (default mode). 
		- MORPHO_WAKEUP_LED_OFF: (only available on MorphoSmart™ MSO FFD) leds are turned off while waiting for a finger (impedance wakeup). 
		- MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE: (not available on MorphoSmart™ CBM-V) force the finger to cover the top of the capture area to increase quality. 
		- MORPHO_WAKEUP_LED_ON: (only available on MorphoSmart™ FINGER VP) leds are turned on while waiting for a finger. 
	* @param MatchingStrategy Value among of the following:
		- MORPHO_STANDARD_MATCHING_STRATEGY: default strategy. 
		- MORPHO_ADVANCED_MATCHING_STRATEGY: less FRR, but more processing time (not available on MorphoSmart™ FINGER VP). 
	* @param CallbackCmd Binary mask with T_MORPHO_CALLBACK_COMMAND elements. This mask describes the asynchronous status events that will trig the callback function. 0 if you do not want any asynchronous status to be received. For example MORPHO_CALLBACK_COMMAND_CMD | MORPHO_CALLBACK_IMAGE_CMD means we want to receive the command status (move finger up...) and low-resolution images.  
	* @param Callback
	* @param resultMatching 
	* @param MorphoUser User MorphoUser instance of the user retrieved. Field contents are read with GetField() method.  
	@return values:
		- MORPHO_OK The matching was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER The matching threshold value or timeout value are out of the range.  
		- MORPHOERR_TIMEOUT The finger detection timeout has expired.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_NO_HIT The function returned a No Hit.  
		- MORPHOERR_CMDE_ABORTED Command is canceled.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_FFD False Finger Detected.  
		- MORPHOERR_MOIST_FINGER The finger can be too moist or the scanner is wet.  
		- MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN Finger was misplaced or has been withdrawn from sensor during acquisition (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_FFD_FINGER_MISPLACED Finger was misplaced during acquisition (MorphoSmart™ MSO FFD only). */  

	public int identify(int timeout,
						int far,
						Coder coder,
						int detectModeChoice,
						int matchingStrategy,
						int callbackCmd,
						Observer callback,
						ResultMatching resultMatching,
						MorphoUser morphoUser)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.identify(this.morphoDatabasePointerCPP, timeout, far, coder.getCode(), detectModeChoice, matchingStrategy, callbackCmd, callback, resultMatching, morphoUser);
	}
	
	/** This function identifies one unique template against the local database. This function is successful if one of the searched templates matches a reference template in the local database. The maximum number of search template is 1.
	 * @param FAR :This parameter specifies how tight the matching threshold is. Morpho recommends MORPHO_FAR_5 (see paragraph "T_ MORPHO_FAR").  
	 * @param TemplateList : Candidate template list. This list must include one unique template. The template format should be MORPHO_PK_COMP. The other template formats (MORPHO_PK_COMP_NORM, MORPHO_PK_MAT, MORPHO_PK_MAT_NORM, MORPHO_PK_ANSI_378, MORPHO_PK_MINEX_A, MORPHO_PK_ISO_FMR, MORPHO_PK_ISO_FMC_NS or MORPHO_PK_ISO_FMC_CS) are also supported but Morpho recommends using them only for compatibility with existing systems or specific usage. Only secure MorphoSmart™ accept the X984 biometric token and verify the integrity. 
	 * @param User : MorphoUser instance of the user retrieved. Field contents are read by GetField() method.  
	 * @return values:
		- MORPHO_OK The matching was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER The matching threshold value or timeout value are out of range.  
		- MORPHOERR_INVALID_TEMPLATE The reference minutiae are not valid (corrupted minutiae).  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_NO_HIT The function returned a No Hit.  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_LICENSE_MISSING A required license is missing (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_INVALID_PK_FORMAT Invalid template format  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int identifyMatch(int far,TemplateList templateList,MorphoUser morphoUser)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.identifyMatch(this.morphoDatabasePointerCPP, far, templateList, morphoUser);
	}

	/** This function cancels the live acquisition. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
*/
	public int cancelLiveAcquisition()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.cancelLiveAcquisition(this.morphoDatabasePointerCPP);
	}
	
	/** This function retrieves the first user instance that matches a searched buffer in a specified field. Private fields can not be retrieved. If multiple instances are present, the first one is retrieved. This method is successful if one user field exactly matches the searched pattern (same size, same content).
	 * @param fieldIndex The search is performed with this field index (0 means UserID, 1 means first supplementary field ...).   
	 * @param dataToFind Pattern to search. 
	 * @param morphoUser First user found with a matching field. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND There is no Database corresponding to the Identifier specified in the Request.  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_USER_NOT_FOUND No record contains the searched data.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted 
*/
	public int dbQueryFirst(int fieldIndex, String dataToFind, MorphoUser morphoUser)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.dbQueryFirst(this.morphoDatabasePointerCPP, fieldIndex, dataToFind, morphoUser);
	}
	
	/**This function must be called after DbQueryFirst(). This function retrieves the next user instance that matches the searched buffer. This method is successful if one user field exactly matches the searched pattern (same size, same content).
	 * @param morphoUser : Next user found with a matching field. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND There is no Database corresponding to the Identifier specified in the Request.  
		- MORPHOERR_BADPARAMETER DbQueryFirst () has not previously been called.  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_USER_NOT_FOUND No record contains the searched data.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_NOCALLTO_DBQUERRYFIRST You have to call MorphoDatabase.DbQueryFirst to initialize the querry  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int dbQueryNext(MorphoUser morphoUser)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.dbQueryNext(this.morphoDatabasePointerCPP, morphoUser);
	}

	/** Get the Pk Format */
	public int getFormatPK(Integer templateType)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getFormatPK(this.morphoDatabasePointerCPP, templateType);
	}
	
	/** This function retreives Maximum number of database allowed by the configuration. 
	 * @param MaxDataBase return the Maximum number of database.
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range.  

	 * */
	public int getMaxDataBase(Integer maxdataBase)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getMaxDataBase(this.morphoDatabasePointerCPP, maxdataBase);
	}
	
	/** This function retreives the Maximum number of records per database allowed by the configuration. 
	 * @param MaxUser : return the Maximum number of records per database. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range.  
*/
	public int getMaxUser(Integer maxUser)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getMaxUser(this.morphoDatabasePointerCPP, maxUser);
	}
	
	/**This function retreives the number of fields in the database. 
	 * @param NbField Number of fields in the database. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	 */
	public int getNbField(Long nbField)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getNbField(this.morphoDatabasePointerCPP, nbField);
	}
		
	/** This function returns the maximum number of templates per person in the database.
	 * @param NbFinger Maximum number of templates per person in the database (from 1 to MORPHO_FINGER_MAX). 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int getNbFinger(Integer nbFinger)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getNbFinger(this.morphoDatabasePointerCPP, nbFinger);
	}
	
	/** This function returns the number of free records in the local database. 
	 * @param nbFreeRecord number of free records in the local database. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int getNbFreeRecord(Long nbFreeRecord)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getNbFreeRecord(this.morphoDatabasePointerCPP, nbFreeRecord);
	}
	
	/** This function returns the total number of records reserved in the local database. 
	 * @param nbTotalRecord total number of records reserved in the local database. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int getNbTotalRecord(Long nbTotalRecord)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getNbTotalRecord(this.morphoDatabasePointerCPP, nbTotalRecord);
	}
	
	/** This function returns the number of registered records in the local database. 
	 * @param nbUsedRecord : number of assigned records in the local database. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DEVICE Database is created without MorphoDevice.GetDatabase.  
		- MORPHOERR_INTERNAL An error occurred during the execution of the function.  
		- MORPHOERR_BASE_NOT_FOUND The specified database doesn't exist.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int getNbUsedRecord(Long nbUsedRecord)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDatabaseNative.getNbUsedRecord(this.morphoDatabasePointerCPP, nbUsedRecord);
	}
}
/**
 * Corresponding native class of MorphoDatabase.
 */
class MorphoDatabaseNative
{
	static {
			System.loadLibrary("NativeMorphoSmartSDK");
	}
		
	public native long getCPPInstance();

	public native long getCPPInstance(long morphoDatabasePointerCPP);

	public native long deleteInstance(long morphoDatabasePointerCPP);
	
	public native int dbDelete(long morphoDatabasePointerCPP, int ordinal) ;

	public native int readPublicFields(long morphoDatabasePointerCPP,int[] fieldsIndex, MorphoUserList morphoUserList) ;

	public native int dbCreate(long morphoDatabasePointerCPP, int maxRecord, int maxNbFinger, int templateType) ;

	public native int getField(long morphoDatabasePointerCPP, int index,MorphoField morphoField);

	public native int putField(long morphoDatabasePointerCPP, MorphoField morphoField,Integer index) ;

	public native int getUser(long morphoDatabasePointerCPP,String userID,MorphoUser morphoUser);
	
	public native int identify(	long morphoDatabasePointerCPP,
								int timeout,
								int far,
								int coderChoice,
								int detectModeChoice,
								int matchingStrategy,
								int callbackCmd,
								Observer callback,
								ResultMatching resultMatching,
								MorphoUser morphoUser);
	
	public native int identifyMatch(long morphoDatabasePointerCPP,
									int far,
									TemplateList templateList,
									MorphoUser morphoUser
									);
	
	public native int cancelLiveAcquisition(long morphoDatabasePointerCPP);
	
	public native int dbQueryFirst(long morphoDatabasePointerCPP, int fieldIndex, String dataToFind, MorphoUser morphoUser);
	
	public native int dbQueryNext(long morphoDatabasePointerCPP, MorphoUser morphoUser);
		
	public native int getFormatPK(long morphoDatabasePointerCPP, Integer templateType);
	
	public native int getMaxDataBase(long morphoDatabasePointerCPP, Integer maxdataBase);
	
	public native int getMaxUser(long morphoDatabasePointerCPP, Integer maxUser);
	
	public native int getNbField(long morphoDatabasePointerCPP, Long nbField);
	
	public native int getNbFinger(long morphoDatabasePointerCPP, Integer nbFinger);
	
	public native int getNbFreeRecord(long morphoDatabasePointerCPP, Long nbFreeRecord);
	
	public native int getNbTotalRecord(long morphoDatabasePointerCPP, Long nbTotalRecord);
	
	public native int getNbUsedRecord(long morphoDatabasePointerCPP, Long nbUsedRecord);
}
