public class EmployeePair {

    private Employee emp1;
    private Employee emp2;

    //project list - workder togetehr


    @Override
    public int hashCode() {
        //getting unique hash
        String hash = null;
        if(emp1.getEmpID().compareToIgnoreCase(emp2.getEmpID()) > 0){
            hash = emp1.getEmpID() + "_" + emp2.getEmpID();
        } else {
            hash = emp2.getEmpID() + "_" + emp1.getEmpID();
        }
        int result = hash.hashCode();


       return result;

    }

    @Override
    public boolean equals(Object obj) {
        EmployeePair ob = (EmployeePair)obj;
        if(ob.emp1 == emp1 && ob.emp2 == emp2 || ob.emp2 == emp1 && ob.emp1 == emp2) {
            return true;
        }
        return false;
    }

    public EmployeePair(Employee emp1 , Employee emp2){
        this.emp1 = emp1;
        this.emp2 = emp2;
    }

    public Employee getEmp1() {
        return emp1;
    }

    public Employee getEmp2() {
        return emp2;
    }
}
