package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialWithDecryptedPassword;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;

    private UserService userService;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    public int addOrUpdate(CredentialForm credentialForm){
        Credential credential = new Credential();
        credential.setUsername(credentialForm.getUsername());
        credential.setUrl(credentialForm.getUrl());
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);
        credential.setKey(key);
        credential.setPassword(encryptedPassword);

        if(credentialForm.getCredentialId() == 0){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username =  authentication.getPrincipal().toString();
            credential.setUserId(userService.getUserIdByUsername(username));
            return credentialMapper.insert(credential);
        }

        else{
            credential.setCredentialId(credentialForm.getCredentialId());
            return credentialMapper.update(credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword(), credential.getCredentialId());
        }

    }

    public ArrayList<CredentialWithDecryptedPassword> getAllCredential(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username =  authentication.getPrincipal().toString();
        Integer userId = userService.getUserIdByUsername(username);
        if(userId == 0){
            return new ArrayList<>();
        }
        ArrayList<Credential> listAllFound = credentialMapper.getAllCredential(userId);
        ArrayList<CredentialWithDecryptedPassword> returnList = new ArrayList<>();
        listAllFound.stream().forEach(credential -> {
            byte[] key = credential.getKey();
            String encodedKey = Base64.getEncoder().encodeToString(key);
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), encodedKey);
            returnList.add(new CredentialWithDecryptedPassword(credential.getCredentialId(),credential.getUrl(), credential.getUsername(),
                    credential.getKey(),credential.getPassword(),credential.getUserId(), decryptedPassword));
        });
        return returnList;
    }

    public boolean deleteCredential(int credentialId){
        int deletedCredential = credentialMapper.delete(credentialId);
        if(deletedCredential == 1){
            return true;
        }
        return false;
    }
}
