package com.morpho.morphosmart.sdk;

/**
 * This enumeration list the possible types of an FVP template 
 * 
 */
public enum TemplateFVPType implements ITemplateType {
	MORPHO_NO_PK_FVP(0, "NO PK FVP", ""), 
	MORPHO_PK_FVP(1, "SAGEM PkFVP",".fvp"), 
	MORPHO_PK_FVP_MATCH(2, "SAGEM PkFVP Match", ".fvp-m");

	private int code;
	private String label;
	private String extension;

	public int getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public String getExtension() {
		return extension;
	}

	private TemplateFVPType(int code, String label, String extension) {
		this.code = code;
		this.label = label;
		this.extension = extension;
	}
	
	protected static TemplateFVPType getValue(int id)
    {
		TemplateFVPType[] templateFVPTypes = TemplateFVPType.values();
        for(int i = 0; i < templateFVPTypes.length; i++)
        {
            if(templateFVPTypes[i].code == id)
                return templateFVPTypes[i];
        }
        return TemplateFVPType.MORPHO_NO_PK_FVP;
    }
}
