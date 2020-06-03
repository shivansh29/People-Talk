package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class Login extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
       // response.setContentType("text/html;charset=UTF-8");
    try{
       String e=request.getParameter("email");
    String p=request.getParameter("password");
    db.DbConnection db=new db.DbConnection();
    HashMap userdetails=db.checkLogin(e, p);
    if(userdetails!=null){
        session.setAttribute("userdetails",userdetails);
        response.sendRedirect("profile.jsp");
    }
    else{
        session.setAttribute("msg","Wrong Entries");
        response.sendRedirect("home.jsp");
    }
    }catch(Exception ex){
        session.setAttribute("msg","Login Failed"+ex);
        response.sendRedirect("home.jsp");
    }}

}
