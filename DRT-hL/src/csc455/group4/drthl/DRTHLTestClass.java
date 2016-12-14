package csc455.group4.drthl;

import java.util.ArrayList;

import org.junit.Test;
import junit.framework.TestCase;

public abstract class DRTHLTestClass extends TestCase{
	//Classes implementing this class needs to set the class it's testing on construction
	@Test
	public abstract void randomTest();
	
	public abstract String getClassBeingTested();
	
	public abstract ArrayList<String> getTestedClassDependencies();
	
	public abstract double getRecentErrorRate();
}
