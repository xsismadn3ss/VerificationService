package com.grupo3.verificationservice.codes.service;

public interface ICodeCacheService {
    void saveCode(String email, String code);
    String getCode(String email);
    void deleteCode(String email);
}
