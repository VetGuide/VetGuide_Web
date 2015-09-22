
package pe.edu.ulima.controladores;

import pe.edu.ulima.DAO.UsuarioDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.edu.ulima.DTO.LoginRequestDTO;
import pe.edu.ulima.DTO.LoginResponseDTO;

/**
 *
 * @author Kevin
 */
public class ValidarLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InputStream is = request.getInputStream();
        
        Reader reader = new InputStreamReader(is, "UTF-8");
        LoginRequestDTO loginRequest = 
                new Gson().fromJson(reader, LoginRequestDTO.class);
        
        boolean existe=UsuarioDAO.getInstance().validarUsuarioEnDB(loginRequest.getUsuario(),loginRequest.getPassword());
        
        LoginResponseDTO loginresponse;
        String json;
        if(existe){
            loginresponse= new LoginResponseDTO("OK","");
            json= new Gson().toJson(loginresponse);
            System.out.println("-------------EXISTE CTM-----------------");
        }else {
            loginresponse= new LoginResponseDTO("ERROR","Error en login");
            json= new Gson().toJson(loginresponse);
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
    }
    
}
