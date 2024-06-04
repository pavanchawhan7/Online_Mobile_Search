package miniProject;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup  {
	String link;
	public static WebDriver driver;

	public static WebDriver getWebDriver(String Browser) {


		if(Browser.equalsIgnoreCase("chrome")) {
			System.out.println("Chrome Browser is selected for Automation Test");
			
			//Setting up Chrome Driver
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();

		}else if(Browser.equalsIgnoreCase("edge")){
			System.out.println("Edge Browser is selected for Automation Test");
			
			//Setting up Chrome Driver
			
			WebDriverManager.edgedriver().setup();
		    driver = new EdgeDriver();
		}

		return driver;

	}
	public static void LaunchUrl(String link) {
		// Launch Amazon website 
	

		driver.get(link);
		System.out.println("Website Link: "+driver.getCurrentUrl()+"\n");

	}
	public static void MaximizeWindow() {

		//maximize browser window to get full window of browser 
		driver.manage().window().maximize();
	
	}

	public static void toSearch(String toSearch) {

		//wait till the browser loads properly
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		//Search “mobile smartphones under 30000” in search box
		WebElement ToSearch = driver.findElement(By.id("twotabsearchtextbox"));
		ToSearch.sendKeys(toSearch);

		//Search Button
		driver.findElement(By.id("nav-search-submit-button")).click();
		System.out.println("To Search:\n	"+toSearch+"\n");
	}

	public static boolean Validation(String toSearch) {

		//Validation 
		WebElement searchElement = driver.findElement(By.className("a-color-state"));

		//Get SearchString of amazon and removing " " from the String
		String searchString = searchElement.getText().replace("\"", "");

		WebElement page_Items= driver.findElement(By.className("a-section"));
		String page_Items_text = page_Items.getText();
		
		//Spliting The String of page_item_text  for Validation
		String[] s= page_Items_text.split(" ");
		
		System.out.println("Validation Display Message:\n	"+  page_Items_text+"\n");

		//Check Validation message 
		if(toSearch.equals(searchString)) {

			System.out.println("\t Search String VALIDATION SUCCESSFUL\n\t\tExpected: "+ 
					searchString+
					"\n\t\tActual: "+ toSearch+"\n");

			// Get Search Page & No_Of_Items
			//s[0] contains 1-16 No of Pages
			//s[3] contains 1000 No of Items
			
			//Cheking No of Pages Using Regex
			if(s[0].matches("[0-9]-[0-9]+")) {
				System.out.println("\tNo of pages: "+s[0]+" 'VALIDATION SUCCESSFUL' ");
			} else {
				System.out.println("\tNo of pages: "+s[0]+" 'VALIDATION UNSUCCESSFUL'");
			}
 
			//Cheking No of Items Using Regex
			if(s[3].matches("[0-9,]*")) {
				System.out.println("\tNo of items: "+s[3]+" 'VALIDATION SUCCESSFUL' ");
			}else {
				System.out.println("\tNo of items: "+s[3]+" 'VALIDATION UNSUCCESSFUL' ");

			}
			return true;
		}else {
			System.out.println("\tSearch String VALIDATION UNSUCCESFUL/n\t\tExpected: "+
					searchString+
					"\n\t\tActual: "+ toSearch+ "\n");

			return false;
		}	

	}



	public static void dropSelect(String toSelect) {

		//Click on Sort By 
		WebElement sortOpt =driver.findElement(By.id("s-result-sort-select"));
		Select selectOpt = new Select(sortOpt);

		// Select Opt "Newest Arrivals"
		selectOpt.selectByVisibleText(toSelect);

		//Total Select Option
		List<WebElement> sortOptions = selectOpt.getOptions();
		System.out.println("\nNumber of option in Sort by Dropdown: \n\t Total ="+sortOptions.size()+"\n");
		for(WebElement e:sortOptions) {
			System.out.println(e.getText());
		}
		System.out.println("_________________________________________________________________________________");

		//Validation of Selected option
		String selectedOption=driver.findElement(By.className("a-dropdown-prompt")).getText();
		if(selectedOption.equals(toSelect)) {
			System.out.println("TEST CASE PASSED: "+"\n"+"\tSelected option: "+selectedOption);
			System.out.println("____________________________________________________________________________");
		}else {
			System.out.println("TEST CASE FAILED:"+"\n"+"\tSelected option: "+selectedOption);
			System.out.println("____________________________________________________________________________");
		}
	}


	public static void ScreenShot(String img ) throws IOException {
		//Captures a screenshot of the current page and saves it to the specified location.
		File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File target = new File(img);
		FileHandler.copy(source,target);
	}

	public static void closeBrowser() {
		
		// Terminate the WebDriver session and close all browser windows
		driver.quit();
	}

}
