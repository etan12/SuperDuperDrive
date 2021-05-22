package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public void createCredential(Credential createdCredential) {
        credentialMapper.createCredential(createdCredential);
    }

    public void updateCredential(Credential createdCredential) {
        credentialMapper.updateCredential(createdCredential);
    }

    public void deleteCredential(String credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getAllCredentials(Integer userId) {
        return this.credentialMapper.getCredentialsByUserId(userId);
    }

    public Credential getCredentialByCredentialId(Integer credentialId) {
        return this.credentialMapper.getCredentialByCredentialId(credentialId);
    }
}
