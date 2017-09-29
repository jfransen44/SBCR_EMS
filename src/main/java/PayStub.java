/**
 * Created by jeremyfransen on 9/25/17.
 */
public class PayStub {

    private String empId;  //Sting to account for leading 0's
    private int checkNum;
    private float curRegEarnings;
    private float curOTEarnings;
    private float ytdRegEarnings;
    private float ytdOTEarnings;
    private float ytdFedTax;
    private float curFedTax;
    private float curSocSec;
    private float ytdSocSec;
    private float curMedicare;
    private float ytdMedicare;
    private float curAddlMedi;
    private float ytdAddlMedi;
    private float curCaTax;
    private float ytdCaTax;
    private float curCaDisability;
    private float ytdCaDisability;
    private float curNetEarnings;
    private float ytdNetEarnings;



    public PayStub(String empId){

        this.empId = empId;

    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public float getCurRegEarnings() {
        return curRegEarnings;
    }

    public void setCurRegEarnings(float curRegEarnings) {
        this.curRegEarnings = curRegEarnings;
    }

    public float getCurOTEarnings() {
        return curOTEarnings;
    }

    public void setCurOTEarnings(float curOTEarnings) {
        this.curOTEarnings = curOTEarnings;
    }

    public float getYtdRegEarnings() {
        return ytdRegEarnings;
    }

    public void setYtdRegEarnings(float ytdRegEarnings) {
        this.ytdRegEarnings = ytdRegEarnings;
    }

    public float getYtdOTEarnings() {
        return ytdOTEarnings;
    }

    public void setYtdOTEarnings(float ytdOTEarnings) {
        this.ytdOTEarnings = ytdOTEarnings;
    }

    public float getYtdFedTax() {
        return ytdFedTax;
    }

    public void setYtdFedTax(float ytdFedTax) {
        this.ytdFedTax = ytdFedTax;
    }

    public float getCurFedTax() {
        return curFedTax;
    }

    public void setCurFedTax(float curFedTax) {
        this.curFedTax = curFedTax;
    }

    public float getCurSocSec() {
        return curSocSec;
    }

    public void setCurSocSec(float curSocSec) {
        this.curSocSec = curSocSec;
    }

    public float getYtdSocSec() {
        return ytdSocSec;
    }

    public void setYtdSocSec(float ytdSocSec) {
        this.ytdSocSec = ytdSocSec;
    }

    public float getCurMedicare() {
        return curMedicare;
    }

    public void setCurMedicare(float curMedicare) {
        this.curMedicare = curMedicare;
    }

    public float getYtdMedicare() {
        return ytdMedicare;
    }

    public void setYtdMedicare(float ytdMedicare) {
        this.ytdMedicare = ytdMedicare;
    }

    public float getCurAddlMedi() {
        return curAddlMedi;
    }

    public void setCurAddlMedi(float curAddlMedi) {
        this.curAddlMedi = curAddlMedi;
    }

    public float getYtdAddlMedi() {
        return ytdAddlMedi;
    }

    public void setYtdAddlMedi(float ytdAddlMedi) {
        this.ytdAddlMedi = ytdAddlMedi;
    }

    public float getCurCaTax() {
        return curCaTax;
    }

    public void setCurCaTax(float curCaTax) {
        this.curCaTax = curCaTax;
    }

    public float getYtdCaTax() {
        return ytdCaTax;
    }

    public void setYtdCaTax(float ytdCaTax) {
        this.ytdCaTax = ytdCaTax;
    }

    public float getCurCaDisability() {
        return curCaDisability;
    }

    public void setCurCaDisability(float curCaDisability) {
        this.curCaDisability = curCaDisability;
    }

    public float getYtdCaDisability() {
        return ytdCaDisability;
    }

    public void setYtdCaDisability(float ytdCaDisability) {
        this.ytdCaDisability = ytdCaDisability;
    }

    public float getCurNetEarnings() {
        return curNetEarnings;
    }

    public void setCurNetEarnings(float curNetEarnings) {
        this.curNetEarnings = curNetEarnings;
    }

    public float getYtdNetEarnings() {
        return ytdNetEarnings;
    }

    public void setYtdNetEarnings(float ytdNetEarnings) {
        this.ytdNetEarnings = ytdNetEarnings;
    }
}
