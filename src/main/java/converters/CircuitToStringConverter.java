
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Circuit;

@Component
@Transactional
public class CircuitToStringConverter implements Converter<Circuit, String> {

	@Override
	public String convert(final Circuit a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
