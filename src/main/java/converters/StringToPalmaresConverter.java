
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PalmaresRepository;
import domain.Palmares;

@Component
@Transactional
public class StringToPalmaresConverter implements Converter<String, Palmares> {

	@Autowired
	PalmaresRepository	palmaresRepository;


	@Override
	public Palmares convert(final String s) {
		Palmares result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.palmaresRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
