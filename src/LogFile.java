import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {


	public static void log(String st){
		//multiplataforma
		String outputLog;
		String so = System.getProperty("os.name").toLowerCase();
		if (so.indexOf("nux") >= 0) {
			 outputLog="/tmp/ia.log";
		}
		else {
			 outputLog="C:\\Users\\Edu\\Documents\\log\\test.txt";
		}
		try (PrintWriter p = new PrintWriter(new FileOutputStream(outputLog,  true))) {
			//AGREGO LA FECHA Y HORA PARA EL LOG
			Date d = new Date();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    p.println(df.format(d)+": "+st);
		    p.close();
		} catch (FileNotFoundException e1) {
		}
	}

}

