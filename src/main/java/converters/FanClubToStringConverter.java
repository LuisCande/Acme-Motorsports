
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FanClub;

@Component
@Transactional
public class FanClubToStringConverter implements Converter<FanClub, String> {

	@Override
	public String convert(final FanClub a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
