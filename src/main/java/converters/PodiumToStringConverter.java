
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Podium;

@Component
@Transactional
public class PodiumToStringConverter implements Converter<Podium, String> {

	@Override
	public String convert(final Podium a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
