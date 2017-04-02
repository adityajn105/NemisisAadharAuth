package com.morpho.morphosmart.sdk;
/**
 * 
 * This class store different possible values of a coding algorithm. 
 *
 */
public enum Coder {

	 /** Default coder, this setting exists since the 08.04 firmware version and allow the MorphoSmart™ to keep its default choice. */
	MORPHO_DEFAULT_CODER(0,"Default"),
	/** Standard coder selection (this is the default choice). */
	MORPHO_MSO_V9_CODER(3,"Standard"),
	/** Juvenile coder selection. */
	MORPHO_MSO_V9_JUV_CODER(7,"Juvenile"),
	/** Thin finger coder selection. */
	MORPHO_MSO_V9_THIN_FINGER_CODER(8,"Thin Finger");
   

	private int code;
	private String label;
	
	/**
	 * @brief  Getter coder code. 
	 * @return code integer */
	public int getCode()
	{         
		return code;
	}       
	
	/** @brief Getter for coder label.
	 *  @return label string
	 * */
	public String getLabel()
	{         
		return label;     
	} 
	/** 
	 * Constructor 
	 * @param code
	 * @param label 
	 */
	private Coder(int code, String label)
	{         
		this.code = code;         
		this.label = label;              
	} 
}
