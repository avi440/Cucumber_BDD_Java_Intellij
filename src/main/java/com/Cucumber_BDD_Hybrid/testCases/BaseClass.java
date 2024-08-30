package com.Cucumber_BDD_Hybrid.testCases;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


import com.Cucumber_BDD_Hybrid.utilities.ReadConfig;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseClass {
	public static int PAGE_LOAD_TIMEOUT = 20;
	public static int IMPLICIT_WAIT = 20;
	
	public static ReadConfig readConfig = new ReadConfig();
	public String baseUrl = readConfig.getApplicationURL();
	public String br = readConfig.getBrowser();
	public String eng = readConfig.getTire();
	public static WebDriver driver;
	public static Logger logger;
//	public  static EventFiringWebDriver e_driver;
	
	public void delectScreenshortFiles() {
		File folder = new File(System.getProperty("user.dir")+"/Screenshots/");
		if(!folder.isFile()) {
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> deFaile = new ArrayList<String>();
		for (File file : listOfFiles){
		    if (file.isFile()) {
		    	deFaile.add(file.getName());
		    }
		}
		for(String fileName:deFaile) {
		File file = new File(System.getProperty("user.dir")+"/Screenshots/"+fileName+".png");  
		file.deleteOnExit();
		}
		}
	}
	

	public void setup() {

		delectScreenshortFiles();
		logger =  Logger.getLogger("orange01");
		 PropertyConfigurator.configure("./Configuration/log4j.properties");
		 
		
		 
		 if(br.equals("chrome")) {
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
//		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		
		 }
		 else if(br.equals("firefox")) {
//			 System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/driver/geckodriver.exe");
			 WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			 
		 }
         else if(br.equals("ie")) {
//        	 System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/driver/IEDriverServer.exe");
        	 WebDriverManager.edgedriver().setup();
     		driver = new InternetExplorerDriver();
     		
		 }
		 
		
		
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
//			driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);

			
//			driver.get(baseUrl);


	}
	 
	public void quickBrowser() {
		driver.quit();
	}
	
	public WebDriver getBr() {
        return driver;
    }

   
	
	public void captureScreenshot(WebDriver driver,String tname) throws IOException {
	 TakesScreenshot ts = (TakesScreenshot)driver;
     File Source = ts.getScreenshotAs(OutputType.FILE);
     File destination = new File(System.getProperty("user.dir")+"/Screenshots/"+tname+".png");
//     String filePath = destination.getAbsolutePath();
     FileUtils.copyFile(Source, destination);
//     return filePath;
     System.out.println("Take screenshot: " + tname);
  }
	
//	public void tearDown(Scenario Scena) {
//	if(Scena.isFailed()) {
//		String screenShotName = Scena.getName().replaceAll(" ", "-");
//		byte[] sourcePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
//		Scena.attach(sourcePath, "image/png", screenShotName);
//	}
//}


}
