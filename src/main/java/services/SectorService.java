
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SectorRepository;
import domain.Sector;

@Service
@Transactional
public class SectorService {

	@Autowired
	private SectorRepository	sectorRepository;


	//Other methods

	//The minimum, the maximum, the average, and the standard deviation of the number of sectors per circuit
	public Double[] minMaxAvgStddevSectorsPerCircuit() {
		return this.sectorRepository.minMaxAvgStddevSectorsPerCircuit();
	}

	//Returns the sectors without fan clubs
	public Collection<Sector> getSectorsWithoutFanClubs() {
		return this.sectorRepository.getSectorsWithoutFanClubs();
	}
}
