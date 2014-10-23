package ua.bu.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by andriybas on 10/23/14.
 */
public class TestFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
