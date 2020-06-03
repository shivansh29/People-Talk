package servlets;

import java.io.IOException;
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
public class SendMessage extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        
            try{
                HttpSession session=request.getSession();
            HashMap userdetails=(HashMap)session.getAttribute("userdetails");
            if(userdetails!=null){
                    String temail=request.getParameter("temail");
                    System.out.println(temail);
                    String semail=(String)userdetails.get("email");
                    String message=request.getParameter("message");
                    Part p=request.getPart("ufile");
                    java.io.InputStream in=null;
                    String fname="";
                    if(p!=null){
                        in=p.getInputStream();
                        fname=p.getSubmittedFileName();
                    }
                    db.DbConnection db=new db.DbConnection();
                    String s=db.sendmsg(semail, temail, message, fname, in);
                    System.out.println("hhhhhhhhhhhhhhuuuuuuu");
                    if(s.equalsIgnoreCase("done"))
                        session.setAttribute("msg","Message sent Successfully");
                    else
                        session.setAttribute("msg","message sent failed");
                    response.sendRedirect("talk.jsp?temail="+temail);
                }         
            else{
                session.setAttribute("msg","please login first");
                response.sendRedirect("home.jsp");
            } 
            }catch (Exception ex) {
                System.out.println("exception in sending message");
                ex.printStackTrace();
                }
            
    }

}
