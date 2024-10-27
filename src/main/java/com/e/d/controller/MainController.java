package com.e.d.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.e.d.model.entity.ChatRoomEntity;
import com.e.d.model.entity.ChatUserEntity;
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
	public String signupForm(@ModelAttribute ChatUserEntity entity) { 
		try {
			userRepository.save(entity);
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
	}
	
	@PostMapping("/signin")
	public String signinForm(
	        @RequestParam String username,
	        @RequestParam String userpassword,
	        HttpSession session) {
	    Optional<ChatUserEntity> optionalUser = userRepository.findByUsername(username); // 사용자 이름으로 찾는 메서드 사용
	    if (!optionalUser.isEmpty()) {
	        ChatUserEntity user = optionalUser.get();
	        session.setAttribute("user", user);
	        return "redirect:/";
	    }
	    return "redirect:/"; // 로그인 실패 시 리다이렉트
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/profile/{username}")
	public String userProfile(Model model, @PathVariable String username) {
	    Optional<ChatUserEntity> optionalUser = userRepository.findByUsername(username);
	    
	    if (optionalUser.isPresent()) {
	        // 사용자 객체를 가져온 후 username에 접근
	        ChatUserEntity user = optionalUser.get();
	        model.addAttribute("userProfile", user);
	        return "user/profile";
	    } else {
	        // 사용자 정보를 찾지 못한 경우 처리
	        model.addAttribute("NoUserException", "사용자를 찾을 수 없습니다.");
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
		System.out.println(entity.getRoomname() + ", 시간 : " + entity.getCreatedAt());
		return "redirect:/";
	}
	
	@GetMapping("/chatroom/{roomid}")
	public String chatRoomMovement(HttpSession session, Model model, @PathVariable int roomid) {
	    ChatUserEntity user = (ChatUserEntity) session.getAttribute("user");
	    if (user == null) {
	        model.addAttribute("NotLoginUserAccess", "로그인을 하지 않으면 채팅을 이용할 수 없습니다.");
	        return "error/NotLoginUserAccess";
	    }
	    
	    Optional<ChatRoomEntity> optionalRoom = roomRepository.findById(roomid);
	    if (optionalRoom.isPresent()) {
	        model.addAttribute("chat", optionalRoom.get());
	        model.addAttribute("messages", roomRepository.findByRoomid(roomid)); // 메시지 목록 추가
	        return "chat/chat";
	    } else {
	        model.addAttribute("NullChatRoomIndex", "존재하지 않는 채팅방입니다.");
	        return "error/NullChatRoomIndex";
	    }
	}

	@PostMapping("/chatroom/{roomid}/insertValue")
	public String insertChatMessage(
	        @PathVariable int roomid,
	        @RequestParam String chattext,
	        HttpSession session,
	        Model model
	) {
	    ChatUserEntity user = (ChatUserEntity) session.getAttribute("user");
	    if (user == null) {
	        return "redirect:/signin"; // 로그인하지 않은 경우 로그인 페이지로 리다이렉트
	    }
	    
	    ChatRoomEntity chatMessage = new ChatRoomEntity(); // ChatMessage 엔티티를 만들기 위해 적절한 import 필요
	    chatMessage.setRoomid(roomid);
	    chatMessage.setTextuser(user.getUsername()); // 현재 사용자 이름을 세팅
	    chatMessage.setChattext(chattext);
	    chatMessage.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
	    
	    roomRepository.save(chatMessage); // 메시지 저장
	    
	    // 다시 채팅방으로 이동
	    return "redirect:/chatroom/" + roomid;
	}
	
	
	
	
	
}
