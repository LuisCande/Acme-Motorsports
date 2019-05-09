
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RaceDirectorRepository;
import domain.RaceDirector;

@Component
@Transactional
public class StringToRaceDirectorConverter implements Converter<String, RaceDirector> {

	@Autowired
	RaceDirectorRepository	raceDirectorRepository;


	@Override
	public RaceDirector convert(final String s) {
		RaceDirector result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.raceDirectorRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
