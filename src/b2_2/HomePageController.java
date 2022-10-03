package b2_2;

import java.util.ArrayList;
import java.util.List;

public class HomePageController {

	List<String[]> studentList = new ArrayList<String[]>();
	List<String[]> classList = new ArrayList<String[]>();
	int totalStudent = 0;
	int minNumInGroup = 5;
	int maxNumInGroup = 7;
	int intMaxNumberGroups = 0;
	int intMinNumberGroups = 0;
	
	public HomePageController() {
		FileIO newFileIO = new FileIO();
		
		//TEST
		try {
			studentList = newFileIO.importCsv("./testFiles/test1.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 0;
		for(String[] students:studentList) {
			System.out.println(students[1]);
			i++;
		}
		System.out.println(i);
		totalStudent = i;
		caculateGroups();
	}
	
	public void caculateGroups() {
		double maxNumberGroups = totalStudent/maxNumInGroup;
		intMaxNumberGroups = (int)maxNumberGroups;
		int restStudents = totalStudent - (intMaxNumberGroups*maxNumInGroup);
		double minNumberGroups = restStudents/minNumInGroup;
		intMinNumberGroups = (int)minNumberGroups;
		restStudents = restStudents - (intMinNumberGroups*minNumInGroup);
		
		int checkRestNum = restStudents/minNumInGroup;
		
		boolean processDone = false;
		while(!processDone) {
			if(checkRestNum < 1) {
				intMaxNumberGroups = intMaxNumberGroups - 1;
				restStudents = restStudents + maxNumInGroup;
			}
			double tempMinNumberGroups = restStudents/minNumInGroup;
			int intTempMinNumberGroups = (int)tempMinNumberGroups;
			intMinNumberGroups = intMinNumberGroups + intTempMinNumberGroups;
			restStudents = restStudents - (intTempMinNumberGroups*minNumInGroup);
			
			if(restStudents == 0) {
				processDone = true;
			}
		}
		
		System.out.println(intMaxNumberGroups);
		System.out.println(intMinNumberGroups);
		System.out.println(restStudents);
	}
}
