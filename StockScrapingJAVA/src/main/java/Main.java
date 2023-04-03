import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
	public static boolean loopStop = false; //trueならスクレイピングループ継続、falseなら停止

	public static void main(String[] args) {
		String reqURL1 = "https://kabutan.jp/stock/kabuka?code="; //株探URL1
		String reqURL2 = "&ashi=day&page="; //株探URL2
		String path = "I:\\HandMadePrograms\\Eclipse\\stockScraping\\bin\\data\\"; //CSV保存先
		String stockNum = "9468"; //銘柄コード
		int pageNum = 0; //過去株価ページNo
		int countDate = 100; //何日分の株価を取得するか

		getAllData(reqURL1,reqURL2,path,stockNum,pageNum,countDate);
		getNewData(reqURL1,reqURL2,path,stockNum);

	}

	// 日付昇順ソート用メソッド
	static class SortByDate implements Comparator<Date>{
		@Override
		public int compare(Date a,Date b) {
			return a.compareTo(b);
		}
	}

	// 指定した期間の全てのデータを取得するメソッド（初回や銘柄追加の場合はこれを使う）
	public static void getAllData(String reqURL1,String reqURL2,String path,String stockNum,int pageNum,int dateCount) {
		loopStop = true;
		Map<Date,List<Double>> stockSet = new HashMap<>();

		while(loopStop) {

			pageNum += 1;
			String reqURL = reqURL1 + stockNum + reqURL2 + pageNum;
			LogMaker.logMake("request URL -> " + reqURL);
			System.out.println(loopStop);
			LogMaker.logMake(stockNum+ " stock data download from " + pageNum + " page");
			stockSet = HtmlReader.getStockData(reqURL,stockSet,dateCount);

			// 取得したデータ数が閾値を超えた場合、処理を中断
			if(stockSet.size() > dateCount) {
				LogMaker.logMake("reached rimit date");
				loopStop = false;
			}

			// 初回のスクレイプで一つもデータを抽出できなかった場合、処理を中断
			if (stockSet.size() < 1) {
				//System.out.println(stockSet.size());
				LogMaker.logMake("cant find data in url -> " + reqURL);
				loopStop = false;
				break;
			}
			// 次のページを読み込む前のディレイ
			try {
				Random rand = new Random();
				int num = 1500 + 100 * (rand.nextInt(9) + 1);
				LogMaker.logMake(" wait time" + num);
				System.out.println("wait "+ num + "s");
				Thread.sleep(num);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				break;
			}
		}

		LogMaker.logMake(stockNum + " stock data download action finish");
		pageNum = 1;

		if(stockSet.size() > 0){
			//取得データを日付昇順ソートして表示
			List<Date> keyList = new ArrayList<Date>(stockSet.keySet());
			Collections.sort(keyList,new SortByDate());

			StockCSV.writeCSV(path,stockNum,stockSet,keyList);
			stockSet = StockCSV.readCSV(path,stockNum);
			/*for (Date key : keyList) {
					System.out.println(key);
					System.out.println(stockSet.get(key));
				}*/
		} else {
			LogMaker.logMake("cant find data of " + stockNum);
		}
	}

	public static void getNewData(String reqURL1,String reqURL2,String path,String stockNum) {
		LogMaker.logMake("start get new data of " + stockNum);
		int countDate = 10;
		//System.out.println(targetDate);

		Map<Date,List<Double>> stockSet1 = StockCSV.readCSV(path,stockNum);
		List<Date> keyList1 = new ArrayList<Date>(stockSet1.keySet());

		String reqURL = reqURL1 + stockNum + reqURL2 + "1";
		LogMaker.logMake("request URL -> " + reqURL);
		Map<Date,List<Double>> stockSet2 = new HashMap<>();
		HtmlReader.getStockData(reqURL,stockSet2,countDate);
		List<Date> keyList2 = new ArrayList<Date>(stockSet2.keySet());

		for (Date key : keyList2) {
			if(keyList1.contains(key)) {
				continue;
			} else {
				LogMaker.logMake("find new data " + stockNum + " of " + key);
				//System.out.println(key);
				//System.out.println(stockSet2.get(key));
				keyList1.add(key);
				stockSet1.put(key, stockSet2.get(key));
				//System.out.println(stockSet1);
				LogMaker.logMake("data append ");
			}
		}
		Collections.sort(keyList1,new SortByDate());
		StockCSV.writeCSV(path,stockNum,stockSet1,keyList1);
	}
}

