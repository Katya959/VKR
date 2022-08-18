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
		
		System.out.println("\nЗагружаем данные\n-------------------------");
		
		getData g = new getData();
		
		/**
		 * Извлекаем все файлы из заданной директории
		 */
		File dir = new File("data/"); //path указывает на директорию
		ArrayList<File> lst = new ArrayList<>();
		for ( File file : dir.listFiles() ){
		    if ( file.isFile() ) {
		    	System.out.println("\n\n"+file.getName());
		    	Data.put(file.getName(), g.getFile(file.getName()));
		        lst.add(file);
		    }
		}
		
		System.out.println("\n\n\nОбрабатываем данные\n-------------------------");
		
		checkAccuracy("06_07.csv");         //key: 06_07.csv value:87 141 62.0 1524 6.0
		checkAccuracy("07_08.csv");         //key: 07_08.csv value:93 141 66.0 1383 7.0
		checkAccuracy("08_085.csv");        //key: 08_085.csv value:70 141 50.0 821 9.0
		checkAccuracy("085_09.csv");        //key: 085_09.csv value:93 141 66.0 948 10.0
		checkAccuracy("09_095.csv");        //key: 09_095.csv value:82 141 59.0 1067 8.0
		checkAccuracy("095_100.csv");       //key: 095_100.csv value:180 141 128.0 1865 10.0
		checkAccuracy("ED_only.csv");       //key: ED_only.csv value:141 141 100.0 141 100.0
		
		System.out.println("\n\nПубликуем результаты\n-------------------------");
		results v = new results();
		v.print(Result);



		//HashMap для отображения разметок врачей (всего 141 штука) + количество попаданий в диапазон из файла 095_100.csv
		System.out.println("\n\nHashMap для отображения разметок врачей и количества попаданий в диапазон +- 1 секунда из файла \n-------------------------");
		ArrayList<String[]> presize = Data.get("ED_only.csv");   //файл с данными врачей
		ArrayList<String[]> file = Data.get("095_100.csv");           //файл с данными для проверки
		System.out.println("checkAccuracy > "+"095_100.csv");
		//Всего 141 разметка врачей(presize) и 180 попаданий наших в эти разметки (file)
		/*HashMap<Float,Integer> map = new HashMap<>();

		//Проходимся по данным врачей
		for (String[] s : presize) { //данные врачей в виде String
			map.put(Float.parseFloat(s[0]), 0);

			//Теперь берем заданный файл для проверки
			for (String[] t : file) {  //полученные нами данные в виде String

				//Если данные из файла попадают в диапазон врачебной разметки +- 1 сек - засчитываем совпадение
				float min = Float.parseFloat(s[0])-1;  //левое значение разметки врачей с учетом -1 секунда
				float max = Float.parseFloat(s[0])+1;  //правое значение разметки врачей с учетом +1 секунда
				float val = Float.parseFloat(t[0]);    //значение в нашем файле
				//если значение попадает в диапазон, то мы в HashMap записываем в value + 1.
				if (val <= max && val >= min ) {
					map.put(Float.parseFloat(s[0]), map.get(Float.parseFloat(s[0])) + 1);
				}
			}
		}*/

		HashMap<Float, Integer> map = countOfDetecties();
		Set<Float> keys = map.keySet();

		/*for (Float key: keys) {
			System.out.println("Детекция врачей: " + key + "   Количество детекций нашей программы в диапазоне +- 1 секунда: " + map.get(key));
		}*/

		/*System.out.println();
		System.out.println("Cтоит внимательнее проанализировать данные детекции: " +
				"в нашей программе данная детекция врачей с диапазоном +- 1 секунда не обнаружена!");
		for (Float key: keys) {
			if (map.get(key) == 0) {
				System.out.println(key);
			}
		}*/




		System.out.println("Начинаем работу с построения диаграммы");
		//Работаем непосредственно с файлом
		ArrayList<String[]> doctor_file = Data.get("ED_only.csv");   //файл с данными врачей

		ArrayList<String[]> II_file = Data.get("095_100.csv");       //файл с данными от ИИ
		//HashMap с участком и списком времен, где была отмечена детекция ИИ в нашем файле
		HashMap<String, ArrayList<Float>> II_map = new HashMap<>();
		for (String[] s : II_file) { //наши данные в виде String
			String brain_area = s[1];            //key
			Float time = Float.parseFloat(s[0]); //значение в ArrayList времени для данного участка головного мозга
			Set<String> keys_II_map = II_map.keySet();   //Set ключей в II_map

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
	 * Сравнение данных врачей и данных распознавания ИИ
	 * @param filename
	 * @throws Exception
	 */
	public static void checkAccuracy(String filename) throws Exception {

		ArrayList<String[]> presize = Data.get("ED_only.csv");   //файл с данными врачей
		ArrayList<String[]> file = Data.get(filename);           //файл с данными для проверки
		System.out.println("checkAccuracy > "+filename);
		Integer success = 0;
		
		//Проходимся по данным врачей
		for (String[] s : presize) {
			
			//Теперь берем заданный файл для проверки
			for (String[] t : file) {
				
				//Если данные из файла попадают в диапазон врачебной разметки +- 1 сек - засчитываем совпадение
				float min = Float.parseFloat(s[0])-1;
				float max = Float.parseFloat(s[0])+1;
				float val = Float.parseFloat(t[0]);
				if (val <= max && val >= min ) {
					success++;
				}
			}
		}
		
		//double scale = Math.pow(10, 3);
		//presize - разметка врачей
		//file - файл для проверки; мы берем 095_100.scv (в нем было найдено 180 попаданий из 141 в разметке врачей)

		String r = success+" "+presize.size()+" "+Math.ceil(((float)success/(float)presize.size())*100)+" "+file.size()+" "+Math.ceil(((float)success/(float)file.size())*100);
		Result.put(filename, r);		
		
	}



	public static HashMap countOfDetecties() throws Exception {
		ArrayList<String[]> presize = Data.get("ED_only.csv");   //файл с данными врачей
		ArrayList<String[]> file = Data.get("095_100.csv");
		HashMap<Float,Integer> map = new HashMap<>();
		for (String[] s : presize) { //данные врачей в виде String
			map.put(Float.parseFloat(s[0]), 0);

			//Теперь берем заданный файл для проверки
			for (String[] t : file) {  //полученные нами данные в виде String

				//Если данные из файла попадают в диапазон врачебной разметки +- 1 сек - засчитываем совпадение
				float min = Float.parseFloat(s[0])-1;  //левое значение разметки врачей с учетом -1 секунда
				float max = Float.parseFloat(s[0])+1;  //правое значение разметки врачей с учетом +1 секунда
				float val = Float.parseFloat(t[0]);    //значение в нашем файле
				//если значение попадает в диапазон, то мы в HashMap записываем в value + 1.
				if (val <= max && val >= min ) {
					map.put(Float.parseFloat(s[0]), map.get(Float.parseFloat(s[0])) + 1);
				}
			}
		}

		//сортировка map по возрастанию значений key в файле
		//map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(System.out::println);
		return map;
	}

}
