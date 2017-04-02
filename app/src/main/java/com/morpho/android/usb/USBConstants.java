/**
 * Definition of USB standard constants
 */
package com.morpho.android.usb;

public class USBConstants {

	/**
	 * Return Codes
	 */
	public static final int RETURN_SUCCESS = 0x00;
	public static final int RETURN_ERROR_CANNOT_CLAIM_INTERFACE = -1;
	public static final int RETURN_ERROR_FEATURE_REPORT_SEND_FAILURE = -2;
	public static final int RETURN_ERROR_FEATURE_REPORT_GET_FAILURE = -3;
	public static final int RETURN_ERROR_CANNOT_GET_STRING_DESCRIPTOR = -4;
	public static final int RETURN_ERROR_CANNOT_RELEASE_INTERFACE = -5;
	public static final int RETURN_ERROR_DEVICE_NOT_CONNECTED = -6;
	public static final int RETURN_ERROR_UNABLE_TO_WRITE_DATA = -7;
	public static final int RETURN_ERROR_UNABLE_TO_READ_DATA = -8;
	
	public static final long SPUSB_TIMEOUT_INFINITE	=	0xFFFFFFFF;
	
	/**
	 * USB constants
	 */
	// forcing this variable to true unloads the Linux driver that may interfere
	// with the read/write ops
	public static final boolean FORCE_CLAIM = true;
	public static final int INVALID_USB_INTERFACE = -1;
	public static final int TIMEOUT = 1000;
	public static final int DEFAULT_PACKET_SIZE = 8;
	public static final int DEFAULT_STRING_DESCRIPTOR_SIZE = 64;

	// information offsets in device descriptor
	public static final int OFFSET_MANUFACTRURER_IDX = 0x0E;
	public static final int OFFSET_PRODUCT_IDX = 0x0F;
	public static final int OFFSET_SERIAL_IDX = 0x10;
	public static final int OFFSET_MAX_PACKET_SIZE = 0x07;
	public static final int USB_DEVICE_DESCRIPTOR_LENGTH = 0x12;
	public static final int USB_DEVICE_DESCRIPTOR_LANGUAGE_ENGLISH = 0x0409;
	
	/**
	 * Request Type
	 */
	// D7 - Data direction
	public static final int USB_ENDPOINT_IN = 0x80;
	public static final int USB_ENDPOINT_OUT = 0x00;
	
	// D6:5 Type
	public static final int USB_REQUEST_TYPE_CLASS = (0x01 << 5);
	public static final int USB_REQUEST_TYPE_STANDARD = 0x00;

	// D4:0 Recipient
	public static final int USB_RECIPIENT_INTERFACE = 0x01;
	
	/**
	 * Request
	 */
	public static final int USB_REQUEST_SET_FEATURE = 0x09;
	public static final int USB_REQUEST_GET_FEATURE = 0x01;
	public static final int USB_REQUEST_GET_DESCRIPTOR = 0x06;
	
	/**
	 * Value
	 */
	public static final int USB_DESCRIPTOR_DEVICE = 0x01;
	public static final int USB_DESCRIPTOR_STRING = 0x03;
	public static final int USB_DESCRIPTOR_REPORT = 0x03;
	public static final int USB_REPORT_TYPE_FEATURE = (USB_DESCRIPTOR_REPORT << 8);
	public static final int USB_REPORT_TYPE_STRING = (USB_DESCRIPTOR_STRING << 8);
	
	public static final int USB_ENDPOINT_ADDRESS_MASK =	0x0f;    /* in bEndpointAddress */
	public static final int USB_ENDPOINT_DIR_MASK	=	0x80;

	public static final int USB_ENDPOINT_TYPE_MASK	=	0x03;    /* in bmAttributes */
	public static final int USB_ENDPOINT_TYPE_CONTROL =	0;
	public static final int USB_ENDPOINT_TYPE_ISOCHRONOUS =	1;
	public static final int USB_ENDPOINT_TYPE_BULK	=	2;
	public static final int USB_ENDPOINT_TYPE_INTERRUPT =	3;

	/* Interface descriptor */
	public static final int USB_MAXINTERFACES =	32;
	
}

