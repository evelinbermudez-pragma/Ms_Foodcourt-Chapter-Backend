package com.chapter.foodcourt.insfrastructure.output.feign.repository;

import com.chapter.foodcourt.domain.spi.ISmsRepository;
import com.chapter.foodcourt.insfrastructure.output.feign.client.ISmsClient;
import com.chapter.foodcourt.insfrastructure.output.feign.dto.request.SmsRequestDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SmsRepositoryImpl implements ISmsRepository {
    private final ISmsClient smsClient;

    @Override
    public void sendSms(String phone, String message) {
        smsClient.sendSms(new SmsRequestDto(phone, message));
    }
}
