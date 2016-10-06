package kr.ac.kaist.cds.qos;

public enum Operator {
	GREATER_THAN("gt"),
	LESS_THAN("lt"),
	EQUALS("eq"),
	GREATER_OR_EQUAL("ge"),
	LESS_OR_EQUAL("le");
	
	public String text;
	
	private Operator(String text){
		this.text = text;
	}
	
	public String toString(){
		return this.text;
	}
	
	public static Operator fromString(String text){
		if (text != null) {
			for(Operator t : Operator.values()){
				if(text.equalsIgnoreCase(t.text)){
					return t;
				}
			}
		}
		return null;
	}
}
