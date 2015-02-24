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
            query="INSERT INTO `paintpg_db`.`user_authentication` (`user_id`, `username`, `password`, `motto`, `color1`, `color2`)" 
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
    
    protected ResultSet getMatches(){
        try{
            String query="SELECT `match_id`,`id_s1`,`id_s2`,`match_date`,`res1`,`res2` FROM `matches`, `user_authentication` WHERE `matches`.`id_s1`=`user_authentication`.`user_id` ORDER BY DATEDIFF(`match_date`, CURDATE())";
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
        
    protected String setAdmin(int user_id){
        try{
            if(db.isAdmin(user_id) == -1 && user_id != -1){
            String query="INSERT INTO `admin`(`user_id`, `admin_level`) VALUES (?, ?)";
            pst=con.prepareStatement(query);
            pst.setInt(1, user_id);
            pst.setInt(2, 2);
            pst.executeUpdate();
                return "ADMIN PROMOSSO";
            }
            else if (user_id == -1)
                return "NOME NON PRESENTE";
            else
                return "ADMIN GIA' PRESENTE";
        } catch (Exception e) {return "Operazione fallita.";}
    }
    
    protected String rmvUser(int user_id){
        try{
            if (user_id != -1){
                String query="DELETE FROM `user_authentication` WHERE `user_id` = ?";
                pst=con.prepareStatement(query);
                pst.setInt(1, user_id);
                pst.executeUpdate();
                return "SQUADRA ELIMINATA DAL DB";
            }
            else
                return "SQUADRA NON PRESENTE NEL DB";
        }catch (Exception e) {return "Operazione fallita.";}
    }
        
    //GETTER METHODS FOR USERS
      
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
    
    protected int getUserid(String username){
	try {
            String query="SELECT * FROM user_authentication WHERE username=?";
            pst=con.prepareStatement(query);
            pst.setString(1, username);
            rs=pst.executeQuery();
            if(rs.next())
                return rs.getInt("user_id");                      
            else
                return -1;
        } catch (Exception e) {
            return -1;
        }
    }
    
    protected String getColor(String username, String color){
	try {
            String query="SELECT " + color + " FROM user_authentication WHERE username=?";
            pst=con.prepareStatement(query);
            pst.setString(1, username);
            rs=pst.executeQuery();
            if(rs.next())
                return rs.getString(color);                      
            else
                return "error";
        } catch (Exception e) {
            return "error";
        }
    }
    
    protected String getMotto(String username){
	try {
            String query="SELECT motto FROM user_authentication WHERE username=?";
            pst=con.prepareStatement(query);
            pst.setString(1,username);
            rs=pst.executeQuery();
            if(rs.next())
                return rs.getString("motto");                      
            else
                return "error";
        } catch (Exception e) {
            return "error";
        }
    }
}

class XMLPostFile {

String post_repo_xml="C:\\xampp\\htdocs\\clientPPG\\postfile.xml";
String feeds_xml="C:\\xampp\\htdocs\\clientPPG\\feeds.xml";

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
                
    } catch (SQLException | ParserConfigurationException | TransformerException ex) {
        System.out.println(ex);
        return false;
    }
}

protected boolean getFeeds() {
    
    Database db = Database.getDatabaseHandler();
    
    ResultSet rs=db.getMatches();
    try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                  
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("root");
        doc.appendChild(root);
                
        while (rs.next()) {    	
        //match_id
        //id_s1
        //id_s2
        //match_date
        //res1
        //res2
            int rs_match_id = rs.getInt("match_id");
            int rs_id_squadra_1 = rs.getInt("id_s1");
            int rs_id_squadra_2 = rs.getInt("id_s2");
            Timestamp rs_match_date = rs.getTimestamp("match_date");
            int rs_res_squadra_1 = rs.getInt("res1");
            int rs_res_squadra_2 = rs.getInt("res2");
            
            // MATCH
            Element match = doc.createElement("match");
            root.appendChild(match);
            
            Element match_id = doc.createElement("match_id");
            match_id.appendChild(doc.createTextNode(Integer.toString(rs_match_id)));
            match.appendChild(match_id);
            
            Element match_date = doc.createElement("match_date");
            match_date.appendChild(doc.createTextNode(String.format("%1$TD %1$TT", rs_match_date)));
            match.appendChild(match_date);
            
            String nome_squadra;
            // MATCH - SQUADRA_1
            Element squadra_1 = doc.createElement("squadra_1");
            match.appendChild(squadra_1);
            
            Element id_squadra_1 = doc.createElement("id_squadra_1");
            id_squadra_1.appendChild(doc.createTextNode(Integer.toString(rs_id_squadra_1)));
            squadra_1.appendChild(id_squadra_1);
            
            Element nome_squadra_1 = doc.createElement("nome_squadra_1");
            nome_squadra_1.appendChild(doc.createTextNode(db.getUsername(rs_id_squadra_1)));
            squadra_1.appendChild(nome_squadra_1);
            
            Element motto_squadra_1 = doc.createElement("motto_squadra_1");
            nome_squadra = db.getUsername(rs_id_squadra_1);
            motto_squadra_1.appendChild(doc.createTextNode(db.getMotto(nome_squadra)));
            squadra_1.appendChild(motto_squadra_1);
            
            // MATCH - SQUADRA_2
            // TODO: controllare se non esiste ancora un avversario
            // NOTE: se l'avversario non esiste il suo nome è 'errore', come ritornato da db.getUsername
            Element squadra_2 = doc.createElement("squadra_2");
            match.appendChild(squadra_2);
            
            Element id_squadra_2 = doc.createElement("id_squadra_2");
            id_squadra_2.appendChild(doc.createTextNode(Integer.toString(rs_id_squadra_2)));
            squadra_2.appendChild(id_squadra_2);
            
            Element nome_squadra_2 = doc.createElement("nome_squadra_2");
            nome_squadra_2.appendChild(doc.createTextNode(db.getUsername(rs_id_squadra_2)));
            squadra_2.appendChild(nome_squadra_2);
            
            Element motto_squadra_2 = doc.createElement("motto_squadra_2");
            nome_squadra = db.getUsername(rs_id_squadra_2);
            motto_squadra_2.appendChild(doc.createTextNode(db.getMotto(nome_squadra)));
            squadra_2.appendChild(motto_squadra_2);
            
            // MATCH - RESULTS
            // TODO: controllare se il match non è stato ancora disputato e non ci sono quindi risultati
            // NOTE: se i risultati sono null nel database sono 0 in java
            Element match_results = doc.createElement("match_results");
            match.appendChild(match_results);
            
            Element match_results_1 = doc.createElement("match_results_1");
            match_results_1.appendChild(doc.createTextNode(Integer.toString(rs_res_squadra_1)));
            Element match_results_2 = doc.createElement("match_results_2");
            match_results_2.appendChild(doc.createTextNode(Integer.toString(rs_res_squadra_2)));
            
            match_results.appendChild(match_results_1);
            match_results.appendChild(match_results_2);
        }
 
        // Scrive nel file XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
	DOMSource source = new DOMSource(doc);
	StreamResult result = new StreamResult(new File(feeds_xml));
 		
 	transformer.transform(source, result);
 
	return true;
                
    } catch (SQLException | ParserConfigurationException | TransformerException ex) {
        System.out.println(ex);
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
                                        @WebParam(name = "color1") String color1,
                                        @WebParam(name = "color2") String color2) {
        if(password.equals(passwordconfirm)) {
           return db.registrationRequest(username, password, motto, color1, color2);
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
    public String setAdmin(@WebParam(name ="user_id") int user_id){
        return db.setAdmin(user_id);
    }
    
    @WebMethod(operationName = "rmvUser")
    public String rmvUser(  @WebParam(name = "user_id") int user_id){
        return db.rmvUser(user_id);
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
    public String getUsername(@WebParam(name = "user_id") int user_id) {
        return db.getUsername(user_id);
    } 
    
    @WebMethod(operationName = "getUserid")
    public int getUserid(@WebParam(name = "username") String username) {
        return db.getUserid(username);
    }
    
    @WebMethod(operationName = "getColor")
    public String getColor(@WebParam(name = "username") String username,
                           @WebParam(name = "color") String color) {
        return db.getColor(username, color);
    } 
    
    @WebMethod(operationName = "getMotto")
    public String getMotto(@WebParam(name = "username") String username) {
        return db.getMotto(username);
    }
    
    @WebMethod(operationName = "runTest")
    public void runTest() {
        XMLPostFile xml = new XMLPostFile();
        xml.getFeeds();
    } 
}

