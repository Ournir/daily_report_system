package controllers.employees;

import java.io.IOException;
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
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        int page =1;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(NumberFormatException e){ }
        List<Employee> employees = em.createNamedQuery("getAllEmployees", Employee.class)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount", Long.class)
                .getSingleResult();

        List<Follow> follows = em.createNamedQuery("getAllFollowsReports", Follow.class)
                .setParameter("employee", login_employee.getId())
                .getResultList();

//        for (Employee employee : employees) {
//            employee.getId();
//            employee.setIsFollowed(false);
//            for (Follow follow : follows) {
//                if (follow.getFollowed().getId() == employee.getId()){
//                    employee.setIsFollowed(true);
//                    employees.add(employee);
//                }else{
//                    employees.add(employee);
//                }
//            }
//        }

        em.close();

        request.setAttribute("employees", employees);
        request.setAttribute("follows", follows);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);

        if(request.getSession().getAttribute("flush") != null){
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        request.setAttribute("loginId", login_employee.getId());
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }

}
