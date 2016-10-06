package kr.ac.kaist.cds.qos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import kr.ac.kaist.Configuration;
import kr.ac.kaist.cds.qos.desc.ServiceType;
import kr.ac.kaist.util.JSONUtil;
import kr.ac.kaist.util.LinuxUtil;

public class TestMain {

	public static void main(String[] args){
		/*String jsonMsg = "{"
				+ "\"deviceID\":\"004\""
				+ "}";
				
		try {
			System.out.println("1111111111");
			String respMsg = JSONUtil.sendPOSTMessage("http://192.168.1.1:8080/qos/", jsonMsg);
			System.out.println("22222222");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		//String output = LinuxUtil.runCmdAndGetOutput("fdaf");
		//System.out.println(output);
		
		/*ArrayList<String> arr = LinuxUtil.runCmdAndGetOutputAsArray("ifconfig");
		
		for(String str : arr){
			
			if(str.contains("inet addr")){
				System.out.println(str);
				
				StringTokenizer st = new StringTokenizer(str, " :");
				while(st.hasMoreTokens())
					System.out.println(st.nextToken());
				
				break;
				
			}
		}*/
		
		Configuration.getInstance();
		NetworkInterfaceManager nim = NetworkInterfaceManager.getInstance();
		QoSInterpreter interpreter = new QoSInterpreter(Configuration.getInstance().getResolverAddr());
		
		// test join
		//interpreter.join(nim.getNetworkInterfaces());
		
		// test leave
		interpreter.leave("6");
		
		// test update
		//interpreter.update("004", services, resources, contents);
		
		// test request
		ArrayList<QoSRequirement> qosReqs = new ArrayList<QoSRequirement> ();
		qosReqs.add(new QoSRequirement("bandwidth", "bps", 200000.0, Operator.GREATER_OR_EQUAL));
		qosReqs.add(new QoSRequirement("delay", "ms", 100.0, Operator.LESS_OR_EQUAL));
		//interpreter.request("002", ServiceType.STREAMING, qosReqs, "A, B, C", null);
	}
}
