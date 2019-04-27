
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Sector;

@Component
@Transactional
public class SectorToStringConverter implements Converter<Sector, String> {

	@Override
	public String convert(final Sector a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
