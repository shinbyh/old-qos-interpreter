package kr.ac.kaist.cds.qos;

public enum NetworkInterfaceType {
	BLUETOOTH("bluetooth"),
	LTE("lte"),
	WIFI("wifi"),
	WIRED("wired"),
	UNDEFINED("undefined");
	
	public String text;
	
	private NetworkInterfaceType(String text){
		this.text = text;
	}
	
	public String toString(){
		return this.text;
	}
	
	public static NetworkInterfaceType fromString(String text){
		if (text != null) {
			for(NetworkInterfaceType nit : NetworkInterfaceType.values()){
				if(text.equalsIgnoreCase(nit.text)){
					return nit;
				}
			}
		}
		return NetworkInterfaceType.UNDEFINED;
	}
}
