package com.morpho.morphosmart.sdk;

import java.util.ArrayList;

/**
 * This class contains a list of templates.
 */

public class TemplateList {
	
	private ArrayList<Template> templateList = new ArrayList<Template>();
	private ArrayList<TemplateFVP> templateFVPList = new ArrayList<TemplateFVP>();
	private ArrayList<MorphoImage> morphoImages  = new ArrayList<MorphoImage>();
	private boolean activateFullImageRetrieving = false;
	
	/** Add a template to the list. */
	public void putTemplate(Template template)
	{		
		templateList.add(template);
	}		
	
	public void setImage(MorphoImage morphoImage)
	{
		morphoImages.add(morphoImage);
	}
	
	public MorphoImage getImage(int imageIndex)
	{
		if(imageIndex< morphoImages.size())
		{
			return morphoImages.get(imageIndex);
		}
		return null;
	}
	
	/**Get the number of template in TemplateList. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	 */
	public int getNbTemplate()
	{
		return templateList.size();
	}
	
	/** Retrieves a template content from the list. */
	public Template getTemplate(int templateIndex)
	{
		if(templateIndex < templateList.size())
		{			
			return templateList.get(templateIndex);
		}
		else
		{			
			return null;
		}
	}
		
	/** Add a template to the list. The TemplateList can contain several multimodal templates. */
	public void putFVPTemplate(TemplateFVP templateFVP)
	{
		templateFVPList.add(templateFVP);
	}
	
	/** Get the number of template in TemplateList. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	*/
	public int getNbFVPTemplate()
	{
		return templateFVPList.size();
	}
	
	/** Retrieves a multimodal template content from the list. */
	public TemplateFVP getFVPTemplate(int templateFVPIndex)
	{
		if(templateFVPIndex < templateFVPList.size())
		{			
			return templateFVPList.get(templateFVPIndex);
		}
		else
		{			
			return null;
		}
	}

	/**
	 * @return the activateFullImageRetrieving
	 */
	public boolean isActivateFullImageRetrieving() {
		return activateFullImageRetrieving;
	}

	/**
	 * @param activateFullImageRetrieving the activateFullImageRetrieving to set
	 */
	public void setActivateFullImageRetrieving(boolean activateFullImageRetrieving) {
		this.activateFullImageRetrieving = activateFullImageRetrieving;
	}
}

