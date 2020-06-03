package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class change extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        HashMap userdetails =(HashMap)session.getAttribute("userdetails");
        if(userdetails!=null){
        String p=request.getParameter("password");
        String np=request.getParameter("newpassword");
        String cp=request.getParameter("confirmpassword");
        String e=(String)userdetails.get("email");
        if(np.equals(cp)){
            try {
                db.DbConnection db=new db.DbConnection(); 
                String s=db.changepassword(e, p, np);
                if(s.equalsIgnoreCase("error")){
                    session.setAttribute("msg","enter valid right password");
                    response.sendRedirect("changepassword.jsp");
                }else if(s.equalsIgnoreCase("done")){
                    response.sendRedirect("home.jsp");
                }
                        } catch (Exception ex) {
                           System.out.println("exception in change servlet");
                           ex.getMessage();
                           ex.printStackTrace();
            }
        }else{
            session.setAttribute("msg","new password and confirm password don't match");
            response.sendRedirect("changepassword.jsp");
        }
    }else{
session.setAttribute("msg","please login first");
response.sendRedirect("home.jsp");
        }
    }

}
