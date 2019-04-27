
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PodiumRepository;
import domain.Podium;

@Component
@Transactional
public class StringToPodiumConverter implements Converter<String, Podium> {

	@Autowired
	PodiumRepository	podiumRepository;


	@Override
	public Podium convert(final String s) {
		Podium result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.podiumRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
