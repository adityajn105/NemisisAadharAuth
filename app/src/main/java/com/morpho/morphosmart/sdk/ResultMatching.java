package com.morpho.morphosmart.sdk;

/**
 * 
 * This class store a matching score and number of PKs as a result of matching operation.
 *
 */
public class ResultMatching {

	private int matchingScore;
	private int matchingPKNumber;
	
	/**
	 * 
	 * @return
	 */
	public int getMatchingScore() {
		return matchingScore;
	}
	
	/**
	 * 
	 * @param matchingScore
	 */
	public void setMatchingScore(int matchingScore) {
		this.matchingScore = matchingScore;
	}
	
	/**
	 * 
	 * @return matching PK number
	 */
	public int getMatchingPKNumber() {
		return matchingPKNumber;
	}
	
	/**
	 * 
	 * @param matchingPKNumber
	 */
	public void setMatchingPKNumber(int matchingPKNumber) {
		this.matchingPKNumber = matchingPKNumber;
	}
	
}
