package com.morpho.morphosmart.sdk;


/**
 * Class containing the list of fields associated to a user.
 * This class contains the list of fields associated to a user.
 */
public class MorphoFieldList implements Cloneable {
	
	private static MorphoFieldListNative morphoFieldListNative = new MorphoFieldListNative();
	private Long morphoFieldListPointerCPP  = Long.valueOf(0);
	protected boolean cppMemOwn = false;
	
	/** constructor */
	public MorphoFieldList()
	{
		long cppPtr = morphoFieldListNative.getCPPInstance();
		
		if (cppPtr != 0)
		{
			this.cppMemOwn = true;
			this.morphoFieldListPointerCPP = cppPtr;			
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
	
	/** copy constructor */
	public MorphoFieldList(MorphoFieldList fieldList)
	{
		if(!fieldList.cppMemOwn)
		{
			long cppPtr = morphoFieldListNative.getCPPInstance(fieldList.morphoFieldListPointerCPP);
			
			if (cppPtr != 0)
			{
				this.cppMemOwn = true;
				this.morphoFieldListPointerCPP = cppPtr;			
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
		else
		{
			try{
				throw new MorphoSmartException("cppMemOwn is true");
			} catch (MorphoSmartException e) {
				e.printStackTrace();
			}			
		}
	}
	
	/** Retrieves a field structure definition from the list. 
	 * @param indexField Field index (index can not be 0 because it is reserved for UserID, first available is 1).  
	 * @param lenField Data length stored in the field. 
	 * @param dataField Data stored in the field. This parameter must not be allocated, released or modified. Before performing such tasks, the application should make a copy of this data.  
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_FIELD_NOT_FOUND Field not found.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	 */
	public int GetField(int fieldIndex, int lenField, String dataField)
	{		
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoFieldListNative.GetField(this.morphoFieldListPointerCPP,fieldIndex, lenField, dataField);
	}
	/** default destructor */
	protected void finalize()
	{
		if(this.cppMemOwn)
		{
			morphoFieldListNative.deleteInstance(this.morphoFieldListPointerCPP);
			this.cppMemOwn = false;
		}
	}
	
	/** Add a field to the list.
	 * @param indexField Index field. Index can not be 0 because this value is reserved for UserID. First available index is 1.  
	 * @param lenField length of data to store in the field. When fields are stored in a database record, only useful length is used, so choosing a lower length than the maximum size (defined during base creation) will save some space in the database. 
	 * @param dataField Data stored in the field. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
		- MORPHOERR_BADPARAMETER One or more input parameters are out of range. 
	 */
	public int PutField(int fieldIndex ,int lenField,String dataField)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoFieldListNative.PutField(this.morphoFieldListPointerCPP, fieldIndex, lenField, dataField);
	}
	
}

/**
 * Corresponding native class of MorphoFieldList.
 */
class MorphoFieldListNative  {
	
	static {
			System.loadLibrary("NativeMorphoSmartSDK");

	}	

	public native long getCPPInstance();

	public native long getCPPInstance(long morphoFieldListPointerCPP);

	public native long deleteInstance(long morphoFieldListPointerCPP);

	public native int GetField(long morphoFieldListPointerCPP, int fieldIndex, int lenField, String dataField);

	public native int PutField(long morphoFieldListPointerCPP, int fieldIndex, int lenField, String dataField);

}