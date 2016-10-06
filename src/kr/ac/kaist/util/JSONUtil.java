package kr.ac.kaist.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import kr.ac.kaist.debug.Debug;

/**
 * 
 * @author bhshin
 *
 */
public class JSONUtil {
	public static final int CONN_TIMEOUT = 5;
	public static final int READ_TIMEOUT = 5;
	
	/**
	 * Send a JSONObject via HTTP POST and get a response message.
	 * 
	 * @param serverURL An HTTP Server URL
	 * @param json A JSONObject to send via POST
	 * @return Response message from the server
	 * @throws IOException
	 */
	public static String sendJSONObject(String serverURL, JSONObject json) throws IOException{
		return sendPOSTMessage(serverURL, json.toString());
	}
	
	/**
	 * Send a POST message to the HTTP server.
	 * 
	 * @param serverURL An HTTP Server URL
	 * @param msg A message to send
	 * @return Response message from the server
	 * @throws IOException
	 */
	public static String sendPOSTMessage(String serverURL, String msg) throws IOException{
		String response = null;
		int responseCode;
		HttpURLConnection   conn   = null;
		OutputStream          os   = null;
		InputStream           is   = null;
		ByteArrayOutputStream baos = null;

		URL url = new URL(serverURL);
		conn = (HttpURLConnection)url.openConnection();
		conn.setConnectTimeout(CONN_TIMEOUT * 1000);
		conn.setReadTimeout(READ_TIMEOUT * 1000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Cache-Control", "no-cache");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		// debug
		if(Debug.UTIL_JSON){
			System.out.println("[sendPOST] server = " + serverURL);
			System.out.println("[sendPOST] msg: " + msg);
		}

		os = conn.getOutputStream();
		os.write(msg.getBytes());
		os.flush();

		responseCode = conn.getResponseCode();

		if(responseCode == HttpURLConnection.HTTP_OK) {
			is = conn.getInputStream();
			baos = new ByteArrayOutputStream();
			byte[] byteBuffer = new byte[1024];
			byte[] byteData = null;
			int nLength = 0;
			while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
				baos.write(byteBuffer, 0, nLength);
			}
			byteData = baos.toByteArray();
			response = new String(byteData);
			
			// debug
			System.out.println(response);
		}
		
		return response;
	}
	
}
