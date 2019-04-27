
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Rider;

@Component
@Transactional
public class RiderToStringConverter implements Converter<Rider, String> {

	@Override
	public String convert(final Rider a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
