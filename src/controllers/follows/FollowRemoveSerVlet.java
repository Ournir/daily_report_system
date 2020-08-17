package controllers.follows;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;

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

        // データベース接続と結果取得のための変数
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.jdbc.Driver");

            // 2. DBと接続する
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/daily_report_system?useSSL=false",
                "root",
                "07141362barca"
            );// "password"の部分は，ご自身でrootユーザーに設定したものを記載してください。

            // 3. DBとやりとりする窓口（Statementオブジェクト）の作成
            stmt = con.createStatement();

            // 4, 5. Select文の実行と結果を格納／代入
            String qry = "DELETE * FROM follows WHERE follower_id = ? AND followed_id = ?";
            // ?の部分に入力値が挿入される。左から1, 2 と番号で指定する。
            PreparedStatement ps = con.prepareStatement(qry); // 問い合わせの準備
            ps.setInt(1, login_employee.getId()); // 1番目の?に入力を挿入。入力が文字列の場合。
            ps.setInt(2, uid); // 2番目の?に入力を挿入。入力が整数型の場合。
            rs = ps.executeQuery(); // SQL文を実行して結果をResultSetで受け取る


        } catch (SQLException e) {
            // DBとの処理で何らかのエラーがあった場合の例外
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            // JDBCドライバを読み込めないエラーがあった場合の例外
            e.printStackTrace();

        } finally {
            // 7. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/employees/index");
    }
}