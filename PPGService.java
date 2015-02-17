package ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;

class Database{
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
        static Database db;
        
        public static Database getDatabaseHandler()
        {
            if(db == null)
            {
                db = new Database();
            }
            return db;
        }
        
	private Database(){
		try{
                    Class.forName("com.mysql.jdbc.Driver");
                    con=DriverManager.getConnection("jdbc:mysql://localhost:3306/paintpg_db","paintpgadm","paintpg");
		}
		catch (Exception e) {
                    System.out.println(e);
		}
	}
	//LOGIN/REGISTRATION METHODS	
	protected String registrationRequest(String usr, String pwd, String mot, String c1, String c2){
            try {
                String query="SELECT `user_authentication`.`username` FROM `paintpg_db`.`user_authentication` WHERE `username`=?";
                pst=con.prepareStatement(query);
                pst.setString(1, usr);
                rs=pst.executeQuery();
                if(rs.next())
                return "Nome utente già in uso!";
                pst.close();
                query="INSERT INTO `paintpg_db`.`user_authentication` (`user_id`, `username`, `password`, `motto`, `colore1`, `colore2`)" 
                      + "VALUES (NULL, ?, ?, ?, ?, ?)";
                pst=con.prepareStatement(query);
                pst.setString(1, usr);
                pst.setString(2, pwd);
                pst.setString(3, mot);
                pst.setString(4, c1);
                pst.setString(5, c2);
                pst.executeUpdate();
            } catch (Exception e) {
                return "Errore! " + e;
            }
            return "Registrazione effettuata con successo!";
        }
        
        protected int checkLogin(String usr,String pwd){
            try {
		String query="SELECT * FROM user_authentication WHERE username=? AND password=?";
		pst=con.prepareStatement(query);
		pst.setString(1, usr);
		pst.setString(2, pwd);
		rs=pst.executeQuery();
		if(rs.next())
                {
                    int id = Integer.parseInt(rs.getString("user_id"));
                    return id;
                }
                else
                {
                    return -1;
                }
            } catch (Exception e) {
		return -1;
            }
	}
        
}

@WebService(serviceName = "PaintPGService")
public class PPGService{
    Database db = Database.getDatabaseHandler();
    
    @WebMethod(operationName = "registrationRequest")
    public String registrationRequest(  @WebParam(name = "username") String username,
                                        @WebParam(name = "password") String password,
                                        @WebParam(name = "passwordconfirm") String passwordconfirm,
                                        @WebParam(name = "motto") String motto,
                                        @WebParam(name = "colore1") String colore1,
                                        @WebParam(name = "colore2") String colore2) {
        if(password.equals(passwordconfirm)) {
           return db.registrationRequest(username, password, motto, colore1, colore2);
        }
        return "le password non corrispondono!";
    }  
    
    @WebMethod(operationName = "loginRequest")
    public int loginRequest(    @WebParam(name = "username") String username,
                                @WebParam(name = "password") String password){
        return db.checkLogin(username,password);
    }
    
    @WebMethod(operationName = "DEBUGSendDate")
    public void DEBUGSendDate(  @WebParam(name = "day") int day,
                                @WebParam(name = "month") int month,
                                @WebParam(name = "year") int year)
    {
    }
}

