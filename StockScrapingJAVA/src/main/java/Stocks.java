import java.util.ArrayList;
import java.util.Date;

// 株価のデータを格納するためのクラスを宣言
class Stocks {

	private static String Name;
	private Date date;
	private double open;
	private double high;
	private double low;
	private double close;

	//　インスタンス変数は日付、始値、高値、安値、終値
	public Stocks(Date date,double open,double high,double low,double close) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}
	// インスタンス変数をコンソール表示するメソッド
	public void allPrint(){
		System.out.print(this.date + ", ");
		System.out.print(this.open + ", ");
		System.out.print(this.high + ", ");
		System.out.print(this.low + ", ");
		System.out.print(this.close + "\n");
	}

	// インスタンスの日付を取得するメソッド
	public Date getDate() {
		return this.date;
	}

	// 日付が合致する場合にインスタンスの各値段を返すメソッド
	public ArrayList<Double> getPrice(Date date){
		if (date.equals(this.date)){
			ArrayList<Double> priceSet = new ArrayList<Double>();
			priceSet.add(this.open);
			priceSet.add(this.high);
			priceSet.add(this.low);
			priceSet.add(this.close);
			return priceSet;
		} else {
			return null;
		}
	}
}