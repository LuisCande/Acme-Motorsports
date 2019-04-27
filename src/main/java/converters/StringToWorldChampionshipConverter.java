
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.WorldChampionshipRepository;
import domain.WorldChampionship;

@Component
@Transactional
public class StringToWorldChampionshipConverter implements Converter<String, WorldChampionship> {

	@Autowired
	WorldChampionshipRepository	worldChampionshipRepository;


	@Override
	public WorldChampionship convert(final String s) {
		WorldChampionship result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.worldChampionshipRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
