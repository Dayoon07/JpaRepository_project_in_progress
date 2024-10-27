<%@page import="com.e.d.model.entity.ChatUserEntity"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="cl" value="${ pageContext.request.contextPath }" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>프로필 | ${ userProfile.username }</title>
	<style>
		 .modal {
            display: none; /* 기본적으로 모달을 숨김 */
        }
        .modal.flex {
            display: flex; /* flex로 설정하여 중앙 정렬 */
        }
        .hidden {
            display: none; /* 숨김 클래스 */
        }
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
	
	
	<c:if test="${ not empty user && userProfile.userid == user.userid }">
		<div class="mx-auto max-w-2xl w-full bg-white p-6 rounded-lg shadow-2xl" style="margin-top: 100px;">
	        <div class="flex items-center mb-4 border-b">
	            <!-- <img src="https://via.placeholder.com/100" alt="프로필 사진" class="w-24 h-24 rounded-full border-2 border-blue-500"> -->
	            <div class="mb-3 flex">
	                <h2 class="text-3xl font-bold text-gray-800 mr-5">사용자 이름</h2>
	                <span class="text-3xl font-bolg text-gray-600">${ userProfile.username }</span>
	            </div>
	        </div>
	        <div class="mb-4">
	            <h3 class="text-2xl font-semibold text-gray-800">프로필 설명</h3>
	            <p class="text-2xl text-gray-700 mt-1">${ userProfile.userbio }</p>
	        </div>
	        <div class="mb-4">
	            <h3 class="text-xl font-semibold text-gray-800">연락처 정보</h3>
	            <ul class="list-disc list-inside text-xl text-gray-700">
	                <li>이메일: ${ userProfile.useremail }</li>
	                <li>전화번호: ${ userProfile.userphone }</li>
	            </ul>
	        </div>
	        <div class="flex justify-between mt-6">
	            <button class="bg-blue-500 text-white px-4 py-2 rounded transition duration-200">정보 수정</button>
	            <button id="openModal" class="bg-red-500 text-white p-2 px-4 rounded transition duration-200 ">
			        계정 삭제
			    </button>
			    <div id="myModal" class="modal fixed inset-0 bg-black bg-opacity-50 items-center justify-center">
			        <div class="bg-white rounded-lg shadow-lg w-96 mx-auto p-6">
			            <h2 class="text-xl font-bold mb-4">모달 제목</h2>
			            <p class="mb-4">여기에 모달 내용을 입력하세요.</p>
			            <button id="closeModal" class="bg-blue-500 text-white p-2 px-4 rounded outline hover:outline-offset-2 transition duration-200">
			                취소하기
			            </button>
			            <button class="bg-red-500 text-white p-2 rounded hover:bg-red-600 transition duration-200">
			                계정 삭제하기
			            </button>
			        </div>
			    </div>
	        </div>
	    </div>
	</c:if>

	<c:if test="${ not empty userProfile && empty user }">
	    <div class="mx-auto max-w-2xl w-full bg-white p-6 rounded-lg shadow-2xl" style="margin-top: 100px;">
	        <div class="flex items-center mb-4 border-b">
	            <!-- <img src="https://via.placeholder.com/100" alt="프로필 사진" class="w-24 h-24 rounded-full border-2 border-blue-500"> -->
	            <div class="mb-3 flex">
	                <h2 class="text-3xl font-bold text-gray-800 mr-5">사용자 이름</h2>
	                <span class="text-3xl font-bolg text-gray-600">${ userProfile.username }</span>
	            </div>
	        </div>
	        <div class="mb-4">
	            <h3 class="text-2xl font-semibold text-gray-800">프로필 설명</h3>
	            <p class="text-2xl text-gray-700 mt-1">${ userProfile.userbio }</p>
	        </div>
	        <div class="mb-4">
	            <h3 class="text-xl font-semibold text-gray-800">연락처 정보</h3>
	            <ul class="list-disc list-inside text-xl text-gray-700">
	                <li>이메일: ${ userProfile.useremail }</li>
	                <li>전화번호: ${ userProfile.userphone }</li>
	            </ul>
	        </div>
	    </div>
	</c:if>
	<c:if test="${ empty userProfile }">
    	<p>사용자를 찾을 수 없습니다.</p>
	</c:if>
	
	<script>
		const modal = document.getElementById('myModal');
	    const openModalButton = document.getElementById('openModal');
	    const closeModalButton = document.getElementById('closeModal');
	
	    // 모달 열기
	    openModalButton.addEventListener('click', () => {
	        modal.classList.remove('hidden'); // 'hidden' 클래스를 제거하여 모달을 표시
	        modal.classList.add('flex'); // 모달을 flex로 설정하여 중앙 정렬
	    });
	
	    // 모달 닫기
	    closeModalButton.addEventListener('click', () => {
	        modal.classList.add('hidden'); // 'hidden' 클래스를 추가하여 모달을 숨김
	        modal.classList.remove('flex'); // 모달의 flex 속성 제거
	    });
	
	    // 모달 외부 클릭 시 닫기
	    modal.addEventListener('click', (e) => {
	        if (e.target === modal) { // 클릭한 요소가 모달인 경우
	            modal.classList.add('hidden');
	            modal.classList.remove('flex');
	        }
	    });
	</script>
	
	<jsp:include page="/WEB-INF/common/footer.jsp"></jsp:include>
</body>
</html>