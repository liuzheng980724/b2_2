package b2_2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class FileIO {
	
	String filePath;
	
	public FileIO() {
		
	}
	
	public List<String[]> importCsv(String filePath) throws Exception {
		CsvReader reader = null;
		List<String[]> dataList = new ArrayList<String[]>();
		try {
			reader = new CsvReader(filePath);
 
			//Get CSV head
			reader.readHeaders();
			
			//read All
			while (reader.readRecord()) {
				//Add data in list.
				dataList.add(reader.getRawRecord().split(","));
				//Show Get hole row.
				System.out.println(reader.getRawRecord());
				//Show Get cols.
				//System.out.println(reader.get(0));
				//System.out.println(reader.get(1));
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Error!!" + e);
			throw e;
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
 
		return dataList;
	}
	
	//Test import
	public void test_import() throws Exception {
		List<String[]> datas = importCsv(filePath);
		for (String[] strings : datas) {
			System.out.println(strings[0]);
		}
	}
	
	public void outputCSV() {
		String outputFile = "./testFiles/test_output.csv";	//Test code
		boolean alreadyExists = new File(outputFile).exists();
		
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
		
			if (!alreadyExists)
			{
				csvOutput.write("id");
				csvOutput.write("name");
				csvOutput.write("group_id");
				csvOutput.endRecord();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
