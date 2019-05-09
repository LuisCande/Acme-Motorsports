
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.GrandPrix;

@Component
@Transactional
public class GrandPrixToStringConverter implements Converter<GrandPrix, String> {

	@Override
	public String convert(final GrandPrix a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
