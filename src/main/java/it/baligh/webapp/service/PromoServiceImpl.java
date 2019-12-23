package it.baligh.webapp.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.baligh.webapp.entities.Promo;
import it.baligh.webapp.repository.PromoRepository;

@Service
@Transactional
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoRepository promoRepository;
	@Override
	public List<Promo> selTutti() {
		
		return promoRepository.findAll();
	}
	@Override
	public Promo findByIdPromo(String IdPromo) {
		
		return promoRepository.findByIdPromo(IdPromo);
		
	}
	@Override
	public Promo findByAnnoAndCodice(String anno, String codice) {
		
		return promoRepository.findByAnnoAndCodice(Integer.parseInt(anno), codice);
	}
	@Override
	public List<Promo> selPromoByData(Date data) {
		
		return promoRepository.selPromoByData(data);
	}
	@Override
	public void insertPromo(Promo promo) {
		promoRepository.saveAndFlush(promo);
		
	}
	@Override
	public void eliminaPromo(Promo promo) {
		promoRepository.delete(promo);
		
	}

	
}
