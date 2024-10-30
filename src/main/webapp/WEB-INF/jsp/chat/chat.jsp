<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="cl" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>${ chat.roomname }</title>
</head>
<body>
	<header class="bg-gray-800 shadow-md">
        <div class="px-4 py-4 flex justify-between items-center">
            <a href="${ cl }/"><h1 class="text-xl font-bold text-white">채팅 애플리케이션</h1></a>
            <nav>
                <c:choose>
                    <c:when test="${ empty user }">
                    	<ul class="flex space-x-6">
		                    <li><a href="${ cl }/" class="text-white">홈</a></li>
		                    <li><a href="${ cl }/signin" class="text-white">로그인</a></li>
		                    <li><a href="${ cl }/signup" class="text-white">회원가입</a></li>
		                </ul>
                    </c:when>
                    <c:otherwise>
                    	<ul class="flex space-x-6">
		                    <li><a href="${ cl }/" class="text-white">홈</a></li>
		                    <li><a href="${ cl }/profile/${ user.username }" class="text-white">${ user.username }님 프로필</a></li>
		                    <form action="${ cl }/logout" method="post" autocomplete="off">
		                    	<button type="submit" class="text-white">로그아웃</button>
		                    </form>
		                    <li><a href="${ cl }/create" class="text-white">방 만들기</a></li>
		                </ul>
                    </c:otherwise>
                </c:choose>
            </nav>
        </div>
    </header>
	
	<div class="flex h-[calc(100vh-60px)] bg-gray-100">
        <!-- Sidebar -->
        <div class="w-64 bg-gray-800 text-white p-4">
            <h2 class="text-xl font-bold mb-6">채널</h2>
            <ul class="space-y-3">
                <li class="p-2 hover:bg-gray-700 rounded">일반</li>
                <li class="p-2 hover:bg-gray-700 rounded">프로젝트</li>
                <li class="p-2 hover:bg-gray-700 rounded">이슈</li>
                <li class="p-2 hover:bg-gray-700 rounded">자유토론</li>
            </ul>
            <div class="mt-auto pt-6 border-t border-gray-700">
                <p class="text-2xl">CopyRight</p>
            </div>
        </div>
    
        <!-- Chat Area -->
        <div class="flex-1 flex flex-col">
            <!-- Chat Header -->
            <div class="bg-white border-b border-gray-300 p-4 flex items-center justify-between">
                <h2 class="text-lg font-semibold">${ chat.roomname } 채팅방</h2>
                <button class="text-gray-600 hover:text-gray-900">설정</button>
            </div>
            
            <!-- Chat Messages -->
			<div class="flex-1 overflow-y-auto p-4 space-y-4 bg-white">
				<c:forEach var="message" items="${ messages }">
				    <div class="flex items-start space-x-3">
				        <div>
				            <p class="text-sm font-semibold">${ message.username }</p> <!-- userId를 사용하여 이름을 가져오는 로직 추가 -->
				            <p class="bg-gray-200 p-3 rounded-lg max-w-xs">${ message.chattext }</p>
				            <p class="text-xs text-gray-500">${ fn:substring(message.dateTime, 0, 16) }</p> <!-- 타임스탬프 추가 -->
				        </div>
				    </div>
				</c:forEach>
			</div>
    
            <!-- Chat Input -->
            <div class="bg-gray-100 p-4 border-t border-gray-300">
                <form action="${ cl }/chatroom/${ chat.roomid }/insertValue" method="post" autocomplete="off" class="flex space-x-3">
                    <input type="hidden" name="roomid" value="${ chat.roomid }">
                    <input type="text" class="flex-1 p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500" name="chattext" id="chattext" placeholder="메시지를 입력하세요..." required="required">
                    <button class="bg-blue-500 text-white px-4 rounded hover:bg-blue-600 transition duration-200" type="submit">전송</button>
                </form>
            </div>
        </div>
    </div>
	
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>
</body>
</html>