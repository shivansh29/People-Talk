package servlets;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPhoto extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String e=request.getParameter("email");
        db.DbConnection db=new db.DbConnection();
        //System.out.println("getphoto");
        byte b[]=db.getphoto(e);
        if(b==null){
                FileInputStream fin=new FileInputStream("D:\\netbeans\\fullpeopletalk\\web\\img\\xyz.jpg");
                b=new byte[3500];
                fin.read(b);
        }
            response.getOutputStream().write(b);
        
        }catch(Exception ex){
            ex.printStackTrace();
                FileInputStream fin=new FileInputStream("D:\\netbeans\\fullpeopletalk\\web\\img\\xyz.jpg");
                byte[]b=new byte[3500];
                fin.read(b);
                response.getOutputStream().write(b);
        }
        
    }

}
