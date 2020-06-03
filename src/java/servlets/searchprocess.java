package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class searchprocess extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session=request.getSession();
            HashMap userdetails=(HashMap)session.getAttribute("userdetails");
            if(userdetails!=null){
                //response.setContentType("text/html;charset=UTF-8");
            String st=request.getParameter("state");
            String ct=request.getParameter("city");
            String a=request.getParameter("area");
            db.DbConnection db=new db.DbConnection();
            ArrayList alluserdetails=db.searchpeople((String)userdetails.get("email"), st, ct, a);
            if(!alluserdetails.isEmpty()){
                HashMap address=new HashMap();
                address.put("state",st);
                address.put("city",ct);
                address.put("area",a);
                session.setAttribute("address", address);
                session.setAttribute("alluserdetails",alluserdetails);
                response.sendRedirect("peoplesearch.jsp");
            }else{
                session.setAttribute("msg","no data found");
                response.sendRedirect("profile.jsp");
            }
            }else{
            session.setAttribute("msg", "Plz login First!");
            response.sendRedirect("home.jsp");
            }
        } catch (Exception ex) {
           System.out.println("exception in searchprocess");
           ex.printStackTrace();
        }
    }

}
