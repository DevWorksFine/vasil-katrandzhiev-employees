

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.HashMap;


public class MainEmployees {


    public static void main(String[] args) {


        HashMap<String, Project> projTable = new HashMap<String, Project>();

        HashMap<String, Employee> emptable = new HashMap<String, Employee>();

        String fileName = null;
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter path to filename (relative -> example: ./employees.txt): ");
            fileName = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }



        try {

            List<String> linesList = Files.readAllLines(Paths.get(fileName));
            for (String line : linesList) {
                String[] eachLine = line.split(",");

                Project p = projTable.get(eachLine[1]);

                if (p == null) {
                    p = new Project(eachLine[1]);

                    projTable.put(eachLine[1], p);
                }


                Employee e = emptable.get(eachLine[0]);

                if (e == null) {

                    e = new Employee(eachLine[0]);
                    emptable.put(eachLine[0], e);
                }

                e.addProject(p, eachLine[2], eachLine[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //table has key(pair<EMP> emp1  emp2) value duration of working together


        HashMap<EmployeePair,Period> PairToDuration = new HashMap<EmployeePair,Period>(); // final data

        ArrayList<Employee> emps = new ArrayList<Employee>(emptable.values());

        for (Project p : projTable.values()) {

            for (int i = 0; i < emps.size(); i++) {
                Employee e1 = emps.get(i);
                if (!e1.getMyProjects().containsKey(p.getProjID())) {
                    continue;
                }
                for (int j = 0; j < emps.size(); j++) {
                    if (j != i) {
                        Employee e2 = emps.get(j);


                        EmployeePair empPair = new EmployeePair(e1, e2);
                        if (!PairToDuration.containsKey(empPair)) {
                            PairToDuration.put(empPair, null);

                        }
                        //e1 get me range of work of p is it the same as e2

                        if (!e2.getMyProjects().containsKey(p.getProjID())) {
                            continue;
                        }

                        ArrayList<MyTask> e1Tasks = e1.getMyProjects().get(p.getProjID());//mytask().date1 date2
                        ArrayList<MyTask> e2Tasks = e2.getMyProjects().get(p.getProjID());

                        for (int x = 0; x < e1Tasks.size(); x++) {
                            if(e1Tasks.get(x).getDateT().trim().equals("NULL")){
                                e1Tasks.get(x).setDateT(LocalDate.now().toString());
                            }
                            LocalDate startDate_e1 = LocalDate.parse(e1Tasks.get(x).getDateF().trim());
                            LocalDate finishDate_e1 = LocalDate.parse(e1Tasks.get(x).getDateT().trim());
                            for (int y = 0; y < e2Tasks.size(); y++) {
                                if(e2Tasks.get(x).getDateT().trim().equals("NULL")){
                                    e2Tasks.get(x).setDateT(LocalDate.now().toString());
                                }
                                LocalDate startDate_e2 = LocalDate.parse(e2Tasks.get(y).getDateF().trim());
                                LocalDate finishDate_e2 = LocalDate.parse(e2Tasks.get(y).getDateT().trim());
                                Period period;
                                Integer periodDaysWorkedTogether;


                                if (startDate_e1.isAfter(startDate_e2) && startDate_e1.isBefore(finishDate_e2)) {
                                    period = Period.between(startDate_e1, finishDate_e1);

                                } else if (finishDate_e1.isAfter(startDate_e2) && finishDate_e1.isBefore(finishDate_e2)) {
                                    period = Period.between(startDate_e2, finishDate_e1);

                                } else if (startDate_e1.isBefore(startDate_e2) && finishDate_e1.isAfter(finishDate_e2)) {
                                    period = Period.between(startDate_e2, finishDate_e2);

                                } else if (startDate_e1.isAfter(startDate_e2) && finishDate_e1.isBefore(finishDate_e2)) {
                                    period = Period.between(startDate_e1, finishDate_e2);

                                } else {
                                    System.out.println("No collision");
                                    continue;
                                }

                                Period UpdatedPer = PairToDuration.get(empPair);
                                if(UpdatedPer != null){
                                    UpdatedPer.plus(period);
                                } else {
                                    UpdatedPer = period;
                                }
                                PairToDuration.put(empPair,UpdatedPer);

                            }


                        }


                    }

                }


            }


        }

        //Key key = Collections.max(PairToDuration.entrySet(), Map.Entry.comparingByValue()).getKey();
        for(EmployeePair pair : PairToDuration.keySet()){
            if(PairToDuration.get(pair) != null) {
                System.out.println("Pair " + pair.getEmp1().getEmpID() + " : "+pair.getEmp2().getEmpID() + " = " + PairToDuration.get(pair));
            }

        }


        System.out.println("HM.");
    }

}
