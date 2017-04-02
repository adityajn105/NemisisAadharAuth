package com.morpho.morphosmart.sdk;
/**
  * - Compression algorithm to be used to compress the fingerprint image. Available algorithms are:
	 *	- #MORPHO_NO_COMPRESS
	 *	- #MORPHO_COMPRESS_V1
	 *	- #MORPHO_COMPRESS_WSQ 
 *
 */
public enum CompressionAlgorithm {
	NO_IMAGE(-1,"NO IMAGE",""),
	/**  No image compression is applied. */
	MORPHO_NO_COMPRESS(0,"RAW",".raw"),
	/**  V1 image compression (Morpho private fingerprint image compression algorithm) is applied. The compression ratio depends on the image to be compressed but it is usually close to 4. */
	/**  This compression algorithm is recommended when the device is connected by a RS232 port. */
	MORPHO_COMPRESS_V1(1,"SAGEM_V1",".bin"),
	/**  WSQ image compression (Wavelets Scalar Quantization algorithm) is applied. */
	MORPHO_COMPRESS_WSQ(2,"WSQ",".wsq");
	
	private int code;
	private String label;
	private String extension;
	
	/**
	 * getter code 
	 * @return integer  */
	public int getCode()
	{         
		return code;
	} 
	/**
	 * @brief Getter of compression algorithm label
	 * @return Label */
	public String getLabel() {
		return label;
	}
	
	public String getExtension() {
		return extension;
	}
	
	private CompressionAlgorithm(int code,String label, String extension)
	{         
		this.code = code;    
		this.label = label;
		this.extension = extension;
	}  
    /**
     * @brief Return the compression algorithm for fingerprint image
     * @param firmware Compression Algorithm
     * @return Compression Algorithm.
     * */
    public static CompressionAlgorithm GetCompressionAlgorithm(int firmwareCompressionAlgorithm)
    {
    	switch(firmwareCompressionAlgorithm)
    	{
			case 44 : return MORPHO_NO_COMPRESS;
			case 60: return MORPHO_COMPRESS_V1;
			case 156: return MORPHO_COMPRESS_WSQ;
			default : return MORPHO_NO_COMPRESS;
    	}
    }
    
    /** 
     * @brief Return Compression Algorithm corresponding to the given identifier.
     * @return CompressionAlgorithm */
	protected static CompressionAlgorithm getValue(int id)
    {
		CompressionAlgorithm[] compressionAlgorithms = CompressionAlgorithm.values();
        for(int i = 0; i < compressionAlgorithms.length; i++)
        {
            if(compressionAlgorithms[i].code == id)
                return compressionAlgorithms[i];
        }
        return CompressionAlgorithm.MORPHO_NO_COMPRESS;
    }
}
