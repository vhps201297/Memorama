
package com.example.mario.energru;

import java.util.List;

public class User {

    private Boolean ok;
    private List<Usuario> usuarios = null;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}