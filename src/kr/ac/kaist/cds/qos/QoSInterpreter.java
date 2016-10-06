package kr.ac.kaist.cds.qos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.kaist.cds.qos.desc.ContentDescription;
import kr.ac.kaist.cds.qos.desc.ResourceDescription;
import kr.ac.kaist.cds.qos.desc.ServiceDescription;
import kr.ac.kaist.cds.qos.desc.ServiceType;
import kr.ac.kaist.debug.Debug;
import kr.ac.kaist.debug.DebugType;
import kr.ac.kaist.util.JSONUtil;

/**
 * 
 * @author bhshin
 *
 */
public class QoSInterpreter {
	private String resolverAddr;
	private String deviceID;
	
	public QoSInterpreter(String resolverAddr){
		this.resolverAddr = resolverAddr;
	}
	
	/**
	 * Send a Join message to the Resolver and get a new device ID.
	 *  
	 * @param nis A list of NetworkInterfaces
	 * @return A device ID
	 */
	public String join(NetworkInterface[] nis){
		String deviceID = null;
		JSONObject joinJSONObj = new JSONObject();
		JSONArray ucJSONArr = new JSONArray();
		
		for(NetworkInterface ni : nis){
			ucJSONArr.put(ni.toJSONObject());
		}
		
		try {
			joinJSONObj.put("uniqueCodes", ucJSONArr);
			String response = JSONUtil.sendJSONObject(resolverAddr + "Join", joinJSONObj);
			JSONObject respObj = new JSONObject(response);
			
			// debug
			if(Debug.UTIL_JSON){
				Debug.println(DebugType.INFO, "[join] response: " + response);
			}
			
			if(respObj.has("id")){
				deviceID = respObj.getString("id");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return deviceID;
	}
	
	public void leave(String deviceID){
		//String ack = null;
		JSONObject leaveJSONObj = new JSONObject();
		
		try {
			leaveJSONObj.put("deviceID", deviceID);
			String response = JSONUtil.sendJSONObject(resolverAddr + "Leave", leaveJSONObj);
			
			// debug
			if(Debug.UTIL_JSON){
				Debug.println(DebugType.INFO, "[leave] response: " + response);
			}
			//JSONObject respObj = new JSONObject(response);
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(String deviceID, ArrayList<ServiceDescription> services){
		
	}
	
	public void update(String deviceID, ArrayList<ServiceDescription> services, ArrayList<ResourceDescription> resources, ArrayList<ContentDescription> contents){
		// service, resource, content
		
		JSONObject updateJSON = new JSONObject();
		
		try {
			updateJSON.put("deviceID", deviceID);
			
			JSONArray serviceArray = new JSONArray();
			for(ServiceDescription service : services){
				serviceArray.put(service.toJSONObject());
			}
			updateJSON.put("services", serviceArray);
			
			JSONArray resourceArray = new JSONArray();
			for(ResourceDescription resource : resources){
				resourceArray.put(resource.toJSONObject());
			}
			updateJSON.put("resources", resourceArray);
			
			JSONArray contentArray = new JSONArray();
			for(ContentDescription content : contents){
				contentArray.put(content.toJSONObject());
			}
			updateJSON.put("contents", contentArray);
			
			String response = JSONUtil.sendJSONObject(resolverAddr + "Leave", updateJSON);
			
			// debug
			if(Debug.UTIL_JSON){
				Debug.println(DebugType.INFO, "[update] response: " + response);
			}
			//JSONObject respObj = new JSONObject(response);
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void request(String deviceID, ServiceType svcType, ArrayList<QoSRequirement> qosReqs, String keywords, HashMap<String, String> additionalAttr){
		JSONObject json = new JSONObject();
		
		try {
			json.put("deviceID", deviceID);
			json.put("serviceType", svcType.toString());
			
			JSONArray qosReqArr = new JSONArray();
			for(QoSRequirement qosReq : qosReqs){
				qosReqArr.put(qosReq.toJSONObject());
			}
			json.put("qosRequirements", qosReqArr);
			
			json.put("keywords", keywords);
			
			// TODO: handle additional fields...
			//for()
			
			String response = JSONUtil.sendJSONObject(resolverAddr + "Request", json);
			
			// debug
			if(Debug.UTIL_JSON){
				Debug.println(DebugType.INFO, "[update] response: " + response);
			}
			//JSONObject respObj = new JSONObject(response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Interpret "QoS Markup Language" format and 
	 * convert it into service/resource/content descriptions
	 * and service/resource/content requests.
	 * 
	 * @param qml QML format
	 */
	public void interpretQML(String qml){
		
	}
	
}
