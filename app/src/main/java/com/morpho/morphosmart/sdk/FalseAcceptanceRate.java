package com.morpho.morphosmart.sdk;
/**
 * 
 * The False Acceptance Rate (FAR) specify the acceptance threshold for a template matching, 
 * This value characterizes the security of the system. The lower the rate, 
 * the more secure the system, but the higher the risk of false rejections.
 *
 */
public class FalseAcceptanceRate {

	/** Very low threshold for test purpose only */
	public static final int MORPHO_FAR_0 = 0;
	
	/** FAR < 1 % */ 
	public static final int MORPHO_FAR_1 = 1;
	
	/** FAR < 0.3 % */
	public static final int MORPHO_FAR_2 = 2;
	
	/** FAR < 0.1 % */ 
	public static final int MORPHO_FAR_3 = 3;
	
	/** FAR < 0.03 % */
	public static final int MORPHO_FAR_4 = 4;
	
	/** Recommended value: FAR < 0.01 % */
	public static final int MORPHO_FAR_5 = 5;
	
	/** FAR < 0.001 % */
	public static final int MORPHO_FAR_6 = 6;
	
	/** FAR < 0.0001 % */
	public static final int MORPHO_FAR_7 = 7;
	
	/** FAR < 0.00001 % */
	public static final int MORPHO_FAR_8 = 8;
	
	/** FAR < 0.000001 % */
	public static final int MORPHO_FAR_9 = 9;
	
	/** Very high threshold for test purpose only */
	public static final int MORPHO_FAR_10 = 10;

    /**
     * Private constructor, do not use.
     */
    private FalseAcceptanceRate() {
    }
}
