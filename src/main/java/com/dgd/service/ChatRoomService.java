package com.dgd.service;

import com.dgd.exception.error.AuthenticationException;
import com.dgd.exception.error.ChatException;
import com.dgd.model.dto.CreateChatRoomDto;
import com.dgd.model.dto.EnterChatRoomDto;
import com.dgd.model.entity.ChatRoom;
import com.dgd.model.entity.SharingApplication;
import com.dgd.model.repo.ChatRoomRepository;
import com.dgd.model.repo.SharingApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dgd.exception.message.AuthErrorMessage.*;
import static com.dgd.exception.message.ChatErrorMessage.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final SharingApplicationRepository sharingApplicationRepository;


    public ChatRoom createChatRoom(CreateChatRoomDto createChatRoomDto) {
        SharingApplication sharingApplication = sharingApplicationRepository
                .findById(createChatRoomDto.getSharingApplicationId())
                .orElseThrow(() -> new ChatException(NOT_ACTIVATED_APPLICATION));

        ChatRoom chatRoom = ChatRoom.builder()
                .offerId(createChatRoomDto.getOfferId())
                .takerId(createChatRoomDto.getTakerId())
                .sharingApplication(sharingApplication)
                .isOpened(false)
                .build();

        return chatRoomRepository.save(chatRoom);
    }
    public ChatRoom enterChatRoom(EnterChatRoomDto enterChatRoomDto) throws ChatException {
        ChatRoom chatRoom = chatRoomRepository.findById(enterChatRoomDto.getRoomId())
                .orElseThrow(() -> new ChatException(NOT_ACTIVATED_APPLICATION));
        if(!enterChatRoomDto.getOfferId().equals(chatRoom.getOfferId()) ||
                !enterChatRoomDto.getTakerId().equals(chatRoom.getTakerId())) {
            throw new AuthenticationException(UNAUTHORIZED_REQUEST);
        }

        chatRoom.roomOpen();
        return chatRoom;
    }

    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
