package db;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {
    
    private PreparedStatement checkLogin,updatephoto,insertUser,getphoto,searchpeople,getpeoplebyemail,sendmsg,getmessages,getfile,getpassword,changepassword,checkpass,updateclient;
    private Connection con;
    private Statement st;
    public DbConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ptalk",
                "root","INCAPP");
        st=con.createStatement();
        insertUser=con.prepareStatement("insert into user_info values(?,?,?,?,?,?,?,?,?,?)");
        checkLogin=con.prepareStatement("select * from user_info where email=? and pass=?");
        getphoto=con.prepareStatement("select photo from user_info where email=?");
        searchpeople=con.prepareStatement("select name,email,phone,dob,gender from user_info where email!=? and state=?"+
                "and city=? and area like ?");
        getpeoplebyemail=con.prepareStatement("select * from user_info where email=? ");
        sendmsg=con.prepareStatement("insert into peoplemsg (sid,rid,msg,filename,ufile,udate) values(?,?,?,?,?,now())");
        getmessages=con.prepareStatement("select * from peoplemsg where sid=? and rid=?");
        getfile=con.prepareStatement("select * from peoplemsg where pid=? ");
        getpassword=con.prepareStatement("select pass from user_info where email=?");
        changepassword=con.prepareStatement("update user_info set pass=? where email=?");
        checkpass=con.prepareStatement("select pass from user_info where email=?");
        updateclient=con.prepareStatement("update user_info set name=?,phone=?,gender=?,dob=?,state=?,city=?,area=? where email=?");
        updatephoto=con.prepareStatement("update user_info set photo=? where email=?");
    }
    public String insertUser(HashMap userdetails) throws SQLException {
        try{insertUser.setString(1,(String)userdetails.get("email"));
        insertUser.setString(2,(String)userdetails.get("pass"));
        insertUser.setString(3,(String)userdetails.get("name"));
        insertUser.setString(4,(String)userdetails.get("phone"));
        insertUser.setString(5,(String)userdetails.get("gender"));
        insertUser.setDate(6,(java.sql.Date)userdetails.get("dob"));
        insertUser.setString(7,(String)userdetails.get("state"));
        insertUser.setString(8,(String)userdetails.get("city"));
        insertUser.setString(9,(String)userdetails.get("area"));
        insertUser.setBinaryStream(10,(InputStream)userdetails.get("photo"));
        int x=insertUser.executeUpdate();
        System.out.println(x);
        if(x!=0)
            return "Success";
        else
            return "failed";
        }catch(SQLIntegrityConstraintViolationException ex){
            return "Already";
        }
    }
    public HashMap checkLogin(String e,String p) throws SQLException{
        checkLogin.setString(1,e);
        checkLogin.setString(2,p);
        ResultSet rs=checkLogin.executeQuery();
        if(rs.next()){
        HashMap userdetails=new HashMap();
        userdetails.put("email",rs.getString("email"));
        userdetails.put("name",rs.getString("name"));
        userdetails.put("phone",rs.getString("phone"));
        userdetails.put("state",rs.getString("state"));
        userdetails.put("city",rs.getString("city"));
        userdetails.put("area",rs.getString("area"));
        userdetails.put("gender",rs.getString("gender"));
        userdetails.put("dob",rs.getDate("dob"));
        return userdetails;
        }else
        return null;
    }
    public byte[] getphoto(String e){
        try{
            getphoto.setString(1,e);
            ResultSet rs=getphoto.executeQuery();
            if(rs.next()){
                byte[] b=rs.getBytes("photo");
                if(b.length!=0)
                    return b;
                else
                    return null;
            }else
                return null;
        }catch(Exception ex){
            return null;
        }
    }
    public ArrayList searchpeople(String e,String st,String ct,String a) throws SQLException{
        searchpeople.setString(1,e);
        searchpeople.setString(2,st);
        searchpeople.setString(3,ct);
        searchpeople.setString(4,"%"+a+"%");
        ResultSet rs=searchpeople.executeQuery();
        ArrayList<HashMap> alluserdetails=new ArrayList();
        while(rs.next()){
            HashMap peopledetails=new HashMap();
            peopledetails.put("email",rs.getString("email"));
            peopledetails.put("name",rs.getString("name"));
            peopledetails.put("phone",rs.getString("phone"));
            peopledetails.put("email",rs.getString("email"));
            peopledetails.put("dob",rs.getDate("dob"));
            peopledetails.put("gender",rs.getString("gender"));
            alluserdetails.add(peopledetails);
        }
        return alluserdetails;
    }
    public HashMap getpeoplebyemail(String e) {
        try {
            getpeoplebyemail.setString(1,e);
            ResultSet rs=getpeoplebyemail.executeQuery();
            if(rs.next()){
                HashMap tuserdetails=new HashMap();
                tuserdetails.put("name",rs.getString("name"));
                tuserdetails.put("email",e);
                tuserdetails.put("gender",rs.getString("gender"));
                tuserdetails.put("phone",rs.getString("phone"));
                tuserdetails.put("state",rs.getString("state"));
                tuserdetails.put("city",rs.getString("city"));
                tuserdetails.put("area",rs.getString("area"));
                tuserdetails.put("dob",rs.getDate("dob"));
                return tuserdetails;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public String sendmsg(String semail,String temail,String message,String fname,InputStream in) throws SQLException{
        System.out.println(temail);
        sendmsg.setString(1,semail);
        sendmsg.setString(2,temail);
        sendmsg.setString(3,message);
        sendmsg.setString(4,fname);
        sendmsg.setBinaryStream(5,in);
        int x=sendmsg.executeUpdate();
        System.out.println(" hhhhhhh    ");
        if(x==1)
            return "done";
        else
            return "failed";
    }
    public ArrayList getmessages(String sid,String rid) throws SQLException{
        getmessages.setString(1,sid);
        getmessages.setString(2,rid);
        ResultSet rs=getmessages.executeQuery();
        ArrayList<HashMap> allmessages=new ArrayList();
        while(rs.next()){
            HashMap message=new HashMap();
            message.put("msg",rs.getString("msg"));
            message.put("filename",rs.getString("filename"));
            System.out.println(rs.getString("filename"));
            message.put("ufile",rs.getBytes("ufile"));
            message.put("udate",rs.getString("udate"));
            message.put("pid",rs.getString("pid"));
            allmessages.add(message);
        }
        return allmessages;
    }
    public byte[] getfile(int pid){
        try {
              System.out.println("file");
            getfile.setInt(1,pid);
            ResultSet rs=getfile.executeQuery();
            if(rs.next()){
              
                byte[] b=rs.getBytes("ufile");
                if(b.length!=0){
                
                    return b;
                }
                else
                    return null;
            }else
                return null;
        } catch (SQLException ex) {
         System.out.println("exception in dbconnectio getfile");
        return null;
        }
    
    }
    public String getpassword(String e){
        try {
            getpassword.setString(1,e);
            ResultSet rs=getpassword.executeQuery();
            if(rs.next())
                return rs.getString("pass");
            else
                return null;
        } catch (SQLException ex) {
            System.out.println("exception in get password in dbconnection");
            return null;
        }
    }
    public String changepassword(String e,String p,String np){
        try {
            checkpass.setString(1,e);
            String check="";
            ResultSet rs=checkpass.executeQuery();
            if(rs.next()){
                String s=rs.getString("pass");
                if(s.equals(p)){
                    changepassword.setString(1,np);
                    changepassword.setString(2,e);
                    int x=changepassword.executeUpdate();
                    if(x==1)
                        check="done";
                }else{
                    check="error";
                }
            }
            return check;
        } catch (SQLException ex) {
            System.out.println("exception in change password");
            return "error2";
        }
    }
    public String updateclient(String e,String n,String ph,String g,java.sql.Date d,String st,String ct,String ar){
        String check="";
        try {
            updateclient.setString(1,n);
            updateclient.setString(2,ph);
            updateclient.setString(3,g);
            updateclient.setDate(4,d);
            updateclient.setString(5,st);
            updateclient.setString(6,ct);
            updateclient.setString(7,ar);
            updateclient.setString(8,e);
            int x=updateclient.executeUpdate();
            System.out.println(e);
            if(x==1){
            System.out.println("check");
                check="done";}
            return check;
        } catch (SQLException ex) {
            System.out.println("exception in update client db");
            ex.printStackTrace();
            return "error";
        }
    }
    public String updatephoto(String e ,InputStream in){
        String s="";
        try{
            updatephoto.setBinaryStream(1,in);
            updatephoto.setString(2,e);
            int x=updatephoto.executeUpdate();
            if(x==1){
                s="updated";
            }
        }catch(Exception ex){
            s="error";
            System.out.println("exception in update photo database");
        }
        return s;
    }
}
