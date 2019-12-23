package it.baligh.webapp.service;

import java.util.Date;
import java.util.List;

import it.baligh.webapp.entities.Promo;

public interface PromoService {
	public List<Promo> selTutti();
	public Promo findByIdPromo(String IdPromo);
	public Promo findByAnnoAndCodice(String anno,String codice);
	public List<Promo> selPromoByData(Date data);
	public void insertPromo(Promo promo);
	public void eliminaPromo(Promo promo);

}
