package perso.sharl3.app;

import java.sql.SQLException;

import perso.sharl3.controller.code.review.CodeReviewService;

public class App 
{
    public static void main( String[] args ) 
    {
        try {
        	CodeReviewService codereviewService = new CodeReviewService();
        	codereviewService.generatePairs();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }
    
    
	   
    
}






