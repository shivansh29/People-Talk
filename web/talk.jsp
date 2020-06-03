<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%
HashMap userdetails=(HashMap)session.getAttribute("userdetails");
if(userdetails!=null){
    String temail=request.getParameter("temail");
    db.DbConnection db=new db.DbConnection();
    HashMap tuserdetails=db.getpeoplebyemail(temail);
%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>PeopleTalk</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/talk.css" rel="stylesheet">

   
  </head>
  
  <body data-spy="scroll" data-target="#my-navbar">
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="profile.jsp">PeopleTalk</a>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><div class="navbar-text"><p>Welcome: <%=userdetails.get("name")%></p></div></li>
					<li><a href="profile.jsp">Home</a></li>
					<li><a href="home.jsp">Logout</a><li>
				</ul>			
			</div>
		</div>
	</nav><!-- end of navbar-->
	
	</br>
	</br>
		<div class="container">
			<div class="row">
				<div class="col-lg-4">
					<img src="GetPhoto?email=<%=tuserdetails.get("email")%>" width="120" height="150">
				</div>
				<div class="col-lg-4">
					<div class="form-group">
					</br>
						<label for="email" class="control-label">Name: <font color="grey"><%=tuserdetails.get("name")%></font></label><br>
						<label for="gender" class="control-label">Gender: <font color="grey"><%=tuserdetails.get("gender")%></font></label><br>
						<label for="phone" class="control-label">Phone: <font color="grey"><%=tuserdetails.get("phone")%></font></label><br>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="form-group">
					</br>
						<label for="name" class="control-label">Email:<font color="grey"> <%=tuserdetails.get("email")%></font></label><br>
						<label for="dob" class="control-label">Date of Birth: <font color="grey"><%=tuserdetails.get("dob")%></font></label><br>
						<label for="address" class="control-label">Address: <font color="grey"><%=tuserdetails.get("state")%>,<%=tuserdetails.get("city")%>,<%=tuserdetails.get("area")%></font></label><br>
					</div>
				</div>
			</div>
		</div>
		</br>
                      <%
            String msg=(String)session.getAttribute("msg");
            if(msg!=null)  
            {
        %>
        <div class="panel panel-warning">
            <div class="panel-heading text-center">
                <p><%=msg%></p>
            </div>
        </div>
        <%
            session.setAttribute("msg", null);
            }
        %>
		<div class="container text-center">
			<div class="panel panel-default">
				<div class="panel-body text-center">
					<form action="SendMessage" data-toggle="validator" method='post' enctype='multipart/form-data' class="form-horizontal">
						<div class="form-group">
							<label for="message" class="col-lg-2 control-label">Message:</label>
								<div class="col-lg-4">
									<textarea id="message" name="message" class="form-control" rows="5" cols="50" required></textarea>
								</div>
						</div><!--end form group-->
							<div class="form-group">
							<label for="filetosend" class="col-lg-2 control-label">File to Send:</label>
								<div class="col-lg-4">
									<input type="file" name="ufile" class="form-control" id="filetosend"/>
								</div>
								<div class="col-lg-2">
                                                                    <input type="hidden" name="temail" value="<%=temail%>"/>
									<button type="submit" class="btn btn-primary">Send</button>
								</div>
						</div><!--end form group-->
					</form>
				</div>
			</div>
		</div>
		<div class="container text-center">
			<div class="panel panel-default">
				<div class="panel-body text-center">
					<div class="row">
						<div class="col-lg-6">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h5><%=userdetails.get("name")%>'s Messages</h5>
								</div>
                                                                <%
                                                                ArrayList allmessages=db.getmessages((String)userdetails.get("email"),(String)tuserdetails.get("email"));
                                                                Iterator i=allmessages.iterator();
                                                                while(i.hasNext()){
                                                                    HashMap message=(HashMap)i.next();
                                                                
                                                                %>
								<div class="panel-body text-left">
									<p><%=message.get("msg")%></p>
                                                                        <p></p>
									<div class="row">
										
										<div>
											<div class="col-lg-2">
												<label for="message" class="control-label">File:<a href="Downloadfile?filename=<%=message.get("filename")%>&pid=<%=message.get("pid")%>"><%=message.get("filename")%></a></label>
											</div>
											<div class="col-lg-2">
												<label for="message" class="control-label">Date:<%=message.get("udate")%></label>
											</div>
										</div>
										
									</div>
									<hr>
								</div>
                                                                                        <%
                                                }
                                                %>
							</div>
						</div>
                                                
						<div class="col-lg-6">
							<div class="panel panel-default">
								<div class="panel-heading text-center">
									<h5><%=tuserdetails.get("name")%>'s Messages</h5>
								</div>
                                                                           <%
                                                                ArrayList alltmessages=db.getmessages((String)tuserdetails.get("email"),(String)userdetails.get("email"));
                                                                Iterator i2=alltmessages.iterator();
                                                                while(i2.hasNext()){
                                                                    HashMap message=(HashMap)i2.next();
                                                                
                                                                %>
								<div class="panel-body text-left">
									<p><%=message.get("msg")%></p>
									<div class="row">
										
											<div class="form-group">
												<div class="col-lg-2">
                                                                                                    <label for="message" class="control-label">File:<a href="Downloadfile?filename=<%=message.get("filename")%>&pid=<%=message.get("pid")%>"><%=message.get("filename")%></a> </label>
												</div>
												<div class="col-lg-2">
													<label for="message" class="control-label">Date:<%=message.get("udate")%></label>
												</div>
											</div>
					
									</div>
									<hr>
								</div>
                                                                <%
                                                                }
                                                                %>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	<hr>
	
	<!--footer-->
	
	<div class="navbar navbar-inverse navbar-fixed-bottom">
		<div class="container">
			<div class="navbar-text pull-left">
				<p>Design and Develop by Shivansh</p>
			</div>
		</div>
	</div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery-2.2.2.min.js"></script>
    <script src="js/validator.js"></script>
  </body>
</html>
<%
}else{
session.setAttribute("msg","please login first");
response.sendRedirect("home.jsp");
}%>