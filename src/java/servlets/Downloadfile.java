package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Downloadfile extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         System.out.println("pid");
        try {
            
            String filename=request.getParameter("filename");
            String pid=request.getParameter("pid");
            db.DbConnection db=new db.DbConnection();
           
            byte b[]=db.getfile(Integer.parseInt(pid));
            if(b!=null){
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition","attachment; filename="+filename); 
                response.getOutputStream().write(b);
            }
        } catch (Exception ex) {
            System.out.println("exception in download file servlet");
            ex.printStackTrace();
        }
       
    }

}
