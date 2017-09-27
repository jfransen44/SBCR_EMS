import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jeremyfransen on 9/25/17.
 */
public class PayStubReportReader {

    private Sheet openFile(String filePath){
       XSSFWorkbook wb;
       Sheet report = null;
       final String SHEETNAME = "Detail";
        try {
            //String filePath = "QB_Payroll_Rpt1.xlsm";
            ClassLoader cl = getClass().getClassLoader();
            File file = new File(cl.getResource(filePath).getFile());            //FileInputStream is = new FileInputStream(filePath);
            wb = new XSSFWorkbook(file);
            report = wb.getSheet(SHEETNAME);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            return report;
        }
    }

    public List<PayStub> gatherStubs(String filePath){
        List<PayStub> payStubList = new LinkedList<PayStub>();
        Sheet report = openFile(filePath);
        int row = 4; //row of first entry
        Cell c = report.getRow(row).getCell(3);

        while (c.toString() != ""){
            String empID = c.toString().substring(7, 11); //last 4 of ssn.  String to account for leading 0s
            PayStub stub = makePayStub(empID, row, report);
            payStubList.add(stub);
            row += 10; //move to next employee entry
            c = report.getRow(row).getCell(3);
        }
        return payStubList;
    }


    private PayStub makePayStub(String empID, int row, Sheet report){
            /*
                 MAP OF PAYSTUB
                 2          3          4          6          7          8
                                                                       checkNum
                 regHours  curHourEarn  ytdHourly curFed     ytdFed
                 otHours   curOTEarn    ytdOT     curSocSec  ytdSocSec
                                                  curMedi    ytdMedi
                                                curAddlMedi  ytdAddMedi
                                                curCAwith    ytdCAwith
                                                curCADis     ytdCADis
                                                  curNet      ytdNet

       */

        //Set all fields of new PayStub object

        PayStub stub = new PayStub(empID);
        int ind = 0;

        //set check number
        stub.setCheckNum(Integer.parseInt(report.getRow(row).getCell(8).getStringCellValue().substring(6)));
        row += 2; //skip empty rows

        stub.setCurRegEarnings(Float.parseFloat(report.getRow(row).getCell(2).toString()));
        stub.setYtdHourly(Float.parseFloat(report.getRow(row).getCell(3).toString()));
        stub.setCurFedTax(Float.parseFloat(report.getRow(row).getCell(5).toString()));
        stub.setYtdFedTax(Float.parseFloat(report.getRow(row).getCell(6).toString()));

        row += 1;

        //check both curOTEarning and ytdOT for empty string, replace with 0
        if (report.getRow(row).getCell(2).toString() == ""){
            stub.setCurOTEarnings(0);
        }
        else{
            stub.setCurOTEarnings(Float.parseFloat(report.getRow(row).getCell(2).toString()));
        }
        if (report.getRow(row).getCell(3).toString() == ""){
            stub.setYtdOT(0);
        }
        else{
            stub.setYtdOT(Float.parseFloat(report.getRow(row).getCell(3).toString()));
        }

        stub.setCurSocSec(Float.parseFloat(report.getRow(row).getCell(5).toString()));
        stub.setYtdSocSec(Float.parseFloat(report.getRow(row).getCell(6).toString()));

        row += 1;

        stub.setCurMedicare(Float.parseFloat(report.getRow(row).getCell(5).toString()));
        stub.setYtdMedicare(Float.parseFloat(report.getRow(row).getCell(6).toString()));

        row += 1;

        //check both curAddlMedi and ytdAddlMedi for empty string, replace with 0
        if (report.getRow(row).getCell(5).toString() == ""){
            stub.setCurAddlMedi(0);
        }
        else {
            stub.setCurAddlMedi(Float.parseFloat(report.getRow(row).getCell(5).toString()));
        }
        if (report.getRow(row).getCell(6).toString() == ""){
            stub.setYtdAddlMedi(0);
        }
        else{
            stub.setYtdAddlMedi(Float.parseFloat(report.getRow(row).getCell(6).toString()));
        }

        row += 1;

        stub.setCurCaTax(Float.parseFloat(report.getRow(row).getCell(5).toString()));
        stub.setYtdCaTax(Float.parseFloat(report.getRow(row).getCell(6).toString()));

        row += 1;

        stub.setCurCaDisability(Float.parseFloat(report.getRow(row).getCell(5).toString()));
        stub.setYtdCaDisability(Float.parseFloat(report.getRow(row).getCell(6).toString()));

        row += 1;

        stub.setCurNetEarnings((float)(report.getRow(row).getCell(8).getNumericCellValue()));
        stub.setYtdNetEarnings((float)(report.getRow(row).getCell(9).getNumericCellValue()));

        return stub;
    }


}
