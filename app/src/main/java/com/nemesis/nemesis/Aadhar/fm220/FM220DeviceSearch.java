package com.nemesis.nemesis.Aadhar.fm220;

import android.content.Context;

import java.util.HashMap;
import java.util.Iterator;

import android.app.PendingIntent;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

public class FM220DeviceSearch {
	
	public static boolean isFM220DeviceConnected(Context context)
	{
		boolean deviceConnected = false;
		
		UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		
		// Request usb device permission, now
		HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
		if (deviceList.size() > 0) 
		{
			Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
			while (deviceIterator.hasNext()) 
			{
				UsbDevice device = deviceIterator.next();

				if (((device.getProductId()== 0x8220) && (device.getVendorId() == 0x0bca))
						|| ((device.getProductId() == 0x8220) && (device.getVendorId()  == 0x0b39))
						|| ((device.getProductId() == 0x8210) && (device.getVendorId() == 0x0b39))) 
				{
					deviceConnected = true;
					break;
				}
			}
		}
		return deviceConnected;
	}
	
	/**
	 * Check if this application has permission to use IriShield.
	 * 
	 * @param context
	 * @param permissionIntent
	 */
	public static boolean hasFM220UsbPermission(Context context) {
		boolean hasPermission = false;
		
		UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		
		// Request usb device permission, now
		HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
		if (deviceList.size() > 0) {
			Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

			while (deviceIterator.hasNext()) {
				UsbDevice device = deviceIterator.next();

				if ((((device.getProductId()== 0x8220) && (device.getVendorId() == 0x0bca))
						|| ((device.getProductId() == 0x8220) && (device.getVendorId()  == 0x0b39))
						|| ((device.getProductId() == 0x8210) && (device.getVendorId() == 0x0b39))) && mUsbManager.hasPermission(device)) 
				{
					hasPermission = true;
					break;
				}
			}
		}
		
		return hasPermission;
	}
	
	public static UsbDevice getUSBDevice(Context context)
	{
		UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		// Request usb device permission, now
		HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
		if (deviceList.size() > 0) {
			Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

			while (deviceIterator.hasNext()) {
				UsbDevice device = deviceIterator.next();

				if ((((device.getProductId()== 0x8220) && (device.getVendorId() == 0x0bca))
						|| ((device.getProductId() == 0x8220) && (device.getVendorId()  == 0x0b39))
						|| ((device.getProductId() == 0x8210) && (device.getVendorId() == 0x0b39))) && mUsbManager.hasPermission(device)) 
				{
					return device;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Request usb permission for IriShield.
	 * 
	 * @param context
	 * @param permissionIntent
	 */
	public static boolean requestFM220UsbPermission(Context context, PendingIntent permissionIntent) {
		boolean requesting = false;
		
		UsbManager mUsbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		
		// Request usb device permission, now
		HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
		if (deviceList.size() > 0) {
			Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

			while (deviceIterator.hasNext()) 
			{
				UsbDevice device = deviceIterator.next();
				if ((((device.getProductId()== 0x8220) && (device.getVendorId() == 0x0bca))
						|| ((device.getProductId() == 0x8220) && (device.getVendorId()  == 0x0b39))
						|| ((device.getProductId() == 0x8210) && (device.getVendorId() == 0x0b39))) && !mUsbManager.hasPermission(device)) 
				{
					mUsbManager.requestPermission(device, permissionIntent);
					requesting = true;
					break;
				}
			}
		}
		
		return requesting;
	}
	
}
