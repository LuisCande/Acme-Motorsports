
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.RepresentativeRepository;
import domain.Representative;

@Component
@Transactional
public class StringToRepresentativeConverter implements Converter<String, Representative> {

	@Autowired
	RepresentativeRepository	representativeRepository;


	@Override
	public Representative convert(final String s) {
		Representative result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.representativeRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
