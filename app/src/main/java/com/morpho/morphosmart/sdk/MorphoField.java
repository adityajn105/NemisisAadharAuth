package com.morpho.morphosmart.sdk;

import com.morpho.morphosmart.sdk.FieldAttribute;

/**
 * 
 * This class represent a database field that can be public or private.
 */

public class MorphoField {	
	private FieldAttribute fieldAttribute;
	private int maxSize;
	private String name;
	
	static final int  MORPHO_FIELD_NAME_LEN = 6;

	/**
	 * @return the fieldAttribute
	 */
	public FieldAttribute getFieldAttribute() {
		return fieldAttribute;
	}
	
	/**
	 * 
	 * @return integer attribute 
	 */
	public int getFieldAttributeIntValue() {
		return fieldAttribute.ordinal();
	}
	
	/**
	 * @param fieldAttribute the fieldAttribute to set
	 */
	public void setFieldAttribute(FieldAttribute fieldAttribute) {
		this.fieldAttribute = fieldAttribute;
	}
	/**
	 * @param fieldAttribute the fieldAttribute to set
	 */
	public void setFieldAttributeIntValue(int fieldAttributeInteger) {
		switch(fieldAttributeInteger)
		{
			case 0 :
				this.fieldAttribute = FieldAttribute.MORPHO_PUBLIC_FIELD;
				break;
			case 1 :
				this.fieldAttribute = FieldAttribute.MORPHO_PRIVATE_FIELD;
				break;
			default:
				break;
		}
	}
	/** 
	 * @return the maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}
	/** set the max size 
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
	/** returns the name 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) throws MorphoSmartException {
		if (name.length() > MORPHO_FIELD_NAME_LEN)
		{
			throw(new MorphoSmartException("Invalid field size, field size must be less than equal to 6"));
		}
		else
		{
			this.name = name;
		}
	}
}
