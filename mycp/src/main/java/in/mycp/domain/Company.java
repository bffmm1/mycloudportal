package in.mycp.domain;

import java.util.List;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooDbManaged(automaticallyDelete = true)
@RooJpaActiveRecord(versionField = "", table = "company", finders = { "findCompanysByNameEquals" })
public class Company {

    public static List<java.lang.String> findAllDistinctCurrency() {
        return entityManager().createQuery("SELECT DISTINCT c.currency FROM Company AS c", String.class).getResultList();
    }
}
