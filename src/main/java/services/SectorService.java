
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SectorRepository;

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
}
