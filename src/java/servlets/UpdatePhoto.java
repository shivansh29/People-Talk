package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
@MultipartConfig
public class UpdatePhoto extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session=request.getSession(); 
        try {
            //response.setContentType("text/html;charset=UTF-8");
        HashMap userdetails=(HashMap)session.getAttribute("userdetails");
        if(userdetails!=null){
            String e=(String)userdetails.get("email");
            Part photo=request.getPart("photo");
            InputStream in=photo.getInputStream();
            db.DbConnection db=new db.DbConnection(); 
            String s=db.updatephoto(e, in);
            if(s.equalsIgnoreCase("updated")){
                session.setAttribute("msg","updated successfully");
            response.sendRedirect("editprofile.jsp");}
            else{
                session.setAttribute("msg","updation failed");
            response.sendRedirect("editprofile.jsp");
            }
        }
        else{
            session.setAttribute("msg","please login first");
            response.sendRedirect("home.jsp");
        }
        }catch (Exception ex) {
            System.out.println("exception in update photo servlet");
            ex.printStackTrace();
        }
        
    }

}
