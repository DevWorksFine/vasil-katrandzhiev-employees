import java.util.ArrayList;
import java.util.HashMap;

public class Employee {

    private String empID;
    private HashMap<String,ArrayList<MyTask>> myProjects;


    public Employee(String empID) {
        myProjects = new HashMap<String,ArrayList<MyTask>>();
        this.empID = empID;
    }



    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public HashMap<String,ArrayList<MyTask>> getMyProjects() {
        return this.myProjects;
    }



    public void addProject(Project p, String dateF, String dateT) {
        MyTask t = new MyTask(p, dateF, dateT);

        ArrayList<MyTask> listP = null;

        if(myProjects.containsKey(p.getProjID()))
            listP = myProjects.get(p.getProjID());
        else
        {
            listP = new ArrayList<MyTask>();
            myProjects.put(p.getProjID(), listP);
        }

        listP.add(t);
    }
}
