package csc455.group4.drthl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class DRTHLProbabilityList {
	Hashtable<String, DRTHLClass> probabilityList;
	double totalProbability;
	double changeRate;
	int maxDependencyLevels;
	Random rng ;
	public DRTHLProbabilityList(ArrayList<String> nonTestClasses,Hashtable<String, String> testClasses, double speedOfChange, int maxLevelsOfDependency){
		changeRate = speedOfChange;
		maxDependencyLevels = maxLevelsOfDependency; //number of levels deep to go when updating probabilities based on dependencies.
		for(String className:nonTestClasses){
			String testClass = testClasses.get(className);
			if (DRTHL.lToggle) {
				//initializeDRTHLClasses with probabilities of 1/# of test cases
				DRTHLClass newDRTHLClass = new DRTHLClass(className, 1.0 / nonTestClasses.size(), testClass);
				Object testClassObject = null;
				try {
					testClassObject = Class.forName(testClass).newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.exit(1);
					e.printStackTrace();
				}
				ArrayList<String> dependencyList = null;
				try {
					dependencyList = (ArrayList<String>) testClassObject.getClass()
							.getDeclaredMethod("getTestedClassDependencies").invoke(testClassObject);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					System.exit(1);
					e.printStackTrace();
				}
				newDRTHLClass.populateAllDependencies(dependencyList);
			}
			probabilityList.put(className, new DRTHLClass(className, 1.0/nonTestClasses.size(), testClass));
		}
		rng = new Random();
		

	}
	
	public String selectTest(){
		double randomNumber = rng.nextDouble();
		double runningCount = 0;
		Enumeration<String> keys = probabilityList.keys();
		//increment running count for every item in the probability list. Select the one that makes running count exceed randomNumber
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			runningCount += probabilityList.get(key).getProbability();
			if(runningCount >= randomNumber){
				return probabilityList.get(key).getTestClass();
			}
		}
		System.out.println("Did not select test.");
		return null;
	}
	
	public void manyErrorsEvent(String basisClass){
		//increases the basis class probability and dependencies up to maxDependencyLevels deep
		int dependencyLevel = 1;
		Enumeration<String> listKeys = probabilityList.keys();
		ArrayList<String> currentLevel = new ArrayList<String>();
		currentLevel.add(basisClass);
		ArrayList<String> nextLevel = new ArrayList<String>();
		currentLevel = probabilityList.get(basisClass).getDependencies();
		while(dependencyLevel < maxDependencyLevels){
			for(String curCl:currentLevel){
				String currentKey = null;
				DRTHLClass currentClass =null;
				double totalDecrease = 0;
				while(listKeys.hasMoreElements()){
					currentKey = listKeys.nextElement();
					currentClass = probabilityList.get(currentKey);
					if(currentKey != curCl){
						totalDecrease += currentClass.manyErrorsDecrease(dependencyLevel, dependencyLevel, dependencyLevel, probabilityList.size());
					}
				}
				probabilityList.get(curCl).manyErrorsIncrease(totalDecrease);
			}
			
			dependencyLevel++;
			currentLevel = nextLevel;
		}
	}
	
	public void fewErrorsEvent(String basisClass){
		probabilityList.get(basisClass).noErrorsDecrease(changeRate*.3, probabilityList.size());
		Enumeration<String> keys = probabilityList.keys();
		String currentKey = null;
		while(keys.hasMoreElements()){
			currentKey = keys.nextElement();
			if(currentKey != basisClass){
				probabilityList.get(currentKey).noErrorsIncrease(changeRate*.3,probabilityList.size(), probabilityList.get(basisClass).getProbability());
			}
		}
	}
	

}
