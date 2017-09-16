package com.lora.walmart.utility;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Arrays;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.Kernel32;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.lora.walmart.testexecutor.FrameworkDriver;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT;

public class BrowserInitiator {
	public WebDriver driver;
	private String MachineIP;

	public BrowserInitiator(String testName, String RunAgainst, String browser, String version) {

	}

	public synchronized void initiateDriver(String RunAgainst, String platform, String browser, String version) throws Exception {
		String runHost = PropertiesFile.RunningHost;

		InetAddress IP = InetAddress.getLocalHost();
		MachineIP = IP.getHostAddress();
		if (runHost.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", "lib/IEDriverServer_32.exe");
				DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				caps.setCapability("nativeEvents", false);
				caps.setCapability("unexpectedAlertBehaviour", "accept");
				caps.setCapability("ignoreProtectedModeSettings", true);
				caps.setCapability("disable-popup-blocking", true);
				caps.setCapability("enablePersistentHover", true);
				driver = new InternetExplorerDriver(caps);
			} else if (browser.equalsIgnoreCase("Edge")) {
				System.setProperty("webdriver.edge.driver", "lib/MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
			} else if (browser.equalsIgnoreCase("Firefox")) {
				System.setProperty("webdriver.gecko.driver", "lib/geckodriver.exe");

				String loggedInUser = System.getProperty("user.home");
				String directory = loggedInUser + "\\AppData\\Local\\Mozilla Firefox\\firefox.exe";
				String fileOnProgramFiles = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
				File firefoxOnCDrive = new File(fileOnProgramFiles);
				File file = new File(directory);
				if (file.exists())
					System.setProperty("webdriver.firefox.bin", loggedInUser + "\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
				else if (firefoxOnCDrive.exists())
					System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
				else
					System.setProperty("webdriver.firefox.profile", "default");

				FirefoxProfile profile = new FirefoxProfile();
				profile.setPreference("network.proxy.type", ProxyType.AUTODETECT.ordinal());

				FirefoxOptions options = new FirefoxOptions();
				options.addPreference("marionette", false);
				options.addPreference("webdriver.log.driver.level", "OFF");
				options.setProfile(profile);
				DesiredCapabilities caps = options.addTo(DesiredCapabilities.firefox());
				this.driver = new FirefoxDriver(caps);

			} else if (browser.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				if (InetAddress.getLocalHost().getHostName().toLowerCase().contains("cdlvdi")) {
					capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
					System.out.println("Switching to incognito");
				}
				this.driver = new ChromeDriver(capabilities);
				System.out.println("Starting Chrome Browser");
			}

			Capabilities cap = ((RemoteWebDriver) this.driver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			String os = cap.getPlatform().toString();
			System.out.println(os);
			String browserVersion = cap.getVersion().toString();
			System.out.println("Browser: " + browserName + " " + browserVersion);

			System.out.println("IP of local system is := " + MachineIP);
			FrameworkDriver.extent.addSystemInfo("Browser", browserName + " " + browserVersion);
			FrameworkDriver.extent.addSystemInfo("Execution Environment", RunAgainst);
		} else if (runHost.equalsIgnoreCase("CrossBrowserTesting")) {

		} else if (runHost.equalsIgnoreCase("Grid")) {

		}
	}

	public void closeDriver() {
		this.driver.close();
		try {
		} catch (Exception e) {

		}
	}

}