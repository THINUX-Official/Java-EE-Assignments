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


@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //Add Headers
            resp.addHeader("Access-Control-Allow-Origin", "*");
            resp.addHeader("Content-Type", "application/json");

            //Initializing connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM customer");
            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder allCustomers = Json.createArrayBuilder();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);

                JsonObjectBuilder customerObject = Json.createObjectBuilder();
                customerObject.add("id", id);
                customerObject.add("name", name);
                customerObject.add("address", address);
                allCustomers.add(customerObject.build());
            }
            //create the response Object
            resp.getWriter().print(ResponseUtil.genJson("Success", "Loaded", allCustomers.build()));
        } catch (ClassNotFoundException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        } catch (SQLException e) {
            resp.setStatus(500);
            resp.getWriter().print(ResponseUtil.genJson("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");

        //Add Headers
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
            pstm.setObject(1, cusID);
            pstm.setObject(2, cusName);
            pstm.setObject(3, cusAddress);
            pstm.setObject(3, cusSalary);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Successfully Added.!"));
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Add Headers
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String cusID = jsonObject.getString("cusID");
        String cusName = jsonObject.getString("cusName");
        String cusAddress = jsonObject.getString("cusAddress");
        String salary = jsonObject.getString("cusSalary");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("UPDATE customer set name=?,address=?, salary=?, where id=?");
            pstm.setObject(3, cusID);
            pstm.setObject(1, cusName);
            pstm.setObject(2, cusAddress);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Customer Updated..!"));
            }else{
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
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("DELETE FROM customer WHERE id=?");
            pstm.setObject(1, cusID);

            if (pstm.executeUpdate() > 0) {
                resp.getWriter().print(ResponseUtil.genJson("Success", "Customer Deleted..!"));
            }else{
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
