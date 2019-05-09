
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;

@Component
@Transactional
public class CategoryToStringConverter implements Converter<Category, String> {

	@Override
	public String convert(final Category a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
