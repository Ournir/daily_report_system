package controllers.follows;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/follow")
public class FollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // フォローする（ログイン中の）ユーザーのEmployeeクラスを取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        // フォローされる側のユーザーのEmployeeクラスを取得
        // Employee e = (Employee)request.getAttribute("employee");
        Integer uid = Integer.valueOf(request.getParameter("uid"));

        if(login_employee.getId() != null){
        Follow f = new Follow();

        Employee followed = em.find(Employee.class, uid);

        f.setFollower(login_employee);
        f.setFollowed(followed);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        f.setCreated_at(currentTime);

        List<Report> reports_followed = em.createNamedQuery("getMyAllFollowsReports", Report.class)
                                   .setParameter("employee", login_employee)
                                   .getResultList();

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "フォローしました。");

        request.setAttribute("follow",f);

        request.setAttribute("reports_followed", reports_followed);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }
    }

}
