package b2_2;

import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

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
}
