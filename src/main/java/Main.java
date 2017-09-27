import java.util.List;

/**
 * Created by jeremyfransen on 9/25/17.
 */
public class Main {

    public static void main(String[] args){
        String fileName = "QB_Payroll_Rpt1.xlsm";

        PayStubReportReader s = new PayStubReportReader();

        List<PayStub> list = s.gatherStubs(fileName);
        System.out.print(list.size());

    }
}
