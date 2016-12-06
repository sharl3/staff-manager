package perso.sharl3.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import perso.sharl3.entity.Developer;

public class DeveloperDAO {

	public Map<String, Developer> getDevelopersData() throws SQLException{
    	DatabaseUtils dbUtils = new DatabaseUtils();
	    Statement stmt = null;
	    Map<String, Developer> developpers = new HashMap<String, Developer>();
	    
	    try {
	        stmt = dbUtils.getConnection().createStatement();
	        // First fetch all the developpers and get their current project
	        String query = "Select P.ID ID, P.NAME NAME,P.SURNAME SURNAME,PR.LABEL LABEL FROM PERSON P, PROJECT PR, PROJECTAFFECTATION PA " +
							"where P.ID=PA.IDPERSON " +
							"AND PR.ID=PA.IDPROJECT " +
							"AND P.IDROLE='DEV' " +
							"AND P.DATESORTIE IS NULL";
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            developpers.put(rs.getString("ID"), new Developer(rs.getString("ID"), rs.getString("NAME"), rs.getString("SURNAME"), rs.getString("LABEL")));
	        }
	    } catch (SQLException e ) {
	        System.out.println(e.getErrorCode());
	        System.out.println(e.getMessage());
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	    return developpers;
    }
	
	public List<String> getDevelopers() throws SQLException {
    	DatabaseUtils dbUtils = new DatabaseUtils();
	    Statement stmt = null;
	    List<String> developpers = new LinkedList<String>();
	    
	    try {
	        stmt = dbUtils.getConnection().createStatement();
	        // First fetch all the developpers
	        String query = "select ID from person where IDROLE='DEV' AND DATESORTIE IS NULL AND DATEENTREE < SYSDATE";
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	            developpers.add(rs.getString("ID"));
	        }
	    } catch (SQLException e ) {
	        System.out.println(e.getErrorCode());
	        System.out.println(e.getMessage());
	    } finally {
	        if (stmt != null) { stmt.close(); }
	    }
	    return developpers;
	}

}
