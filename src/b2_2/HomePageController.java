package b2_2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class HomePageController {

	@FXML
	private ListView<String> groupDetail = new ListView<String>();
	@FXML
	private Button importButtonCSV;
	@FXML
	private Button importButtonXLSX;
	@FXML
	private Button exportButton;
	@FXML
	private Button settingsButton;
	@FXML
	private Label sysMessage;
	
	List<String[]> studentList = new ArrayList<String[]>();
	//List<String[]> classList = new ArrayList<String[]>();
	
	List<String> dataPrepare = new ArrayList<String>();
	int totalStudent = 0;
	int minNumInGroup = 5; //Default Value MIN
	int maxNumInGroup = 7; //Default Value MAX
	int intMaxNumberGroups = 0;
	int intMinNumberGroups = 0;
	int maxGroupInClass = 5;
	
	@FXML
	public void initialize() {
		sysMessage.setText("Welcome! Select button 'Import' to start.");
		//outputFileFunction();	//Test
	}
	
	public HomePageController() {
		//FileIO newFileIO = new FileIO();
		//TEST
		/*try {
			studentList = newFileIO.importCsv("./testFiles/test1.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 0;
		for(String[] students:studentList) {
			System.out.println(students[1]);	//Test code.
			i++;
		}
		System.out.println(i);
		totalStudent = i;*/
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
		
		System.out.println(intMaxNumberGroups);	//Test Code.
		System.out.println(intMinNumberGroups);	//Test Code.
		System.out.println(restStudents);	//Test Code.
	}
	
	public void allocateStudentInGroup() {
		//List<String[]> temporyGroupsList = new ArrayList<String[]>();
		int nextGroupInitialRowID = 0;
		int totalGroupNum = 0;
		
		//MAX Number Groups
		for (int i = 1; i <= intMaxNumberGroups; i++) {
			//groupDetail.getItems().add("Group: " + totalGroupNum);
			
			totalGroupNum++;
			for(int j = 1; j <= maxNumInGroup; j++) {
				dataPrepare.add(studentList.get(nextGroupInitialRowID)[0] + "," + studentList.get(nextGroupInitialRowID)[1] + "," + "GROUP: " + totalGroupNum);
				System.out.println(studentList.get(nextGroupInitialRowID)[0]);
				groupDetail.getItems().add(dataPrepare.get(nextGroupInitialRowID)); //TO FXML
				nextGroupInitialRowID++;
			}
			//temporyGroupsList.add();
		}
		
		//MIN Number Groups
		for (int l = 1; l <= intMinNumberGroups; l++) {
			//groupDetail.getItems().add("Group: " + totalGroupNum);
			totalGroupNum++;
			for(int k = 1; k <= minNumInGroup; k++) {
				dataPrepare.add(studentList.get(nextGroupInitialRowID)[0] + "," + studentList.get(nextGroupInitialRowID)[1] + "," + "GROUP " + totalGroupNum);
				System.out.println(studentList.get(nextGroupInitialRowID)[0]);
				groupDetail.getItems().add(dataPrepare.get(nextGroupInitialRowID)); //TO FXML
				nextGroupInitialRowID++;
				
			}
	        
		}
		
	}
	
	public void importFileFunction(ActionEvent event) {
		reloadList();
		
		FileChooser newfileChooser = new FileChooser();
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Only CSV.", "*.csv");
		newfileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = newfileChooser.showOpenDialog(null);
		
        if(selectedFile != null) {
        	String filepath = selectedFile.getAbsolutePath();
        	FileIO newFileIO = new FileIO();
        	
        	try {
    			studentList = newFileIO.importCsv(filepath);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	boolean validDataFlag = false;
        	validDataFlag = newFileIO.getValidDataFlag();
        	if(validDataFlag) {
        		calculateTotalStudent();
            	caculateGroups();
        		allocateStudentInGroup();
        		exportButton.setDisable(false);
        		sysMessage.setText("Done! Check the list below.");
        	} else {
            	sysMessage.setText("Error! Check data format.");
        	}
        }
        
	}
	
	public void importFileFunctionXlsx(ActionEvent event) {
		reloadList();
		
		FileChooser newfileChooser = new FileChooser();
		ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Only XLSX.", "*.xlsx");
		newfileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = newfileChooser.showOpenDialog(null);
		
        if(selectedFile != null) {
        	String filepath = selectedFile.getAbsolutePath();
        	FileIO newFileIO = new FileIO();
        	
        	try {
    			
        		if(newFileIO.sheetPrepare(filepath)) {
        			studentList = newFileIO.importCsv("./temporaryFile.csv");
        		}
        		
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	boolean validDataFlag = false;
        	validDataFlag = newFileIO.getValidDataFlag();
        	if(validDataFlag) {
        		calculateTotalStudent();
            	caculateGroups();
        		allocateStudentInGroup();
        		exportButton.setDisable(false);
        		sysMessage.setText("Done! Check the list below.");
        	} else {
            	sysMessage.setText("Error! Check data format.");
        	}
        }
        
	}
	
	public void outputFileFunction(ActionEvent event) {
		FileIO newFileIO = new FileIO();
		newFileIO.outputCSV(dataPrepare);
		PopUpWindows newPopUpWindows = new PopUpWindows();
		newPopUpWindows.ShowErrorPopWindow("Done! File Path: " + newFileIO.returnPath());
		sysMessage.setText("Done! File Path: " + newFileIO.returnPath());
	}
	
	public void reloadList() {
		studentList.clear();	//DELET all list exists
		dataPrepare.clear(); 	//DELET all list exists
		groupDetail.getItems().clear();	//DELET all list exists
		exportButton.setDisable(true);
		sysMessage.setText("Welcome! Select button 'Import' to start.");
	}
	
	public void calculateTotalStudent() {
		int i = 0;
		for(String[] students:studentList) {
			i++;
		}
		totalStudent = i;
		System.out.println(totalStudent);	//Test code.
	}
}
