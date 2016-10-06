package kr.ac.kaist.cds.qos.desc;

import org.json.JSONException;
import org.json.JSONObject;

public class ContentDescription {
	String type;
	String name;
	
	public ContentDescription(String type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("contentType", this.type.toString());
			obj.put("contentName", this.name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public static ContentDescription fromJSONObject(JSONObject json) throws JSONException{
		String type = json.getString("contentType");
		String name = json.getString("contentName");
		ContentDescription desc = new ContentDescription(type, name);
		
		// do additional parsing jobs.
		
		return desc;
	}
}
