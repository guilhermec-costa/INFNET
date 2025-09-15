package com.infnet.TP5_CLI.session;

public class SessionManager {
    
    private static SessionManager instance;
    private String clienteId;
    private String nomeCliente;
    private String emailCliente;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public void login(String clienteId, String nome, String email) {
        this.clienteId = clienteId;
        this.nomeCliente = nome;
        this.emailCliente = email;
    }
    
    public void logout() {
        this.clienteId = null;
        this.nomeCliente = null;
        this.emailCliente = null;
    }
    
    public boolean isLoggedIn() {
        return clienteId != null;
    }
    
    public String getClienteId() {
        return clienteId;
    }
    
    public String getNomeCliente() {
        return nomeCliente;
    }
    
    public String getEmailCliente() {
        return emailCliente;
    }
}