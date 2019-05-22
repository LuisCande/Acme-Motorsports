
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.TeamManager;

@Component
@Transactional
public class TeamManagerToStringConverter implements Converter<TeamManager, String> {

	@Override
	public String convert(final TeamManager a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());

		return result;
	}
}
