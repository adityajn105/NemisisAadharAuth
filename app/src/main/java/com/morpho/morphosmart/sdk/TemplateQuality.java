package com.morpho.morphosmart.sdk;

/**
 * 
 * This class store a template quality value.
 *
 */
public class TemplateQuality
{
	private byte [] templateQuality;
	
	public TemplateQuality(){
		setTemplateQuality(new byte [100]);
	}

	public byte [] getTemplateQuality() {
		return templateQuality;
	}

	public void setTemplateQuality(byte [] templateQuality) {
		this.templateQuality = templateQuality;
	}

}
