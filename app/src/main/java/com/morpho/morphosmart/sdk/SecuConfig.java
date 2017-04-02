package com.morpho.morphosmart.sdk;

/**
 * This class represent the security configuration of the MorphoSmart™ device
 *
 */
public class SecuConfig {

	private String serialNumber;
	private char securityOptions;
	private int securityFAR;
	private int minMultimodalSecurityLevel;
	
	private boolean downloadIsProtected = false;
	private boolean modeTunneling = false;
	private boolean modeOfferedSecurity = false;
	private boolean acceptsOnlySignedTemplates = false;
	private boolean exportScore = false;
	
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the securityOptions
	 */
	public char getSecurityOptions() {
		return securityOptions;
	}
	/**
	 * @param securityOptions the securityOptions to set
	 */
	public void setSecurityOptions(char securityOptions) {
		this.securityOptions = securityOptions;

		if((securityOptions & 0x08) == 1)
		{
			setDownloadIsProtected(true);
		}
		
		if((securityOptions & 0x01) == 1)
		{
			setModeTunneling(true);
		}
		
		if((securityOptions & 0x02) == 1)
		{
			setModeOfferedSecurity(true);
		}
		
		if((securityOptions & 0x04) == 1)
		{
			setAcceptsOnlySignedTemplates(true);
		}
		
		if((securityOptions & 0x10) != 1)
		{
			setExportScore(true);
		}
	}
	/**
	 * @return the securityFAR
	 */
	public int getSecurityFAR() {
		return securityFAR;
	}
	
	public String getSecurityFARDescription()
	{
		switch(securityFAR)
		{
		case 0:
			return "All FAR are allowed";
		case 1:
			return "1 (1 %)";
		case 2:
			return "2 (0.3 %)";
		case 3:
			return "3 (0.1 %)";
		case 4:
			return "4 (0.03 %)";
		case 5:
			return "5 (0.01 %) : recommended";
		case 6:
			return "6 (0.001 %)";
		case 7:
			return "7 (0.0001 %)";
		case 8:
			return "8 (0.00001 %)";
		case 9:
			return "9 (0.0000001 %)";
		default:
			return "No FAR are allowed";
		}
	}
	/**
	 * @param securityFAR the securityFAR to set
	 */
	public void setSecurityFAR(int securityFAR) {
		this.securityFAR = securityFAR;
	}
	/**
	 * @return the minMultimodalSecurityLevel
	 */
	public int getMinMultimodalSecurityLevel() {
		return minMultimodalSecurityLevel;
	}
	/**
	 * @param minMultimodalSecurityLevel the minMultimodalSecurityLevel to set
	 */
	public void setMinMultimodalSecurityLevel(int minMultimodalSecurityLevel) {
		this.minMultimodalSecurityLevel = minMultimodalSecurityLevel;
	}
	/**
	 * @return the downloadIsProtected
	 */
	public boolean isDownloadIsProtected() {
		return downloadIsProtected;
	}
	/**
	 * @param downloadIsProtected the downloadIsProtected to set
	 */
	private void setDownloadIsProtected(boolean downloadIsProtected) {
		this.downloadIsProtected = downloadIsProtected;
	}
	/**
	 * @return the modeTunneling
	 */
	public boolean isModeTunneling() {
		return modeTunneling;
	}
	/**
	 * @param modeTunneling the modeTunneling to set
	 */
	private void setModeTunneling(boolean modeTunneling) {
		this.modeTunneling = modeTunneling;
	}
	/**
	 * @return the modeOfferedSecurity
	 */
	public boolean isModeOfferedSecurity() {
		return modeOfferedSecurity;
	}
	/**
	 * @param modeOfferedSecurity the modeOfferedSecurity to set
	 */
	private void setModeOfferedSecurity(boolean modeOfferedSecurity) {
		this.modeOfferedSecurity = modeOfferedSecurity;
	}
	/**
	 * @return the acceptsOnlySignedTemplates
	 */
	public boolean isAcceptsOnlySignedTemplates() {
		return acceptsOnlySignedTemplates;
	}
	/**
	 * @param acceptsOnlySignedTemplates the acceptsOnlySignedTemplates to set
	 */
	private void setAcceptsOnlySignedTemplates(boolean acceptsOnlySignedTemplates) {
		this.acceptsOnlySignedTemplates = acceptsOnlySignedTemplates;
	}
	/**
	 * @return the exportScore
	 */
	public boolean isExportScore() {
		return exportScore;
	}
	/**
	 * @param exportScore the exportScore to set
	 */
	private void setExportScore(boolean exportScore) {
		this.exportScore = exportScore;
	} 
}
