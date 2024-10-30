package com.e.d.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.e.d.model.entity.ChatMessageEntity;
import com.e.d.model.entity.ChatRoomEntity;
import com.e.d.model.entity.ChatUserEntity;
import com.e.d.model.repository.ChatMessageRepository;
import com.e.d.model.repository.ChatRoomRepository;
import com.e.d.model.repository.ChatUserRepository;
import com.e.d.model.repository.RoomMemberShipRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

	@Autowired
	private ChatRoomRepository roomRepository;
	
	@Autowired
	private ChatUserRepository userRepository;
	
	@Autowired
	private RoomMemberShipRepository memberShipRepository;
	
	@Autowired
	private ChatMessageRepository messageRepository;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("AllChatRoom", roomRepository.findAll());
		return "index";
	}
	
	@GetMapping("/signin")
	public String signIn() {
		return "user/signin";
	}
	
	@GetMapping("/signup")
	public String signUp() {
		return "user/signup";
	}
	
	@PostMapping("/signup")
	public String signupForm(@ModelAttribute ChatUserEntity entity, RedirectAttributes redirectAttributes) { 
	    try {
	        // dateTime이 설정되지 않았으면 현재 시간으로 설정
	        if (entity.getDateTime() == null) {
	            entity.setDateTime(LocalDateTime.now());
	        }

	        userRepository.save(entity);
	        return "redirect:/"; // 회원가입 성공 시 메인 페이지로 리다이렉트
	    } catch (Exception e) {
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("errorMessage", "회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
	        return "redirect:/signup"; // 오류 발생 시 회원가입 페이지로 리다이렉트
	    }
	}
	
	@PostMapping("/signin")
	public String signinForm(
	        @RequestParam String username,
	        @RequestParam String userpassword,
	        HttpSession session) {
	    // 사용자 이름으로 조회
	    List<ChatUserEntity> users = userRepository.findByUsername(username);
	    
	    // 사용자가 존재하고 비밀번호가 일치하는지 확인
	    if (!users.isEmpty() && users.get(0).getUserpassword().equals(userpassword)) {
	        ChatUserEntity user = users.get(0); // 첫 번째 사용자 선택
	        
	        user.setStatus("online");
	        userRepository.save(user);
	        
	        // 세션에 단일 사용자 객체와 상태 저장
	        session.setAttribute("user", user);
	        session.setAttribute("useStatus", "online");
	        
	        return "redirect:/";
	    }
	    
	    session.setAttribute("loginError", "Invalid username or password");
	    return "redirect:/signin";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
	    ChatUserEntity user = (ChatUserEntity) session.getAttribute("user");
	    
	    if (user != null) {
	        // 상태를 offline으로 변경하고 저장
	        user.setStatus("offline"); // 상태를 offline으로 변경
	        userRepository.save(user); // 상태 변경을 데이터베이스에 저장
	    }
	    
	    session.invalidate(); // 세션 무효화
	    return "redirect:/";
	}
	
	@GetMapping("/profile/{username}")
	public String getUserProfile(Model model, @PathVariable String username) {
	    List<ChatUserEntity> users = userRepository.findByUsername(username);
	    
	    if (!users.isEmpty()) {
	        ChatUserEntity userProfile = users.get(0);
	        model.addAttribute("userProfile", userProfile);
	        return "user/profile"; // 사용자 프로필 JSP로 이동
	    } else {
	        model.addAttribute("errorMessage", "사용자를 찾을 수 없습니다."); // 에러 메시지 추가
	        return "error/NoUserException";
	    }
	}
	
	@GetMapping("/create")
	public String create() {
		return "chat/create";
	}
	
	@PostMapping("/create/createChatRoom")
	public String createChatRoom(@ModelAttribute ChatRoomEntity entity) {
		roomRepository.save(entity);
		System.out.println(entity.getRoomname() + ", 시간 : " + entity.getDateTime());
		return "redirect:/";
	}
	
	@GetMapping("/chatroom/{roomid}")
	public String chatRoomMovement(HttpSession session, Model model, @PathVariable int roomid) {
	    // 세션에서 사용자 정보 가져오기
	    ChatUserEntity user = (ChatUserEntity) session.getAttribute("user");
	    
	    // 사용자 로그인 여부 확인
	    if (user == null) {
	        model.addAttribute("NotLoginUserAccess", "로그인을 하지 않으면 <br> 채팅을 이용할 수 없습니다.");
	        return "error/NotLoginUserAccess"; // 로그인하지 않은 경우 에러 페이지로 리다이렉트
	    }

	    // 주어진 roomid로 방 찾기
	    Optional<ChatRoomEntity> optionalRoom = roomRepository.findById(roomid);
	    
	    if (optionalRoom.isPresent()) {
	        ChatRoomEntity chatRoom = optionalRoom.get();
	        
	        // 해당 방의 메시지 목록을 messageRepository에서 가져오기
	        List<ChatMessageEntity> messages = messageRepository.findByRoomid(roomid);

	        // 모델에 방 정보와 메시지 추가
	        model.addAttribute("chat", chatRoom); // 채팅방 정보 추가
	        model.addAttribute("messages", messages); // 해당 방의 메시지 목록 추가
	        
	        System.out.printf("'%d' 번 방에 '%s' 이라는 이름을 가진 유저가 입장했습니다.%n", roomid, user.getUsername());
	        return "chat/chat"; // 채팅 JSP 페이지로 이동
	    } else {
	        model.addAttribute("NullChatRoomIndex", "존재하지 않는 채팅방입니다.");
	        return "error/NullChatRoomIndex"; // 방이 존재하지 않는 경우 에러 페이지로 리다이렉트
	    }
	}

	@PostMapping("/chatroom/{roomid}/insertValue")
	public String insertChatMessage(
	        @PathVariable int roomid,
	        @RequestParam String chattext,
	        HttpSession session,
	        Model model) {

	    // 사용자 세션 확인
	    ChatUserEntity user = (ChatUserEntity) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/signin"; // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
	    }

	    // 메시지 엔티티 생성
	    ChatMessageEntity chatMessage = new ChatMessageEntity();
	    chatMessage.setRoomid(roomid); // 방 ID 설정
	    chatMessage.setUserid(user.getUserid()); // 현재 사용자 ID를 세팅
	    chatMessage.setChattext(chattext); // 입력된 채팅 메시지 설정
	    chatMessage.setDateTime(LocalDateTime.now()); // 현재 시간으로 설정
	    chatMessage.setUsername(user.getUsername());

	    messageRepository.save(chatMessage); // 메시지 저장

	    // 방의 모든 메시지 가져오기
	    List<ChatMessageEntity> messages = messageRepository.findByRoomid(roomid);
	    model.addAttribute("messages", messages); // 모델에 메시지 추가

	    // 다시 채팅방으로 이동
	    return "redirect:/chatroom/" + roomid; // JSP 파일 이름
	}

	
	
	
	
	
}
