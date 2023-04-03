import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class LogMaker {
	
	
	public static void logMake(String str) {
		
		
		 File file = new File("I:\\HandMadePrograms\\Eclipse\\stockScraping\\bin\\data\\log.txt");
		try{
			  if(file.exists() == false) {
			        FileWriter filewriter = new FileWriter(file, true);
			        filewriter.close();
			  }
			  if (checkBeforeWritefile(file)){
				  FileWriter filewriter = new FileWriter(file,true);
			      Date date = new Date();
				  filewriter.write(date + " , " + str + "\r\n");
				  filewriter.close();
			  }else{
				  System.out.println("ログファイルが存在しないか使用中のため、書き込みできません");
			  }
		}catch(IOException e){
				System.out.println(e);
		}
	}
	
	private static boolean checkBeforeWritefile(File file){
	    if (file.exists()){
	    	if (file.isFile() && file.canWrite()){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }else{
	    	return false;
	    }
	}
	
	public static void logReset() {
		try{
			  File file = new File("I:\\HandMadePrograms\\Eclipse\\stockScraping\\bin\\data\\log.txt");
			  FileWriter filewriter = new FileWriter(file);
			  filewriter.close();
		}catch(IOException e){
				System.out.println(e);
		}
	}
}