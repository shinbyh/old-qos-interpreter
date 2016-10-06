package kr.ac.kaist.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.kaist.debug.Debug;
import kr.ac.kaist.debug.DebugType;

public class LinuxUtil {
	/**
	 * Run a Linux command and returns output after execution.
	 * 
	 * @param command A command String
	 * @return The output of running the command
	 */
	public static String runCmdAndGetOutput(String command){
		StringBuilder builder = new StringBuilder();
		String temp = null;
		String[] cmd = {
				"/bin/sh",
				"-c",
				command
				};
		
		// debug
		if(Debug.UTIL_LINUX){
			Debug.println(DebugType.DETAIL, "  [cmd] " + command);
		}
		
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader stdInput = new BufferedReader(new
					InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p.getErrorStream()));
			
			// read any errors from the attempted command
			if ((temp = stdError.readLine()) != null) {
				System.err.println("  [cmd] Error: " + temp);
				return null;
			}
			
			// if no error, make output msg
			while ((temp = stdInput.readLine()) != null){
				builder.append(temp);
				builder.append("\n");
			}
			
			stdInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(builder.length() != 0 ) return builder.toString();
		else return null;
	}
	
	/**
	 * Run a Linux command and returns output as a String array.
	 * Each line on the screen output is considered as one String 
	 * in the array.
	 * 
	 * @param command A command String
	 * @return The output of running the command as ArrayList
	 */
	public static ArrayList<String> runCmdAndGetOutputAsArray(String command){
		ArrayList<String> outputArr = new ArrayList<String>();
		//StringBuilder builder = new StringBuilder();
		String temp = null;
		String[] cmd = {
				"/bin/sh",
				"-c",
				command
				};
		
		// debug
		if(Debug.UTIL_LINUX){
			Debug.println(DebugType.DETAIL, " [cmd] " + command);
		}
		
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader stdInput = new BufferedReader(new
					InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p.getErrorStream()));
			
			// read any errors from the attempted command
			if ((temp = stdError.readLine()) != null) {
				Debug.println(DebugType.ERROR, "  [cmd] Error: " + temp);
				return null;
			}
			
			// if no error, add output to array
			while ((temp = stdInput.readLine()) != null){
				outputArr.add(temp);
			}
			
			stdInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return outputArr;
	}
	
	/**
	 * Run a Linux command (/bin/sh) without checking output.
	 * 
	 * @param command A command String
	 * @return Whether the command is successfully run
	 */
	public static boolean runCmd(String command){
		String temp;
		String[] cmdArray = {
				"/bin/sh",
				"-c",
				command
				};
		
		// debug
		if(Debug.UTIL_LINUX){
			System.out.println("  [cmd] " + command);
		}
		
		//run a command to also remove the route in the Kernel
		try {
			Process p = Runtime.getRuntime().exec(cmdArray);
			
			BufferedReader stdError = new BufferedReader(new
					InputStreamReader(p.getErrorStream()));
			
			// read any errors from the attempted command
			if ((temp = stdError.readLine()) != null) {
				System.err.println("  [cmd] Error msg: " + temp);
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		// The command was run properly.
		return true;
	}
}
