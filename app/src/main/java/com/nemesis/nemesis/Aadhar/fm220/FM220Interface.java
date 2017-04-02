package com.nemesis.nemesis.Aadhar.fm220;

import com.startek.fm210.tstlib;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class FM220Interface {

	private UsbDevice device;
	private UsbInterface usbIf;
	private Context context;
	private IFM220 iface;
	
 	private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
 	private UsbNotification mUsbNotification   			= null;
	private PendingIntent mPermissionIntent ;
	
	private UsbManager manager;
	private UsbDeviceConnection conn;

	private UsbEndpoint epIN; // NO_UCD (unused code)
	private UsbEndpoint epOUT; // NO_UCD (unused code)
	private UsbEndpoint ep2IN; // NO_UCD (unused code)


	public void initialize(Context context, IFM220 iface) throws Exception
	{
		this.context  = context;
		this.iface = iface;

		if(FM220DeviceSearch.isFM220DeviceConnected(context) == false)
		{
			throw new Exception("FM220 Device not connected!");
		}
		
		//Get notification instance
        mUsbNotification = UsbNotification.getInstance(context);
        
        //Register detached event for the IriShield
        IntentFilter filter = new IntentFilter();
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
		context.registerReceiver(mUsbReceiver, filter);		
		
		mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
		IntentFilter filterPermisson = new IntentFilter(ACTION_USB_PERMISSION);
		context.registerReceiver(mUsbPermissionReceiver, filterPermisson);

		
		if(FM220DeviceSearch.hasFM220UsbPermission(context) == true)
		{
			connect();
		}
		else
		{
			FM220DeviceSearch.requestFM220UsbPermission(context, mPermissionIntent);
		}
		
	}
	
	/*****************************************************************************
	 * A broadcast receiver that is used to receive the detached events whenever
	 * a IriShield device is detached from the Android system. This function also 
	 * sends a message to notify the main thread (GUI thread) to execute the post
	 * jobs.
	 *****************************************************************************/
	private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() 
	{
	    public void onReceive(Context context, Intent intent) 
	    {
	        String action = intent.getAction();
	        if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) 
	        {         
	        	//Make a notice to user
	        	mUsbNotification.cancelNofitications();
	        	mUsbNotification.createNotification("FM220 is disconnected.");
	        	
	        	//Send a message to main thread
		        final Message msg = Message.obtain(mHandler, 0, null);
		        mHandler.dispatchMessage(msg);
	        }
	    }
	};
	
	/*****************************************************************************
	 * A handler that is used to receive and handle the message sent from the 
	 * broadcast receiver.
	 *****************************************************************************/
	final Handler mHandler = new Handler() 
	{
	    @Override
	    public void handleMessage(Message msg) 
	    {
	    	//Reset state of the application
	    	//setInitState();
	    	
	    	//Scan and open device again
	    	try
	    	{
	    		connect();
	    	}catch(Exception ex)
	    	{ 
	    		iface.onFM220DeviceConnectError(ex.getMessage());
	    	}
	    }
	};
	
	private final BroadcastReceiver mUsbPermissionReceiver = new BroadcastReceiver() 
	{

	    public void onReceive(Context context, Intent intent) 
	    {
	        String action = intent.getAction();
	        if (ACTION_USB_PERMISSION.equals(action)) 
	        {
	        	Log.d("FM220", "USB Permission accepted...1");
	            synchronized (this)
	            {
	                UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

	                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) 
	                {
	    	        	Log.d("FM220", "USB Permission accepted...2");
	                    if(device != null)
	                    {
	        	        	Log.d("FM220", "USB Permission accepted...3");

	                      //call method to set up device communication
	            	    	try
	            	    	{
	            	        	Log.d("FM220", "USB Permission accepted...4");
	            	    		connect();
	            	    	}catch(Exception ex)
	            	    	{ 
	            	    		iface.onFM220DeviceConnectError(ex.getMessage());
	            	    	}
	                    }
	                } 
	                else {
	                	iface.onFM220PermissionDevice();
	                }
	            }
	        }
	    }
	};

	
	private void connect() throws Exception
	{
		Log.d("FM220", "In Connect");

		device = FM220DeviceSearch.getUSBDevice(context);

		if (device == null)
		{
			Log.d("FM220", "Device is NULL");
			return;
		}
		usbIf = device.getInterface(0);

		epIN = null;
		epOUT = null;
		ep2IN = null;
		epOUT = usbIf.getEndpoint(0);
		epIN = usbIf.getEndpoint(1);
		ep2IN = usbIf.getEndpoint(2);

		manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
		conn = manager.openDevice(device);

		if (conn.getFileDescriptor() == -1) 
		{
			Log.d("FM220", "Fails to open DeviceConnection");
			throw new Exception("Fails to open FM220 device");
		} else {
			Log.d("FM220",
					"Opened DeviceConnection"
							+ Integer.toString(conn.getFileDescriptor()));
		}

		if (conn.releaseInterface(usbIf)) 
		{
			Log.d("FM220", "Released OK");
		} 
		else 
		{
			Log.d("FM220", "Released fails");
		}

		if (conn.claimInterface(usbIf, true)) 
		{
			Log.d("FM220", "Claim OK");
		} 
		else 
		{
			Log.d("FM220", "Claim fails");
		}
		
		
		Log.d("FM220", "Device Connected");
		iface.onFM220DeviceConnected();
	}
	
	public void captureFMR() 
	{
		
		new Thread() 
		{
			public void run() 
			{
				if (conn == null) 
				{
					iface.onFM220CaptureError("FM220 Device not found!");
					return;
				}
				
				tstlib.SetFPLibraryPath(context.getApplicationContext().getFilesDir().getParentFile().getPath() + "/lib/");
				tstlib.InitialSDK();

				int ret = tstlib.FP_ConnectCaptureDriver(conn.getFileDescriptor());
				if (ret != 0) 
				{
					iface.onFM220CaptureError("FM220 Driver connect error!");
					return;
				}
				
				System.gc();
				int rtn;
				int ctr = 0;
				int MAX_CTR = 200;
				while ((rtn = tstlib.FP_Capture()) != 0) {
				
					if(ctr++ > MAX_CTR)
						break;
					try
					{
						Thread.sleep(50);
					}catch(InterruptedException ex) {}
					
				}
				
				if(ctr > MAX_CTR )
				{
					iface.onFM220DeviceConnectError("Timeout!");
					return;
				}
				
				byte[] minu_code1 = new byte[512];
		
				rtn = tstlib.FP_GetTemplate(minu_code1);
				int sz, un, ln;
				un = minu_code1[10];
				ln = minu_code1[11];
				if (ln < 0)
					ln = ln + 256;
				if (un < 0)
					ln = ln + 256;
				sz = (un * 256) + ln;
				byte[] ISOdata = new byte[sz];
				System.arraycopy(minu_code1, 0, ISOdata, 0,sz);
				tstlib.FP_DisconnectCaptureDriver();
				iface.onFM220CaptureCompleted(ISOdata);
			}
		}.start();
	}
	
	public void unInitialize()
	{
    	mUsbNotification.cancelNofitications();
    	if(mUsbReceiver != null)
    		context.unregisterReceiver(mUsbReceiver);
    	if(mUsbPermissionReceiver != null)
    		context.unregisterReceiver(mUsbPermissionReceiver);
	}
}
