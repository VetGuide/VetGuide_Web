package pe.edu.ulima.DTO;

public class LoginRequestDTO {
    private String correo;
    private String password;

    public LoginRequestDTO(String usuario, String password) {
        this.correo = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return correo;
    }

    public void setUsuario(String usuario) {
        this.correo = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
