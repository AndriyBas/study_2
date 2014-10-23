package ua.bu.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andriybas on 10/23/14.
 */
public class TestServlet extends HttpServlet {

    class User {
        final String name;
        final String password;

        User(String name, String password) {
            this.name = name;
            this.password = password;
        }
    }

    List<User> users = new ArrayList<User>();

    {
        users.add(new User("root", "root"));
        users.add(new User("user1", "root"));
        users.add(new User("user2", "root"));
        users.add(new User("user3", "root"));
        users.add(new User("user4", "root"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAction(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAction(request, response);
    }

    private void doAction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("hi");

        String u = request.getParameter("user");
        String pass = request.getParameter("password");

        if (isValidCredentials(u, pass)) {
            Cookie c = new Cookie("user", u);
            c.setMaxAge(60 * 15); // 15 min
//            HttpSession session = request.getSession();
            response.addCookie(c);
            response.sendRedirect("TestContent.jsp");

        }

    }

    private boolean isValidCredentials(String name, String password) {
        for (User u : users) {
            if (u.name.equals(name) && u.password.equals(password))
                return true;
        }
        return true;
    }
}
