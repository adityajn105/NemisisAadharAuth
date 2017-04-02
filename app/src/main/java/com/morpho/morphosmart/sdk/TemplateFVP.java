package com.morpho.morphosmart.sdk;
/**
 * 
 * This class represent the finger VP template used or generated during a
 * Capture/Enroll/Verify/Identify operation.
 */
public class TemplateFVP {
	private TemplateFVPType templateFVPType;
	private byte [] data;
	private int dataIndex = 0xFF;
	private int templateQuality;
	private boolean advancedSecurityLevelsCompatibility;
	private MorphoImage image;
	
	/**
	 * Retrieve data of the current FVP Template object.
	 * @param None
	 * @return Data of the current FVP Template object
	 */
	public byte [] getData() {
		return data;
	}
	
	/**
	 * Set data of the current FVP Template object.
	 * @param data input data
	 * @return None
	 */
	public void setData(byte [] data) {
		this.data = data;
	}
	
	/**
	 * 
	 * @param data
	 */
	@SuppressWarnings("unused") //used in jni
	private void setData(Object data) {
		this.data = (byte[]) data;
	}
	
	/**
	 * Retrieve data index of the current FVP Template object.
	 * @param None
	 * @return index of the current FVP Template object
	 */
	public int getDataIndex() {
		return dataIndex;
	}
	
	/**
	 * Set data index of the current FVP Template object.
	 * @param dataIndex input data index
	 * @return None
	 */
	public void setDataIndex(int dataIndex) {
		this.dataIndex = dataIndex;
	}
	
	/**
	 * Retrieve quality of the current FVP Template object.
	 * @param None
	 * @return Quality of the current FVP Template object
	 */
	public int getTemplateQuality() {
		return templateQuality;
	}
	
	/**
	 * Set quality of the current FVP Template object.
	 * @param templateQuality input quality
	 * @return None
	 */
	public void setTemplateQuality(int templateQuality) {
		this.templateQuality = templateQuality;
	}
	
	/**
	 * Check if Advanced security levels compatibility is required for the current FVP Template object.
	 * @param None
	 * @return  
	 * 	- true if Advanced security levels compatibility is required
	 * 	- false,if not.
	 */
	public boolean getAdvancedSecurityLevelsCompatibility() {
		return advancedSecurityLevelsCompatibility;
	}
	
	/**
	 * Set Advanced security levels compatibility value for the current FVP Template object.
	 * @param advancedSecurityLevelsCompatibility (boolean)
	 * @return None
	 */
	public void setAdvancedSecurityLevelsCompatibility(boolean advancedSecurityLevelsCompatibility) {
		this.advancedSecurityLevelsCompatibility = advancedSecurityLevelsCompatibility;
	}

	/**
	 * Retrieve corresponding image of the current FVP Template object.
	 * @param None
	 * @return corresponding image
	 */
	public MorphoImage getImage() {
		return image;
	}

	/**
	 * Set corresponding image of the current FVP Template object.
	 * @param image input image
	 * @return None
	 */
	public void setImage(MorphoImage image) {
		this.image = image;
	}
	
	/**
	 * Retrieve type of the current Template object if it's a an FVP template.
	 * @param None
	 * @return Type of the current Template object
	 */
	public TemplateFVPType getTemplateFVPType() {
		return templateFVPType;
	}

	/**
	 * Set the type of the current Template object if it's a an FVP template.
	 * @param templateFVPType the templateFVPType to set
	 * @return None
	 */
	public void setTemplateFVPType(TemplateFVPType templateFVPType) {
		this.templateFVPType = templateFVPType;
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused") //used in jni
	private int getTemplateFVPTypeIntValue() {
		return templateFVPType.getCode();
	}
	
	/**
	 * Set the type of the current Template object if it's a an FVP template.
	 * @param id identifier of a FVP template type
	 * @return None
	 */
	public void setTemplateFVPType(int id) {
		this.templateFVPType = TemplateFVPType.getValue(id);
	}
	
}
