package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
@MultipartConfig
public class Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession(); 
        try{
            String e=request.getParameter("email");
            String p=request.getParameter("password");
            String n=request.getParameter("name");
            String ph=request.getParameter("phone");
            String g=request.getParameter("gender");
            String d=request.getParameter("dob");
            SimpleDateFormat sd=new SimpleDateFormat("dd/mm/yyyy");
            java.util.Date sdt=sd.parse(d);
            java.sql.Date dt=new java.sql.Date(sdt.getTime());
            String st=request.getParameter("state");
            String ct=request.getParameter("city");
            String a=request.getParameter("area");
            Part photo=request.getPart("photo");
            InputStream in=null;
            if(photo!=null)
                in=photo.getInputStream();
            HashMap userdetails=new HashMap();
            userdetails.put("email",e);
            userdetails.put("pass",p);
            userdetails.put("name",n);
            userdetails.put("phone",ph);
            userdetails.put("gender",g);
            userdetails.put("dob",dt);
            userdetails.put("state",st);
            userdetails.put("city",ct);
            userdetails.put("area",a);
            userdetails.put("photo",in);
            db.DbConnection db=new db.DbConnection();
            String s=db.insertUser(userdetails);
            if(s.equalsIgnoreCase("success")){
                userdetails.remove("pass");
                userdetails.remove("photo");
                session.setAttribute("userdetails",userdetails);
                response.sendRedirect("profile.jsp");
            }else if(s.equalsIgnoreCase("failed")){
                session.setAttribute("msg","Registration Failed");
                response.sendRedirect("home.jsp");
            }else{
                session.setAttribute("msg","Email Already Exists");
                response.sendRedirect("home.jsp");
            }
        }catch(Exception e){
                session.setAttribute("msg","Registration Failed"+e);
                response.sendRedirect("home.jsp");
        }
    }
}
