package com.morpho.morphosmart.sdk;

/** Common interface for TemplateFVPType and TemplateType.*/

public interface ITemplateType {
	
	/** Get template type code.
	 * @return Code of the template type */
	public int getCode();

	/** Get template type label.
	 * @return Label of the template type */
	public String getLabel();
	
	/** Get corresponding template type extension.
	 * @return Extension of the Template type*/
	public String getExtension();
	 
}
