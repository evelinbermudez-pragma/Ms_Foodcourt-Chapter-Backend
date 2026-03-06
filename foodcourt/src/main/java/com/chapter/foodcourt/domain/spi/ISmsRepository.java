package com.chapter.foodcourt.domain.spi;

public interface ISmsRepository {
    void sendSms(String phone, String message);

}
