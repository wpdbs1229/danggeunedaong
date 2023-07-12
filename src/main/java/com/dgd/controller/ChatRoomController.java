package com.dgd.controller;

import com.dgd.model.dto.EnterChatRoomDto;
import com.dgd.model.entity.ChatRoom;
import com.dgd.service.ChatRoomService;
import com.dgd.model.dto.CreateChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody @Valid CreateChatRoomDto createChatRoomDto) {
        return ResponseEntity.ok(chatRoomService.createChatRoom(createChatRoomDto));
    }

    @GetMapping("/enter")
    public void enterChatRoom(@RequestParam @Valid Long roomId,
                              @RequestParam @Valid String offerId,
                              @RequestParam @Valid String takerId) {
        chatRoomService.enterChatRoom(roomId,offerId,takerId);
    }

    @DeleteMapping("/leave")
    public void leaveChatRoom(@RequestParam @Valid Long roomId) {
        chatRoomService.deleteRoom(roomId);
    }
}
