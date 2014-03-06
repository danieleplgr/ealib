package com.ealib.net.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.ealib.net.http.exceptions.SSLCertificateException;
import com.ealib.net.http.ssl.certificates.EasySSLSocketFactory;

public class HttpsClientUtils {
	
	protected static int CONNECTION_TIMEOUT = 35000;	
	// the millsec time for waiting data
	protected static int SOCKET_TIMEOUT = 35000;
	
	public static HttpClient getClient(int connectionTimeout, int socketTimeout)
			throws SSLCertificateException {
		CONNECTION_TIMEOUT = connectionTimeout;
		SOCKET_TIMEOUT = socketTimeout;
		return createClient(null, null, null);
	}
	
	public static HttpClient getClient(HttpHost proxy, AuthScope authScope,
			UsernamePasswordCredentials usernamePasswordCredentials) throws SSLCertificateException {
		return createClient(proxy, authScope, usernamePasswordCredentials);
	}
	
	public static HttpClient getClientForNotTrustedCertificates() throws SSLCertificateException {
		return createClient(null, null, null);
	}
	
	protected static HttpClient createClient(HttpHost proxyHost,AuthScope authScope,UsernamePasswordCredentials usernamePasswordCredentials) throws SSLCertificateException {

		KeyStore trustStore;
		SSLSocketFactory sf = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			sf = new EasySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (KeyStoreException e) {
			throw new SSLCertificateException(e.getMessage());
		} catch (KeyManagementException e) {
			throw new SSLCertificateException(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			throw new SSLCertificateException(e.getMessage());
		} catch (UnrecoverableKeyException e) {
			throw new SSLCertificateException(e.getMessage());
		} catch (CertificateException e) {
			throw new SSLCertificateException(e.getMessage());
		} catch (IOException e) {
			throw new SSLCertificateException(e.getMessage());
		}

		DefaultHttpClient httpclient = null;

		// sets up parameters
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");
		params.setBooleanParameter("http.protocol.expect-continue", false);

		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		// registers schemes for both http and https
		HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
		
		
		SchemeRegistry supportedSchemes = new SchemeRegistry();
		supportedSchemes.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		supportedSchemes.register(new Scheme("https", sf, 443));
		
		
		HttpProtocolParams.setUseExpectContinue(params, true);
		
		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, supportedSchemes);
		httpclient = new DefaultHttpClient(manager, params);
		
		if(proxyHost != null){
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxyHost);
			httpclient.getCredentialsProvider().setCredentials(authScope, usernamePasswordCredentials);
		}
		
		return httpclient;
	}



}
