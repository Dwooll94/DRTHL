package csc455.group4.example;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;

import csc455.group4.drthl.DRTHLErrorReport;

public class SettingsTest  extends csc455.group4.drthl.DRTHLTestClass{
	static int NUMBER_OF_TESTS = 4;
	static double changeRate = 0.001;
   int MAX_RESULTS = 10;
   

static LinkedList<Boolean> results ;
static ArrayList<Double> probabilities;
static ArrayList<ArrayList<Boolean>> recentTestResults;
static ArrayList<DRTHLErrorReport> failures;
public SettingsTest(){
	if(results == null){
		results = new LinkedList<>();
	}
	if (probabilities == null){
		probabilities = new ArrayList<Double>();
	}
	if(recentTestResults == null){
		recentTestResults = new ArrayList<ArrayList<Boolean>>();
		for(int i = 0 ; i < NUMBER_OF_TESTS; i++){
			recentTestResults.add(new ArrayList<Boolean>());
		}
	}
	if(failures == null){
		failures = new ArrayList<DRTHLErrorReport>();
	}
	for(int i = 0; i < NUMBER_OF_TESTS; i++){
		probabilities.add(1.00/NUMBER_OF_TESTS);
	}
}

@Override
public void randomTest() {
	JUnitCore core = new JUnitCore();
	double runningProbability = 0;
	Random rand = new Random(System.nanoTime());
	int selectedTest = 0;
	double selectedNumber = rand.nextDouble();
	for(int i = 0; i < NUMBER_OF_TESTS; i++){
		selectedTest = i;
		runningProbability += probabilities.get(i);
		if(runningProbability >= selectedNumber){
			break;
		}
	}
	org.junit.runner.Result result = null;
	switch(selectedTest){
	case 0:
		result = core.run(Request.method(this.getClass(), "testGetAndSetA"));
		break;
	case 1:
		result = core.run(Request.method(this.getClass(), "testGetAndSetB"));
		break;
	case 2:
		result = core.run(Request.method(this.getClass(), "testGetAndSetC"));
		break;
	case 3:
		result = core.run(Request.method(this.getClass(), "testGetCalculatedConfig"));
		break;
	default:
		result = core.run(Request.method(this.getClass(), "testGetAndSetA"));
		break;
	}
	ArrayList<Boolean> thisTestsResults = recentTestResults.get(selectedTest);
	if(thisTestsResults.size() >= MAX_RESULTS){
		thisTestsResults.remove(0);
	}
	thisTestsResults.add(result.wasSuccessful());
	if(!result.wasSuccessful()){
		switch(selectedTest){
		case 0:
			failures.add(new DRTHLErrorReport(getClassBeingTested(), "testGetAndSetA"));
			break;
		case 1:
			failures.add(new DRTHLErrorReport(getClassBeingTested(), "testGetAndSetB"));
			break;
		case 2:
			failures.add(new DRTHLErrorReport(getClassBeingTested(), "testGetAndSetC"));
			break;
		case 3:
			failures.add(new DRTHLErrorReport(getClassBeingTested(), "testGetCalculatedConfig"));
			break;
		default:
			failures.add(new DRTHLErrorReport(getClassBeingTested(),  "testGetAndSetA"));
			break;
		}
	}
	recentTestResults.set(selectedTest, thisTestsResults);
	int errorTotal = 0;
	for(int i = 0; i <recentTestResults.get(selectedTest).size(); i++){
		if(recentTestResults.get(selectedTest).get(i)){
			errorTotal++;
		}
	}
	double errorRate = errorTotal / recentTestResults.get(selectedTest).size();
	if(errorRate == 0.0){
		for(int i = 0; i < NUMBER_OF_TESTS; i++){
			if (i != selectedTest){
				if(probabilities.get(i) >= changeRate*.3){
					probabilities.set(i, probabilities.get(i) + ( changeRate*.3 / (NUMBER_OF_TESTS - 1)));
				}
				else{
					probabilities.set(i, probabilities.get(i) + ( probabilities.get(selectedTest)/ (NUMBER_OF_TESTS - 1)));
				}
				
			}
			else{
				if(probabilities.get(i) >=changeRate*.3){
					probabilities.set(i, probabilities.get(i) - changeRate*.3);
				}
				else{
					probabilities.set(i, 0.00);
				}
				
			}
		}
	}
		else if (errorRate >0.1){
			double selectedTestProb = 1;
			for(int i = 0; i < NUMBER_OF_TESTS; i++){
				if (i != selectedTest){
					if(probabilities.get(i) >= changeRate / (NUMBER_OF_TESTS - 1)){
						Double newProbability =  probabilities.get(i) - changeRate / (NUMBER_OF_TESTS - 1);
						probabilities.set(i, newProbability);
						selectedTestProb -= newProbability;
					}
				}
			}
			probabilities.set(selectedTest, selectedTestProb);
	}
	addResult(result.wasSuccessful());
}

@Override
public String getClassBeingTested() {
    return "csc455.group4.example.Settings";
}

@Override
public ArrayList<String> getTestedClassDependencies() {
    ArrayList<String> dependencies = new ArrayList<>();
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
  
    @Test
    public void testGetAndSetA(){
    	Random rnd = new Random();
    	Settings settings = new Settings();
    	int rndInt = rnd.nextInt();
    	settings.setA(rndInt);
    	assertEquals(settings.getA(), rndInt);
    }
    
    @Test
    public void testGetAndSetB(){
    	Random rnd = new Random();
    	Settings settings = new Settings();
    	int rndInt = rnd.nextInt();
    	settings.setB(rndInt);
    	assertEquals(settings.getB(), rndInt);
    }
    
    @Test
    public void testGetAndSetC(){
    	Random rnd = new Random();
    	Settings settings = new Settings();
    	int rndInt = rnd.nextInt();
    	settings.setC(rndInt);
    	assertEquals(settings.getC(), rndInt);
    }
    
    @Test
    public void testGetCalculatedConfig() throws Exception{
    	Random rnd = new Random();
    	Settings settings = new Settings();
    	int rndA = rnd.nextInt();
    	int rndB = rnd.nextInt();
    	int rndC = rnd.nextInt();
    	settings.setA(rndA);
    	settings.setB(rndB);
    	settings.setC(rndC);
    	double expectedResult = 10.0/(rndC -  rndB -  rndA);
    	assertEquals(settings.getCalculatedConfig(), expectedResult, 0.001);
    }

	@Override
	public
	ArrayList<DRTHLErrorReport> getFailures() {
		return failures;
	}
} 