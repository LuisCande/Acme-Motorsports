
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Representative;

@Component
@Transactional
public class RepresentativeToStringConverter implements Converter<Representative, String> {

	@Override
	public String convert(final Representative a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
