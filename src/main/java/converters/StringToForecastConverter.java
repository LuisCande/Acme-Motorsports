
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ForecastRepository;
import domain.Forecast;

@Component
@Transactional
public class StringToForecastConverter implements Converter<String, Forecast> {

	@Autowired
	ForecastRepository	forecastRepository;


	@Override
	public Forecast convert(final String s) {
		Forecast result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.forecastRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
