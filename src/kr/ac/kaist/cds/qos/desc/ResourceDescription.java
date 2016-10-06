package kr.ac.kaist.cds.qos.desc;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author bhshin
 *
 */
public class ResourceDescription {
	ResourceType type;
	String name;
	String unit;
	String value;
	String otherDesc;
	
	public ResourceDescription(ResourceType type, String name, String unit, String value) {
		this.type = type;
		this.name = name;
		this.unit = unit;
		this.value = value;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getResourceName() {
		return name;
	}

	public void setResourceName(String name) {
		this.name = name;
	}

	public String getResourceUnit() {
		return unit;
	}

	public void setResourceUnit(String unit) {
		this.unit = unit;
	}

	public String getResourceValue() {
		return value;
	}

	public void setResourceValue(String value) {
		this.value = value;
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
			obj.put("resourceType", this.type.toString());
			obj.put("resourceName", this.name);
			obj.put("resourceUnit", this.unit);
			obj.put("resourceValue", this.value);
			obj.put("otherDescription", this.otherDesc);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
	public static ResourceDescription fromJSONObject(JSONObject json) throws JSONException{
		String typeStr = json.getString("resourceType");
		ResourceType type = ResourceType.fromString(typeStr);
		String name = json.getString("resourceName");
		String unit = json.getString("resourceUnit");
		String value = json.getString("resourceValue");
		
		ResourceDescription desc = new ResourceDescription(type, name, unit, value);
		desc.setOtherDesc(json.getString("otherDescription"));
		
		return desc;
	}
}
