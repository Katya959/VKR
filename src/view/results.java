package view;

import java.util.HashMap;

/**
 * 
 * @author andym
 *
 */
public class results {
	
	/**
	 * Печать результатов из переданной карты результатов (Result)
	 * @param Result
	 */
	public void print(HashMap<String, String> Result) {
		
		/**
		 * Печатаем результаты
		 */
		Result.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));
		
	}

}
