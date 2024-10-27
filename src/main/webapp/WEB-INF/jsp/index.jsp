<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="cl" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>채팅 애플리케이션</title>
</head>
<body>

	<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>

	<c:if test="${ not empty AllChatRoom }">
	    <div class="mx-auto max-w-screen-sm w-full bg-white border border-gray-300 mt-5 shadow-md" style="max-height: 700px; overflow-y: auto;">
			<ul role="list" class="divide-y divide-blue-200 px-5">
	        	<c:forEach var="room" items="${ AllChatRoom }">
	            	<a href="${ cl }/chatroom/${ room.roomid }" class="flex justify-between gap-x-6 py-5 border-b-1">
	                	<div class="flex min-w-0 gap-x-4">
	                    	<div class="min-w-0 flex-auto">
	                        	<p class="text-sm font-semibold leading-6 text-gray-900">${ room.roomname }</p>
	                            <p class="mt-1 truncate text-xs leading-5 text-gray-500">${ room.roomnameinname }</p>
							</div>
						</div>
					</a>
	        	</c:forEach>
	        </ul>
	    </div>
	</c:if>
	<c:if test="${ empty AllChatRoom }">
		<h2 class="text-center text-3xl m-5">아무런 방도 있지 않음</h2>
	</c:if>
	
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>

</body>
</html>