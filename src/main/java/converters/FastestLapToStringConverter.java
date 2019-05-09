
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FastestLap;

@Component
@Transactional
public class FastestLapToStringConverter implements Converter<FastestLap, String> {

	@Override
	public String convert(final FastestLap a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
