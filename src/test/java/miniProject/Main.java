package miniProject;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		String Browser;
		boolean bol = true;
		System.out.println("Enter the browser to Automate (Chrome/Edge)");
		while(bol) {
			Scanner sc = new Scanner(System.in);
			Browser = sc.next();
			if(Browser.equalsIgnoreCase("Chrome") || (Browser.equalsIgnoreCase("Edge"))){
				DriverSetup.getWebDriver(Browser);
				bol =false;
				sc.close();
			}else {
				System.out.println("Invalid Browser name\nEnter the Browser Name Again(Chrome/Edge)");		
			}	
		}
		try {
			
			String filePath = System.getProperty("user.dir")+"\\testData\\Details.xlsx";
			int rows = ExcelUtils.getRowCount(filePath, "Amazon");
			
			for(int i=1; i<=rows;i++) {
				//read data from excel
				String link = ExcelUtils.getCellData(filePath, "Amazon", rows, 0);
				String toSearch = ExcelUtils.getCellData(filePath, "Amazon", rows, 1);
				String toSelect = ExcelUtils.getCellData(filePath, "Amazon", rows, 2);
				
				//pass data to the driver
				DriverSetup.LaunchUrl(link);
				
				//Screenshot the of the launch page
				DriverSetup.ScreenShot("./snaps/1)Launch.png");

				//maximizes the Browser window
				DriverSetup.MaximizeWindow();
				
				//Screeshot of the maximized Browser Page 
				DriverSetup.ScreenShot("./snaps/2)Maximized.png");
				

				//Search “mobile smartphones under 30000” in search box
				DriverSetup.toSearch(toSearch);
				
				//Screenshot of the search
				DriverSetup.ScreenShot("./snaps/3)ToSearch.png");
				
 
				//Validation of Search String
				boolean condition =DriverSetup.Validation(toSearch);
				
				DriverSetup.ScreenShot("./snaps/4)Validation.png");
				
				//Selecting of Newest Arrivals in DropDown

				DriverSetup.dropSelect(toSelect);
				
				//Screenshot of the DropDown Select Menu 
				DriverSetup.ScreenShot("./snaps/5)DropSelect.png");
				
				//Closing the Broser
				DriverSetup.closeBrowser();
				System.out.println("The Browser is Closed Successfully\n");
				
				//write output in excel
				if(condition)
				{
					System.out.println("Test Results Printed in EXCEL");
					ExcelUtils.setCellData(filePath, "Amazon",i,4,"Passed");					
					ExcelUtils.fillGreenColor(filePath, "Amazon",i,4);
				}
				else
				{
					System.out.println("Test Results Printed in EXCEL");
					ExcelUtils.setCellData(filePath, "Amazon",i,4,"Failed");
					ExcelUtils.fillRedColor(filePath, "Amazon",i,4);
				}
				
			}
		}
		catch(Exception e )
		{
			System.out.println(e.getMessage());
		}
	}
}