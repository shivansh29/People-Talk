package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
public class updateclient extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession(); 
        HashMap userdetails=(HashMap)session.getAttribute("userdetails");
        if(userdetails!=null){
            try {
                String e=(String)userdetails.get("email");
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
                db.DbConnection db=new db.DbConnection();
                String s=db.updateclient(e, n, ph, g, dt, st, ct, a);
                if(s.equalsIgnoreCase("done")){
                    session.setAttribute("msg","updated successfully");
                    response.sendRedirect("home.jsp");
                }
            } catch (Exception ex) {
                System.out.println("exception in update client servlet");
                ex.printStackTrace();
            }
            
        }else{
            session.setAttribute("msg","please login first");
            response.sendRedirect("home.jsp");
        }
    }

}
