package view;

import java.util.HashMap;

/**
 * 
 * @author andym
 *
 */
public class results {
	
	/**
	 * ������ ����������� �� ���������� ����� ����������� (Result)
	 * @param Result
	 */
	public void print(HashMap<String, String> Result) {
		
		/**
		 * �������� ����������
		 */
		Result.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));
		
	}

}
