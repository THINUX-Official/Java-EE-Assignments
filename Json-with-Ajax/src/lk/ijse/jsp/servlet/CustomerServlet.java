package lk.ijse.jsp.servlet;

import lk.ijse.jsp.servlet.util.ResponseUtil;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static java.lang.Class.forName;


@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                String salary = rst.getString(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("id", id);
                objectBuilder.add("name", name);
                objectBuilder.add("address", address);
                objectBuilder.add("salary", salary);
                arrayBuilder.add(objectBuilder.build());
            }
            resp.setContentType("application/json");
            resp.getWriter().print(arrayBuilder.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        try {
            forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?)");

            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);
            pstm.setObject(4, cusSalary);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", "success");
                objectBuilder.add("message", "Successfully Added!");
                resp.setContentType("application/json");
                resp.getWriter().print(objectBuilder.build());

            } else {
                resp.getWriter().println("Error!");
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", "success");
            objectBuilder.add("message", e.getMessage());
            objectBuilder.add("data", e.getErrorCode());
            resp.setContentType("application/json");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Add Headers
//        resp.addHeader("Access-Control-Allow-Origin", "*");
//        resp.addHeader("Content-Type", "application/json");

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String cusID = jsonObject.getString("cusID");
//        String cusName = jsonObject.getString("cusName");
//        String cusAddress = jsonObject.getString("cusAddress");
//        String cusSalary = jsonObject.getString("cusSalary");

        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        try {
            forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("UPDATE customer set name=?,address=?, salary=?, where id=?");

            pstm.setObject(4, cusID);
            pstm.setObject(1, cusName);
            pstm.setObject(2, cusAddress);
            pstm.setObject(3, cusSalary);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Customer Updated..!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer Updated Failed..!"));
            }
        } catch (ClassNotFoundException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Add Headers
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        String cusID = req.getParameter("cusID");
        try {
            forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            pstm.setObject(1, cusID);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Customer Deleted..!"));
            } else {
                resp.getWriter().print(ResponseUtil.genJson("Failed", "Customer Delete Failed..!"));
            }
        } catch (ClassNotFoundException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT, DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "content-type");
    }
}
