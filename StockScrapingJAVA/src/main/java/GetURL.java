import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetURL {
	public static String getStockURL(String url) {
		String aa = "";
		try {
			aa = getSourceText(new URL(url));
		} catch (MalformedURLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return aa;

	}
	public static String getSourceText(URL url) throws IOException {
		InputStream in = url.openStream(); //接続
		//文字エンコーディング
		//InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String s;
			while ((s=bf.readLine())!=null) {
				sb.append(s);
			}
		} finally {
			in.close();
		}
		return sb.toString();
	}
} 