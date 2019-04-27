
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.WorldChampionRepository;
import domain.WorldChampion;

@Component
@Transactional
public class StringToWorldChampionConverter implements Converter<String, WorldChampion> {

	@Autowired
	WorldChampionRepository	worldChampionRepository;


	@Override
	public WorldChampion convert(final String s) {
		WorldChampion result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.worldChampionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
