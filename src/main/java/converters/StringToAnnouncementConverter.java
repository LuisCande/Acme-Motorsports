
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.AnnouncementRepository;
import domain.Announcement;

@Component
@Transactional
public class StringToAnnouncementConverter implements Converter<String, Announcement> {

	@Autowired
	AnnouncementRepository	announcementRepository;


	@Override
	public Announcement convert(final String s) {
		Announcement result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.announcementRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
