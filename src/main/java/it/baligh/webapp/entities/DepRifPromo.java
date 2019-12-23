package it.baligh.webapp.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@Table(name="DEPRIFPROMO")
@Data
public class DepRifPromo implements Serializable{
	
	
	private static final long serialVersionUID = -8453863869024666255L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	@Column(name="IDDEPOSITO")
	@Basic(optional = false)
	private long idDeposito;
	@ManyToOne
	@EqualsAndHashCode.Exclude
	@JoinColumn(name = "IDPROMO",referencedColumnName = "IdPromo")
	@JsonBackReference
	private Promo promo;
	
}
