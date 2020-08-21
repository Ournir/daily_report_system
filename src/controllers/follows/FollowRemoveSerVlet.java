package controllers.follows;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
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

        List<Follow> follows = em.createNamedQuery("getAllFollowsReports", Follow.class)
                .setParameter("employee",login_employee.getId())
                .getResultList();

        Iterator<Follow> iterator = follows.iterator();
        while (iterator.hasNext()) {
            Follow follow = iterator.next();
            if (follow.getFollowed().getId() == uid) {
                iterator.remove();
            }
        }

        em.getTransaction().begin();
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "フォロー解除しました。");

        response.sendRedirect(request.getContextPath() + "/index.html");
    }
}