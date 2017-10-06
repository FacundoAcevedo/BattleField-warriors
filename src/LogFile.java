import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile {


	public static void log(String st){
		try (PrintWriter p = new PrintWriter(new FileOutputStream("C:\\Users\\Edu\\Documents\\log\\test.txt", true))) {
			//AGREGO LA FECHA Y HORA PARA EL LOG
			Date d = new Date();
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		    p.println(df.format(d)+": "+st);
		    p.close();
		} catch (FileNotFoundException e1) {
		}
	}

}

