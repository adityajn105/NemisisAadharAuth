package com.morpho.morphosmart.sdk;

/**
 * 
 * Number of fingers to acquire during the Capture/Enroll operations.
 *
 */
public class FingerNumber
{
	private byte [] fingerNumber;
	
	public FingerNumber(){
		fingerNumber = new byte [100];
	}

	public byte [] getFingerNumber() {
		return fingerNumber;
	}

	public void setFingerNumber(byte [] fingerNumber) {
		this.fingerNumber = fingerNumber;
	}

	
}
