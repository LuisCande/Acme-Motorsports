
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorldChampion;

@Component
@Transactional
public class WorldChampionToStringConverter implements Converter<WorldChampion, String> {

	@Override
	public String convert(final WorldChampion a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
