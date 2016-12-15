package csc455.group4.example;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.naming.spi.DirStateFactory.Result;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Runner;

public class MainTest extends csc455.group4.drthl.DRTHLTestClass {
    int MAX_RESULTS = 10;

    LinkedList<Boolean> results = new LinkedList<>();

    @Override
    public void randomTest() {
    	JUnitCore core = new JUnitCore();
    	org.junit.runner.Result result = core.run(Request.method(this.getClass(), "mainTest"));
    	addResult(result.wasSuccessful());
    }
    
    @Override
    public String getClassBeingTested() {
        return "csc455.group4.example.example";
    }

    @Override
    public ArrayList<String> getTestedClassDependencies() {
        ArrayList<String> dependencies = new ArrayList<>();
        dependencies.add("csc455.group4.example.Settings");
        dependencies.add("csc455.group4.example.SettingsPrinter");
        dependencies.add("csc455.group4.example.SettingsReader");
        return dependencies;
    }

    @Override
    public double getRecentErrorRate() {
        int pass = 0;
        for (int i = 0; i < MAX_RESULTS; i++) {
            if (results.get(i)){
                pass++;
            }
        }
        return (double) pass / (double) results.size();
    }

    private void addResult(boolean result){
        results.add(Boolean.valueOf(result));
        if (results.size() > MAX_RESULTS) {
            results.remove(0);
        }
    }
    
    @Test
    public void mainTest(){
    	boolean ranSuccessfully = false;
    	try {
			example.main(new String[]{});
			ranSuccessfully = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	assertTrue(ranSuccessfully);
    }
}
