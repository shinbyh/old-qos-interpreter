package kr.ac.kaist.cds.qos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import kr.ac.kaist.debug.Debug;
import kr.ac.kaist.debug.DebugType;
import kr.ac.kaist.util.LinuxUtil;

public class NetworkInterfaceManager {
	private static NetworkInterfaceManager _instance = new NetworkInterfaceManager();
	private HashMap<String, NetworkInterface> interfaces = null;
	
	public static NetworkInterfaceManager getInstance(){
		return _instance;
	}
	
	private NetworkInterfaceManager(){
		this.interfaces = new HashMap<String, NetworkInterface>();
		addInterfacesFromSysClassNet(this.interfaces);
		updateDetailsFromIfconfig(this.interfaces.keySet().toArray(new String[0]));
		addBluetoothInterfaces(this.interfaces);
	}
	
	public NetworkInterface getNetworkInterface(String ifName){
		return this.interfaces.get(ifName);
	}
	
	public NetworkInterface[] getNetworkInterfaces(){
		return this.interfaces.values().toArray(new NetworkInterface[0]);
	}
	
	private void addInterfacesFromSysClassNet(HashMap<String, NetworkInterface> niArr){
		String ifNames = LinuxUtil.runCmdAndGetOutput("ls /sys/class/net");
		StringTokenizer st = new StringTokenizer(ifNames, " \n\t");
		
		while(st.hasMoreTokens()){
			String ifName = st.nextToken();
			
			// filter out exceptional names
			if(ifName.equals("lo") || ifName.contains("br")){
				continue;
			}
			
			// debug
			Debug.println(DebugType.DETAIL, "[NIM] adding interface: " + ifName);
			
			NetworkInterface ni = new NetworkInterface(ifName, null, null, null, null);
			niArr.put(ifName, ni);
		}
	}
	
	private void updateDetailsFromIfconfig(String[] ifNames){
		for(String ifName : ifNames){
			NetworkInterface ni = this.interfaces.get(ifName);
			ni.setHwAddress(getHwAddrFromIfconfig(ifName));
			ni.setIpv4(getInetAddrFromIfconfig(ifName));
			ni.setIfType(getIfType(ifName));
			ni.setWifiSSID(getWifiSSID(ifName));
		}
	}
	
	private void addBluetoothInterfaces(HashMap<String, NetworkInterface> niArr){
		ArrayList<String> hcitoolOut = LinuxUtil.runCmdAndGetOutputAsArray("hcitool dev");
		
		for(String str : hcitoolOut){
			if(str.contains("Devices")){
				continue;
			}
			else {
				StringTokenizer st = new StringTokenizer(str, " \t");
				String ifName = st.nextToken();
				String hwAddr = st.nextToken();
				
				NetworkInterface ni = new NetworkInterface(ifName, NetworkInterfaceType.BLUETOOTH, hwAddr, null, null);
				niArr.put(ifName, ni);
			}
		}
	}
	
	private String getHwAddrFromIfconfig(String ifName){
		String cmd = "/sbin/ifconfig " + ifName + " | grep -o -E '([[:xdigit:]]{1,2}:){5}[[:xdigit:]]{1,2}'";
		
		String output = LinuxUtil.runCmdAndGetOutput(cmd);
		if(output != null){
			return output.trim();
		} else {
			return null;
		}
	}
	
	private String getInetAddrFromIfconfig(String ifName){
		String cmd = "/sbin/ifconfig " + ifName + " | grep 'inet addr' | cut -d: -f2 | awk '{ print $1}'";
		
		String output = LinuxUtil.runCmdAndGetOutput(cmd);
		if(output != null){
			return output.trim();
		} else {
			return null;
		}
	}
	
	private NetworkInterfaceType getIfType(String ifName){
		ArrayList<String> outputArr = LinuxUtil.runCmdAndGetOutputAsArray("/sbin/iwconfig 2>&1 | grep " + ifName);
		
		for(String str : outputArr){
			if(str.contains("no wireless")){
				return NetworkInterfaceType.WIRED;
			}
			
			if(str.contains("IEEE 802.11")){
				return NetworkInterfaceType.WIFI;
			}
		}
		
		return NetworkInterfaceType.UNDEFINED;
	}
	
	private String getWifiSSID(String ifName){
		ArrayList<String> outputArr = LinuxUtil.runCmdAndGetOutputAsArray("/sbin/iwconfig 2>&1 | grep " + ifName);
		
		for(String str : outputArr){
			if(str.contains("no wireless")) continue;
			
			if(str.contains("IEEE 802.11")){
				// check if this is associated to an ESSID.
				if(str.contains("ESSID")){
					int essidStart = str.indexOf("\"");
					int essidEnd = str.indexOf("\"", essidStart+1);
					return str.substring(essidStart+1, essidEnd);
				}
			}
		}
		
		return null;
	}
	
	public static void main(String[] args){
		NetworkInterfaceManager.getInstance();
	}
}
