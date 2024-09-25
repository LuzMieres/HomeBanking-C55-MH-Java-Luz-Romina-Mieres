package com.MindHub.Homebanking.services;

import com.MindHub.Homebanking.dtos.PasswordChangeDTO;

public interface PasswordChangeService {
    void changePassword(String email, PasswordChangeDTO passwordChangeDTO) throws Exception;
}

