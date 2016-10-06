package kr.ac.kaist.cds.qos.desc;

public enum ResourceType {
	COMPUTING("Computing"),
	NETWORKING("Networking"),
	STORAGE("Storage"),
	SENSOR("Sensor"),
	UNDEFINED("Undefined");
	
	public String text;
	
	private ResourceType(String text){
		this.text = text;
	}
	
	public static ResourceType fromString(String text){
		if (text != null) {
			for(ResourceType t : ResourceType.values()){
				if(text.equalsIgnoreCase(t.text)){
					return t;
				}
			}
		}
		return ResourceType.UNDEFINED;
	}
	
	@Override
	public String toString(){
		return this.text;
	}
}
