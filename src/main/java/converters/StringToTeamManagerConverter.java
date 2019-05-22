
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TeamManagerRepository;
import domain.TeamManager;

@Component
@Transactional
public class StringToTeamManagerConverter implements Converter<String, TeamManager> {

	@Autowired
	TeamManagerRepository	managerRepository;


	@Override
	public TeamManager convert(final String s) {
		TeamManager result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.managerRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
