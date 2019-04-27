
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorldChampionship;

@Component
@Transactional
public class WorldChampionshipToStringConverter implements Converter<WorldChampionship, String> {

	@Override
	public String convert(final WorldChampionship a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
