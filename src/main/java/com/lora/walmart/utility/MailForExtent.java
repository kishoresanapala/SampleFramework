package com.lora.walmart.utility;

import com.lora.walmart.steps.GenericFucntions;

public class MailForExtent extends GenericFucntions {

	POJOCompenentsHolder pojoCompenentsHolder;

	public MailForExtent(ExecutionContext context, POJOCompenentsHolder pojoCompenentsHolder) {
		super(context, pojoCompenentsHolder);
	}

	//private WebDriver driver;

	public void SendEmail(String To, String From, String CC) {/*
		try {
			Thread.sleep(2000);
			String isJenkins = System.getProperty("env.jenkins");
			Properties props = new Properties();
			props.put("mail.smtp.host", "mailrelay.nj.adp.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "25");

			System.setProperty("webdriver.chrome.driver", "lib/chromedriver_31.exe");
			System.out.println("Opening chrome driver for result html on server: "+InetAddress.getLocalHost().getHostName().toLowerCase());
			driver = new ChromeDriver();

			Thread.sleep(2000);
			driver.get("file:///" + RunStory.extentFileName);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			if (isJenkins == null)
				System.out.println("Continue");
			else if (isJenkins.contains("env.ENV_JENKINS") || isJenkins.equalsIgnoreCase("true")) {
				System.out.println("Set Browser Size");
				driver.manage().window().setSize(new Dimension(1280, 1024));
			}

			System.out.println(driver.manage().window().getSize());

			clickElement(By.xpath("//a[@class='dashboard-view']"));
			Thread.sleep(2000);

			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			if (isJenkins == null)
				FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport" + RunStory.reportGenerationTime + ".png"));
			else
				FileUtils.copyFile(file, new File("\\\\cdlvdiw800733\\AutomationResults\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport" + RunStory.reportGenerationTime + ".png"));
			clickElement(By.xpath("//a[@class='test-view']"));
			Thread.sleep(4000);
			file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			if (isJenkins == null)
				FileUtils.copyFile(file, new File(System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png"));
			else
				FileUtils.copyFile(file, new File("\\\\cdlvdiw800733\\AutomationResults\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png"));
			int height = driver.findElement(By.id("test-collection")).getSize().height;
			int w = driver.findElement(By.id("test-collection")).getSize().width;
			w = w - 40;
			Image orig;
			if (isJenkins == null)
				orig = ImageIO.read(new File(System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png"));
			else
				orig = ImageIO.read(new File("\\\\cdlvdiw800733\\AutomationResults\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png"));
			int x = 100, y = 177;
			BufferedImage bi = new BufferedImage(w, height, BufferedImage.TYPE_INT_ARGB);
			bi.getGraphics().drawImage(orig, 0, 0, w, height, x, y, x + w, y + height, null);
			if (isJenkins == null)
				ImageIO.write(bi, "png", new File(System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png"));
			else
				ImageIO.write(bi, "png", new File("\\\\cdlvdiw800733\\AutomationResults\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png"));
			driver.close();
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("username", "password");
				}
			});
			try {
				// Inserting text and image
				MimeMultipart multipart = new MimeMultipart("related");
				BodyPart messageBodyPart = new MimeBodyPart();
				String htmlText = null;

				htmlText = "<font face=\"Verdana\" size=\"2\"><p>Hi Team, </p>Below is the automation execution test report.<span size=\"1\">(Attachment is best viewed in Google Chrome)" + "</p>" + "</span></br><p><img width=\"950\" height=\"415\" src=\"cid:image@foo.com\"></p> <p><b>Story execution status: </b></p><p><img width=\"600\" height=\"" + height + "\" src=\"cid:image1@foo.com\"></p>" + "<p>Thanks,</br>Automation Team</font></p>";
				
				messageBodyPart.setContent(htmlText, "text/html; charset=utf-8");
				multipart.addBodyPart(messageBodyPart);

				// Inserting execution summary report
				messageBodyPart = new MimeBodyPart();
				String fileName = "";
				if (System.getProperty("env.jenkins") == null)
					fileName = System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport" + RunStory.reportGenerationTime + ".png";
				else
					fileName = "\\\\cdlvdiw800733\\AutomationResults\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport" + RunStory.reportGenerationTime + ".png";
				DataSource fds = new FileDataSource(fileName);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setHeader("Content-ID", "<image@foo.com>");
				multipart.addBodyPart(messageBodyPart);

				// Inserting scenario summary report
				messageBodyPart = new MimeBodyPart();
				String fileName1 = "";
				if (System.getProperty("env.jenkins") == null)
					fileName1 = System.getProperty("user.dir") + "\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png";
				else
					fileName1 = "\\\\cdlvdiw800733\\AutomationResults\\test-output\\ExtentHTML\\" + RunStory.getModuleName + "\\Screenshots\\emailbodyreport1" + RunStory.reportGenerationTime + ".png";
				DataSource fds1 = new FileDataSource(fileName1);
				messageBodyPart.setDataHandler(new DataHandler(fds1));
				messageBodyPart.setHeader("Content-ID", "<image1@foo.com>");
				multipart.addBodyPart(messageBodyPart);

				// Attachment
				messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(RunStory.extentFileName);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName("ExecutionReport.html");
				multipart.addBodyPart(messageBodyPart);

				// sending email
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(From));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
				Date date = new Date();
				if (isJenkins == null)
					message.setSubject(RunStory.Environment + "_" + RunStory.suiteName + "_" + RunStory.appName + ": ADPDataCloud Automation Execution_" + dateFormat.format(date));
				else
					message.setSubject(RunStory.Environment + "_" + RunStory.suiteName + "_" + RunStory.appName + ": ADPDataCloud Automation Execution_" + dateFormat.format(date) + " [Execution via Jenkins]");
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Mail Sent Successfully !!!");
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	*/}

}