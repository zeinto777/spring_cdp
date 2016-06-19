<%@page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<c:if test="${not empty ticket}">
    <h1>Ticket</h1>
       <p>Place: ${ticket.place}</p>
       <p>Event_ID: ${ticket.eventId}</p>
       <p>User_ID: ${ticket.userId}</p>
       <p>Category_ID: ${ticket.category}</p>
</c:if>

<c:if test="${not empty tickets}">
<h1>Tickets</h1>
    <c:forEach var="ticket" items="${tickets}">
         <p>Place: ${ticket.place}</p>
         <p>Event_ID: ${ticket.eventId}</p>
         <p>User_ID: ${ticket.userId}</p>
         <p>Category_ID: ${ticket.category}</p>
         <br>
    </c:forEach>
</c:if>
</body>
</html>