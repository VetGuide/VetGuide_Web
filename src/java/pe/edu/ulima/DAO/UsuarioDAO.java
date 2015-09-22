package pe.edu.ulima.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

/**
 * Created by Kevin on 11/09/2015.
 */
public class UsuarioDAO {
    private MongoClient client;
    private DB database;
    private DBCollection col;
    private static UsuarioDAO instance=null;

    private UsuarioDAO(){
    }

    public static UsuarioDAO getInstance(){
        if(instance==null){
            instance= new UsuarioDAO();
        }
        return instance;
    }

    private void getConnection() throws UnknownHostException {
        client= new MongoClient(new MongoClientURI("mongodb://root:root@ds041571.mongolab.com:41571/vetguide"));
        database= client.getDB("vetguide");
        col= database.getCollection("Usuario");
    }

    public boolean validarUsuarioEnDB(String usuario, String password) throws UnknownHostException {
        boolean existe=false;
        this.getConnection();

        BasicDBObject filtro= new BasicDBObject("correo",usuario);
        filtro.put("password",password);
        
        DBObject object=null;
        object=col.findOne(filtro);

        if(object!=null){
            return true;
        }

        return existe;
    }
    
    public boolean registrar(String correo, String password, String nombres, String apellidos) throws UnknownHostException{
        this.getConnection();
        
        boolean existeCorreo= this.existeUsuario(correo);
        
        if(existeCorreo){
            System.out.println("Existe el usuario en DB");
            return false;
        }else{
            System.out.println("Inscribiendolo en DB");
            BasicDBObject object= new BasicDBObject();
            object.put("_id",this.getCount()+1);
            object.put("correo",correo);
            object.put("password",password);
            object.put("nombres", nombres);
            object.put("apellidos", apellidos);
            
            col.insert(object);
            
            return true;
        }
        
    }
    
    //Contar registros en DB
    private long getCount() throws UnknownHostException{
        this.getConnection();
        long count= col.count();
        return count;
    }
    
    //Validar existencia de otro usuario con el mismo correo
    private boolean existeUsuario(String correo) throws UnknownHostException{
        this.getConnection();
        
        BasicDBObject filtro= new BasicDBObject();
        filtro.put("correo", correo);
        
        DBObject object= col.findOne(filtro);
        
        if(object!=null){
            return true;
        }else{
            return false;
        }
        
    }

}
