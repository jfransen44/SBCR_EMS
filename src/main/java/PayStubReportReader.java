import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void gatherStubs(String filePath){
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
        try{
            postToDB(payStubList);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    private PayStub makePayStub(String empID, int row, Sheet report){
            /*
                 MAP OF PAYSTUB
                 2          3          4          6          7          8             9
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
        stub.setYtdRegEarnings(Float.parseFloat(report.getRow(row).getCell(3).toString()));
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
            stub.setYtdOTEarnings(0);
        }
        else{
            stub.setYtdOTEarnings(Float.parseFloat(report.getRow(row).getCell(3).toString()));
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

        row += 2;

        stub.setCurNetEarnings((float)(report.getRow(row).getCell(8).getNumericCellValue()));
        stub.setYtdNetEarnings((float)(report.getRow(row).getCell(9).getNumericCellValue()));

        return stub;
    }

    private void postToDB(List<PayStub> payStubList) throws SQLException{
        Connection conn = DBConnect.getConnection();
        String sql = "INSERT INTO payStubs VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (int i = 0; i < payStubList.size(); i++){
            PayStub stub = payStubList.get(i);
            ps.setString(1, stub.getEmpId());
            ps.setInt(2, stub.getCheckNum());
            ps.setFloat(3, stub.getCurRegEarnings());
            ps.setFloat(4, stub.getYtdRegEarnings());
            ps.setFloat(5, stub.getCurFedTax());
            ps.setFloat(6, stub.getYtdFedTax());
            ps.setFloat(7, stub.getCurOTEarnings());
            ps.setFloat(8, stub.getYtdOTEarnings());
            ps.setFloat(9, stub.getCurSocSec());
            ps.setFloat(10, stub.getYtdSocSec());
            ps.setFloat(11, stub.getCurMedicare());
            ps.setFloat(12, stub.getYtdMedicare());
            ps.setFloat(13, stub.getCurAddlMedi());
            ps.setFloat(14, stub.getYtdAddlMedi());
            ps.setFloat(15, stub.getCurCaTax());
            ps.setFloat(16, stub.getYtdCaTax());
            ps.setFloat(17, stub.getCurCaDisability());
            ps.setFloat(18, stub.getYtdCaDisability());
            ps.setFloat(19, stub.getCurNetEarnings());
            ps.setFloat(20, stub.getYtdNetEarnings());

            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        conn.close();
        System.out.println("SUCCESS");
    }


}
