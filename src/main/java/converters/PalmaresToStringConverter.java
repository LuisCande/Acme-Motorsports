
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Palmares;

@Component
@Transactional
public class PalmaresToStringConverter implements Converter<Palmares, String> {

	@Override
	public String convert(final Palmares a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
