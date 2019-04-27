
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.VictoryRepository;
import domain.Victory;

@Component
@Transactional
public class StringToVictoryConverter implements Converter<String, Victory> {

	@Autowired
	VictoryRepository	victoryRepository;


	@Override
	public Victory convert(final String s) {
		Victory result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.victoryRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
