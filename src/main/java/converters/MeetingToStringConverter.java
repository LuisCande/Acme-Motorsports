
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Meeting;

@Component
@Transactional
public class MeetingToStringConverter implements Converter<Meeting, String> {

	@Override
	public String convert(final Meeting a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
