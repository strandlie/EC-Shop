package tdt4140.gr1864.app.ui;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class FxAppTest extends ApplicationTest {
	
	@BeforeClass
	public static void headless() {
		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
			GitlabCISupport.headless();
		}
	}

    @Test
    public void testFxApp() {
    	Assert.assertTrue(true);
    }
}
