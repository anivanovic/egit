package org.eclipse.egit.svn;

public class ProcessResult {
	
	private int exitCode = -1;
	private String message;
	
	public ProcessResult(int exitCode, String message) {
		this.exitCode = exitCode;
		this.message = message;
	}
	
	public int getExitCode() {
		return exitCode;
	}
	public String getMessage() {
		return message;
	}
	
	public boolean isOk() {
		return exitCode == 0;
	}
	
}
