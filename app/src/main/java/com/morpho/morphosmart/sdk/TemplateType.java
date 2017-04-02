package com.morpho.morphosmart.sdk;
/**
 * 
 * This enumeration list the possible types of a template 
 * 
 *
 */
public enum TemplateType implements ITemplateType {
	
	MORPHO_PK_COMP(0,"SAGEM PkComp",".pkc"),
	MORPHO_PK_MAT_NORM(1,"SAGEM PkMat Norm",".pkmn"),
	MORPHO_PK_COMP_NORM(2,"SAGEM PkComp Norm",".pkcn"),
	MORPHO_PK_MAT(3,"SAGEM PkMat",".pkm"),
	MORPHO_PK_ANSI_378(4,"ANSI INCITS 378",".ansi-fmr"),
	MORPHO_PK_MINEX_A(5,"MINEX A",".minex-a"),
	MORPHO_PK_ISO_FMR(6,"ISO 19794-2",".iso-fmr"),
	MORPHO_PK_ISO_FMC_NS(7,"ISO 19794-2, FMC Normal Size",".iso-fmc-ns"),
	MORPHO_PK_ISO_FMC_CS(8,"ISO 19794-2, FMC Compact Size",".iso-fmc-cs"),
	MORPHO_PK_ILO_FMR(9,"ILO International Labour Organisation",".ilo-fmr"),
	MORPHO_PK_MOC(12,"SAGEM PKMOC",".moc"),
	MORPHO_PK_DIN_V66400_CS(13,"DIN V66400 Compact Size",".din-cs"),
	MORPHO_PK_DIN_V66400_CS_AA(14,"DIN V66400 Compact Size, ordered by Ascending Angle",".din-cs"),
	MORPHO_PK_ISO_FMC_CS_AA(15,"ISO 19794-2, FMC Compact Size, ordered by Ascending Angle",".iso-fmc-cs"),
	MORPHO_NO_PK_FP(16,"NO PK FP","");
	
	private int code;
	private String label;
	private String extension;
	
	public int getCode()
	{         
		return code;
	}       
	
	public String getLabel()
	{         
		return label;     
	} 
	
	public String getExtension()
	{
		return extension;
	}
	
	private TemplateType(int code, String label, String extension)
	{         
		this.code = code;      
		this.label = label;       
		this.extension = extension;
	}    
		
	public static TemplateType getValue(int id)
    {
		TemplateType[] templateTypes = TemplateType.values();
        for(int i = 0; i < templateTypes.length; i++)
        {
            if(templateTypes[i].code == id)
                return templateTypes[i];
        }
        return TemplateType.MORPHO_NO_PK_FP;
    }
}
