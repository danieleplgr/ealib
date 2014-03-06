package com.ealib.net.http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.ealib.net.http.ssl.certificates.NullHostnameVerifier;

public class HttpsConnectionUtils {

//	protected static int CONNECTION_TIMEOUT = 12000;
//	protected static int SOCKET_TIMEOUT = 12000;

	public static class _FakeX509TrustManager implements
			javax.net.ssl.X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public boolean isClientTrusted(X509Certificate[] chain) {
			return (true);
		}

		public boolean isServerTrusted(X509Certificate[] chain) {
			return (true);
		}

		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		}
	}

	public static void setIgnoreCertificates(
			HttpsURLConnection httpsURLConnection) throws Exception {
		httpsURLConnection.setHostnameVerifier(new NullHostnameVerifier());
		javax.net.ssl.SSLContext context = createContextForNotTrustedCertificates();
		httpsURLConnection.setSSLSocketFactory(context.getSocketFactory());
	}

	// public static void setIgnoreCertificates(HttpClient httpclient,
	// HttpParams httpParams) throws Exception {
	// createContextForNotTrustedCertificates();
	//
	// SSLSocketFactory _sSLSocketFactory = null;
	// _sSLSocketFactory = new EasySSLSocketFactory(createDummyKeystore());
	// _sSLSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	//
	// SchemeRegistry supportedSchemes = new SchemeRegistry();
	// supportedSchemes.register(new Scheme("http", PlainSocketFactory
	// .getSocketFactory(), 80));
	// supportedSchemes.register(new Scheme("https", _sSLSocketFactory, 443));
	//
	// ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
	// httpParams, supportedSchemes);
	// }

//	public static void setIgnoreCertificates(HttpClient httpclient)
//			throws Exception {
//		javax.net.ssl.SSLContext context = createContextForNotTrustedCertificates();
//
//		SSLSocketFactory _sSLSocketFactory = null;
//		_sSLSocketFactory = new EasySSLSocketFactory(createDummyKeystore());
//		_sSLSocketFactory
//				.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//		SchemeRegistry supportedSchemes = new SchemeRegistry();
//		supportedSchemes.register(new Scheme("http", PlainSocketFactory
//				.getSocketFactory(), 80));
//		supportedSchemes.register(new Scheme("https", _sSLSocketFactory, 443));
//
//		ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
//				createDefaultHttpParams(), supportedSchemes);
//	}

//	private static HttpParams createDefaultHttpParams() {
//		HttpParams params = new BasicHttpParams();
//		params.setBooleanParameter("http.protocol.expect-continue", false);
//		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
//		HttpConnectionParams.setSoTimeout(params, SOCKET_TIMEOUT);
//		return params;
//	}

	protected static javax.net.ssl.SSLContext createContextForNotTrustedCertificates()
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, KeyManagementException {

		KeyStore trustStore = createDummyKeystore();

		TrustManagerFactory trustManagerFactory = null;
		trustManagerFactory = TrustManagerFactory
				.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		trustManagerFactory.init(trustStore);

		javax.net.ssl.SSLContext context = null;
		context = SSLContext.getInstance("TLS");

		TrustManager[] trustManagers = new javax.net.ssl.TrustManager[] { new _FakeX509TrustManager() };

		context.init(null, trustManagers, null);
		return context;
	}

	protected static KeyStore createDummyKeystore() throws KeyStoreException,
			IOException, NoSuchAlgorithmException, CertificateException {
		KeyStore trustStore = null;

		trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		trustStore.load(null, null);
		return trustStore;
	}

}
