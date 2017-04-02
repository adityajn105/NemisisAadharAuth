package com.morpho.morphosmart.sdk;

/**
 * 
 * This Class describe a Morpho Image Header that's stored in the first 12
 * bytes of a frame already read from MorphoSmart™ device in live mode.
 * 
 */
public class MorphoImageHeader {
	private int nbRow;
	private int nbColumn;
	private int resY;
	private int resX;	
	private int nbBitsPerPixel;
	private int compressionRatio;

	/**
	 * @return the nbBitsPerPixel
	 */
	public int getNbBitsPerPixel() {
		return nbBitsPerPixel;
	}

	/**
	 * @param nbBitsPerPixel
	 *            the nbBitsPerPixel to set
	 */
	public void setNbBitsPerPixel(int nbBitsPerPixel) {
		this.nbBitsPerPixel = nbBitsPerPixel;
	}

	/**
	 * @return the resX
	 */
	public int getResX() {
		return resX;
	}

	/**
	 * @param resX
	 *            the resX to set
	 */
	public void setResX(int resX) {
		this.resX = resX;
	}

	/**
	 * @return the resY
	 */
	public int getResY() {
		return resY;
	}

	/**
	 * @param resY
	 *            the resY to set
	 */
	public void setResY(int resY) {
		this.resY = resY;
	}

	/**
	 * @return the nbColumn
	 */
	public int getNbColumn() {
		return nbColumn;
	}

	/**
	 * @param nbColumn
	 *            the nbColumn to set
	 */
	public void setNbColumn(int nbColumn) {
		this.nbColumn = nbColumn;
	}

	/**
	 * @return the nbRow
	 */
	public int getNbRow() {
		return nbRow;
	}

	/**
	 * @param nbRow
	 *            the nbRow to set
	 */
	public void setNbRow(int nbRow) {
		this.nbRow = nbRow;
	}

	/**
	 * @return the compressionRatio
	 */
	public int getCompressionRatio() {
		return compressionRatio;
	}

	/**
	 * @param compressionRatio the compressionRatio to set
	 */
	public void setCompressionRatio(int compressionRatio) {
		this.compressionRatio = compressionRatio;
	}
}
