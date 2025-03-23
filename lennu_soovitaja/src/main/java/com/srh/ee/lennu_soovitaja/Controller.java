package com.srh.ee.lennu_soovitaja;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	List<Lend> lennud = new ArrayList<>();

	// Tagastab lennud, mis vastavad kasutaja poolt sisestatud kriteeriumitele
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/api/lennud")
	public List<Lend> index(@RequestParam String kust, @RequestParam String kuhu, @RequestParam String kuupaev,
			@RequestParam Integer lennuaeg, @RequestParam Integer hind, @RequestParam Integer piletidNr) {

		// Kui lendu antud kuupäeval antud sihtkohtade vahel veel pole loodud/ei leidu,
		// siis loome mõned lennud antud juhul juhuslikult
		if (!kasLeidubLend(kust, kuhu, kuupaev)) {
			looLennud(kust, kuhu, kuupaev, lennuaeg, hind, piletidNr);
		}

		List<Lend> sobivadLennud = leiaLennud(kust, kuhu, kuupaev, lennuaeg, hind, piletidNr);
		System.out.println(sobivadLennud.size() + " lendu leitud");
		return sobivadLennud;
	}

	// Tagastab konkreetse lennu vastavald id-le
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/api/lend")
	public Lend index(@RequestParam int id) {
		for (Lend lend : lennud) {
			if (lend.getId() == id) {
				return lend;
			}
		}
		return null;
	}

	public void looLennud(String kust, String kuhu, String kuupaev, int lennuaeg, int hind, int piletidNr) {
		for (int i = 0; i < 10; i++) {
			lennud.add(new Lend(kust, kuhu, kuupaev));
		}
	}

	public List<Lend> leiaLennud(String kust, String kuhu, String kuupaev, int lennuaeg, int hind, int piletidNr) {
		List<Lend> vastus = new ArrayList<>();

		for (Lend lend : lennud) {
			if (lend.getKust().equals(kust) && lend.getKuhu().equals(kuhu) && lend.getKuupaev().equals(kuupaev) && lend.getLennuaeg() < lennuaeg && lend.getHind() < hind && lend.leiaVabadIstekohad() >= piletidNr) {
				vastus.add(lend);
			}
		}

		return vastus;
	}

	public boolean kasLeidubLend(String kust, String kuhu, String kuupaev) {
		for (Lend lend : lennud) {
			if (lend.getKust().equals(kust) && lend.getKuhu().equals(kuhu) && lend.getKuupaev().equals(kuupaev)) {
				return true;
			}
		}
		return false;
	}

}