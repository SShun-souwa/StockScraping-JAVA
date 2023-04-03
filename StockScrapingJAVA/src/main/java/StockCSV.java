import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//CSVファイルの読み書きを行うクラス

public class StockCSV {

	public static void writeCSV (String path,String code, Map<Date,List<Double>> hasmap, List<Date> keyList) {
		LogMaker.logMake(code + " data csv write start");
		FileWriter fw;
		try {
			fw = new FileWriter(path + code + ".csv", false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			pw.print("date");
			pw.print(",");
			pw.print("Open");
			pw.print(",");
			pw.print("High");
			pw.print(",");
			pw.print("Low");
			pw.print(",");
			pw.print("Close");
			pw.println();
			for (Date d:keyList) {
				pw.print(HtmlReader.setDate(d));
				pw.print(",");
				pw.print(hasmap.get(d).get(0));
				pw.print(",");
				pw.print(hasmap.get(d).get(1));
				pw.print(",");
				pw.print(hasmap.get(d).get(2));
				pw.print(",");
				pw.print(hasmap.get(d).get(3));
				pw.print(",");
				pw.println();
			}
			pw.close();
			LogMaker.logMake(code + " data csv write finish");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			LogMaker.logMake(code + " data csv write failed");
			e.printStackTrace();
		}
	}

	public static Map<Date,List<Double>> readCSV (String path,String code) {
		LogMaker.logMake(code + " data csv read start");
		Path filepath = Paths.get(path + code + ".csv");
		Map<Date,List<Double>> stockSet = new HashMap<>();
		try {
			// CSVファイルの読み込み
			List<String> lines = Files.readAllLines(filepath, Charset.forName("UTF-8"));
			for (int i = 1; i < lines.size(); i++) {
				String[] data = lines.get(i).split(",");
				if (data.length > 3) {
					/* 読み込んだCSVファイルの内容を出力
                    System.out.print(data[0] + ",");
                    System.out.print(data[1] + ",");
                    System.out.print(data[2] + ",");
                    System.out.print(data[3] + ",");
                    System.out.println(data[4]);*/
					// 読み込んだデータを日付をキーとするMAPへ出力
					List<Double> s = new ArrayList<Double>();
					s.add(HtmlReader.setPrice(data[1]));
					s.add(HtmlReader.setPrice(data[2]));
					s.add(HtmlReader.setPrice(data[3]));
					s.add(HtmlReader.setPrice(data[4]));
					stockSet.put(HtmlReader.setDate(data[0]),s);
				}
			}
		} catch (IOException e) {
			LogMaker.logMake(code + " data csv read failed");
		}
		LogMaker.logMake(code + " data csv read finish");
		return stockSet;
	}
}
