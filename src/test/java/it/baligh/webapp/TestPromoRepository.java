package it.baligh.webapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import it.baligh.webapp.entities.Promo;
import it.baligh.webapp.repository.PromoRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPromoRepository {
	@Autowired
	PromoRepository promoRepository;
	@Test
	public void testFindByIdPromo() {
		String Id="C9517A3E-5FCE-464F-8DAF-C4485CD2D925";
		assertThat(promoRepository.findByIdPromo(Id))
		.extracting(Promo::getDescrizione).isEqualTo("PROMO TEST1");
		
	}
	@Test
	public void testFindByAnnoAndCodice() {
		String codice="TEST05";
		int anno=2018;
		assertThat(promoRepository.findByAnnoAndCodice(anno, codice))
		.extracting(Promo::getDescrizione)
		.isEqualTo("CAMPAGNA ANGULAR");
		
		
	}
	@Test
	public void testSelPromoByData() {
		Date data =new Date();
		List<Promo> liste=promoRepository.selPromoByData(data);
		assertEquals(liste.size(),7);
		
	}
	@Test
	public void testSelPromoByData2() {
		Date data =new Date();
		
		assertThat(promoRepository.selPromoByData(data).get(0))
		.extracting(Promo::getIdPromo).isEqualTo("274147E6-CE80-4320-A28E-7EBC0EBEF2287");
		
	}
	
	
	/*
	 * Promo findByIdPromo(String IdPromo);
	Promo findByAnnoAndCodice(int anno,String codice);
	@Query("SELECT a FROM Promo a JOIN a.dettPromo b WHERE ?1 BETWEEN b.inizio AND b.fine")
	List<Promo> selPromoByData(Date data);
	
	 */

}
