
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FanClubRepository;
import domain.FanClub;

@Component
@Transactional
public class StringToFanClubConverter implements Converter<String, FanClub> {

	@Autowired
	FanClubRepository	fanClubRepository;


	@Override
	public FanClub convert(final String s) {
		FanClub result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.fanClubRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
