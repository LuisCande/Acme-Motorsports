
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Answer;

@Component
@Transactional
public class AnswerToStringConverter implements Converter<Answer, String> {

	@Override
	public String convert(final Answer a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
