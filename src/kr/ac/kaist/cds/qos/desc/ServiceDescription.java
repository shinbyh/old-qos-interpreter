package kr.ac.kaist.cds.qos.desc;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author bhshin
 *
 */
public class ServiceDescription {
	ServiceType type;
	String otherDesc;
	
	public ServiceDescription(ServiceType type) {
		super();
		this.type = type;
	}

	public ServiceType getType() {
		return type;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}
	
	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("serviceType", this.type.toString());
			//obj.put("otherDescription", this.otherDesc);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public static ServiceDescription parseFromJSON(String jsonStr) throws JSONException{
		JSONObject json = new JSONObject(jsonStr);
		return parseFromJSON(json);
	}
	
	public static ServiceDescription parseFromJSON(JSONObject json) throws JSONException{
		String svcTypeStr = json.getString("serviceType");
		ServiceDescription desc = new ServiceDescription(ServiceType.fromString(svcTypeStr));
		
		// do additional tasks about parsing
		
		return desc;
	}
	
}
