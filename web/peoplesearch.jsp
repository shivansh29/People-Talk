<%@page import="java.util.*"%>
<%
HashMap userdetails=(HashMap)session.getAttribute("userdetails");
if(userdetails!=null){
   ArrayList alluserdetails=(ArrayList)session.getAttribute("alluserdetails");
   HashMap address=(HashMap)session.getAttribute("address");
   if(alluserdetails!=null){
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
	</nav>
	<div class="container">
		<div class="panel panel-default text center">
			<div class="panel-heading text-center">
				<h3>Search Results for: <%=address.get("state")%>/<%=address.get("city")%>/<%=address.get("area")%></h3>
			</div>
		</div>
	</div>
	</br>
	</br>
		<div class="container">
			<section>
                            <%
                            Iterator i=alluserdetails.iterator();
                            while(i.hasNext())
                            {
                                HashMap peopledetail=(HashMap)i.next();
                            %>
			<div class="row">
				<div class="col-lg-3">
					<img src="GetPhoto?email=<%=peopledetail.get("email")%>" width="120" height="150" >
				</div>
				<div class="col-lg-7">
						<div class="form-group">
							<label for="email" class="control-label">Name: <font color="grey"><%=peopledetail.get("name")%></font></label><br>
							<label for="name" class="control-label">Email:<font color="grey"><%=peopledetail.get("email")%></font></label><br>
							<label for="gender" class="control-label">Gender: <font color="grey"><%=peopledetail.get("gender")%></font></label><br>
							<label for="dob" class="control-label">Date of Birth: <font color="grey"><%=peopledetail.get("dob")%></font></label><br>
							<label for="phone" class="control-label">Phone: <font color="grey"><%=peopledetail.get("phone")%></font></label><br>										
						
						</div>
				</div>
				<form action="talk.jsp" method="post" class="form-horizontal">
					<div class="col-lg-2">
						<div class="form-group">
						</br>
						</br>
                                                <input type="hidden" name="temail" value="<%=peopledetail.get("email")%>"/>
							<button type="search" class="btn btn-primary">Talk</button>
						</div>
					</div>
				</form>
			</div>
			<hr>
			<%
                        }
                        %>
		</section>
	</div>
	</br>
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
  </body>
</html>
<%}else{
   session.setAttribute("msg","search Again");
   response.sendRedirect("profile.jsp");
}
}else{
session.setAttribute("msg","please login first");
response.sendRedirect("home.jsp");
}%>