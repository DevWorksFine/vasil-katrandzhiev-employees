import java.time.LocalDate;
import java.time.LocalTime;

public class MyTask {
    Project id;
    String dateF;
    String dateT;

   public MyTask(Project p, String datef, String datet) {
        id = p;
        dateF = datef;
        dateT = datet;
    }

    public String getDateF() {
       return dateF;
    }

    public String getDateT() {
       return dateT;
    }

    public void setDateT(String now){
       dateT = now;
    }
}
