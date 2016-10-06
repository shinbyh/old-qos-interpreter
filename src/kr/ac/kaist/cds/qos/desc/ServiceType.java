package kr.ac.kaist.cds.qos.desc;

public enum ServiceType {
	STREAMING("Streaming"),
	FILETRANSFER("FileTransfer"),
	WEB("Web"),
	VOICE("Voice"),
	GAME("Game"),
	BEST_EFFORT("BestEffort");
	
	public String text;
	
	private ServiceType(String text){
		this.text = text;
	}
	
	public static ServiceType fromString(String text){
		if (text != null) {
			for(ServiceType t : ServiceType.values()){
				if(text.equalsIgnoreCase(t.text)){
					return t;
				}
			}
		}
		return ServiceType.BEST_EFFORT;
	}
	
	@Override
	public String toString(){
		return this.text;
	}
}
