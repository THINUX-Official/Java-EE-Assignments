package lk.ijse.jsp.servlet;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/pages/test")
public class HttpMethodsServlet extends HttpServlet {

//    Query String

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        String address = req.getParameter("address");
//        String salary = req.getParameter("salary");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String c_id = jsonObject.getString("id");
        String c_name = jsonObject.getString("name");
        String c_address = jsonObject.getString("address");
        String c_salary = jsonObject.getString("salary");


//        System.out.println("DO GET "+id+" "+name+" "+address+" "+salary);
        System.out.println("DO GET "+c_id+" "+c_name+" "+c_address+" "+c_salary);
    }


    //Query String
    //x-www-form-urlencoded
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        String address = req.getParameter("address");
//        String salary = req.getParameter("salary");

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String c_id = jsonObject.getString("id");
//        String c_name = jsonObject.getString("name");
//        String c_address = jsonObject.getString("address");
//        String c_salary = jsonObject.getString("salary");
//
//
//        System.out.println("DO POST "+c_id+" "+c_name+" "+c_address+" "+c_salary);

//        System.out.println("DO POST "+id+" "+name+" "+address+" "+salary);


        //how to read an JSON array
        JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonValues = reader.readArray();

        for (JsonValue jsonValue : jsonValues) {
            JsonObject jsonObject = jsonValue.asJsonObject();
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String address = jsonObject.getString("address");
            String salary = jsonObject.getString("salary");
            System.out.println(id+ ":"+ name +" "+address+" "+salary);
        }
    }

    //Query String
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        String address = req.getParameter("address");
//        String salary = req.getParameter("salary");
//
//        System.out.println("DO PUT "+id+" "+name+" "+address+" "+salary);

//        JsonReader reader = Json.createReader(req.getReader());
//        JsonObject jsonObject = reader.readObject();
//        String c_id = jsonObject.getString("id");
//        String c_name = jsonObject.getString("name");
//        String c_address = jsonObject.getString("address");
//        String c_salary = jsonObject.getString("salary");
//
//
////        System.out.println("DO GET "+id+" "+name+" "+address+" "+salary);
//        System.out.println("DO PUT "+c_id+" "+c_name+" "+c_address+" "+c_salary);



        //Read  more complex data
        JsonReader reader = Json.createReader(req.getReader());
        JsonArray jsonValues = reader.readArray();
        for (JsonValue jsonValue : jsonValues) {
            JsonObject jsonObject = jsonValue.asJsonObject();
            String oid = jsonObject.getString("oid");
            String date = jsonObject.getString("date");
            JsonArray orderDetails = jsonObject.getJsonArray("orderDetails");

            System.out.print(oid+ " : "+date);
            for (JsonValue orderDetail : orderDetails) {
                JsonObject odOb = orderDetail.asJsonObject();
                String code = odOb.getString("code");
                System.out.print(": "+code);
            }
            System.out.println();

        }

//        [
//    {
//        "oid": "OID001",
//        "date": "2023/08/20",
//        "orderDetails": [
//            {
//                "code": "I001"
//            },
//            {
//                "code": "I002"
//            }
//        ]
//    },
//    {
//        "oid": "OID002",
//        "date": "2023/08/20",
//        "orderDetails": [
//            {
//                "code": "I005"
//            },
//            {
//                "code": "I006"
//            }
//        ]
//    }
//]
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String id = req.getParameter("id");
//        String name = req.getParameter("name");
//        String address = req.getParameter("address");
//        String salary = req.getParameter("salary");
//
//        System.out.println("DO DELETE "+id+" "+name+" "+address+" "+salary);

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String c_id = jsonObject.getString("id");
        String c_name = jsonObject.getString("name");
        String c_address = jsonObject.getString("address");
        String c_salary = jsonObject.getString("salary");

        System.out.println("DO Delete "+c_id+" "+c_name+" "+c_address+" "+c_salary);
    }


}
