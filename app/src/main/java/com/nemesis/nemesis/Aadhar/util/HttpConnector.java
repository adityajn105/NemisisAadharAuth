package com.nemesis.nemesis.Aadhar.util;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Base64;

public class HttpConnector 
{
	private static HttpConnector instance = null;
	private HttpContext httpContext = null;

	public static String UserId;
	public static String Password;

	public static HttpConnector getInstance()
	{
		if(instance == null)
			instance = new HttpConnector();
		return instance;
	}
	
	private HttpConnector()
	{
		
	}
	
	public String postData(String xmlData) throws Exception
	{
		SchemeRegistry registry = new SchemeRegistry();
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null, null);
		SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

         // Set the timeout in milliseconds until a connection is established.
	     // The default value is zero, that means the timeout is not used. 
	     int timeoutConnection = 5000;
	     HttpConnectionParams.setConnectionTimeout(params, timeoutConnection);
	     // Set the default socket timeout (SO_TIMEOUT) 
	     // in milliseconds which is the timeout for waiting for data.
	     int timeoutSocket = 30000;
	     HttpConnectionParams.setSoTimeout(params, timeoutSocket);
	     
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		socketFactory.setHostnameVerifier((X509HostnameVerifier) SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        registry.register(new Scheme("https", sf, 443));
        ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
     
		DefaultHttpClient httpClient = new DefaultHttpClient(ccm, params);

		// Set verifier     
		HttpsURLConnection.setDefaultHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		//System.out.println("KYCServerURI : " + authServiceURI.toASCIIString());
		HttpPost httpPost = new HttpPost(Global.Url);
		BasicHttpEntity filter = new BasicHttpEntity();
		filter.setContent(new ByteArrayInputStream(xmlData.getBytes()));
		httpPost.setEntity(filter);

		HttpResponse response = null;

		if(httpContext == null)
			response = httpClient.execute(httpPost);
		else
		{
			response = httpClient.execute(httpPost,httpContext);
		}
		
		HttpEntity entity = response.getEntity();
		if (entity == null) 
		{
			throw new Exception("No response from server!");
		}
		String responseXML = EntityUtils.toString(entity);

		System.out.println("RESPONSE1: " + responseXML);
		
		return responseXML;
		//return  XMLUtilities.parseXML(respClazz, responseXML);
	}
}