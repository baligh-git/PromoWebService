package it.baligh.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="PROMO")
@Data
public class Promo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3038902717560090713L;
	@Id
	@Column(name="IDPROMO")
	private String idPromo;
	@Basic(optional = false)
	private int anno;
	@Basic(optional = false)
	private String codice;
	@Basic
	private String descrizione;
	@OneToMany(mappedBy = "promo",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	@OrderBy("riga asc")
	@JsonManagedReference
	Set<DettPromo> dettPromo=new HashSet<>();
	@OneToMany(mappedBy = "promo",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	@JsonManagedReference
	Set<DepRifPromo> depRifPromo=new HashSet<>();
	
	
}
