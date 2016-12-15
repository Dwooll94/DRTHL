package csc455.group4.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import csc455.group4.drthl.DRTHLErrorReport;

public class SettingsReaderTest extends csc455.group4.drthl.DRTHLTestClass {
    int MAX_RESULTS;
    static ArrayList<DRTHLErrorReport> failures;
    static LinkedList<Boolean> results;

    public SettingsReaderTest(){
    	if(results == null){
    		results = new LinkedList<>();
    	}
    	if(failures == null){
    		failures = new ArrayList<DRTHLErrorReport>();
    	}
    	MAX_RESULTS = 10;
    }
    @Override
    public void randomTest() {
    	JUnitCore core = new JUnitCore();
    	org.junit.runner.Result result = core.run(Request.method(this.getClass(), "readTest"));
    	addResult(result.wasSuccessful());
    	if(!result.wasSuccessful()){
    		failures.add(new DRTHLErrorReport(getClassBeingTested(), "readTest"));
    	}
    }
    
    @Override
    public String getClassBeingTested() {
        return "csc455.group4.example.SettingsReader";
    }

    @Override
    public ArrayList<String> getTestedClassDependencies() {
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("csc455.group4.example.Settings");
        return dependencies;
    }

    @Override
    public double getRecentErrorRate() {
        int pass = 0;
        for (int i = 0; i < results.size(); i++) {
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
    
	@Override
	public
	ArrayList<DRTHLErrorReport> getFailures() {
		return failures;
	}
	
    @Test
    public void readTest(){
    	SettingsReader reader = new SettingsReader();
    	Settings returnedSettings = reader.read();
    	assertEquals(returnedSettings.getA(), 1);
    	assertEquals(returnedSettings.getB(), 2);
    	assertEquals(returnedSettings.getC(), 4);
    }
}