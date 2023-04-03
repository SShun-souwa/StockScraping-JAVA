import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlReader {

	public static Map<Date,List<Double>> getStockData(String url,Map<Date,List<Double>> stockSet,int DateCount) {
		LogMaker.logMake("scraping start from " + url);
		String html = GetURL.getStockURL(url);
		String[] b = html.split("<th scope=\"row\"><time datetime=\""); //株価日足データの日付直前で分割
		String regex = "(\\d{4}-\\d{2}-\\d{2}).+?</th><td>(.+?)</td><td>(.+?)</td><td>(.+?)</td><td>(.+?)</td><td><span class=\"";//株価データ部分の正規表現
		Pattern p = Pattern.compile(regex);
		//System.out.println(a);

		for (String str:b) {
			//System.out.println(str);
			Matcher m = p.matcher(str);
			if (m.find()){
				List<Double> s = new ArrayList<Double>(); //株価データを格納するリスト
				s.add(setPrice(m.group(2))); //始値Open
				s.add(setPrice(m.group(3))); //高値High
				s.add(setPrice(m.group(4))); //安値Low
				s.add(setPrice(m.group(5))); //終値Close
				stockSet.put(setDate(m.group(1)),s); //日付をキーとしてMapへ格納
				/*System.out.println(setDate(m.group(1)));
				System.out.println(setPrice(m.group(2)));
				System.out.println(setPrice(m.group(3)));
				System.out.println(setPrice(m.group(4)));
				System.out.println(setPrice(m.group(5)));
				System.out.println(stock.getPrice(setDate(m.group(1))));
				System.out.println(stockSet.size());
				System.out.println(tf);*/
			}
		}
		LogMaker.logMake("scraping finish from " + url);
		return stockSet;
	}

	// 文字列を日付型へ型変更するメソッド
	public static Date setDate(String date) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		try{
			Date reDate = sdFormat.parse(date);
			return reDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 日付型を文字列へ型変更するメソッド
	public static String setDate(Date date) {
		String reDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return reDate;
	}

	// 文字列をdouble型へ型変更するメソッド
	public static double setPrice(String price) {
		String rePrice = price.replace(",", "");
		double rerePrice =  Double.parseDouble(rePrice);
		return rerePrice;
	}
}	