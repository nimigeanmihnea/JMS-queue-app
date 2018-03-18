package servlet;

import entity.Dvd;
import producer.Producer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/DvdServlet")
public class DvdServlet extends HttpServlet {

    public DvdServlet(){
        super();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html");
        PrintWriter printWriter = null;

        String title = request.getParameter("title");
        int year = Integer.parseInt(request.getParameter("year"));
        double price = Double.parseDouble(request.getParameter("price"));

        try {
            printWriter = response.getWriter();
            Producer producer = new Producer("queue");
            HashMap message = new HashMap();
            message.put("DVD", new Dvd(title, year, price));
            producer.sendMessage(message);
            printWriter.print("DVD added");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }
    }
}
