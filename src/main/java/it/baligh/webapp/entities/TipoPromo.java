package it.baligh.webapp.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name="TIPOPROMO")
@Data
public class TipoPromo implements Serializable {
	
	private static final long serialVersionUID = -1161718393110468450L;
	@Id
	@Column(name="IDTIPOPROMO")
	private long idTipoPromo;
	@Basic
	private String descrizione;
	@OneToMany(mappedBy = "tipoPromo",fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<DettPromo> dettPromo=new HashSet<>();

}
