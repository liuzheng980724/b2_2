package b2_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import javafx.stage.DirectoryChooser;

public class FileIO {
	
	private String filePath;
	private String outputFile;
	private boolean validDataFlag = false;
	
	public FileIO() {
		
	}
	
	public List<String[]> importCsv(String filePath) throws Exception {
		CsvReader reader = null;
		List<String[]> dataList = new ArrayList<String[]>();
		try {
			reader = new CsvReader(filePath);

			//Get CSV head
			reader.readHeaders();
			String[] headerFromFile = reader.getHeaders();
			vaildData(headerFromFile);	//iDENTIFY HEADER
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
	
	public boolean sheetPrepare(String path) {
        boolean doneflag = false;
		try (InputStream inp = new FileInputStream(path)) {
            Workbook wb;
				wb = WorkbookFactory.create(inp);
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                System.out.println(wb.getSheetAt(i).getSheetName());
                importXlsx(wb.getSheetAt(i));
                doneflag = true;
            }
        }  catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
		
		return doneflag;
	}
	
	public void importXlsx(Sheet sheet) {	
		StringBuilder data = new StringBuilder();
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    CellType type = cell.getCellTypeEnum();
                    if (type == CellType.BOOLEAN) {
                        data.append(cell.getBooleanCellValue());
                    } else if (type == CellType.NUMERIC) {
                        data.append(cell.getNumericCellValue());
                    } else if (type == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        if(!cellValue.isEmpty()) {
                            cellValue = cellValue.replaceAll("\"", "\"\"");
                            data.append("\"").append(cellValue).append("\"");
                        }
                    } else if (type == CellType.BLANK) {
                    } else {
                        data.append(cell + "");
                    }
                    if(cell.getColumnIndex() != row.getLastCellNum()-1) {
                        data.append(",");
                    }
                }
                data.append('\n');
                try {
					Files.write(Paths.get("./temporaryFile.csv"),
					        data.toString().getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
	}
	
	public void outputCSV(List<String> dataPrepare) {
		//outputFile = "./testFiles/test_output.csv";	//Test code

		DirectoryChooser newDirectoryChooser=new DirectoryChooser();


		File selectedFile = newDirectoryChooser.showDialog(null);
		outputFile = selectedFile.getAbsolutePath() + "/default_out.csv";
		boolean alreadyExists = new File(outputFile).exists();
		
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
		
			if (!alreadyExists)
			{
				csvOutput.write("id");
				csvOutput.write("name");
				csvOutput.write("groupid");
				csvOutput.endRecord();
			}
			
			for(String strings : dataPrepare) {
				String[] split = strings.split(",");
				
				csvOutput.write(split[0]);
				csvOutput.write(split[1]);
				csvOutput.write(split[2]);
				csvOutput.endRecord();
			}
			
			csvOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void vaildData(String[] headerFromFile) {
		int i = 0;
		boolean otherHeader = false;
		boolean firstHeader = false;
		boolean secHeader = false;
		for(String strings : headerFromFile) {
			System.out.println(strings);
			if(i == 0 & strings.equals("StudentID")) {
				firstHeader = true;
			} else if(i == 1 & strings.equals("StudentName")) {
				secHeader = true;
			} else {
				otherHeader = true;
			}
			i++;
		}
		validDataFlag = firstHeader & secHeader & !otherHeader;
		System.out.println("validDataFlag " + validDataFlag);	//Test code
	}
	
	public boolean getValidDataFlag() {
		return validDataFlag;
	}
	
	public String returnPath() {
		return outputFile;
	}
}
