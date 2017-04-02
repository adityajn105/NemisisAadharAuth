package com.morpho.android.usb;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

public class USBDevice {
	public  USBDeviceAttributes mAttributes = null;
	public  static UsbManager mManager = null;
	public  UsbDeviceConnection mConnection = null;
	public  UsbEndpoint mEndpointIn = null;
	public  UsbEndpoint mEndpointOut = null;
	public  int mMaxPacketOutSize = 0;
	public  int mMaxPacketInSize = 0;
	public  UsbInterface mInterface = null;
	public  UsbDevice mDevice = null;
	public	String mFreindlyName = "";
	private byte[] mDeviceDescriptor = null;
	private boolean permissionGranted = false;
	
	
	/**
	 * 
	 * @return true if the device was granted permission to be used false otherwise
	 */
	public synchronized boolean hasPermission() {

		if (mDevice == null || mManager == null) {
			return false;
		}
		
		return mManager.hasPermission(mDevice);
	}
	
	public synchronized int open() throws IOException {

		if (mDevice == null || mManager == null) {
			throw new IOException("Failuire to open device: either usb manager or connection null");
		}
		
		// create the interface
		mInterface = mDevice.getInterface(getAttributes().getInterfaceNumber());

		// Open the device
		
		mConnection = mManager.openDevice(mDevice);
		if(mConnection != null)
		{
			mConnection.claimInterface(mInterface, true);
		}
		else
		{
			return USBConstants.RETURN_ERROR_DEVICE_NOT_CONNECTED;
		}
		//Log.d("MORPHO_USB","device opened !");
		return USBConstants.RETURN_SUCCESS;
	}
	
	public synchronized UsbInterface CreateInterface(int interfaceNumber)
	{
		return mDevice.getInterface(interfaceNumber);
	}
	
	public USBDevice()
	{		
	}
	
	public USBDevice(USBDeviceAttributes iAttributes, UsbManager iManager, UsbDevice iDevice) {
		
		mDevice = iDevice;
		mAttributes = iAttributes;
		mManager = iManager;	
	}
	
	/**
	 * Get The Product String from a USB device.
	 * 
	 * @return the string buffer to put the data into
	 * @throws IOException
	 */
	public  synchronized String getProductString() throws IOException {
		//Log.d("MORPHO_USB","Entring get product string");
		if (getAttributes().getProduct() == null) {
			//Log.d("MORPHO_USB","product name null ");
			byte[] desc = getDeviceDescriptor();
			//Log.d("MORPHO_USB","getDeviceDescriptor : " + desc);
			byte[] pdt = new byte[USBConstants.DEFAULT_STRING_DESCRIPTOR_SIZE];
			int idx = desc[USBConstants.OFFSET_PRODUCT_IDX];

			// Get the descriptor given the index
			getStringDescriptor(pdt, idx);
			
			
			//Log.d("MORPHO_USB","getStringDescriptor : pdt = " + pdt + ", idx = " + idx);
			// cache the manufacturer string
			getAttributes().setProduct(convertDescriptorUnicodeLEToString(pdt));
		}

		return getAttributes().getProduct();
	}
	
	/**
	 * Convert the descriptor unicode value to a string
	 * 
	 * @param buffer
	 *            Buffer containing unicode string in unicode UTF-16LE
	 * @return converted string
	 */
	private synchronized String convertDescriptorUnicodeLEToString(byte[] buffer) {

		try {
			// retrieve the product string
			// offset 0: length
			// offset 1 descriptor type (0x03)
			// offset 2: string unicode little endian null terminated
			int bufferLength = buffer[0];
			byte[] subStr = new byte[bufferLength - 2];
			System.arraycopy(buffer, 2, subStr, 0, bufferLength - 2);

			// Convert the returned string descriptor to a string
			// the returned descriptor is in unicode little-endian
			return new String(subStr, "UTF-16LE");

		} catch (UnsupportedEncodingException ex) {
			return "not available";
		}
	}
	
	public synchronized int findEndPoint()
	{
		if(mInterface != null)
		{
			//Log.d("MORPHO_USB","entring findendpoint ..");
			int count = mInterface.getEndpointCount();
			//Log.d("MORPHO_USB","endpoints count : " + count);
			if(count ==  2)
			{
				UsbEndpoint lEndPoint0  = mInterface.getEndpoint(0);
				UsbEndpoint lEndPoint1  = mInterface.getEndpoint(1);
				
				if (  ((lEndPoint0.getAttributes() & USBConstants.USB_ENDPOINT_TYPE_MASK) == USBConstants.USB_ENDPOINT_TYPE_BULK) 
					  && ((lEndPoint1.getAttributes() & USBConstants.USB_ENDPOINT_TYPE_MASK) == USBConstants.USB_ENDPOINT_TYPE_BULK))
				{
					
					if(((lEndPoint0.getAddress() & USBConstants.USB_ENDPOINT_IN) != 0 )
						&& ((lEndPoint1.getAddress() & USBConstants.USB_ENDPOINT_IN) == 0) 
					)
					{
						//Log.d("MORPHO_USB","Setting endpoints .. 0");
						mEndpointOut = lEndPoint1;
						mEndpointIn = lEndPoint0;
						mMaxPacketInSize = lEndPoint0.getMaxPacketSize();
						mMaxPacketOutSize = lEndPoint1.getMaxPacketSize();
						return 0;
					}
					else if(((lEndPoint0.getAddress() & USBConstants.USB_ENDPOINT_IN) == 0 )
							&& ((lEndPoint1.getAddress() & USBConstants.USB_ENDPOINT_IN) != 0))
					{
						//Log.d("MORPHO_USB","Setting endpoints .. 1");
						mEndpointOut = lEndPoint0;
						mEndpointIn = lEndPoint1;
						mMaxPacketInSize = lEndPoint1.getMaxPacketSize();
						mMaxPacketOutSize = lEndPoint0.getMaxPacketSize();
						return 0;
					}	
				}
				return -1;
			}
			return -2;
		}
		return -3;
	}
	
    /**
     * @return the attributes
     */
    public synchronized USBDeviceAttributes getAttributes() {
        return mAttributes;
    }
    
	/**
	 * Get the string descriptor given an index
	 * 
	 * @param buffer
	 *            The buffer to fill
	 * @param index
	 *            The index of the string descriptor
	 * @return The value of read bytes
	 * @throws IOException
	 *             If res is -1 throw an ioexception
	 */
	private synchronized int getStringDescriptor(byte[] buffer, int index)
			throws IOException {

		return getDescriptor(buffer, USBConstants.USB_REPORT_TYPE_STRING, index);

	}
	
	/**
	 * Get the device descriptor from tyhe physical device or the cached value
	 * if it has been already retrieved.
	 * 
	 * @return the string buffer to put the data into
	 * @throws IOException
	 */
	private synchronized byte[] getDeviceDescriptor() throws IOException {

		// If not cached, fetch the device descriptor from the device
		if (mDeviceDescriptor == null) {

			mDeviceDescriptor = new byte[USBConstants.USB_DEVICE_DESCRIPTOR_LENGTH];

			getDescriptor(mDeviceDescriptor,
					(USBConstants.USB_DESCRIPTOR_DEVICE << 8), 0x01);
		}

		return mDeviceDescriptor;
	}
	
	/**
	 * Get a descriptor given an index
	 * 
	 * @param buffer
	 *            The buffer to fill
	 * @param index
	 *            The index of the string descriptor
	 * @return The value of read bytes
	 * @throws IOException
	 *             If res is -1 throw an ioexception
	 */
	private synchronized int getDescriptor(byte[] buffer, int descType,
			int index) throws IOException {

		// Claim the interface before sending the data
		if (mConnection == null || mInterface == null) {
			throw new IOException(
					"Error get the feature report from the device: either connection or interface null");
		}

		if (!(mConnection.claimInterface(mInterface, USBConstants.FORCE_CLAIM))) {
			throw new IOException("Could not claim the USB device interface");
		}

		int length = buffer.length;
		byte[] desc = new byte[buffer.length];

		// Send the feature report
		int res = mConnection.controlTransfer(USBConstants.USB_ENDPOINT_IN
				| USBConstants.USB_REQUEST_TYPE_STANDARD,
				USBConstants.USB_REQUEST_GET_DESCRIPTOR, descType | index,
				USBConstants.USB_DEVICE_DESCRIPTOR_LANGUAGE_ENGLISH, desc,
				length, USBConstants.TIMEOUT);

		if (res < 0) {
			throw new IOException(
					"getDescriptor operation is unsuccessful. Descriptor index="
							+ index + "Descriptor type=" + descType);
		}

		// copy the array back
		System.arraycopy(desc, 0, buffer, 0, desc.length);

		return res;

	}


	public synchronized int close()
	{
		boolean ret = false;
		if (mConnection != null ){
			
			ret = mConnection.releaseInterface(mInterface);
			mConnection.close();
			if(ret)
			{
				return USBConstants.RETURN_SUCCESS;
			}
			else
			{
				return USBConstants.RETURN_ERROR_CANNOT_RELEASE_INTERFACE;
			}			
		}
		return USBConstants.RETURN_SUCCESS;	
	};
	
	public synchronized UsbInterface claimInterface()
	{
		return  mInterface;
	};
	
	public synchronized void releaseInterface()
	{
	};
	
	public synchronized int write(byte[] data, int datasize, int timeout)
	{		
		//Log.e("MORPHO_USB","write 1");
		if(mConnection != null)
		{
			//Log.e("MORPHO_USB","write 2");
			mConnection.claimInterface(mInterface, true);
			
			if((datasize > 0) && (datasize % mMaxPacketOutSize == 0))
			{
				//Log.d("MORPHO_USB","Send ZLP ..");
				if(mConnection.bulkTransfer(mEndpointOut, data, 0, 1) < 0)
				{
					//Log.e("MORPHO_USB","Unable to write ZLP !");
					return USBConstants.RETURN_ERROR_UNABLE_TO_WRITE_DATA;
				}
			}
			return mConnection.bulkTransfer(mEndpointOut, data, datasize, timeout);
		}
		else
		{
			//Log.e("MORPHO_USB", "connection is null");
			return USBConstants.RETURN_ERROR_DEVICE_NOT_CONNECTED;
		}
	};
	
	public synchronized int read(byte[] data, int datasize, int timeout)
	{
		//Log.e("MORPHO_USB","counter = " + counter);
		if(mConnection != null)
		{			
			mConnection.claimInterface(mInterface, true);
			if(mEndpointIn == null)
			{
				//Log.e("MORPHO_USB","null read endpoint !");
			}
			int ret = mConnection.bulkTransfer(mEndpointIn, data, datasize, timeout);
			//Log.d("MORPHO_USB","bulkTransfer ret ="  + ret);
			return ret;
			
		}
		else
		{
			//Log.e("MORPHO_USB", "connection is null");
			return USBConstants.RETURN_ERROR_DEVICE_NOT_CONNECTED;
		}
		
	};
	
	public synchronized int getStringSimple(byte[] buffer, int index)
	{
		try {
			return getStringDescriptor(buffer, index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return USBConstants.RETURN_ERROR_CANNOT_GET_STRING_DESCRIPTOR;
	}
	

    public synchronized String getSerialNumberString() throws IOException {
    	//Log.d("MORPHO_USB" ,"getting serial number ..");
		if (mConnection != null) {
			
			//Log.d("MORPHO_USB" ,"getting serial number .. #" + mConnection.getSerial());
			return mConnection.getSerial();
		}
		
		return "Serial not available";
	}
    
    public synchronized int getMaxPacketInSize()
    {
    	return mMaxPacketInSize;
    }
    
    public int getMaxPacketOutSize()
    {
    	return mMaxPacketOutSize;
    }

	/**
	 * @return the permissionGranted
	 */
	public boolean isPermissionGranted() {
		return permissionGranted;
	}

	/**
	 * @param permissionGranted the permissionGranted to set
	 */
	public void setPermissionGranted(boolean permissionGranted) {
		this.permissionGranted = permissionGranted;
	}
    
}
