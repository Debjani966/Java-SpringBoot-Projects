<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View ToDo Item List</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <style>
        a {
            color: white;
        }
        a:hover {
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="p-3">ToDo Item List</h1>
    <form:form>
        <table class="table table-bordered">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Date</th>
                <th>Status</th>
                <th>Mark Completed</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="todo" items="${list}">
                <tr>
                    <td>${todo.id}</td>
                    <td>${todo.title}</td>
                    <td>${todo.date}</td>
                    <td>${todo.status}</td>
                    <td><a class="btn btn-success" href="/updateToDoStatus/${todo.id}">Mark Complete</a></td>
                    <td><a class="btn btn-primary" href="/editToDoItem/${todo.id}">Edit</a></td>
                    <td><a class="btn btn-danger" href="/deleteToDoItem/${todo.id}">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </form:form>
    <button type="button" class="btn btn-primary btn-block">
        <a href="/addToDoItem">Add New ToDo Item</a>
    </button>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
<script>
    $(function() {
        var msg = "${message}";
        if (msg === "Save Success") {
            toastr.success("Item added successfully!!");
        } else if (msg === "Delete Success") {
            toastr.success("Item deleted successfully!!");
        } else if (msg === "Delete Failure") {
            toastr.error("Some error occurred, couldn't delete item");
        } else if (msg === "Edit Success") {
            toastr.success("Item updated successfully!!");
        }
    });
</script>
</body>
</html>
