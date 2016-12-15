package csc455.group4.drthl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

public class DRTHLobject {
	String packageString;
	private DRTHLProbabilityList probList;
	public static boolean lToggle = true; //True: Linked on, False: Linked off.
	

	public DRTHLobject(String packageName){
		packageString = packageName;
		//get all classes using open source java reflections
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
		    .setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
		    .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
		    .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageString))));
		Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
		//separate out ones that are tests
		Iterator<Class<? extends Object>> classesIterator = classes.iterator();
		Hashtable<String, String> tests = new Hashtable<String, String>();
		ArrayList<String> classList = new ArrayList<String>();
		while(classesIterator.hasNext()){
			Class<? extends Object>currentClass = classesIterator.next();
			if(currentClass.getSuperclass().getCanonicalName().equals("csc455.group4.drthl.DRTHLTestClass")){
				Object instance = null;
				try {
					instance = currentClass.newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(1);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(1);
				}
				String classBeingTested = null;
				try {
					classBeingTested = (String) currentClass.getDeclaredMethod("getClassBeingTested").invoke(instance);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				}
				tests.put(classBeingTested, currentClass.getCanonicalName());
			}
			else{
				classList.add(currentClass.getCanonicalName());
			}
		}
		
		//generate the probability matrix
		probList = new DRTHLProbabilityList(classList, tests, 0.001, 3);
		
	}
	public void run(int iterations){
			for(int i = 0; i < iterations; i++){
				String test = probList.selectTest();
				Class testClass = null;
				try {
					testClass = Class.forName(test);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					System.exit(1);
					e1.printStackTrace();
				}
				Object testInstance = null;
				try {
					testInstance = testClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					testClass.getDeclaredMethod("randomTest").invoke(testInstance);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					System.exit(1);
					e.printStackTrace();
				}
				Double recentErrorRate = 0.0;
				try {
					recentErrorRate = (Double) testClass.getDeclaredMethod("getRecentErrorRate").invoke(testInstance);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					System.exit(1);
					e.printStackTrace();
				}
				if(recentErrorRate > 0.1){
					probList.manyErrorsEvent(test);
				}
				else{
					probList.fewErrorsEvent(test);
				}
			}
	}
}
