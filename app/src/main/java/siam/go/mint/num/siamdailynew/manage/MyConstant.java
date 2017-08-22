package siam.go.mint.num.siamdailynew.manage;

/**
 * Created by masterung on 8/22/2017 AD.
 */

public class MyConstant {

    //For URL
    private String urlfecdep = "http://gopraew.com/Android/getAllData.php";
    private String urlAddMember = "http://gopraew.com/Android/addMember.php";
    private String urlGetAllMember = "http://gopraew.com/Android/getAllMember.php";

    //For Other
    private String[] columnMemberStrings = new String[]{
            "m_name",
            "m_surname",
            "m_gender",
            "m_email",
            "m_username",
            "m_password",
            "f_id"};


    public String getUrlGetAllMember() {
        return urlGetAllMember;
    }

    public String[] getColumnMemberStrings() {
        return columnMemberStrings;
    }

    public String getUrlAddMember() {
        return urlAddMember;
    }

    public String getUrlfecdep() {
        return urlfecdep;
    }
}   // Main Class
