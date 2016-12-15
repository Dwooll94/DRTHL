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
		probabilityList = new Hashtable<String, DRTHLClass>();
		changeRate = speedOfChange;
		maxDependencyLevels = maxLevelsOfDependency; //number of levels deep to go when updating probabilities based on dependencies.
		for(String className:nonTestClasses){
			String testClass = testClasses.get(className);
			if (DRTHLobject.lToggle) {
				//initializeDRTHLClasses with probabilities of 1/# of test cases
				DRTHLClass newDRTHLClass = new DRTHLClass(className, (1.0 / (double) nonTestClasses.size()), testClass);
				Object testClassObject = null;
				try {
					testClassObject = Class.forName(testClass).newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NullPointerException e) {
					System.out.println(testClass);
					e.printStackTrace();
					System.exit(1);
				}
				ArrayList<String> dependencyList = null;
				try {
					dependencyList = (ArrayList<String>) testClassObject.getClass()
							.getDeclaredMethod("getTestedClassDependencies").invoke(testClassObject);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {				
					e.printStackTrace();
					System.exit(1);
				}
				newDRTHLClass.populateAllDependencies(dependencyList);
			}
			probabilityList.put(className, new DRTHLClass(className, 1.0/(double) nonTestClasses.size(), testClass));
		}
		rng = new Random(System.nanoTime());
		

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
		
		return null;
	}
	
	public void manyErrorsEvent(String basisClass){
		//increases the basis class probability and dependencies up to maxDependencyLevels deep
		int dependencyLevel = 1;
		Enumeration<String> listKeys = probabilityList.keys();
		ArrayList<String> currentLevel = new ArrayList<String>();
		currentLevel.add(basisClass);
		ArrayList<String> nextLevel = new ArrayList<String>();
		while(dependencyLevel < maxDependencyLevels && currentLevel.size() > 0){
			if(dependencyLevel > 1){
			}
			for(String curCl:currentLevel){
				String currentKey = null;
				DRTHLClass currentClass =null;
				double totalNotX = 0;
				listKeys = probabilityList.keys();
				while(listKeys.hasMoreElements()){
					currentKey = listKeys.nextElement();
					currentClass = probabilityList.get(currentKey);
					if(currentKey != curCl){
						currentClass.manyErrorsDecrease(dependencyLevel, changeRate , maxDependencyLevels, probabilityList.size());
						totalNotX += currentClass.getProbability();
					}
				}
				ArrayList<String> curClDeps = probabilityList.get(curCl).getDependencies();
				if(curClDeps != null && curClDeps.size() > 0){
					for(String dep: curClDeps){
						if(!nextLevel.contains(dep)){
							nextLevel.add(dep);
						}
					}
				}

				probabilityList.get(curCl).manyErrorsIncrease(1.0 - totalNotX);
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

	public void printAllErrors(){
		Enumeration<String> keys = probabilityList.keys();
		String key = null;
		ArrayList<DRTHLErrorReport> failures = new ArrayList<DRTHLErrorReport>();
		while(keys.hasMoreElements()){
			key = keys.nextElement();
			Class testClass = null;
			try {
				testClass = Class.forName( probabilityList.get(key).getTestClass());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Object testInstance = null;
			try {
				testInstance = testClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				failures.addAll((ArrayList<DRTHLErrorReport>)testClass.getDeclaredMethod("getFailures").invoke(testInstance));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1);
			}
			

		}
		for(int i = 0; i < failures.size(); i++){
			failures.get(i).printResult();
		}
		System.out.println(failures.size() + " failures.");
	}

}
