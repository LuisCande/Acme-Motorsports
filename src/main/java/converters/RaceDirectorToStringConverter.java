
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.RaceDirector;

@Component
@Transactional
public class RaceDirectorToStringConverter implements Converter<RaceDirector, String> {

	@Override
	public String convert(final RaceDirector a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
