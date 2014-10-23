package ua.bu.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by andriybas on 10/16/14.
 */
public class MathServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Hi");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Hi");

        HttpSession session = request.getSession();

        boolean isNew = session.isNew();
        // log out
        session.invalidate();


        Cookie c = new Cookie("name", "value");
        response.addCookie(c);

        Cookie[] cs = request.getCookies();
        for (Cookie co : cs) {

        }
    }

    class MyFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        }

        @Override
        public void destroy() {

        }
    }
}
