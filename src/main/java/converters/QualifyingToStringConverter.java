
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Qualifying;

@Component
@Transactional
public class QualifyingToStringConverter implements Converter<Qualifying, String> {

	@Override
	public String convert(final Qualifying a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
