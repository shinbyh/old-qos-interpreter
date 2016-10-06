package kr.ac.kaist.cds.qos;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author bhshin
 *
 */
public class NetworkInterface {
	private String ifName; // ifname shown in the machine (ifconfig)
	private NetworkInterfaceType ifType;
	private String hwAddress;
	private String ipv4;
	private String wifiSSID;
	
	public NetworkInterface(String ifName, NetworkInterfaceType ifType, String hwAddress, String ipv4, String wifiSSID) {
		this.ifName = ifName;
		this.ifType = ifType;
		this.hwAddress = hwAddress;
		this.ipv4 = ipv4;
		this.wifiSSID = wifiSSID;
	}

	public NetworkInterfaceType getIfType() {
		return ifType;
	}

	public void setIfType(NetworkInterfaceType ifType) {
		this.ifType = ifType;
	}

	public String getIfName() {
		return ifName;
	}

	public void setIfName(String ifName) {
		this.ifName = ifName;
	}

	public String getHwAddress() {
		return hwAddress;
	}

	public void setHwAddress(String hwAddress) {
		this.hwAddress = hwAddress;
	}

	public String getIpv4() {
		return ipv4;
	}

	public void setIpv4(String ipv4) {
		this.ipv4 = ipv4;
	}
	
	public String getWifiSSID() {
		return wifiSSID;
	}

	public void setWifiSSID(String wifiSSID) {
		this.wifiSSID = wifiSSID;
	}

	public JSONObject toJSONObject(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("ifaceType", this.ifType.toString());
			obj.put("hwAddress", this.hwAddress);
			obj.put("ipv4", this.ipv4);
			if(this.ifType == NetworkInterfaceType.WIFI){
				obj.put("wifiSSID", this.wifiSSID);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
}
