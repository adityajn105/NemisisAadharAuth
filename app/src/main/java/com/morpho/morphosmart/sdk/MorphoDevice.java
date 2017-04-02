package com.morpho.morphosmart.sdk;

import com.morpho.android.usb.USBManager;

import java.io.Serializable;
import java.util.Observer;

public class MorphoDevice implements Serializable, Cloneable  
{	
	private static final long serialVersionUID = 1L;
	private static MorphoDeviceNative morphoDeviceNative = new MorphoDeviceNative();
	private Long morphoDevicePointerCPP  = Long.valueOf(0);
	
	// Configuration Keys
	/** @brief Sensor Window Position Configuration Key. */
	public static final int CONFIG_SENSOR_WIN_POSITION_TAG = 0x0E10;
	/** @brief UI Configuration Key. */
	public static final int CONFIG_UI_CONFIG_TAG = 0x1410;
	/** @brief UI Reset Key. */
	public static final int CONFIG_UI_RESET_TAG = 0x1411;	
	
	protected boolean cppMemOwn = false;
	
	/** constructor */
	public MorphoDevice()
	{
		long cppPtr = morphoDeviceNative.getCPPInstance();
		
		if (cppPtr != 0)
		{
			this.cppMemOwn = true;
			this.morphoDevicePointerCPP = cppPtr;
			//Log.d("MORPHO_DEVICE","pointer = <"+ cppPtr+">");
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
	
	/**copy constructor */
	public MorphoDevice(MorphoDevice device)
	{
		if(!device.cppMemOwn)
		{
			long cppPtr = morphoDeviceNative.getCPPInstance(device.morphoDevicePointerCPP);
			
			if (cppPtr != 0)
			{
				this.cppMemOwn = true;
				this.morphoDevicePointerCPP = cppPtr;
			}
			else
			{
				try {
					throw new MorphoSmartException("cppPtr is null");
				} catch (MorphoSmartException e) {
					e.printStackTrace();
				}	
			}
		}
		else
		{
			try {
				throw new MorphoSmartException("cppMemOwn is true");
			} catch (MorphoSmartException e) {
				e.printStackTrace();
			}			
		}
	}

	protected void finalize()
	{
		if(this.cppMemOwn)
		{
			this.closeDevice();
			morphoDeviceNative.deleteInstance(this.morphoDevicePointerCPP);
			this.cppMemOwn = false;
		}
	}

	public Object clone()
	{
		return new MorphoDevice(this);
	}
	
	public void setMorphoDeviceNativePointerCPP(long morphoDevicePointerCPP)
	{
		this.morphoDevicePointerCPP = morphoDevicePointerCPP;	
		this.cppMemOwn = true;
	}	

	public int getDatabase(int databaseIndex, MorphoDatabase morphoDatabase)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		
		return morphoDeviceNative.getDatabase(morphoDevicePointerCPP, databaseIndex, morphoDatabase);
	}
	public int cancelLiveAcquisition()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		
		return morphoDeviceNative.cancelLiveAcquisition(morphoDevicePointerCPP);
	}

	public int capture(
						int timeout,
						int acquisitionThreshold,
						int advancedSecurityLevelsRequired,
						int fingerNumber,
						TemplateType templateType,
						TemplateFVPType templateFVPType,
						int maxSizeTemplate,
						int enrollType,
						int latentDetection,
						Coder coderChoice,
						int detectModeChoice,
						TemplateList templateList,
						int callbackCmd,
						Observer callback
						)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		
		return morphoDeviceNative.capture(morphoDevicePointerCPP, timeout, acquisitionThreshold, advancedSecurityLevelsRequired, fingerNumber, templateType.getCode(), templateFVPType.getCode(), maxSizeTemplate, enrollType, latentDetection, coderChoice.getCode(), detectModeChoice, templateList, callbackCmd, callback);
	}

	public int closeDevice()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.closeDevice(morphoDevicePointerCPP);
	}
	
	/** MORPHO USE ONLY  */
	public int enableCS(boolean enable)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.enableCS(morphoDevicePointerCPP, enable);
	}

	public int enableDataEncryption(boolean enable, String diversificationData)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.enableDataEncryption(morphoDevicePointerCPP, enable, diversificationData);
	}

	public int openDevice(int serialPortNumber, int baudRate)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
				
		int ret = USBManager.isPermissionGranted();
		
		if( ret == ErrorCodes.MORPHO_OK)
		{
			return morphoDeviceNative.openDevice(morphoDevicePointerCPP, serialPortNumber, baudRate);
		}
		else
		{
			return ret;
		}
	}

	public String getProductDescriptor()
	{
		return morphoDeviceNative.getProductDescriptor(morphoDevicePointerCPP);
	}
	

	public String getSensorDescriptor()
	{
		return morphoDeviceNative.getSensorDescriptor(morphoDevicePointerCPP);
	}
	

	public String getSoftwareDescriptor()
	{
		return morphoDeviceNative.getSoftwareDescriptor(morphoDevicePointerCPP);
	}
	

	public int openUsbDevice(String sensorName,int timeOut)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
				
		int ret = USBManager.isPermissionGranted();
		
		if( ret == ErrorCodes.MORPHO_OK)
		{		
			return morphoDeviceNative.openUsbDevice(morphoDevicePointerCPP, sensorName, timeOut);
		}
		else
		{
			return ret;
		}
	}
	

	public int setConfigParam(int tag,String paramValue)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.setConfigParam(morphoDevicePointerCPP, tag, paramValue);
	}

	public int setSecurityLevel(int securityLevel)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.setSecurityLevel(morphoDevicePointerCPP, securityLevel);
	}
	

	public int getSecurityLevel()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.getSecurityLevel(morphoDevicePointerCPP);
	}

	public int getComType()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.getComType(morphoDevicePointerCPP);
	}

	public String getConfigParam(int parameterIdentifier)
	{
		return morphoDeviceNative.getConfigParam(morphoDevicePointerCPP, parameterIdentifier);
	}
	

	public String getStringDescriptorBin(int descriptorIdentifier)
	{
		return morphoDeviceNative.getStringDescriptorBin(morphoDevicePointerCPP, descriptorIdentifier);
	}
	

	public int getIntDescriptorBin(int descriptorIdentifier)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.getIntDescriptorBin(morphoDevicePointerCPP, descriptorIdentifier);
	}

	public int releaseFFDLogs(String log){
		return morphoDeviceNative.releaseFFDLogs(morphoDevicePointerCPP,log);
	} 
	

	public String getFFDLogs()
	{
		return morphoDeviceNative.getFFDLogs(morphoDevicePointerCPP);
	}
	

	
	public int getInternalError()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.getInternalError(morphoDevicePointerCPP);
	}

	public int getImage(
						int timeOut,
						int acquisitionThreshold,
						int compressAlgo,
						int compressRate,
						int detectModeChoice,
						int latentDetection,
						MorphoImage morphoImage,
						int callbackCmd,
						Observer callback				
						)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.getImage(morphoDevicePointerCPP, timeOut, acquisitionThreshold, compressAlgo, compressRate, detectModeChoice, latentDetection, morphoImage, callbackCmd, callback);
	}
	

	public int getSecuConfig(SecuConfig secuConfig)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.getSecuConfig(morphoDevicePointerCPP, secuConfig);
	}
	

	public String getUnlockSeed()
	{
		return morphoDeviceNative.getUnlockSeed(morphoDevicePointerCPP);
	}

	public String getUsbDeviceName(int index)
	{
		return morphoDeviceNative.getUsbDeviceName(morphoDevicePointerCPP, index);
	}
	

	public int initUsbDevicesNameEnum(Integer nbUsbDevice)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		
		USBManager.enumerate();
		
		int ret = USBManager.isPermissionGranted();
		
		if( ret == ErrorCodes.MORPHO_OK)
		{		
			return morphoDeviceNative.initUsbDevicesNameEnum(morphoDevicePointerCPP, nbUsbDevice);
		}
		else
		{
			return ret;
		}
	}
	

	
	public String getUsbDevicePropertie(int index)
	{
		return morphoDeviceNative.getUsbDevicePropertie(morphoDevicePointerCPP, index);
	}
	
	/** MORPHO USE ONLY */
	public boolean isCSEnabled()
	{
		return morphoDeviceNative.isCSEnabled(morphoDevicePointerCPP);
	}
	

	public boolean isDataEncryptionEnabled()
	{

		return morphoDeviceNative.isDataEncryptionEnabled(morphoDevicePointerCPP);
	}


	public int rebootSoft()
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.rebootSoft(morphoDevicePointerCPP);
	}
	

	public int unlock(String SecretID, String CipheredSeed)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.unlock(morphoDevicePointerCPP, SecretID, CipheredSeed);
	}

	public int verify(
						int timeOut,
						int far,
						Coder coder,
						int detectModeChoice,
						int matchingStrategy,
						TemplateList templateList,
						int callbackCmd,
						Observer callback,
						ResultMatching resultMatching
						)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.verify(morphoDevicePointerCPP, timeOut, far, coder.getCode(), detectModeChoice, matchingStrategy, templateList, callbackCmd, callback, resultMatching);
	}

	public int verifyMatch(
						int far,
						TemplateList templateListSearch,
						TemplateList templateListReference,
						Integer matchingScore
						)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.verifyMatch(morphoDevicePointerCPP, far, templateListSearch, templateListReference, matchingScore);
	}

	public String getKCV(int keyID)
	{
		if(!this.cppMemOwn)
		{
			return "Class not instantiated";
		}
		return morphoDeviceNative.getKCV(this.morphoDevicePointerCPP, keyID);
	}
	

	public int loadKs(byte[] key)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.loadKs(this.morphoDevicePointerCPP, key);
	}


	public int loadMocKey(byte[] key_enc_Ciffered_by_Certificate, byte[] key_enc_Ciffered_by_Certificate_Signature, byte[] hostCertificate)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.loadMocKey(this.morphoDevicePointerCPP, key_enc_Ciffered_by_Certificate, key_enc_Ciffered_by_Certificate_Signature, hostCertificate);
	}
	


	public int loadMocKey(byte[] key_enc_Ciffered_by_KencTrans)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoDeviceNative.loadMocKey(this.morphoDevicePointerCPP, key_enc_Ciffered_by_KencTrans);
	}
}

/**
 * Corresponding native class of MorphoDevice.
 */
class MorphoDeviceNative
{	
	static {
		System.loadLibrary("NativeMorphoSmartSDK");
	}
	
	public native long getCPPInstance();
	
	public native int releaseFFDLogs(long morphoDevicePointerCPP, String log) ;

	public native String getKCV(long morphoDevicePointerCPP, int keyID) ;

	public native long getCPPInstance(long morphoDevicePointerCPP);
	
	public native void deleteInstance(long morphoDevicePointerCPP);
	
	public native int getDatabase(long morphoDevicePointerCPP,int databaseIndex, MorphoDatabase morphoDatabase);
	
	public native int cancelLiveAcquisition(long morphoDevicePointerCPP);

	public native int capture(
						long morphoDevicePointerCPP,
						int timeout,
						int acquisitionThreshold,
						int advancedSecurityLevelsRequired,
						int fingerNumber,
						int templateType,
						int templateFVPType,
						int maxSizeTemplate,
						int enrollType,
						int latentDetection,
						int coderChoice,
						int detectModeChoice,
						TemplateList templateList,
						int callbackCmd,
						Observer callback
						);
	
	public native int closeDevice(long morphoDevicePointerCPP);
	
	public native int enableCS(long morphoDevicePointerCPP,boolean enable);
	
	public native int enableDataEncryption(long morphoDevicePointerCPP,boolean enable, String diversificationData);
	
	public native int openDevice(long morphoDevicePointerCPP,int serialPortNumber, int baudRate);
					
	public native String getProductDescriptor(long morphoDevicePointerCPP);
	
	public native String getSensorDescriptor(long morphoDevicePointerCPP);
	
	public native String getSoftwareDescriptor(long morphoDevicePointerCPP);
	
	public native int openUsbDevice(long morphoDevicePointerCPP,String sensorName,int timeOut);	
	
	public native int setConfigParam(long morphoDevicePointerCPP,int tag,String paramValue);
	
	public native int setSecurityLevel(long morphoDevicePointerCPP,int securityLevel);
	
	public native int getSecurityLevel(long morphoDevicePointerCPP);
	
	public native int getComType(long morphoDevicePointerCPP);
	
	public native String getConfigParam(long morphoDevicePointerCPP,int parameterIdentifier);
	
	public native String getStringDescriptorBin(long morphoDevicePointerCPP,int descriptorIdentifier);
	
	public native int getIntDescriptorBin(long morphoDevicePointerCPP,int descriptorIdentifier);
	
	public native String getFFDLogs(long morphoDevicePointerCPP);
	
	public native int getInternalError(long morphoDevicePointerCPP);
	
	public native int getImage(
						long morphoDevicePointerCPP,
						int timeOut,
						int acquisitionThreshold,
						int compressAlgo,
						int compressRate,
						int detectModeChoice,
						int latentDetection,
						MorphoImage morphoImage,
						int callbackCmd,
						Observer callback				
						);
	
	public native int getSecuConfig(long morphoDevicePointerCPP,SecuConfig secuConfig);
	
	public native String getUnlockSeed(long morphoDevicePointerCPP);
	
	public native int initUsbDevicesNameEnum(long morphoDevicePointerCPP,Integer nbUsbDevice);
	
	public native String getUsbDeviceName(long morphoDevicePointerCPP,int index);
	
	public native String getUsbDevicePropertie(long morphoDevicePointerCPP,int index);
	
	public native boolean isCSEnabled(long morphoDevicePointerCPP);
	
	public native boolean isDataEncryptionEnabled(long morphoDevicePointerCPP);

	public native int rebootSoft(long morphoDevicePointerCPP);
	
	public native int unlock(long morphoDevicePointerCPP,String SecretID, String CipheredSeed);
	
	public native int verify(
						long morphoDevicePointerCPP,
						int timeOut,
						int far,
						int coderChoice,
						int detectModeChoice,
						int matchingStrategy,
						TemplateList templateList,
						int callbackCmd,
						Observer callback,
						ResultMatching resultMatching
						);
	
	public native int verifyMatch(
						long morphoDevicePointerCPP,
						int far,
						TemplateList templateListSearch,
						TemplateList templateListReference,
						Integer matchingScore
						);
 
	public native int loadKs(long morphoDevicePointerCPP, byte[] key);

	public native int loadMocKey(long morphoDevicePointerCPP, byte[] key_enc_Ciffered_by_Certificate, byte[] key_enc_Ciffered_by_Certificate_Signature, byte[] hostCertificate);

	public native int loadMocKey(long morphoDevicePointerCPP, byte[] key_enc_Ciffered_by_KencTrans);
}
