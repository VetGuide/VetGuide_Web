
package pe.edu.ulima.controladores;

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
import pe.edu.ulima.DAO.UsuarioDAO;
import pe.edu.ulima.DTO.LoginResponseDTO;
import pe.edu.ulima.DTO.RegistroRequestDTO;

/**
 *
 * @author Kevin
 */
public class RegistrarUsuarioServlet extends HttpServlet {
    


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InputStream is = request.getInputStream();
        Reader reader = new InputStreamReader(is, "UTF-8");
        
        RegistroRequestDTO usuario= new Gson().fromJson(reader,RegistroRequestDTO.class);
        
        boolean existe= UsuarioDAO.getInstance().registrar(usuario.getCorreo(), usuario.getPassword(), usuario.getNombre(), usuario.getApellido());
        LoginResponseDTO responseDTO;
        String json;
        
        if(existe){
            //Armar json de exito
            responseDTO= new LoginResponseDTO("OK","");
            json= new Gson().toJson(responseDTO);
            System.out.println("-------------EXISTE CTM-----------------");
        }else{
            //Armar json de error
             responseDTO= new LoginResponseDTO("Error","");
            json= new Gson().toJson(responseDTO);
        }
        
        response.setContentType("application/json");
        
        //Manda la respuesta a la App Movil
        PrintWriter out = response.getWriter();
        out.print(json);
    }

}
