package com.cybertek;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {

	WebDriver driver;
	List<String> allList = new ArrayList<String>();
	List<String> cityList = new ArrayList<String>();
	List<String> countryList = new ArrayList<String>();
	List<String> uniqueCityList = new ArrayList<String>();
	List<String> uniqueCountryList = new ArrayList<String>();
	Set<String> countrySet;
	Set<String> citySet;

	

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://mockaroo.com/");
		driver.manage().window().fullscreen();
	}


	@Test(priority=1)
	public void titleValidation() {
		String title = "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel";
		Assert.assertEquals(driver.getTitle(), title);
	}

	@Test(priority=2)
	public void isMackarooDisplayed() {
		String title = "mockaroo";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='brand']")).getText(), title);
		String title1 = "realistic data generator";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='tagline']")).getText(), title1);
	}

	@Test(priority=3)
	public void removeByX(){
		List<WebElement> ls = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		for (WebElement el : ls) {
			el.click();
		}
	}
	@Test(priority=4)
	public void isFieldsDisplayed() {
		String fieldName = "Field Name";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='column column-header column-name']")).getText(), fieldName);
		String type = "Type";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='column column-header column-type']")).getText(), type);
		String option = "Options";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='column column-header column-options']")).getText(), option);
	}
	@Test(priority=5)
	public void isAddAnotherFieldEnabled() {
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).isEnabled());
	}
	@Test(priority=6)
	public void isRow1000() {
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='num_rows']")).getAttribute("value"),"1000");
	}
	@Test(priority=7)
	public void isDefaultCSV() {
		Assert.assertEquals(driver.findElement(By.xpath("//select[@id='schema_file_format']/option")).getText(),"CSV");
	}
	@Test(priority=8)
	public void isLineEndingUnix() {
		Assert.assertEquals(driver.findElement(By.xpath("//select[@id='schema_line_ending']/option")).getText(),"Unix (LF)");
	}
	@Test(priority=9)
	public void isChecked() {
		Assert.assertTrue(driver.findElement(By.xpath("//input[@id='schema_include_header']")).isSelected());
		Assert.assertFalse(driver.findElement(By.xpath("//input[@id='schema_bom']")).isSelected());
	}
	
	@Test(priority=10)
	public void addingCity() {
		driver.findElement(By.xpath("//div[@class='table-body']/a")).click();
		
	}

	@Test(priority=11)
	public void addingCity1() {
		driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[2]/input[starts-with(@id,'schema_columns_attributes')]")).sendKeys("City");
	}
	
	@Test(priority=12)
    public void chooseTypeIsDisplayed() throws InterruptedException {
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[3]/input[@class='btn btn-default']")).isDisplayed());
        driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[3]/input[@class='btn btn-default']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("City");
        driver.findElement(By.xpath("//div[@class='examples']")).click();
        Thread.sleep(2000);
    }
	
	@Test(priority=13)
	public void addingCountry() throws InterruptedException {
		driver.findElement(By.cssSelector("a[class='btn btn-default add-column-btn add_nested_fields']")).click();
        driver.findElement(By.xpath("//div[@class='fields'][8]/div[2]/input[@class='column-name form-control']")).clear();
        driver.findElement(By.xpath("//div[@class='fields'][8]/div[2]/input[@class='column-name form-control']")).sendKeys("Country");
		driver.findElement(By.xpath("(//input[@class='btn btn-default'])[8]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("country");
		driver.findElement(By.xpath("(//div[@class='examples'])[1]")).click();
		
	}
	@Test(priority=14) 
	public void download() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@id='download']")).click();
		driver.close();

	}
	@Test (priority=15)
	public void checkingFirstLineAndSize() throws IOException {
		// 18 checking if the first line of the file matches with the selected
		FileReader reader = new FileReader("/Users/merveulgur/Downloads/MOCK_DATA.csv");
		BufferedReader breader = new BufferedReader(reader);
		String actual = breader.readLine();
		String expected = "City,Country";
		Assert.assertEquals(actual, expected);
		System.out.println("\n\nExpected first line: \""+expected+ "\" | Actual first line: \""+actual+"\"");
		
		
		// loading all lines to allList arrayList
		String temp = breader.readLine();
		while (temp != null) {
			allList.add(temp);
			temp = breader.readLine();
		}

		// 19 asserting 1000 records
		int actual1 = allList.size();
		int expected1 = 1000;
		Assert.assertEquals(actual1, expected1);
		System.out.println("Expected list size is: "+expected1+ " | Actual list size is: "+actual1);

	}

	@Test (priority=16)
	public void loadingCities() {

		// 20 loading only cities to cityList
		for (int i = 0; i < allList.size(); i++) {
			cityList.add(allList.get(i).substring(0, allList.get(i).indexOf(",")));
			citySet = new HashSet<String>(cityList);
		}
	}

	@Test (priority=17)
	public void loadingCountries() {

		// 21 loading only countries to countryList
		for (int i = 0; i < allList.size(); i++) {
			countryList.add(allList.get(i).substring(allList.get(i).indexOf(",") + 1));
			countrySet = new HashSet<String>(countryList);
		}
	}

	@Test (priority=18)
	public void sortingCities() {

		Collections.sort(cityList);
	}

	@Test (priority=19)
	public void longestCity() {

		// 22 finding the longest city name
		String longestCity = cityList.get(0);
		for (int i = 1; i < cityList.size(); i++) {
			if (cityList.get(i).length() > longestCity.length()) {
				longestCity = cityList.get(i);
			}
		}
		System.out.println("The longest city name is: \""+longestCity+"\"");
	}

	@Test (priority=20)
	public void shortestCity() {

		// 22 finding the shortest city name
		String shortestCity = cityList.get(0);
		for (int i = 1; i < cityList.size(); i++) {
			if (cityList.get(i).length() < shortestCity.length()) {
				shortestCity = cityList.get(i);
			}
		}
		System.out.println("The shortest city name is: \""+shortestCity+"\"");
	}

	@Test (priority=21)
	public void frequencyOfCountry() {
		System.out.println("\n======country frequency count start=======");
		for (String each1 : countrySet) {
			int count = 0;
			for (String each2 : countryList) {
				if (each1.equals(each2)) {
					count++;
				}

			}
			
			System.out.println("\t"+each1 + "-" + count);
		}
		System.out.println("======country frequency count end========\n");
	}

	@Test (priority=22)
	public void uniqueHashMatchCountry() {
		
		for (int i = 0; i < countryList.size(); i++) {
			if (uniqueCountryList.contains(countryList.get(i))) {
				continue;
			}
			uniqueCountryList.add(countryList.get(i));
		}
	
		Assert.assertEquals(uniqueCountryList.size(), countrySet.size());
		System.out.println("UniqueCountry list size is: "+uniqueCountryList.size()+ " | Country HashSet size is: "+countrySet.size());
		

	}

	@Test (priority=23)
	public void uniqueHashMatchCity() {
	

		for (String each : cityList) {
			if (uniqueCityList.contains(each)) {
				continue;
			}
			uniqueCityList.add(each);
		}
	
		Assert.assertEquals(uniqueCityList.size(), citySet.size());
		System.out.println("UniqueCity list size is: "+uniqueCityList.size()+ " | Country HashSet size is: "+citySet.size()+"\n\n");
		

	}
	
	
	
	
	
	

	

}
