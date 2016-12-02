package perso.sharl3.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import perso.sharl3.entity.Developer;
import perso.sharl3.persistence.DatabaseUtils;

public class App 
{
    public static void main( String[] args ) 
    {
        try {
			generatePairs();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    
    private static List<String> getDevelopers() throws SQLException {
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
    
    private static Map<String, Developer> getDevelopersData() throws SQLException{
    	DatabaseUtils dbUtils = new DatabaseUtils();
	    Statement stmt = null;
	    Map<String, Developer> developpers = new HashMap<String, Developer>();
	    
	    try {
	        stmt = dbUtils.getConnection().createStatement();
	        // First fetch all the developpers
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


	private static void generatePairs() throws SQLException {
    	// TODO : prevent devs from the same project to be pairs
    	// TODO : prevent devs that have already been paired to be pairs
		// TODO : generate db script / write to db
		Map<String, String> pairs = new HashMap<String, String>();
	    Random randomGenerator = new Random();
	    List<String> developpers = getDevelopers();
		// Let's create some pairs
	    int remaimingDevs = developpers.size();
	    System.out.println("Number of developpers : " + remaimingDevs);
		while(remaimingDevs > 1) {
			int idLeftDev = 1;
			int idRightDev = 0;
			if(remaimingDevs > 2){
				idLeftDev = randomGenerator.nextInt(remaimingDevs);
				idRightDev = randomGenerator.nextInt(remaimingDevs);
//				System.out.println("idLeftDev " + idLeftDev + " - idRightDev" + idRightDev);
				while(idLeftDev == idRightDev) {
					System.out.println("idLeftDev " + idLeftDev + " - idRightDev " + idRightDev);
					idRightDev = randomGenerator.nextInt(remaimingDevs);
				}
			}
			String leftDev = developpers.get(idLeftDev);
			String rightDev = developpers.get(idRightDev);
			pairs.put(leftDev, rightDev);
			List<String> idsToRemove = new ArrayList<String>();
			idsToRemove.add(leftDev);
			idsToRemove.add(rightDev);
			developpers.removeAll(idsToRemove);
			remaimingDevs = developpers.size();
		}
		printPairs(pairs, developpers,getDevelopersData());
	}


	private static void printPairs(Map<String, String> pairs, List<String> developpers, Map<String, Developer> developersData) {
		
		System.out.println("Number of pairs  : " + pairs.size());
		if(developpers.size() > 0){
			System.out.println("First dev is left with no pair : ");
			System.out.println(developersData.get(developpers.get(0)).toString(";"));
		}
		for(String idLeftDev : pairs.keySet()){
//			System.out.println("idLeftDev : " + idLeftDev + " - idRightDev : " + pairs.get(idLeftDev));
			System.out.println(developersData.get(idLeftDev).toString(";") + ";" + developersData.get(pairs.get(idLeftDev)).toString(";"));
		}
	}


	public static void viewTable()
    	    throws SQLException {

    		DatabaseUtils dbUtils = new DatabaseUtils();
    	    Statement stmt = null;
    	    String query = "select * from PERSON";
    	    try {
    	        stmt = dbUtils.getConnection().createStatement();
    	        ResultSet rs = stmt.executeQuery(query);
    	        while (rs.next()) {
    	            System.out.println(rs.getString("ID"));
    	        }
    	    } catch (SQLException e ) {
    	        System.out.println(e.getErrorCode());
    	        System.out.println(e.getMessage());
    	    } finally {
    	        if (stmt != null) { stmt.close(); }
    	    }
    	}
    
    
}






