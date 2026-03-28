package parte4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String name;
    private String email;
    private List<Address> addresses;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.addresses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addAddress(Address address) {
        if (address != null) {
            this.addresses.add(address);
        }
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public int getAddressCount() {
        return addresses.size();
    }

    public boolean hasAddress() {
        return !addresses.isEmpty();
    }

    public void clearAddresses() {
        addresses.clear();
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', email='" + email + "', addresses=" + addresses + "}";
    }

    public static void main(String[] args) {
        User user = new User("João Silva", "joao@email.com");
        
        System.out.println("=== Teste de Encapsulamento ===\n");
        
        System.out.println("Usuário criado: " + user);
        
        System.out.println("\n--- Adicionando endereços ---");
        
        Address address1 = new Address("Rua A", "São Paulo", "SP", "01000-000", "Brasil");
        Address address2 = new Address("Av. B", "Rio de Janeiro", "RJ", "20000-000", "Brasil");
        
        user.addAddress(address1);
        user.addAddress(address2);
        
        System.out.println("Endereços após adição: " + user.getAddressCount());
        for (Address addr : user.getAddresses()) {
            System.out.println("  - " + addr);
        }
        
        System.out.println("\n--- Tentando modificar lista direta ---");
        System.out.println("Tentando adicionar via getAddresses()...");
        
        try {
            user.getAddresses().add(new Address("Rua C", "Belo Horizonte", "MG", "30000-000", "Brasil"));
        } catch (UnsupportedOperationException e) {
            System.out.println("Operação bloqueada! Lista imutável retornada.");
        }
        
        System.out.println("Quantidade de endereços: " + user.getAddressCount());
        
        System.out.println("\n--- Removendo endereço ---");
        user.removeAddress(address1);
        System.out.println("Endereços após remoção: " + user.getAddressCount());
        
        System.out.println("\n--- Estado final do usuário ---");
        System.out.println(user);
    }
}
