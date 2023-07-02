package com.dgd.service;

import com.dgd.exception.error.ApplicationException;
import com.dgd.exception.error.AuthenticationException;
import com.dgd.exception.error.ChatException;
import com.dgd.exception.message.ApplicationErrorCode;
import com.dgd.model.dto.CreateChatRoomDto;
import com.dgd.model.dto.EnterChatRoomDto;
import com.dgd.model.entity.ChatRoom;
import com.dgd.model.entity.Good;
import com.dgd.model.entity.SharingApplication;
import com.dgd.model.repo.ChatRoomRepository;
import com.dgd.model.repo.GoodRepository;
import com.dgd.model.repo.SharingApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.dgd.exception.message.AuthErrorMessage.*;
import static com.dgd.exception.message.ChatErrorMessage.*;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final SharingApplicationRepository sharingApplicationRepository;
    private final GoodRepository goodRepository;

    public ChatRoom createChatRoom(CreateChatRoomDto createChatRoomDto) {
        SharingApplication sharingApplication = sharingApplicationRepository
                .findById(createChatRoomDto.getSharingApplicationId())
                .orElseThrow(() -> new ChatException(NOT_ACTIVATED_APPLICATION));
        Good good = goodRepository.findById(sharingApplication.getGood().getId())
                .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.NOT_REGISTERED_GOOD));

        String offerId = good.getUser().getUserId();

        ChatRoom chatRoom = ChatRoom.builder()
                .offerId(offerId)
                .takerId(createChatRoomDto.getUserId())
                .isOpened(false)
                .build();

        return chatRoomRepository.save(chatRoom);
    }
    public void enterChatRoom(EnterChatRoomDto enterChatRoomDto) throws ChatException {
        ChatRoom chatRoom = chatRoomRepository.findById(enterChatRoomDto.getRoomId())
                .orElseThrow(() -> new ChatException(NOT_ACTIVATED_APPLICATION));
        if(!enterChatRoomDto.getUserId().equals(chatRoom.getOfferId())) {
            throw new AuthenticationException(UNAUTHORIZED_REQUEST);
        }
        if (!enterChatRoomDto.getUserId().equals(chatRoom.getTakerId())) {
            throw new AuthenticationException(UNAUTHORIZED_REQUEST);
        }
        chatRoom.roomOpen();
    }

    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }
}
