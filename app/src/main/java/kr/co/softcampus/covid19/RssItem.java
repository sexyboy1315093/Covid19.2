package kr.co.softcampus.covid19;

public class RssItem {

    private String accDefRate;
    private String accExamCnt;
    private String accExamCompCnt;

    public RssItem(){

    }

    public RssItem(String accDefRate, String accExamCnt, String accExamCompCnt){
        this.accDefRate = accDefRate;
        this.accExamCnt = accExamCnt;
        this.accExamCompCnt = accExamCompCnt;
    }

    public String getAccDefRate() {
        return accDefRate;
    }

    public void setAccDefRate(String accDefRate) {
        this.accDefRate = accDefRate;
    }

    public String getAccExamCnt() {
        return accExamCnt;
    }

    public void setAccExamCnt(String accExamCnt) {
        this.accExamCnt = accExamCnt;
    }

    public String getAccExamCompCnt() {
        return accExamCompCnt;
    }

    public void setAccExamCompCnt(String accExamCompCnt) {
        this.accExamCompCnt = accExamCompCnt;
    }
}
