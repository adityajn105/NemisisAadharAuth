package com.morpho.morphosmart.sdk;

import java.util.Observer;
/**
 * 
 * This class performs the operations related to a defined user 
 *
 */
public class MorphoUser implements Cloneable
{
	private static MorphoUserNative morphoUserNative = new MorphoUserNative();
	private Long morphoUserPointerCPP  = Long.valueOf(0);
	private String userID;
	protected boolean cppMemOwn = false;
	
	
	/** constructor */
	public MorphoUser()
	{
		long cppPtr = morphoUserNative.getCPPInstance();
		
		if (cppPtr != 0)
		{
			this.cppMemOwn = true;
			this.morphoUserPointerCPP = cppPtr;
		}
		else
		{
			try {
				throw new MorphoSmartException("classe non instantiÃ©");
			} catch (MorphoSmartException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** constructor with argument */
	public MorphoUser(String userID)
	{
		long cppPtr = morphoUserNative.getCPPInstance(userID);
		
		if (cppPtr != 0)
		{
			this.cppMemOwn = true;
			this.morphoUserPointerCPP = cppPtr;
			this.userID = userID;
		}
		else
		{
			try {
				throw new MorphoSmartException("classe non instantiÃ©");
			} catch (MorphoSmartException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** copy constructor */
	public MorphoUser(MorphoUser user)
	{
		if(!user.cppMemOwn)
		{
			long cppPtr = morphoUserNative.getCPPInstance(user.morphoUserPointerCPP);
			
			if (cppPtr != 0)
			{
				this.cppMemOwn = true;
				this.morphoUserPointerCPP = cppPtr;
			}
			else
			{
				try {
					throw new MorphoSmartException("classe non instantiÃ©");
				} catch (MorphoSmartException e) {
					e.printStackTrace();
				}		
			}
		}
		else
		{
			try {
				throw new MorphoSmartException("classe non instantiÃ©");
			} catch (MorphoSmartException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** default destructor */
	protected void finalize()
	{
		if(this.cppMemOwn)
		{
			morphoUserNative.deleteInstance(this.morphoUserPointerCPP);
			this.cppMemOwn = false;
		}
	}

	public Object clone()
	{
		return new MorphoUser(this);
	}
	
	public void setMorphoUserPointerCPP(long morphoUserPointerCPP)
	{
		this.morphoUserPointerCPP = morphoUserPointerCPP;	
		this.cppMemOwn = true;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @return the noCheckOnTemplate
	 */
	public boolean isNoCheckOnTemplate() {
		//	
		return true;
	}

	/**
	 * @return the indexUser
	 */
	public int getIndexUser() {
		//TODO
		return 0;
	}

	/**
	 * @return the morphoUserPointerCPP
	 */
	public long getMorphoUserPointerCPP() {
		return morphoUserPointerCPP;
	}
	
	/**This function cancels the live acquisition.
	 * @param None
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_NO_ASSOCIATED_DB Database is created without MorphoDatabase.GetUser.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
 */
	public int cancelLiveAcquisition()
	{
		return morphoUserNative.cancelLiveAcquisition(this.morphoUserPointerCPP);
	}
	
	/** This function removes the user from the local database. 
	 * @param None
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_INVALID_USER_ID The UserID does not exist in the database.  
		- MORPHOERR_BASE_NOT_FOUND The database does not exist.  
		- MORPHOERR_USER_NOT_FOUND The user doesn't exist in the database.  
		- MORPHOERR_NO_ASSOCIATED_DB Database is created without MorphoDatabase.GetUser.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  */ 
	public int dbDelete()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.dbDelete(this.morphoUserPointerCPP);
	}
	
	/** This command creates a new record in the local database with user characteristics. If a record with the same identifier already exists 
	 * the command fails (VerifyAndUpdateToDatabase() should be used in this case). Prior calling this method, you should fill up template list and 
	 * field list using TemplateList.PutTemplate() and MorphoFieldList.PutField(). 
	
	 * @param None
	 * @return values:
		- MORPHO_OK The execution of the function succeeded.  
		- MORPHOERR_INTERNAL An error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER Invalid parameter. You should call TemplateList.PutTemplate().  
		- MORPHOERR_INVALID_USER_ID The record identifier already exists in the database.  
		- MORPHOERR_INVALID_TEMPLATE The reference template is not valid: bad identifier, corrupted minutiae.  
		- MORPHOERR_ALREADY_ENROLLED The person is already in this database. Call SetNoCheckOnTemplateForDBStore to force enrollment.  
		- MORPHOERR_BASE_NOT_FOUND Database doesn't exist.  
		- MORPHOERR_DB_FULL The maximum number of users that can be stored in the database has been reached.  
		- MORPHOERR_SAME_FINGER User give twice the same finger.  
		- MORPHOERR_FIELD_NOT_FOUND Invalid field number.  
		- MORPHOERR_NO_ASSOCIATED_DB Database is created without MorphoDatabase.GetUser  
		- MORPHOERR_LICENSE_MISSING A required license is missing (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_PROTOCOLE Communication protocol error  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
 */
	public int dbStore()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.dbStore(this.morphoUserPointerCPP);
	}
	
	/**This function specifies whether templates verification will be done or not when a new record is created (cf CUSer::DBStore method). By default, it is not allowed to add the record of a user that is already enrolled in the database.
	 * @param noCheckOnTemplate Set this parameter to TRUE to add a record without templates verification. This option is useful to reduce the time taken to fill large databases. In this case, the database coherence must be previously checked. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
*/
	public int setNoCheckOnTemplateForDBStore(boolean noCheckOnTemplate)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.setNoCheckOnTemplateForDBStore(this.morphoUserPointerCPP,noCheckOnTemplate);
	}
	
	
	/**
	 * Add a field to the list
	 *
	 * @param fieldIndex Index field : Index can not be 0 because this value is reserved for UserID.
	 * First available index is 1.
	 * @param fieldData Data stored in the field.
	 *
	 * @return values :
	 * 	- MORPHO_OK	The execution of the function was successful.
	 * 	- MORPHOERR_MEMORY_PC	Not enough memory on the PC.
	 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
	 *	- MORPHOERR_BADPARAMETER  One or more input parameters are out of range.  
	 */
	
	public int putField(int fieldIndex,String fieldData)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		if (fieldData.equalsIgnoreCase("")) fieldData = " ";
		return morphoUserNative.putField(this.morphoUserPointerCPP,fieldIndex,fieldData);
	}
	
	
	/**
	 * Retrieves a field structure definition from the list.
	 *
	 * @param fieldIndex Field index (index can not be 0 because it is reserved for UserID, first available is 1).
	 * @return Data stored in the field.
	 */
	
	public String getField(int fieldIndex) throws MorphoSmartException
	{	
		MorphoString fieldData = new MorphoString();
		int ret = morphoUserNative.getField(this.morphoUserPointerCPP,fieldIndex,fieldData );
		
		if(ret != ErrorCodes.MORPHO_OK)
		{
			throw(new MorphoSmartException(ErrorCodes.getError(ret, 0)));
		}
		return fieldData.getData();
	}
	/**This function captures and enrolls a live finger. The number of fingers can be specified. The calculated minutiae can also be exported to the host. 
	 * The minutiae are calculated after three fingerprint image acquisitions (the user has to put each finger 3 times on the MorphoSmart™ sensor). 
	 * To obtain the best accuracy, it is strongly recommended to use the fore, the thumb or the middle fingers. Acquired templates and fields can be 
	 * accessed thanks to methods defined in TemplateList and MorphoFieldList classes. Fingerprint quality and advanced security levels compatibility
	 * (MorphoSmart™ FINGER VP only) are only available if export of templates is activated. TemplateList.GetFVPTemplate must be called to retreive multimodal template.
	 * TemplateList.GetTemplate must be called to retreive fingerprint template.
	 * @param timeout Finger detection timeout in seconds. Its value must be between 0 and 0xFFFF. 0 corresponds to an infinite timeout.  
	 * @param acquisitionThreshold Minimum value of fingerprint quality. This value can be 0 (strongly recommended) or any value between 20 and 100.  
	 * @param advancedSecurityLevelsRequired This parameter is supported only by MorphoSmart™ FINGER VP. This parameter can be set to
		- 1 : to get multimodal templates compatible with advanced security levels (levels greater than Standard) 
		- 0 : to get information about the multimodal templates compatibilty with advanced security levels. 
		- 0xFF : neither be informed nor force multimodal templates compatibility with advanced security levels. 
	 * @param - compressAlgo Compression algorithm to be used to compress the fingerprint image. Available algorithms are:
		- MORPHO_NO_COMPRESS 
		- MORPHO_COMPRESS_V1 
		- MORPHO_COMPRESS_WSQ 
		- Image export is activated with TemplateList.SetActiveFullImageRetrieving(). 
	* @param compressRate Compression rate used by the fingerprint image compression algorithm:
		- useless for MORPHO_NO_COMPRESS and MORPHO_COMPRESS_V1 algorithms (must be set to 0). 
		- can vary between 2 and 256 for MORPHO_COMPRESS_WSQ algorithm, usual recommended value is 10.  
	* @param exportMinutiae Defines the format of the exported minutiae.
		- Set this value to 0 to exclude the calculated minutiae from the reply. 
		- Set this value to 1 to export the minutiae with its default size. 
		- For MORPHO_PK_COMP fingerprint template format only, this field can be set to a value from 170 (0xAA) to 255 (0xFF) to limit the size of the fingerprint template. If the fingerprint template size is higher than the required value, it is compressed to match the requirement before being included in the reply, otherwise the fingerprint template is included without modification. It means that the fingerprint template size is less or equal to the specified value. 
		- For MORPHO_PK_ANSI_378, MORPHO_PK_ISO_FMC_CS, MORPHO_PK_ISO_FMC_NS and MORPHO_PK_ISO_FMR fingerprint template formats, this field can be set to a value from 2 (0x02) to 255 (0xFF) to limit the number of PK (minutiae) in the fingerprint template.
 	* @param fingerNumber The number of fingers to enroll. This function can enroll 1 or 2 fingers. Set this value to 0x01 to enroll 1 finger per user. Set this value to 0x02 to enroll 2 fingers per user.
 	* @param templateType Indicates the template acquisition format. Value can be
		- MORPHO_PK_COMP (recommended) 
		- MORPHO_PK_COMP_NORM 
		- MORPHO_PK_MAT 
		- MORPHO_PK_MAT_NORM 
		- MORPHO_PK_ANSI_378 
		- MORPHO_PK_MINEX_A 
		- MORPHO_PK_ISO_FMR 
		- MORPHO_PK_ISO_FMC_NS 
		- MORPHO_PK_ISO_FMC_CS 
		- MORPHO_NO_PK_FP
    * @param templateFVPType Indicates the template acquisition format. Value can be
		- MORPHO_PK_FVP 
		- MORPHO_NO_PK_FVP
 	* @param saveRecord Indicate if the template will saved in the data base or not
		- 0 the template will not be saved in the data base 
		- 1 the template will be be savaed in the data base 
 	* @param coder Contains the biometric coder to use (MORPHO_MSO_V9_CODER or MORPHO_MSO_V9_JUV_CODER). Morpho recommends using MORPHO_MSO_V9_CODER. Please refer to the MorphoSmartHostInterface document for details.
 	* @param detectModeChoice detection mode :
 	* 	- MORPHO_VERIF_DETECT_MODE and MORPHO_ENROLL_DETECT_MODE cannot be used together.
	* 	- MORPHO_VERIF_DETECT_MODE and MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE cannot be used together.
 	* @param templateList resulting template list
 	* @param callbackCmd Binary mask with CallbackMask elements.
 	* @param callback User context called on the reception of the asynchronous status. null if not used.
 	* @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range.  
		- MORPHOERR_INVALID_USER_DATA The input user data is not valid: bad identifier or wrong size.  
		- MORPHOERR_TIMEOUT The finger detection timeout has expired.  
		- MORPHOERR_BASE_NOT_FOUND The specified database does not exist.  
		- MORPHOERR_DB_FULL The maximum number of users that can be stored in the local database has been reached.  
		- MORPHOERR_CMDE_ABORTED Command is canceled.  
		- MORPHOERR_MEMORY_PC Not enough memory on tne PC.  
		- MORPHOERR_SAME_FINGER The user gave the same finger twice.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_INVALID_USER_ID The user already exists in the database (same UserID).  
		- MORPHOERR_INVALID_PK_FORMAT Invalid template format  
		- MORPHOERR_FFD False Finger Detected.  
		- MORPHOERR_MOIST_FINGER The finger can be too moist or the scanner is wet.  
		- MORPHOERR_LICENSE_MISSING A required license is missing (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_ADVANCED_SECURITY_LEVEL_MISMATCH Failed to make a multimodal template compatible with advanced security levels (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_BAD_FINAL_FINGER_PRINT_QUALITY Failed to capture the fingerprint with a quality greater than or equal to the specified threshold.  
		- MORPHOERR_KEY_NOT_FOUND The specified key is missing, unable to encrypt biometriques data.  
	 */
	
	public int enroll(
			int timeout,
			int acquisitionThreshold,
			int advancedSecurityLevelsRequired,
			CompressionAlgorithm compressAlgo,
			int compressRate,
			boolean exportMinutiae,
			int fingerNumber,
			TemplateType templateType,
			TemplateFVPType templateFVPType,
			boolean saveRecord,
			Coder coder,
			int detectModeChoice,
			TemplateList templateList,
			int callbackCmd,
			Observer callback
			)
	{

			return morphoUserNative.enroll(
								this.morphoUserPointerCPP, 
								timeout, 
								acquisitionThreshold, 
								advancedSecurityLevelsRequired, 
								compressAlgo.getCode(), 
								compressRate, 
								exportMinutiae, 								
								fingerNumber, 
								templateType.getCode(), 
								templateFVPType.getCode(), 
								saveRecord, 
								coder.getCode(), 
								detectModeChoice, 
								templateList, 
								callbackCmd, 
								callback);

	}
	
	/**This function captures a live finger and checks if it matches with the user referred to.
	 * @param Timeout Finger detection timeout in seconds. Its value must be between 0 and 0xFFFF. 0 corresponds to an infinite timeout.  
	 * @param FAR This parameter specifies how tight the matching threshold is. Morpho recommends MORPHO_FAR_5 (see paragraph "T_ MORPHO_FAR").
	 * @param MatchingScore Contains the result matching score. NULL if not used. For security reason, the secure MorphoSmart™ can not export the matching score because a "rogue" application can mount an "hillclimbing" attack by sequentially randomly modifying a sample and retaining only the changes that produce an increase in the returned score.
	 * @param CoderChoice contains the biometric coder to use (MORPHO_MSO_V9_CODER or MORPHO_MSO_V9_JUV_CODER). Morpho recommends using MORPHO_MSO_V9_CODER. Please refer to the MorphoSmartHostInterface document for details. 
	 * @param DetectModeChoice Bitmask of the following:
		- MORPHO_VERIF_DETECT_MODE: more permissive mode than default; MorphoSmart™ detects more easily finger presence, but might issue lower quality templates. 
		- MORPHO_ENROLL_DETECT_MODE: strongest detection mode (default mode). 
		- MORPHO_WAKEUP_LED_OFF: (only available on MorphoSmart™ MSO FFD) leds are turned off while waiting for a finger (impedance wakeup). 
		- MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE: (not available on MorphoSmart™ CBM-V) force the finger to cover the top of the capture area to increase quality. 
		- MORPHO_WAKEUP_LED_ON: (only available on MorphoSmart™ FINGER VP) leds are turned on while waiting for a finger.
 	 * @param matchingStrategy Value among of the following:
		- MORPHO_STANDARD_MATCHING_STRATEGY: default strategy. 
		- MORPHO_ADVANCED_MATCHING_STRATEGY: less FRR, but more processing time (not available on MorphoSmart™ FINGER VP).
     * @param callbackCmd Binary mask with CallbackMask elements.
	 * @param callback User context called on the reception of asynchronous status. null if not used.
	 * @param resultMatching The result of matching represented by :
	 * 	- matchingScore
	 * 	- matchingPKNumber  
	 * @return values:
		- MORPHO_OK The comparison was successful.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER The matching threshold value or timeout value is out of range or there is no input biometric data.  
		- MORPHOERR_INVALID_TEMPLATE The reference template is not valid: bad identifier, corrupted minutiae.  
		- MORPHOERR_TIMEOUT The finger detection timeout has expired.  
		- MORPHOERR_NO_HIT The function returned a No Hit.  
		- MORPHOERR_CMDE_ABORTED Command is canceled.  
		- MORPHOERR_NO_ASSOCIATED_DB Database is created without MorphoDatabase.GetUser.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_FFD False Finger Detected.  
		- MORPHOERR_MOIST_FINGER The finger can be too moist or the scanner is wet.  
		- MORPHOERR_FVP_MINUTIAE_SECURITY_MISMATCH Fingerprint template cannot be matched in high security level (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN Finger was misplaced or has been withdrawn from sensor during acquisition (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_FFD_FINGER_MISPLACED Finger was misplaced during acquisition (MorphoSmart™ MSO FFD only). 
	 */
	public int verify(
					int timeout,
					int far,
					Coder coder,
					int detectModeChoice,
					int matchingStrategy,
					int callbackCmd,
					Observer callback,
					ResultMatching resultMatching
					)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.verify(morphoUserPointerCPP, timeout, far, coder.getCode(), detectModeChoice, matchingStrategy, callbackCmd, callback, resultMatching);
	}
	
	
	/**
	 * Add a template to the list.
	 * @param template Template to add.
	 * @param templateIndex Template index in the list (starts from 0).
	 * @return values :
	 * 	- MORPHO_OK	The execution of the function was successful.
	 * 	- MORPHOERR_MEMORY_PC	Not enough memory on the PC.
	 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
	 * 	- MORPHOERR_BADPARAMETER  One or more input parameters are out of range.  
	 *
	 */
	public int putTemplate(Template template,Integer templateIndex)
	{		
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.putTemplate(morphoUserPointerCPP,template,templateIndex);
	}
	
	/** This command updates already existing public fields in the local database. MorphoUser object has to be filled with public fields that need to be updated (see method PutField).
	 * Prior calling this method, you should fill up field list using MorphoFieldList.PutField().
	 * @param None
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_INVALID_USER_DATA The input user data is not valid: bad identifier, wrong size.  
		- MORPHOERR_BASE_NOT_FOUND The specified base doesn't exist.  
		- MORPHOERR_NO_ASSOCIATED_DB Database is created without MorphoDatabase.GetUser.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	 */
	public int dbUpdatePublicFields()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.dbUpdatePublicFields(morphoUserPointerCPP);
	}

	/** This command updates an already existing record in the local database. If no record with the same identifier already exists the command fails (AddToDatabase() should be used). 
	 * This command updates already existing fields (public or private) in the local database. MorphoUser object has to be filled with public fields that need to be updated (see method PutField). 
	 * The field update is performed only if the user identity is verified. Prior calling this method, you should fill up field list using MorphoFieldList.PutField().
	 * @param timeout Finger detection timeout in seconds. Its value must be between 0 and 0xFFFF. 0 corresponds to an infinite timeout.  
	 * @param far This parameter specifies how tight the matching threshold is. Morpho recommends MORPHO_FAR_5 (see paragraph "T_ MORPHO_FAR"). 
	 * @param coder contains the biometric coder to use (MORPHO_MSO_V9_CODER or MORPHO_MSO_V9_JUV_CODER). Morpho recommends using MORPHO_MSO_V9_CODER. Please refer to the MorphoSmartHostInterface document for details.  
	 * @param detectModeChoice Bitmask of the following:
		- MORPHO_VERIF_DETECT_MODE: more permissive mode than default; MorphoSmart™ detects more easily finger presence, but might issue lower quality templates. 
		- MORPHO_ENROLL_DETECT_MODE: strongest detection mode (default mode). 
		- MORPHO_WAKEUP_LED_OFF: (only available on MorphoSmart™ MSO FFD) leds are turned off while waiting for a finger (impedance wakeup). 
		- MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE: (not available on MorphoSmart™ CBM-V) force the finger to cover the top of the capture area to increase quality. 
		- MORPHO_WAKEUP_LED_ON: (only available on MorphoSmart™ FINGER VP) leds are turned on while waiting for a finger.
  	 * @param matchingStrategy Value among of the following:
		- MORPHO_STANDARD_MATCHING_STRATEGY: default strategy. 
		- MORPHO_ADVANCED_MATCHING_STRATEGY: less FRR, but more processing time (not available on MorphoSmart™ FINGER VP).
	 * @param callbackCmd Binary mask with CallbackMask elements.
 	 * @param callback User context called on the reception of the asynchronous status. null if not used.
 	 
	 * @return values:
		- MORPHO_OK The matching was successful.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER The matching threshold value or timeout value is out of range.  
		- MORPHOERR_TIMEOUT The finger detection timeout has expired.  
		- MORPHOERR_NO_HIT Finger does not match.  
		- MORPHOERR_CMDE_ABORTED Command is canceled.  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_PROTOCOLE Communication protocol error.  
		- MORPHOERR_NO_ASSOCIATED_DB Database is created without MorphoDatabase.GetUser.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_FFD False Finger Detected.  
		- MORPHOERR_MOIST_FINGER The finger can be too moist or the scanner is wet.  
		- MORPHOERR_FVP_FINGER_MISPLACED_OR_WITHDRAWN Finger was misplaced or has been withdrawn from sensor during acquisition (MorphoSmart™ FINGER VP only).  
		- MORPHOERR_FFD_FINGER_MISPLACED Finger was misplaced during acquisition (MorphoSmart™ MSO FFD only).  
*/
	
	public int dbVerifyAndUpdate
		(int timeout, 
			int far, 
			Coder coder, 
			int detectModeChoice, 
			int matchingStrategy, 
			int callbackCmd, 
			Observer callback)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		//return morphoUserNative.dbVerifyAndUpdate(this.morphoUserPointerCPP, timeout, far, coder.getCode(), detectModeChoice, matchingStrategy, callbackCmd, callback);
		return ErrorCodes.MORPHOERR_NOT_IMPLEMENTED;
	}
	
	/** This function returns the number of acquisitions per finger. 
	 * @param None
	 * @return the number of acquisitions per finger */
	public int getEnrollmentType()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.getEnrollmentType(this.morphoUserPointerCPP);
	}

	/** This function specifies the number of acquisitions per finger. 
	 * @param the number of acquisitions per finger.
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BADPARAMETER Invalid parameter  
	 */
	public int setEnrollmentType(int enrollmentType)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.setEnrollmentType(this.morphoUserPointerCPP, enrollmentType);
	}

	/**This function retreives the finger templates quality of a user in the database. <a>
	 * @param indexDB Index of the database to search in.  
	 * @param userID ID of the user whose finger's quality must be retrieved 
	 * @param userIdSize UserID size 
	 * @param userIndex Index of the user whose finger's quality must be retrieved. This parameter is used if only i_puc_UserId is null.  
	 * @param templateQuality Finger template quality(ies). Each quality is returned as a byte.
	 * @param fingerNumber Number of fingers in the database for the specified user.     
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_INTERNAL An internal error occurred during the execution of the function.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range.  
		- MORPHOERR_INVALID_USER_ID The UserID does not exist in the database.  
		- MORPHOERR_BASE_NOT_FOUND The database does not exist.  
		- MORPHOERR_USER_NOT_FOUND The user does not exist in the database.  
	 */
	public int getUserTemplateQuality(int indexDB, String userID, int userIdSize, long userIndex, TemplateQuality templateQuality, FingerNumber fingerNumber)
	{
		if(!this.cppMemOwn)	
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.getUserTemplateQuality(this.morphoUserPointerCPP,  indexDB, userID, userIdSize, userIndex, templateQuality,  fingerNumber);
	}
	
	
	 /**
		 * Add a FVP template to the list.
		 *
		 * @param templateFVP Input template FVP.
		 * @param templateIndex Template index in the list (starts from 0).
		 *
		 * @return values :
		 * 	- MORPHO_OK	The execution of the function was successful.
		 * 	- MORPHOERR_MEMORY_PC	Not enough memory on the PC.
		 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
		 */
	
 	public int putFVPTemplate(TemplateFVP templateFVP, Integer templateIndex)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.putFVPTemplate(this.morphoUserPointerCPP, templateFVP, templateIndex);
	}
	
	/**
	 * Retrieves a template content from the list.
	 *
	 * @param templateIndex Template index in the list (starts from 0).
	 * @param template output Template. null if not used. 
	 * @return values :
	 * 	- MORPHO_OK	The execution of the function was successful.
	 * 	- MORPHOERR_BADPARAMETER	Invalid template index.
	 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
	 */
 	
	public int getTemplate(int templateIndex, Template template)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.getTemplate(this.morphoUserPointerCPP, templateIndex, template);
	}
	
	/**
	 * Retrieves an FVP template content from the list.
	 *
	 * @param templateIndex Template index in the list (starts from 0).
	 * @param template output FVP Template. null if not used. 
	 * @return values :
	 * 	- MORPHO_OK	The execution of the function was successful.
	 * 	- MORPHOERR_BADPARAMETER	Invalid template index.
	 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
	 */
	
	public int getFVPTemplate(int templateIndex, TemplateFVP templateFVP)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.getFVPTemplate(this.morphoUserPointerCPP, templateIndex, templateFVP);
	}
	
	/**
	 * Get the number of templates in TemplateList.
	 *
	 * @param nbTemplate The output number of templates.
	 * @return values :
	 * 	- MORPHO_OK	The execution of the function was successful.
	 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
	 */
	public int getNbTemplate(Integer nbTemplate)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.getNbTemplate(this.morphoUserPointerCPP, nbTemplate);
	}
	
	/**
	 * Get the number of FVP templates in TemplateList.
	 *
	 * @param nbTemplateFVP The output number of FVP templates.
	 * @return values :
	 * 	- MORPHO_OK	The execution of the function was successful.
	 * 	- MORPHOERR_CORRUPTED_CLASS	Class has been corrupted.
	 */
	public int getNbTemplateFVP(Integer nbTemplateFVP)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoUserNative.getNbTemplateFVP(this.morphoUserPointerCPP, nbTemplateFVP);
	}
}

/**
 * 
 * Native class related to MorphoUser class. 
 *
 */
class MorphoUserNative
{
	static {
		System.loadLibrary("NativeMorphoSmartSDK");
	}
	
	public native long getCPPInstance();

	public native int getUserTemplateQuality(long morphoUserPointerCPP, int indexDB, String userID, int userIdSize, long userIndex, TemplateQuality templateQuality, FingerNumber fingerNumber);
	
	public native long getCPPInstance(String userID);

	public native long getCPPInstance(long morphoUserPointerCPP);

	public native void deleteInstance(long morphoUserPointerCPP);
	
	public native int dbUpdatePublicFields(long morphoUserPointerCPP);

	public native int putTemplate(long morphoUserPointerCPP, Template template,Integer templateIndex) ;

	public native int cancelLiveAcquisition(long morphoUserPointerCPP);
	
	public native int dbDelete(long morphoUserPointerCPP);
	
	public native int dbStore(long morphoUserPointerCPP);
	
	public native int setNoCheckOnTemplateForDBStore(long morphoUserPointerCPP,boolean noCheckOnTemplate);
	
	public native int putField(long morphoUserPointerCPP,int fieldIndex,String fieldData);
	
	public native int getField(long morphoUserPointerCPP,int fieldIndex,MorphoString fieldData);
	
	public native int verify(
					long morphoUserPointerCPP,
					int timeout,
					int far,
					int coderChoice,
					int detectModeChoice,
					int matchingStrategy,
					int callbackCmd,
					Observer callback,
					ResultMatching resultMatching
					);
	
	public native int enroll(
					long morphoUserPointerCPP,
					int timeout,
					int acquisitionThreshold,
					int advancedSecurityLevelsRequired,
					int compressAlgo,
					int compressRate,
					boolean exportMinutiae,					
					int fingerNumber,
					int templateType,
					int fVPTemplateType,
					boolean saveRecord,
					int coderChoice,
					int detectModeChoice,
					TemplateList templateList,
					int callbackCmd,
					Observer callback
					); 

	public native int dbVerifyAndUpdate(long morphoUserPointerCPP, 
					int timeout, 
					int far, 
					int coderChoice, 
					int detectModeChoice, 
					int matchingStrategy, 
					int callbackCmd, 
					Observer callback);

	public native int getEnrollmentType(long morphoUserPointerCPP);

	public native int setEnrollmentType(long morphoUserPointerCPP, int enrollmentType);
	
	public native int getUserTemplateQuality(
					long morphoUserPointerCPP,
					int dbIndex,
					String userID,
					int userIndex,
					Integer templateQuality,
					Integer numberOfFingers
					); 
	
	public native int putFVPTemplate(long morphoUserPointerCPP, TemplateFVP templateFVP, Integer templateIndex);
		
	public native int getTemplate(long morphoUserPointerCPP, int templateIndex, Template template);
	
	public native int getFVPTemplate(long morphoUserPointerCPP, int templateIndex, TemplateFVP templateFVP);
	
	public native int getNbTemplate(long morphoUserPointerCPP, Integer nbTemplate);
	
	public native int getNbTemplateFVP(long morphoUserPointerCPP, Integer nbTemplateFVP);	
}
