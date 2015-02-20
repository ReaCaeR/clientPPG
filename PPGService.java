package ws;

import java.io.File;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.sql.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


class Database{
    
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    static Database db;
        
    public static Database getDatabaseHandler(){
        if(db == null)
            db = new Database();
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
            if(rs.next()){
                int id = Integer.parseInt(rs.getString("user_id"));
                return id;
            }
            else{
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }
    
    //POST METHODS
    protected String setPost(int user_id, String post_body){
        try {
            String query="SELECT `user_authentication`.`username` FROM `user_authentication` WHERE `user_authentication`.`user_id`=?";
            pst=con.prepareStatement(query);
            pst.setInt(1, user_id);
            rs=pst.executeQuery();
            String username="";
            if(rs.next())
                username=rs.getString("username");
            pst.clearParameters();
            query="INSERT INTO `paintpg_db`.`posts` (`post_id`, `post_date`, `post_body`, `user_id`, `username`)"
                  + "VALUES (NULL, CURRENT_TIMESTAMP,?,?,?)";
            pst=con.prepareStatement(query);
            pst.setString(1, post_body);
            pst.setInt(2, user_id);
            pst.setString(3, username);
            pst.executeUpdate();
        } catch (Exception e){
            return "Errore! " + e;
        }
        return "Post inserito!";  
    }
    
     protected String setMatch(int user_id, String match_date){
        try {
            String query="INSERT INTO `paintpg_db`.`matches` (`match_id`, `id_s1`, `id_s2`, `match_date`, `res1`, `res2`)"
                  + "VALUES (NULL,?,NULL,?,NULL,NULL)";
            pst=con.prepareStatement(query);
            pst.setInt(1, user_id);
            pst.setString(2, match_date);
            pst.executeUpdate();
        } catch (Exception e){
            return "Errore! " + e;
        }
        return "Proposta di Match inserita!";  
    }
    
    protected ResultSet getPost(){
        try {
            String query="SELECT * FROM posts ORDER BY `post_date` DESC";
            pst=con.prepareStatement(query);
            rs=pst.executeQuery();
        } catch (Exception e) {
            System.out.println("Errore: " + e);
            return null;
        }
        return rs;
    }
    
    //ADMIN METHODS
    protected int isAdmin(int user_id){
        try {
            String query="SELECT `admin_level` FROM `admin` WHERE `user_id` = ?";
            pst=con.prepareStatement(query);
            pst.setInt(1, user_id);
            rs=pst.executeQuery();
            if(rs.next())
		return rs.getInt("admin_level");                 
            else 
                return -1;
        } catch (Exception e) {
            return -1;
        }
    }
        
    protected void setAdmin(int user_id){
        try{
            String query="INSERT INTO `admin`(`user_id`, `admin_level`) VALUES (?, ?)";
            pst=con.prepareStatement(query);
            pst.setInt(1, user_id);
            pst.setInt(2, 2);
            pst.executeUpdate();
        } catch (Exception e) {}
    }
        
    protected void rmvAdmin(int user_id){
        try{
            String query="DELETE FROM `admin` WHERE `user_id` = ?";
            pst=con.prepareStatement(query);
            pst.setInt(1, user_id);
            pst.executeUpdate();
        } catch (Exception e) {}
    }  
    
    //OTHER METHODS
      
    protected String getUsername(int user_id){
	try {
            String query="SELECT * FROM user_authentication WHERE user_id=?";
            pst=con.prepareStatement(query);
            pst.setString(1, String.valueOf(user_id));
            rs=pst.executeQuery();
            if(rs.next())
                return rs.getString("username");                      
            else
                return "error";
        } catch (Exception e) {
            return "error";
        }
    }
}


class XMLPostFile {

String post_repo_xml="C:\\xampp\\htdocs\\clientPPG\\postfile.xml";

protected boolean getXML() {
    
    Database db = Database.getDatabaseHandler();
    
    ResultSet xrs=db.getPost();
    try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                  
        Document doc = docBuilder.newDocument();
        Element post_root = doc.createElement("post_root");
        doc.appendChild(post_root);
                
        while (xrs.next()) {
            String pbody = xrs.getString("post_body");
            String uname = xrs.getString("username");
            Timestamp pdate = xrs.getTimestamp("post_date");
            int pid = xrs.getInt("post_id");
            int uid = xrs.getInt("user_id");
                  
            Element post = doc.createElement("post");
            post_root.appendChild(post);
                    
            Element post_id = doc.createElement("post_id");
            post_id.appendChild(doc.createTextNode(Integer.toString(pid)));
            post.appendChild(post_id);
                    
            Element user_id = doc.createElement("user_id");
            user_id.appendChild(doc.createTextNode(Integer.toString(uid))); 
            post.appendChild(user_id);

            Element post_date = doc.createElement("post_date");
            post_date.appendChild(doc.createTextNode(String.format("%1$TD %1$TT", pdate)));
            post.appendChild(post_date);

            Element post_body = doc.createElement("post_body");
            post_body.appendChild(doc.createTextNode(pbody));
            post.appendChild(post_body);
                    
            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(uname));
            post.appendChild(username);
                    
        }
 
        // Scrive nel file XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File(post_repo_xml));
 		
 	transformer.transform(source, result);
 
	return true;
                
    } catch (SQLException ex) {
        System.out.println(ex);
        return false;
    } catch (ParserConfigurationException pce) {
        System.out.println(pce);
        return false;
    } catch (TransformerException tfe) {
        System.out.println(tfe);
        return false;
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
    
    //ADMIN WEBMETHODS
    @WebMethod(operationName = "isAdmin")
    public int isAdmin(@WebParam(name ="user_id") int user_id){
        return db.isAdmin(user_id);
    }
    
    @WebMethod(operationName = "setAdmin")
    public void setAdmin(@WebParam(name ="user_id") int user_id){
        db.setAdmin(user_id);
    }
    
    @WebMethod(operationName = "rmvAdmin")
    public void rmvAdmin(@WebParam(name ="user_id") int user_id){
        db.rmvAdmin(user_id);
    }
    
    //POST WEBMETHODS
    @WebMethod(operationName = "setPost")
    public String setPost(  @WebParam(name = "user_id") int user_id, 
                            @WebParam(name = "post_body") String post_body){
        String res = db.setPost(user_id, post_body);
        return res;
    }
    
    @WebMethod(operationName = "setMatch")
    public String setMatch(  @WebParam(name = "user_id") int user_id,
                             @WebParam(name = "match_date") String match_date) {
           return db.setMatch(user_id, match_date);
    }  

    @WebMethod(operationName = "updatePostXML")
    public boolean updatePostXML() {
        XMLPostFile xml = new XMLPostFile();
        boolean res = xml.getXML();
        return res;
    }
    
    //OTHERS
    
    @WebMethod(operationName = "getUsername")
    public String getUserName(@WebParam(name = "user_id") int user_id) {
        return db.getUsername(user_id);
    }     
}

