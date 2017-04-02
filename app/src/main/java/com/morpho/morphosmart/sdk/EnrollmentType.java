package com.morpho.morphosmart.sdk;

public class EnrollmentType {
	/**  It is also possible to set this value to 1 for verification purpose. In this case, it is not possible to save the record in the internal database: in this case, the template is generated from one single fingerprint acquisition */
	public static final int ONE_ACQUISITIONS = 1;
	 /**  We strongly recommend setting this value to 0 (default value) for enrollment purpose to increase the system performances: in this case, the template is generated from a consolidation calculation of three consecutive acquisitions of the same fingerprint. */
	public static final int THREE_ACQUISITIONS = 0;
	
	/**
     * Private constructor, do not use.
     */
    private EnrollmentType() {
    }
}
