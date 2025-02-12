package com.art.pojos;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Data
public class User {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;
	    private String name;
	    private String email;
	    private String password;
	    private String phoneNo;
	    private String shippingNo;
	    @Enumerated(EnumType.STRING)
	    private Role role;
	    private Date dateCreated;

	    public enum Role {
	        ADMIN, CUSTOMER
	    }
}
