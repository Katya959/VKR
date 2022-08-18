package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author andym
 *
 */
public class getData {

	/**
	 * �������� (����) ������
	 */
	private static String ADDRESS_PATH = "data/";
	private static String ADDRESS_FILE = ADDRESS_PATH;
	private String temp = "";

	/**
	 * ������������
	 */
	public getData() {
		
	}
	
	public getData(String name) {
		prepareDate(name);
	}

	public getData(String path, String name) {
		prepareDate(path, name);
	}
	
	/**
	 * ������
	 */	
	
	/**
	 * ���������� ������. �������� ����� � �������� ������
	 * @param name
	 */
	private void prepareDate(String name) {
		ADDRESS_FILE = ADDRESS_PATH + name;
		//System.out.println(ADDRESS_FILE);
	}
	
	/**
	 * ���������� ������. �������� ���� � ����� � �������� ������
	 * @param path
	 * @param name
	 */
	private void prepareDate(String path, String name) {
		ADDRESS_PATH = path;
		ADDRESS_FILE = path+name;
		//System.out.println(ADDRESS_FILE);
	}
	
	
    /**
     * �������� ������ �� ����� �� ������ ���� � ����� �� ������� ������ 
     * @return
     * @throws IOException
     */
	public ArrayList<String[]> getFile() throws IOException {

    	System.out.print("> "+ADDRESS_FILE);
    	ArrayList<String[]> tempData  = new ArrayList<String[]>();
    	
        try (BufferedReader br = new BufferedReader(new FileReader(ADDRESS_FILE))) {
			while((temp = br.readLine()) != "" & temp != null) {
				        	
				tempData.add(temp.split(","));
				
			}
		}
        return tempData;       
    }
    
	/**
	 * �������� ������ �� ����� �� ������ ����������� ����� ����� (name) ���� �� ������� ������
	 * @param name
	 * @return
	 * @throws IOException
	 */
    public ArrayList<String[]> getFile(String name) throws IOException {
    	prepareDate(name);
    	return getFile();
    }

}
