package lk.ijse.jsp.servlet;

import lk.ijse.jsp.dto.ItemDTO;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/pages/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("select * from Item");

            ResultSet rst = pstm.executeQuery();

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (rst.next()) {
                String code = rst.getString(1);
                String name = rst.getString(2);
                int qtyOnHand = rst.getInt(3);
                double unitPrice = rst.getDouble(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code", code);
                objectBuilder.add("description", name);
                objectBuilder.add("qty", qtyOnHand);
                objectBuilder.add("unitPrice", unitPrice);
                arrayBuilder.add(objectBuilder.build());
            }

            resp.setContentType("application/json");
            resp.getWriter().print(arrayBuilder.build());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        String name = req.getParameter("description");
        String qtyOnHand = req.getParameter("qty");
        String unitPrice = req.getParameter("unitPrice");

        String option = req.getParameter("option");
//
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_one", "root", "1234");

            switch (option) {
                case "add":
                    PreparedStatement pstm = connection.prepareStatement("insert into Item values(?,?,?,?)");

                    pstm.setObject(1, code);
                    pstm.setObject(2, name);
                    pstm.setObject(3, qtyOnHand);
                    pstm.setObject(4, unitPrice);

                    if (pstm.executeUpdate() > 0) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("message", "Item Added!");
                        resp.setContentType("application/json");
                        resp.getWriter().print(objectBuilder.build());
                    } else {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("message", "Item Addition Failed!");
                        resp.setContentType("application/json");
                        resp.setStatus(400);
                        resp.getWriter().print(objectBuilder.build());
                    }

                    break;

            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        } catch (SQLException e) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("error", e.getMessage());
            resp.setContentType("application/json");
            resp.setStatus(400);
            resp.getWriter().print(objectBuilder.build());
        }
    }
}
