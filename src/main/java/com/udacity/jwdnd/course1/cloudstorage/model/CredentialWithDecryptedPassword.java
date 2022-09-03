package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialWithDecryptedPassword {
    private int credentialId;

    private String url;

    private String username;

    private byte[] key;

    private String password;

    private int userId;

    private String dePassword;

    public CredentialWithDecryptedPassword(int credentialId, String url, String username, byte[] key, String password, int userId, String dePassword) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
        this.dePassword = dePassword;
    }

    public CredentialWithDecryptedPassword() {

    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDePassword() {
        return dePassword;
    }

    public void setDePassword(String dePassword) {
        this.dePassword = dePassword;
    }
}
