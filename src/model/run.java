package model;

import java.io.File;
import java.util.*;

import control.getData;
import view.results;
  
/**
 * 
 * @author andym
 *
 */
public class run {
	
	public static HashMap<String, ArrayList> Data  = new HashMap();
	public static HashMap<String, String> Result  = new HashMap();

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	try {
		
		System.out.println("\n��������� ������\n-------------------------");
		
		getData g = new getData();
		
		/**
		 * ��������� ��� ����� �� �������� ����������
		 */
		File dir = new File("data/"); //path ��������� �� ����������
		ArrayList<File> lst = new ArrayList<>();
		for ( File file : dir.listFiles() ){
		    if ( file.isFile() ) {
		    	System.out.println("\n\n"+file.getName());
		    	Data.put(file.getName(), g.getFile(file.getName()));
		        lst.add(file);
		    }
		}
		
		System.out.println("\n\n\n������������ ������\n-------------------------");
		
		checkAccuracy("06_07.csv");         //key: 06_07.csv value:87 141 62.0 1524 6.0
		checkAccuracy("07_08.csv");         //key: 07_08.csv value:93 141 66.0 1383 7.0
		checkAccuracy("08_085.csv");        //key: 08_085.csv value:70 141 50.0 821 9.0
		checkAccuracy("085_09.csv");        //key: 085_09.csv value:93 141 66.0 948 10.0
		checkAccuracy("09_095.csv");        //key: 09_095.csv value:82 141 59.0 1067 8.0
		checkAccuracy("095_100.csv");       //key: 095_100.csv value:180 141 128.0 1865 10.0
		checkAccuracy("ED_only.csv");       //key: ED_only.csv value:141 141 100.0 141 100.0
		
		System.out.println("\n\n��������� ����������\n-------------------------");
		results v = new results();
		v.print(Result);



		//HashMap ��� ����������� �������� ������ (����� 141 �����) + ���������� ��������� � �������� �� ����� 095_100.csv
		System.out.println("\n\nHashMap ��� ����������� �������� ������ � ���������� ��������� � �������� +- 1 ������� �� ����� \n-------------------------");
		ArrayList<String[]> presize = Data.get("ED_only.csv");   //���� � ������� ������
		ArrayList<String[]> file = Data.get("095_100.csv");           //���� � ������� ��� ��������
		System.out.println("checkAccuracy > "+"095_100.csv");
		//����� 141 �������� ������(presize) � 180 ��������� ����� � ��� �������� (file)
		/*HashMap<Float,Integer> map = new HashMap<>();

		//���������� �� ������ ������
		for (String[] s : presize) { //������ ������ � ���� String
			map.put(Float.parseFloat(s[0]), 0);

			//������ ����� �������� ���� ��� ��������
			for (String[] t : file) {  //���������� ���� ������ � ���� String

				//���� ������ �� ����� �������� � �������� ��������� �������� +- 1 ��� - ����������� ����������
				float min = Float.parseFloat(s[0])-1;  //����� �������� �������� ������ � ������ -1 �������
				float max = Float.parseFloat(s[0])+1;  //������ �������� �������� ������ � ������ +1 �������
				float val = Float.parseFloat(t[0]);    //�������� � ����� �����
				//���� �������� �������� � ��������, �� �� � HashMap ���������� � value + 1.
				if (val <= max && val >= min ) {
					map.put(Float.parseFloat(s[0]), map.get(Float.parseFloat(s[0])) + 1);
				}
			}
		}*/

		HashMap<Float, Integer> map = countOfDetecties();
		Set<Float> keys = map.keySet();

		/*for (Float key: keys) {
			System.out.println("�������� ������: " + key + "   ���������� �������� ����� ��������� � ��������� +- 1 �������: " + map.get(key));
		}*/

		/*System.out.println();
		System.out.println("C���� ������������ ���������������� ������ ��������: " +
				"� ����� ��������� ������ �������� ������ � ���������� +- 1 ������� �� ����������!");
		for (Float key: keys) {
			if (map.get(key) == 0) {
				System.out.println(key);
			}
		}*/




		System.out.println("�������� ������ � ���������� ���������");
		//�������� ��������������� � ������
		ArrayList<String[]> doctor_file = Data.get("ED_only.csv");   //���� � ������� ������

		ArrayList<String[]> II_file = Data.get("095_100.csv");       //���� � ������� �� ��
		//HashMap � �������� � ������� ������, ��� ���� �������� �������� �� � ����� �����
		HashMap<String, ArrayList<Float>> II_map = new HashMap<>();
		for (String[] s : II_file) { //���� ������ � ���� String
			String brain_area = s[1];            //key
			Float time = Float.parseFloat(s[0]); //�������� � ArrayList ������� ��� ������� ������� ��������� �����
			Set<String> keys_II_map = II_map.keySet();   //Set ������ � II_map

			if (keys_II_map.contains(brain_area)) {
				for (Map.Entry<String, ArrayList<Float>> pair: II_map.entrySet()) {
					String key = pair.getKey();
					if (key.equals(brain_area)) {
						ArrayList<Float> ar = pair.getValue();
						ar.add(time);
						II_map.put(brain_area, ar);
					}
				}
			}
			else {
				ArrayList<Float> ar1 = new ArrayList<>();
				ar1.add(time);
				II_map.put(brain_area, ar1);
			}
		}
		for (Map.Entry<String, ArrayList<Float>> pair: II_map.entrySet()) {
			System.out.println(pair.getKey() + " " + pair.getValue());
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}

	}
	
	/**
	 * ��������� ������ ������ � ������ ������������� ��
	 * @param filename
	 * @throws Exception
	 */
	public static void checkAccuracy(String filename) throws Exception {

		ArrayList<String[]> presize = Data.get("ED_only.csv");   //���� � ������� ������
		ArrayList<String[]> file = Data.get(filename);           //���� � ������� ��� ��������
		System.out.println("checkAccuracy > "+filename);
		Integer success = 0;
		
		//���������� �� ������ ������
		for (String[] s : presize) {
			
			//������ ����� �������� ���� ��� ��������
			for (String[] t : file) {
				
				//���� ������ �� ����� �������� � �������� ��������� �������� +- 1 ��� - ����������� ����������
				float min = Float.parseFloat(s[0])-1;
				float max = Float.parseFloat(s[0])+1;
				float val = Float.parseFloat(t[0]);
				if (val <= max && val >= min ) {
					success++;
				}
			}
		}
		
		//double scale = Math.pow(10, 3);
		//presize - �������� ������
		//file - ���� ��� ��������; �� ����� 095_100.scv (� ��� ���� ������� 180 ��������� �� 141 � �������� ������)

		String r = success+" "+presize.size()+" "+Math.ceil(((float)success/(float)presize.size())*100)+" "+file.size()+" "+Math.ceil(((float)success/(float)file.size())*100);
		Result.put(filename, r);		
		
	}



	public static HashMap countOfDetecties() throws Exception {
		ArrayList<String[]> presize = Data.get("ED_only.csv");   //���� � ������� ������
		ArrayList<String[]> file = Data.get("095_100.csv");
		HashMap<Float,Integer> map = new HashMap<>();
		for (String[] s : presize) { //������ ������ � ���� String
			map.put(Float.parseFloat(s[0]), 0);

			//������ ����� �������� ���� ��� ��������
			for (String[] t : file) {  //���������� ���� ������ � ���� String

				//���� ������ �� ����� �������� � �������� ��������� �������� +- 1 ��� - ����������� ����������
				float min = Float.parseFloat(s[0])-1;  //����� �������� �������� ������ � ������ -1 �������
				float max = Float.parseFloat(s[0])+1;  //������ �������� �������� ������ � ������ +1 �������
				float val = Float.parseFloat(t[0]);    //�������� � ����� �����
				//���� �������� �������� � ��������, �� �� � HashMap ���������� � value + 1.
				if (val <= max && val >= min ) {
					map.put(Float.parseFloat(s[0]), map.get(Float.parseFloat(s[0])) + 1);
				}
			}
		}

		//���������� map �� ����������� �������� key � �����
		//map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
		return map;
	}

}
