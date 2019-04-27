
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Manager;

@Component
@Transactional
public class ManagerToStringConverter implements Converter<Manager, String> {

	@Override
	public String convert(final Manager a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
