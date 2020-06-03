package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class Logout extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        HttpSession session=request.getSession();
        HashMap userdetails=(HashMap)session.getAttribute("userdetails");
        if(userdetails!=null){
            session.invalidate();
            response.sendRedirect("home.jsp");
        }
        else{
            session.setAttribute("msg","please login first");
            response.sendRedirect("home.jsp");
        }
    }

}
