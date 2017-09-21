import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class LogFile {


	public static void log(String st){
		try (PrintWriter p = new PrintWriter(new FileOutputStream("/tmp/ia.log", true))) {
		    p.println(st);
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		}
	}

}

