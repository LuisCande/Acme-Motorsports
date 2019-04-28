
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FastestLapRepository;
import domain.FastestLap;

@Component
@Transactional
public class StringToFastestLapConverter implements Converter<String, FastestLap> {

	@Autowired
	FastestLapRepository	fastestLapRepository;


	@Override
	public FastestLap convert(final String s) {
		FastestLap result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.fastestLapRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
