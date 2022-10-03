package b2_2;

import java.util.ArrayList;
import java.util.List;

public class HomePageController {

	List<String[]> studentList = new ArrayList<String[]>();
	
	public HomePageController() {
		FileIO newFileIO = new FileIO();
		
		//TEST
		try {
			studentList = newFileIO.importCsv("./testFiles/test1.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String[] students:studentList) {
			System.out.println(students[1]);
		}
		

		
	}
}
