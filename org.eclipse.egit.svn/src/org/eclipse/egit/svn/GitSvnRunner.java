package org.eclipse.egit.svn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.egit.svn.internal.preferences.PreferencesConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class GitSvnRunner {

	private static final String SVN = "svn";
	private static final String REBASE = "rebase";
	private static final String DCOMMIT = "dcommit";
	private static final String FETCH = "fetch";
	private static final String TAG = "tag";
	private static final String BRANCH = "branch";
	private static final String INFO = "info";

	private static String getGitInstallationPath() {
		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		return preferenceStore.getString(PreferencesConstants.gitInstallationDirectory);
	}
	
	public static boolean isValidGitPathPreference() {
		boolean isValid = false;
		String gitPath = getGitInstallationPath();
		
		if (gitPath != null && !"".equals(gitPath)) {
			Path gitExe = Paths.get(gitPath);
			
			LinkOption[] emptyOptions = new LinkOption[] {};
			if (Files.exists(gitExe, emptyOptions)
					&& !Files.isDirectory(gitExe, emptyOptions)
					&& Files.isExecutable(gitExe)) {
				
				// check if git is installed
				ProcessBuilder gitProcessBuilder = new ProcessBuilder(gitPath);
				Process gitProcess;
				try {
					gitProcess = gitProcessBuilder.start();
					isValid = gitProcess.exitValue() == 0;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// check if git svn command is working.
				gitProcessBuilder = new ProcessBuilder(gitPath);
				try {
					gitProcess = gitProcessBuilder.start();
					isValid = gitProcess.waitFor() == 0;
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return isValid;
	}
	
	public static boolean isGitSvnRepo(File projectDir) {
		String gitPath = getGitInstallationPath();
		
		ProcessBuilder processBuilder = new ProcessBuilder(gitPath, SVN, INFO);
		processBuilder.directory(projectDir);
		
		try {
			Process process = processBuilder.start();
			return process.waitFor() == 0;
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public static ProcessResult dcommit(File projectDir) {
		return runGitCommand(projectDir, SVN, DCOMMIT);
	}
	
	public static ProcessResult rebase(File projectDir) {
		return runGitCommand(projectDir, SVN, REBASE);
	}
	
	public static ProcessResult fetch(File projectDir) {
		return runGitCommand(projectDir, SVN, FETCH);
	}
	
	public static ProcessResult info(File projectDir) {
		return runGitCommand(projectDir, SVN, INFO);
	}
	
	private static ProcessResult runGitCommand(File projectDir, String... commands) {
		String gitPath = getGitInstallationPath();
		
		String[] gitCommands = new String[commands.length + 1];
		gitCommands[0] = gitPath;
		System.arraycopy(commands, 0, gitCommands, 1, commands.length);
		
		ProcessBuilder processBuilder = new ProcessBuilder(gitCommands);
		processBuilder.directory(projectDir);
		processBuilder.redirectErrorStream(true);
		
		try {
			Process process = processBuilder.start();
			int exitCode = process.waitFor();
			BufferedReader reader = new BufferedReader(
										new InputStreamReader(process.getInputStream()));
			String line = null;
			StringBuilder message = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				message.append(line);
				message.append(System.lineSeparator());
			}
			
			return new ProcessResult(exitCode, message.toString());
		} catch (IOException | InterruptedException e) {
			return new ProcessResult(-1, e.getMessage());
		}
	}

}
