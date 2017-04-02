package com.morpho.morphosmart.sdk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;


/**
 * Helper class to display images from the device. 
 * This class displays the images received from MorphoSmart™;.
 */

public class MorphoImage {
	private MorphoImageHeader morphoImageHeader = new MorphoImageHeader();	
	private byte[] image;
	private byte[] compressedImage;
	private CompressionAlgorithm compressionAlgorithm;	

	/**
	 * Get corresponding image header
	 * @param None
	 * @return the morphoImageHeader
	 */
	public MorphoImageHeader getMorphoImageHeader() {
		return morphoImageHeader;
	}

	/**
	 * Set corresponding image header
	 * @param morphoImageHeader
	 * the morphoImageHeader to set
	 */
	public void setMorphoImageHeader(MorphoImageHeader morphoImageHeader) {
		this.morphoImageHeader = morphoImageHeader;
	}
	
	/**
	 * Get image binary data
	 * @param None
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Set image data
	 * @param input data
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * Set image data from input object
	 * @param input object
	 */
	public void setImage(Object image) {
		this.image = (byte[]) image;
	}

	/**
	 * Get compressed image data
	 * @param None
	 * @return the compressedImage
	 */
	public byte[] getCompressedImage() {
		return compressedImage;
	}

	/**
	 *  * Set compressed image data
	 * @param input compressed data
	 */
	public void setCompressedImage(byte[] compressedImage) {
		this.compressedImage = compressedImage;
	}
	
	/**
	 * Set compressed image data from input object
	 * @param input object
	 */
	
	public void setCompressedImage(Object compressedImage) {
		this.compressedImage = (byte[]) compressedImage;
	}
	
	/**
	 * Construct a MorphoImage from the frame received when the live mode is launched
	 * @param imageStructure input image structure
	 * @return constructed MorphoImage
	 */	

	public static MorphoImage getMorphoImageFromLive(byte[] imageStructure)
	{
		if(imageStructure == null) return null;
		if(imageStructure.length < 12) return null;
		MorphoImage morphoImage = new MorphoImage();
		int nbRow;
		int nbColumn;
		int resX;
		int resY;		
		int nbBitsPerPixel = 0;
		
		ByteBuffer buffer;
		buffer = ByteBuffer.wrap(imageStructure,2,2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);		
		nbRow = buffer.getShort();
		
		buffer = ByteBuffer.wrap(imageStructure,4,2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);		
		nbColumn = buffer.getShort();
		
		buffer = ByteBuffer.wrap(imageStructure,6,2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);		
		resX = buffer.getShort();
		
		buffer = ByteBuffer.wrap(imageStructure,8,2);
		buffer.order(ByteOrder.LITTLE_ENDIAN);		
		resY = buffer.getShort();
		
		nbBitsPerPixel = imageStructure[11];
	
		morphoImage.setCompressionAlgorithm(CompressionAlgorithm.GetCompressionAlgorithm(imageStructure[10]));
		
		morphoImage.morphoImageHeader.setNbRow(nbRow);		
		morphoImage.morphoImageHeader.setNbColumn(nbColumn);		
		morphoImage.morphoImageHeader.setResX(resX);		
		morphoImage.morphoImageHeader.setResY(resY);		
		morphoImage.morphoImageHeader.setNbBitsPerPixel(nbBitsPerPixel);	
		morphoImage.setImage(Arrays.copyOfRange(imageStructure, 12, imageStructure.length));	
		
		return morphoImage;
	}

	/**
	 * Get corresponding compression algorithm
	 * @param None
	 * @return the compression algorithm
	 */
	public CompressionAlgorithm getCompressionAlgorithm() {
		return compressionAlgorithm;
	}

	/** 
	 * Set the compression algorithm of MorphoImage
	 * @param input compression algorithm 
	 */
	public void setCompressionAlgorithm(CompressionAlgorithm compressionAlgorithm) {
		this.compressionAlgorithm = compressionAlgorithm;
	}
	
	/** 
	 * @param id the id to set
	 */
	@SuppressWarnings("unused")
	private void setCompressionAlgorithm(int id) {
		this.compressionAlgorithm = CompressionAlgorithm.GetCompressionAlgorithm(id);
	}
}