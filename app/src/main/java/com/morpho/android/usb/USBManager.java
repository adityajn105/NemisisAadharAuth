package com.morpho.android.usb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.morpho.morphosmart.sdk.ErrorCodes;

public class USBManager {
	
	static {
		System.loadLibrary("NativeMorphoSmartSDK");
	}

	private static USBManager instance = null;
	public static final String DEVICE_MODEL_MSO100 	= "MSO100";
	public static final String DEVICE_MODEL_MSO300 	= "MSO300";
	public static final String DEVICE_MODEL_MSO350 	= "MSO350";
	public static final String DEVICE_MODEL_MSOTEST = "MSOTEST";
	public static final String DEVICE_MODEL_CBM 	= "CBM";
	public static final String DEVICE_MODEL_MSO1350 = "MSO1350";
	public static final String DEVICE_MODEL_FVP 	= "FVP";
	public static final String DEVICE_MODEL_FVP_C 	= "FVP_C";
	public static final String DEVICE_MODEL_FVP_CL 	= "FVP_CL";
	public static final String DEVICE_MODEL_MEP 	= "MEP";
	
	public static final String SOFTWAREID_MSO100 	= "MSO100";
	public static final String SOFTWAREID_MSO300 	= "MSO300";
	public static final String SOFTWAREID_MSO350 	= "MSO350";
	public static final String SOFTWAREID_MSOTEST 	= "MSOXXX";
	public static final String SOFTWAREID_CBM 		= "CBM";
	public static final String SOFTWAREID_MSO1350 	= "MSO1350";

	public static final String SOFTWAREID_FVP 		= "MSO FVP";
	public static final String SOFTWAREID_FVP_C		= "MSO FVP_C";
	public static final String SOFTWAREID_FVP_CL 	= "MSO FVP_CL";
	public static final String SOFTWAREID_MEP 		= "MEPUSB";
	
	static volatile List<USBDevice> deviceList = null;
	public static Activity context = null;
	public static String ACTION_USB_PERMISSION = "com.morpho.android.usb.USB_PERMISSION";	
	
	public static synchronized USBManager getInstance()
	{	
		if (instance == null)
		{ 
			instance = new USBManager();	
		}
		return instance;
	}

	/**
	 * broadcast receive to handle token permissions If the user grants use
	 * permission to use the token, it is then opened and added to the granted
	 * list
	 */

	public USBManager() {
	}

	/**
	 * The map that stores the list of supported device (based on
	 * vendorId/productId)
	 **/
	private static final Map<USBDeviceAttributes, String> supportedDevices = new HashMap<USBDeviceAttributes, String>();

	static {
		supportedDevices.put(new USBDeviceAttributes(0x079b, 0x0023),DEVICE_MODEL_MSO100);
		supportedDevices.put(new USBDeviceAttributes(0x079b, 0x0024),DEVICE_MODEL_MSO300);
		supportedDevices.put(new USBDeviceAttributes(0x079b, 0x0026),DEVICE_MODEL_MSO350);
		supportedDevices.put(new USBDeviceAttributes(0x079b, 0x0025),DEVICE_MODEL_MSOTEST);
		supportedDevices.put(new USBDeviceAttributes(0x079b, 0x0047),DEVICE_MODEL_CBM);
		supportedDevices.put(new USBDeviceAttributes(0x079b, 0x0052),DEVICE_MODEL_MSO1350);
		supportedDevices.put(new USBDeviceAttributes(0x225D, 0x0001),DEVICE_MODEL_FVP);
		supportedDevices.put(new USBDeviceAttributes(0x225D, 0x0002),DEVICE_MODEL_FVP_C);
		supportedDevices.put(new USBDeviceAttributes(0x225D, 0x0003),DEVICE_MODEL_FVP_CL);
		supportedDevices.put(new USBDeviceAttributes(0x225D, 0x0007),DEVICE_MODEL_MEP);
	}
	
	/**
	 * Initialize context and requests temporary permission for the given package to access the device
	 * @param context  : context to be used in the USB Manager
	 * @param packagePath : package to access the USB device
	 * @return
	 */
	public int initialize(Activity context,String packagePath)
	{
		if(context == null)
		{
			return ErrorCodes.MORPHOERR_BADPARAMETER;
		}
		
		USBManager.context = context;
		USBManager.ACTION_USB_PERMISSION = packagePath;		
		
		if (deviceList == null)
		{
			deviceList = new LinkedList<USBDevice>();
		}
		
		deviceList.clear();
		
		this.initialize();
		
		grantePermission();
		
		return 0;		
	}
	
	private int grantePermission()
	{
		Context context = USBManager.context;
		if(context != null)
		{							
			UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
			HashMap<String, UsbDevice> usbDeviceList = usbManager.getDeviceList();				
			
			Iterator<UsbDevice> usbDeviceIterator = usbDeviceList.values().iterator();
			while (usbDeviceIterator.hasNext()) {
				UsbDevice usbDevice = usbDeviceIterator.next();
				//if (usbDevice.getDeviceName().equals(deviceName)) {
				USBDeviceAttributes l_attr = new USBDeviceAttributes(usbDevice.getDeviceName(), usbDevice.getVendorId(),usbDevice.getProductId(), 1);
				if(USBManager.isSupported(l_attr))
				{
					boolean hasPermission = usbManager.hasPermission(usbDevice);
					if (!hasPermission) {
						// Request permission for using the device									
						usbManager.requestPermission(
								usbDevice,
								PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0)
						);
					}					
				}
			}
		}
				
		return 0;
	}
	
	public native void initialize();
	
	/**
	 * Get the device model from given attributes
	 * 
	 * @param attribs
	 *            the USBDeviceAttributes object to check
	 * @return the device model associated to the attributes
	 */
	public static synchronized String getDeviceModel(USBDeviceAttributes attribs) {
		// Go through the map using an efficient entrySet
		Set<Entry<USBDeviceAttributes, String>> entrySet = supportedDevices.entrySet();
		// For each entry check if attributes match
		for (Entry<USBDeviceAttributes, String> entry : entrySet) {
			USBDeviceAttributes supportedAttribs = entry.getKey();
			// If attributes match, get the corresponding device model
			if (supportedAttribs.getVendorId() == attribs.getVendorId() && supportedAttribs.getProductId() == attribs.getProductId()) {
				return entry.getValue();
			}
		}
		return ""; // should not happen if the corresponding device is supported
	}

	/**
	 * Says if given device attributes are supported
	 * 
	 * @param attribs
	 *            the USBDeviceAttributes object to check
	 * @return true if supported, false otherwise
	 */
	public static synchronized boolean isSupported(USBDeviceAttributes attribs) {
		for (USBDeviceAttributes supportedAttribs : supportedDevices.keySet()) {
			//Log.i("MORPHO_USB", "Supported device : Vendor Id = " + supportedAttribs.getVendorId() + ", product Id = " + supportedAttribs.getProductId());
			//Log.i("MORPHO_USB", "Vendor Id = " + attribs.getVendorId() + ", product Id = " + attribs.getProductId());
			if (supportedAttribs.getVendorId() == attribs.getVendorId()
					&& supportedAttribs.getProductId() == attribs
							.getProductId()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Add a device that was granted permission to the current list
	 * 
	 * @param device
	 *            Usb device
	 */
	private static synchronized int addGrantedDevice(UsbDevice device) {

		int ret = USBConstants.RETURN_ERROR_DEVICE_NOT_CONNECTED;
		// Retrieve the context
		UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);

		USBDeviceAttributes attr = new USBDeviceAttributes(device.getDeviceName(), device.getVendorId(),device.getProductId(), 1); 
		// TODO interface number needs to be dynamic

		// Open the device then add it to the device
		USBDevice usbDevice = new USBDevice(attr, manager, device);

		try {
			//Log.i("MORPHO_USB", "opening device ..");
			ret = usbDevice.open();
			
			if(ret == USBConstants.RETURN_SUCCESS)
			{		
				if (deviceList == null)
				{
					deviceList = new LinkedList<USBDevice>();
				}
				
				deviceList.clear();
				
				usbDevice.setPermissionGranted(true);
				
				deviceList.add(usbDevice);
			}
			//Log.i("MORPHO_USB", "Adding device "+ usbDevice.getProductString() + "to list");
		} catch (IOException e) {
			Log.e("MORPHO_USB",e.getMessage());			
		}		
		
		return ret;
	}

	/**
	 * Enumerate USB devices that have an COMM interface only and request for
	 * permission to use the device
	 * 
	 * @return @throws IOException
	 */
	public static synchronized USBDeviceAttributes[] enumerate() {

		if (context == null) {
			return null;
		}

		// enumerate the devices

		UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		//Log.i("MORPHO_USB","enumerte after getSystemService");
		HashMap<String, UsbDevice> usbdeviceList = manager.getDeviceList();
		if (usbdeviceList.isEmpty()) {
			//Log.i("MORPHO_USB","List empty ..");
			return null;
		}
		
		USBDeviceAttributes[] attrList = new USBDeviceAttributes[usbdeviceList.size()];
		Iterator<UsbDevice> deviceIterator = usbdeviceList.values().iterator();

		// Fetch for USB devices that have and USB interface
		int i = 0;
		int ret = 0;
		int usbInterfaceNum = USBConstants.INVALID_USB_INTERFACE;
		
		if(deviceList != null)
		{
			deviceList.clear();	
		}
		// loop through the USB devices
		while (deviceIterator.hasNext()) {
			UsbDevice device = deviceIterator.next();
			if (device.getDeviceClass() == UsbConstants.USB_CLASS_COMM) {
				int count = device.getInterfaceCount();				
				for (int j = 0; j < count; j++) {					
					if (device.getInterface(j).getInterfaceClass() == UsbConstants.USB_CLASS_COMM) {						
						usbInterfaceNum = j;
						break;
					}
				}
				
				// If the device has an USB interface, add it to the list
				if (usbInterfaceNum != USBConstants.INVALID_USB_INTERFACE) {
					USBDeviceAttributes attrs = new USBDeviceAttributes(device.getDeviceName(),device.getVendorId(),device.getProductId(), usbInterfaceNum);

					if (isSupported(attrs)) {						
						// reset flag for next iteration
						usbInterfaceNum = USBConstants.INVALID_USB_INTERFACE;
						// ask for permission to use
						// Request the device permission
												
						if (manager.hasPermission(device))
						{									
							ret = addGrantedDevice(device);
							
							if(ret == USBConstants.RETURN_SUCCESS)
							{
							
								attrList[i++] = attrs;
																			
								switch(attrs.getProductId())
								{	
									// DEVICE_MODEL_MSO100
									case 0x0023 :
										attrs.setFreindlyName(SOFTWAREID_MSO100);
										break;
										
									// DEVICE_MODEL_MSO300
									case 0x0024 :
										attrs.setFreindlyName(SOFTWAREID_MSO300);
										break;
										
									// DEVICE_MODEL_MSO350
									case 0x0026 :
										attrs.setFreindlyName(SOFTWAREID_MSO350);
										break;
										
									// DEVICE_MODEL_MSOTEST
									case 0x0025 :
										attrs.setFreindlyName(SOFTWAREID_MSOTEST);
										break;
										
									// DEVICE_MODEL_CBM
									case 0x0047 :
										attrs.setFreindlyName(SOFTWAREID_CBM);
										break;
									// DEVICE_MODEL_MSO1350
									case 0x0052 :
										attrs.setFreindlyName(SOFTWAREID_MSO1350);
										break;
										
									// DEVICE_MODEL_FVP
									case 0x0001 :
										attrs.setFreindlyName(SOFTWAREID_FVP);
										break;
										
									// DEVICE_MODEL_FVP_C
									case 0x0002 :
										attrs.setFreindlyName(SOFTWAREID_FVP_C);
										break;
										
									// DEVICE_MODEL_FVP_CL
									case 0x0003 :
										attrs.setFreindlyName(SOFTWAREID_FVP_CL);
										break;
										
									// DEVICE_MODEL_MEP
									case 0x0007 :
										attrs.setFreindlyName(SOFTWAREID_MEP);
										break;
									default:
										break;
								}
								break;
							}
						}
					}
				}
			}
		}

		// return the array
		return attrList;
	}

	/**
	 * List the HID devices attached to host that are supported
	 * 
	 * requestDevicePermissions() must be called before and SEPARATELY in order
	 * to request permissions from the user. Requesting permissions is an
	 * asynchronous operation that runs in the main UI thread (design limitation
	 * by Android). This implies that enumerating for devices has be done in a
	 * two step process.
	 * 
	 * 1. requestDevicePermissions() 2. listDevices()
	 * 
	 * @return an ArrayList of USBDevice of supported devices or null in case
	 *         the requestDevicePermissions() was not called SEPARATELY before
	 * @throws IOException
	 *             if enumeration fails for any reason
	 */
	public static synchronized USBDevice listDevices() throws IOException {
		
		if(deviceList != null)
		{
			USBDeviceAttributes[] attribsList = enumerate();
			if (deviceList.size() == 1 && attribsList.length > 0)
			{
				//Log.d("MORPHO_USB", "creating interface ..");
				deviceList.get(0).CreateInterface(attribsList[0].getInterfaceNumber());
			}		
						
			if(deviceList.isEmpty())
			{
				return null;
			}
			else
			{
				return deviceList.get(0);
			}
		}
		
		return null;
	}

	public static synchronized int getNbDevices() throws IOException {
		if(deviceList == null) return 0;
		return deviceList.size();
	}
	
	public void clearDevices() {
		deviceList.clear();
	}
	
	public static int isPermissionGranted()
	{
		USBDevice usbDevice = getDevice();
		
		if(usbDevice != null)
		{
			if(usbDevice.isPermissionGranted())
			{
				return ErrorCodes.MORPHO_OK;
			}
			else
			{
				return ErrorCodes.MORPHOERR_USB_PERMISSION_DENIED;
			}
		}
		else
		{
			return ErrorCodes.MORPHOERR_CONNECT;
		}
	}
	
	public static USBDevice getDevice()
	{
		if(deviceList == null)
		{
			return null;
		}
		else
		{
			if(deviceList.size() > 0)
			{
				return deviceList.get(0);
			}
			else
			{
				return null;
			}
		}
	}
}
