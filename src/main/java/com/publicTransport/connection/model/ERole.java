package com.publicTransport.connection.model;

public enum ERole {
	ROLE_USER("user"),
    ROLE_MODERATOR("mod"),
    ROLE_ADMIN("admin");
	
	private String abreviation ; 
	
	private ERole(String abreviation) {  
        this.abreviation = abreviation ;  
   }  
     
    public String getAbreviation() {  
        return  this.abreviation ;  
   }  
}
