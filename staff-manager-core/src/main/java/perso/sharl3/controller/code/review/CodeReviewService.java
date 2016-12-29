package perso.sharl3.controller.code.review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import perso.sharl3.entity.Developer;
import perso.sharl3.persistence.DeveloperDAO;

public class CodeReviewService {
	
	private DeveloperDAO developerDAO;
	
	public CodeReviewService() {
		developerDAO = new DeveloperDAO();
	}

	public void generatePairs()  throws SQLException {
    	// TODO : prevent devs from the same project to be pairs
    	// TODO : prevent devs that have already been paired to be pairs
		// TODO : generate db script / write to db
		Map<String, String> pairs = new HashMap<String, String>();
	    Random randomGenerator = new Random();
	    List<String> developpers = developerDAO.getDevelopers();
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
		printPairs(pairs, developpers,developerDAO.getDevelopersData());
	}
	
	private void printPairs(Map<String, String> pairs, List<String> developpers, Map<String, Developer> developersData) {
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

}
