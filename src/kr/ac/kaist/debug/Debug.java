package kr.ac.kaist.debug;

import kr.ac.kaist.Configuration;

public class Debug {
	public static final boolean UTIL_LINUX = true;
	public static final boolean UTIL_JSON = true;
	
	public static void println(DebugType type, String msg){
		if(Configuration.getInstance().getDebugType().ordinal() <= type.ordinal())
			System.out.println(msg);
	}
}
