
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//The average, the minimum, the maximum, and the standard deviation of the number of applications per grand prixes
	@Query("select avg((select count(a) from Application a where a.grandPrix.id=g.id)*1.), min((select count(a) from Application a where a.grandPrix.id=g.id)*1.), max((select count(a) from Application a where a.grandPrix.id=g.id)*1.), stddev((select count(a) from Application a where a.grandPrix.id=g.id)*1.) from GrandPrix g")
	Double[] avgMinMaxStddevApplicationsPerGrandPrix();

	//The ratio of pending applications
	@Query("select count(a)*1./(select count(a1) from Application a1) from Application a where a.status='0'")
	Double ratioPendingApplications();

	//The ratio of accepted applications
	@Query("select count(a)*1./(select count(a1) from Application a1) from Application a where a.status='1'")
	Double ratioAcceptedApplications();

	//The ratio of rejected applications
	@Query("select count(a)*1./(select count(a1) from Application a1) from Application a where a.status='2'")
	Double ratioRejectedApplications();

}
