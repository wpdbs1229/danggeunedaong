package com.dgd.service;

import com.dgd.exception.ApplicationErrorCode;
import com.dgd.exception.ApplicationException;
import com.dgd.model.dto.SharingApplicationDto;
import com.dgd.model.repo.SharingApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharingApplicationService {

    private final SharingApplicationRepository sharingApplicationRepository;

    /**
     * 나눔 신청
     * @param form
     */
    public void applySharing(SharingApplicationDto.Request form){
        boolean exists = sharingApplicationRepository.existsByUserIdAndProductId(form.getUserId(), form.getProductId());
        if (exists){
            throw new ApplicationException(ApplicationErrorCode.DUPLICATE_APPLICATION);
        }
        sharingApplicationRepository.save(form.toEntity());
    }
}
