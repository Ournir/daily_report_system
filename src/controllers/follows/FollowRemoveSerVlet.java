package controllers.follows;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class FollowRemoveSerVlet
 */
@WebServlet("/followremove")
public class FollowRemoveSerVlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowRemoveSerVlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Employee login_employee =(Employee)request.getSession().getAttribute("login_employee");
        Integer uid = Integer.valueOf(request.getParameter("uid"));

        EntityManager em = DBUtil.createEntityManager();

        int deleted = em.createQuery("DELETE FROM Follow f"
                                    +"WHERE f.follower.id = ?1"
                                    +"AND f.followed.id = ?2")
                    .setParameter(1, login_employee.getId())
                    .setParameter(2, uid)
                    .executeUpdate();

        em.close();

        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}