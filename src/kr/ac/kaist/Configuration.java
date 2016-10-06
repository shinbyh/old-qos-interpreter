package kr.ac.kaist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import kr.ac.kaist.debug.DebugType;

public class Configuration {
	public static final String DEFAULT_CONFIG_PATH = "qos.config";
	public static final String DEFAULT_RESOLVER_ADDR = "http://147.46.216.250:3333/";
	//public static final String DEFAULT_RESOLVER_ADDR = "http://localhost:8080/qos/";
	
	private static Configuration _instance = new Configuration(DEFAULT_CONFIG_PATH);
	private DebugType debugType = null;
	private String resolverAddr = null;
	
	private Hashtable<String, String> confMap;
	
	public static Configuration getInstance() {
		return _instance;
	}
	
	private Configuration(String filePath){
		this.confMap = new Hashtable<String, String>();
		loadConfigFromFile(filePath);
	}
	
	private void loadConfigFromFile(String filePath){
		FileReader fr = null;
		BufferedReader br = null;
		try {
			String line;
			int idx;
			String key, value;
			fr = new FileReader(new File(filePath));
			br = new BufferedReader(fr);
			
			// parse line by line
			while( (line = br.readLine()) != null){
				line = line.trim();
				if(line.trim().startsWith("#") || line.trim().startsWith("//") || line.equals("") || !line.contains("=")){
					continue;
				}
				idx = line.indexOf("=");
				key = line.substring(0, idx).trim();
				value = line.substring(idx + 1).trim();

				System.out.println("[Conf] loading configuration: " + key + " = " + value);
				
				// add interface names to ifMap
				if(key.equals("ResolverAddr")){
					this.resolverAddr = value;
				}
				
				if(key.equals("DebugType")){
					this.debugType = DebugType.valueOf(value);
				}
				
				// add all other configurations to confMap
				this.confMap.put(key, value);
			}
			
			// Check undefined configurations.
			// If undefined, set default values.
			if( this.resolverAddr == null ){
				this.resolverAddr = DEFAULT_RESOLVER_ADDR;
			}
			
			if( this.debugType == null){
				this.debugType = DebugType.INFO;
			}
			
			fr.close();
			br.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.err.println("[Config] Config file not found. Loading defaults...\n");
			loadDefaults();
		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println("[Conig] Error reading config file. Loading defaults...\n");
			loadDefaults();
		}
	}
	
	private void loadDefaults(){
		System.out.println("[Config] ResolverAddr = " + DEFAULT_RESOLVER_ADDR);
		this.resolverAddr = DEFAULT_RESOLVER_ADDR;
		
		System.out.println("[Config] DebugType = " + DebugType.INFO.toString());
		this.debugType = DebugType.INFO;
	}
	
	public String getResolverAddr(){
		return this.resolverAddr;
	}
	
	public DebugType getDebugType(){
		return this.debugType;
	}
	
	public String get(String key) {
		String value = this.confMap.get(key);
		
		if(value == null){
			return null;
		}
		else {
			return (this.confMap.get(key));
		}
	}
}
