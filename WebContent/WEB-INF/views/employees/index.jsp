<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員　一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>フォロー</th>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="employee" items="${employees}" varStatus="status">
                    <sql:setDataSource
                        var = "db" url = "jdbc:mysql://localhost/daily_report_system?useSSL=false&useUnicode=true&characterEncoding=utf8"
                        user = "repuser" password = "reppass" />
                        <sql:query sql= "SELECT * FROM follows WHERE follower_id = ? AND followed_id = ?" var= "rs" dataSource= "${db}" >
                            <sql:param value="${loginId}" />
                            <sql:param value="${employee.id}"/>
                    </sql:query>
                    <tr class="row${status.count % 2}">
                    <c:choose>
                        <c:when test="${rs == null}">
                            <td class="follow"><a href="<c:url value='/follow' />?uid=${employee.id} ">フォロー</a></td>
                        </c:when>
                        <c:otherwise>
                            <td class="follow"><a href="<c:url value='/followremove' />?uid=${employee.id} ">フォロー解除</a></td>
                        </c:otherwise>
                    </c:choose>
                        <td><c:out value="${employee.code}" /></td>
                        <td><c:out value="${employee.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${employee.delete_flag == 1}">
                                    (削除済み)
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${employee.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全 ${employees_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((employees_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>

    </c:param>
</c:import>