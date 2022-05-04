package com.publicTransport.connection.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "noReseauId", "routeId", "reseauId", "stopId" })
@ToString(of = { "id", "noReseauId", "routeId", "reseauId", "stopId" })
@Entity
public class Favori {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String noReseauId = "";

	private String reseauId = "";

	private String routeId = "";

	private String stopId = "";

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
