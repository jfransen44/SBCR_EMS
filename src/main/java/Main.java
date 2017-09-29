import java.sql.SQLException;

/**
 * Created by jeremyfransen on 9/25/17.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        String fileName = "QB_Payroll_Rpt1.xlsm";
        String employeeID = "5075";
        String query = "SELECT ? from payStubs WHERE employeeID = ?";
        PayStubReportReader s = new PayStubReportReader();
        s.gatherStubs(fileName);

    }
}
