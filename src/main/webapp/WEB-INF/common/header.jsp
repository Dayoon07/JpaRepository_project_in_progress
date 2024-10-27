<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="cl" value="${ pageContext.request.contextPath }" />
	<header class="bg-white shadow-md">
        <div class="max-w-7xl mx-auto px-4 py-4 flex justify-between items-center">
            <a href="${ cl }/"><h1 class="text-xl font-bold text-gray-800">채팅 애플리케이션</h1></a>
            <nav>
                <c:if test="${ empty user }">
                	<ul class="flex space-x-6">
	                    <li><a href="${ cl }/" class="text-gray-600 hover:text-blue-500">홈</a></li>
	                    <li><a href="${ cl }/signin" class="text-gray-600 hover:text-blue-500">로그인</a></li>
	                    <li><a href="${ cl }/signup" class="text-gray-600 hover:text-blue-500">회원가입</a></li>
	                </ul>
                </c:if>
                <c:if test="${ not empty user }">
                	<ul class="flex space-x-6">
	                    <li><a href="${ cl }/" class="text-gray-600 hover:text-blue-500">홈</a></li>
	                    <li><a href="${ cl }/profile/${ user.username }" class="text-gray-600 hover:text-blue-500">${ user.username }님 프로필</a></li>
	                    <form action="${ cl }/logout" method="post" autocomplete="off">
	                    	<button type="submit" class="text-gray-600 hover:text blue-500">로그아웃</button>
	                    </form>
	                    <li><a href="${ cl }/create" class="text-gray-600 hover:text-blue-500">방 만들기</a></li>
	                </ul>
                </c:if>
            </nav>
        </div>
    </header>