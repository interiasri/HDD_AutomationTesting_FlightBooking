package DriverFactory;

import org.testng.annotations.Test;

public class AppTest extends DriverScript{
	
	@Test
	public void startTest() throws Throwable {
		DriverScript sc = new DriverScript();
		sc.kickStart();
	}
}
