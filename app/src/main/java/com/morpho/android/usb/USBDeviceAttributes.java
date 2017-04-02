package com.morpho.android.usb;



import android.os.Parcel;
import android.os.Parcelable;

public class USBDeviceAttributes implements Parcelable {
	
	private final String path; // NOT included in equals() method

	private final int vendorId;
	private final int productId;
	private final int interfaceNumber;
	private String manufacturer = null;
	private String product = null;
	private String mFreindlyName = ""; // default value
	
	protected USBDeviceAttributes(int vendorId, int productId) {
		this.vendorId = vendorId;
		this.productId = productId;
		this.path = null;
		this.interfaceNumber = 0;
	}
	
	private USBDeviceAttributes(Parcel parcel) {
		path = parcel.readString();
		vendorId = parcel.readInt();
		productId = parcel.readInt();
		interfaceNumber = parcel.readInt();

	}
	
	/**
	 * Creator method from a parcel
	 */
	public static Creator<USBDeviceAttributes> CREATOR = new Creator<USBDeviceAttributes>() {
		public USBDeviceAttributes createFromParcel(Parcel parcel) {
			return new USBDeviceAttributes(parcel);
		}

		public USBDeviceAttributes[] newArray(int size) {
			return new USBDeviceAttributes[size];
		}
	};
	
	/**
	 * @return the vendorId
	 */
	public int getVendorId() {
		return vendorId;
	}
	
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * 
	 * @return the interfaceNumber
	 */
	public int getInterfaceNumber() {
		return interfaceNumber;
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	
	/**
	 * Constructs an USBDeviceAttributes object (called by the JNI)
	 * 
	 * @param path
	 * @param vendorId
	 * @param productId
	 * @param interfaceNumber
	 */
	public USBDeviceAttributes(String path, int vendorId, int productId,
			int interfaceNumber) {
		this.path = path;
		this.vendorId = vendorId;
		this.productId = productId;
		this.interfaceNumber = interfaceNumber;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getPath());
		dest.writeInt(getInterfaceNumber());
		dest.writeInt(getProductId());
		dest.writeInt(getVendorId());
	}

	public String getFreindlyName()
	{
		return mFreindlyName;
	}


	public void setFreindlyName(String freindlyName) {
		//Log.d("MORPHO_USB","Setting freindly name to " + freindlyName);
		mFreindlyName = freindlyName;
		
	}
}
