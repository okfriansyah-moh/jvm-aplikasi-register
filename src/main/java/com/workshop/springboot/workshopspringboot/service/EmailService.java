package com.workshop.springboot.workshopspringboot.service;

public interface EmailService {
    void kirimEmail(String from, String to, String subject, String content);
}
