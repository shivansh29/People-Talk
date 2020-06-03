package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class sendmail extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            HttpSession session=request.getSession();
            String e=request.getParameter("email");
            db.DbConnection db=new db.DbConnection();
            String p=db.getpassword(e);
            if(p!=null){
                final String SEmail="Your Mail";
                final String SPass="Your Password";
                final String REmail=e;
                final String Sub="Your Password is Here from PTALK!";
                final String Body="Your Email Id: "+e+" and Password: "+p;
                //mail send Code
            Properties props=new Properties();
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port","465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth","true");
            props.put("mail.smtp.port","465");
            Session ses=Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(SEmail,SPass);
                }
            }
            );
            Message message=new MimeMessage(ses);
            message.setFrom(new InternetAddress(SEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(REmail));
            message.setSubject(Sub);
            message.setContent(Body,"text/html" );
            Transport.send(message);
            session.setAttribute("msg","Mail Sent successfully.");
            response.sendRedirect("forgetpassword.jsp");
            }else{
                session.setAttribute("msg", "Wrong Emial ID");
                response.sendRedirect("forgetpassword.jsp");
            }
        }catch(Exception ex){
            
        }
        
    }

}
