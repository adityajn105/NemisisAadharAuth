 	package com.morpho.morphosmart.sdk;


//types

/** @brief Those values are used to define whether a field is private or public. */
 enum T_MORPHO_FIELD_ATTRIBUTE{
		MORPHO_PUBLIC_FIELD, /**< Field is public and can be freely retrieved any time. */
		MORPHO_PRIVATE_FIELD /**< Field is private and can only be retrieved after a hit condition. */
} ;

/**
 * Class used to describe the field structure of the internal database.
 * This class describes the field structure of the internal database.
 * This class is built as a list of field descriptors. This class is an abstract class
 * that should not be instantiated.
 */

public class MorphoFieldDescriptor implements Cloneable {
	
	private static MorphoFieldDescriptorNative morphoFieldDescriptorNative = new MorphoFieldDescriptorNative();
	private Long morphoFieldDescriptorPointerCPP  = Long.valueOf(0);
	protected boolean cppMemOwn = false;
	
	/** constructor */
	public MorphoFieldDescriptor()
	{
		long cppPtr = morphoFieldDescriptorNative.getCPPInstance();
		
		if (cppPtr != 0)
		{
			this.cppMemOwn = true;
			this.morphoFieldDescriptorPointerCPP = cppPtr;			
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
	public MorphoFieldDescriptor(MorphoFieldDescriptor fieldDescriptor)
	{
		if(!fieldDescriptor.cppMemOwn)
		{
			long cppPtr = morphoFieldDescriptorNative.getCPPInstance(fieldDescriptor.morphoFieldDescriptorPointerCPP);
			
			if (cppPtr != 0)
			{
				this.cppMemOwn = true;
				this.morphoFieldDescriptorPointerCPP = cppPtr;			
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
	 * @param fieldIndex Field index. First supplementary field is 1 (0 is reserved for UserID is not allowed).  
	 * @param fieldAttribute Field attribute. 
	 * @param fieldMaxSize Field maximum size.  
	 * @param fieldName Field friendly name. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_BADPARAMETER Invalid field index (0 or higher than field number).  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	 */
	public int getField(int fieldIndex, T_MORPHO_FIELD_ATTRIBUTE fieldAttribute, int fieldMaxSize,String fieldName)
	{		
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoFieldDescriptorNative.getField(this.morphoFieldDescriptorPointerCPP,fieldIndex,fieldAttribute, fieldMaxSize,fieldName);
	}
	
	protected void finalize()
	{
		if(this.cppMemOwn)
		{
			morphoFieldDescriptorNative.deleteInstance(this.morphoFieldDescriptorPointerCPP);
			this.cppMemOwn = false;
		}
	}
	
	/** Retrieves the number of field structure definitions stored in MorphoFieldDescriptor instance. 
	 * @return values:
		- Number of field structure definitions stored in the list.  
	 */
	public int getNBField(int i)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoFieldDescriptorNative.getNBField(this.morphoFieldDescriptorPointerCPP);
	}
	
	/** Add a field structure definition to the list. 
	 * @param fieldAttribute Field attribute 
	 * @param fieldMaxSize Field maximum size 
	 * @param fieldName Field friendly name. 
	 * @return values:
		- MORPHO_OK The execution of the function was successful.  
		- MORPHOERR_MEMORY_PC Not enough memory on the PC.  
		- MORPHOERR_CORRUPTED_CLASS Class has been corrupted.  
	 */
	public int putField(Object fieldAttribute ,int fieldMaxSize,String fieldName)
	{
		if(!this.cppMemOwn)
		{
			return ErrorCodes.CLASS_NOT_INSTANTIATED;
		}
		return morphoFieldDescriptorNative.putField(this.morphoFieldDescriptorPointerCPP, fieldAttribute, fieldMaxSize, fieldName);
	}
	
}

/**
 * Corresponding native class of MorphoFieldDescriptor.
 */
class MorphoFieldDescriptorNative  {
	
	static {
			System.loadLibrary("NativeMorphoSmartSDK");

	}	

	public native long getCPPInstance();

	public native long getCPPInstance(long morphoFieldDescriptorPointerCPP);

	public native long deleteInstance(long morphoFieldDescriptorPointerCPP);

	public native int getField(long morphoFieldDescriptorPointerCPP, int fieldIndex,T_MORPHO_FIELD_ATTRIBUTE fieldAttribute, int fieldMaxSize, String fieldName );

	public native int getNBField(long morphoFieldDescriptorPointerCPP);

	public native int putField(long morphoFieldDescriptorPointerCPP,Object fieldAttribute, int fieldMaxSize, String fieldName);

}