package com.download_file;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public class DownloadFileToCustomFolder {

	public static void main(String[] args) throws InterruptedException {
		
		String downloadFolderPath = System.getProperty("user.dir") + File.separator + "JenkinsDownloads";
		File jenkinsDownloadDir = new File(downloadFolderPath);
		System.out.println(jenkinsDownloadDir.exists());
		
		if(!jenkinsDownloadDir.exists()) {
			System.out.println("Jenkins folder is not present!!");
		 if(jenkinsDownloadDir.mkdir()) {
			 System.out.println("Created jenkins folder directory");
		 }
		}
		
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", downloadFolderPath);
		prefs.put("download.prompt_for_download", false);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);		
		
		WebDriver wd = new ChromeDriver(options);
		wd.get("https://get.jenkins.io/war-stable/2.580.2/jenkins.war");
		
		
		// Check if the file has been downloaded successfully
		
		File jenkinsFile = new File(jenkinsDownloadDir, "jenkins.war");
		int timeOutSeconds = 30;
		int elapsedTime = 0;
		while(elapsedTime < timeOutSeconds && !jenkinsFile.exists()) {
			Thread.sleep(1000);
			elapsedTime++;
			System.out.println("Waiting for the file to download... ");
		}
		
		if(jenkinsFile.exists()) {
			System.out.println("File download successfully");
		} else {
			System.err.println("File could'nt be downloaded!! Time out error!!");
		}
		
		wd.close();
		
	}
}

