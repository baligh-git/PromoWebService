package it.baligh.webapp.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.core.annotation.Order;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="DETTPROMO")
@Data
public class DettPromo  implements Serializable {
	
	private static final long serialVersionUID = 2488456842621770087L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	@Basic(optional = false)
	private int riga;
	@Basic(optional = false)
	@Column(name="CODART")
	private String codArt;
	@Column(name="CODFID")
	@Basic
	private String codFid;
	@Temporal(TemporalType.DATE)
	private Date inizio;
	@Temporal(TemporalType.DATE)
	private Date fine;
	@Basic(optional = false)
	private String oggetto;
	@Column(name="ISFID")
	@Basic
	private String isFid;
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name ="IDPROMO",referencedColumnName =  "idPromo")
	@JsonBackReference
	private Promo promo;
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name ="IDTIPOPROMO",referencedColumnName =  "idTipoPromo")
	private TipoPromo tipoPromo;
	
	

}
