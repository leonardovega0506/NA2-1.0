package mx.com.upiicsa.na2.NA210.model.auth;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Authority(String authority) {
        this.authority = authority;
    }
}
