package csc455.group4.drthl;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;
import junit.framework.TestCase;

public abstract class DRTHLTestClass extends TestCase {

	int MAX_RESULTS = 10;

	LinkedList<Boolean> results = new LinkedList<>();

	//Classes implementing this class needs to set the class it's testing on construction
	public abstract void randomTest();
	
	public abstract String getClassBeingTested();
	
	public abstract ArrayList<String> getTestedClassDependencies();

	public double getRecentErrorRate() {
		int pass = 0;
		for (int i = 0; i < MAX_RESULTS; i++) {
			if (results.get(i)){
				pass++;
			}
		}
		return (double) pass / (double) results.size();
	}

	protected void addResult(boolean result){
		results.add(Boolean.valueOf(result));
		if (results.size() > MAX_RESULTS) {
			results.remove(0);
		}
	}
}
