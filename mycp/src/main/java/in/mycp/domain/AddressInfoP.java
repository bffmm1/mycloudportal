package in.mycp.domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooDbManaged(automaticallyDelete = true)
@RooJpaActiveRecord(versionField = "", table = "address_info_p", finders = { "findAddressInfoPsByAsset", "findAddressInfoPsByInstanceIdEquals", "findAddressInfoPsByPublicIpEquals", "findAddressInfoPsByInstanceIdLike" })
public class AddressInfoP {

    @Transient
    public String product;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public static TypedQuery<in.mycp.domain.AddressInfoP> findAddressInfoPsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("The user argument is required");
        EntityManager em = entityManager();
        TypedQuery<AddressInfoP> q = em.createQuery("SELECT o FROM AddressInfoP AS o WHERE o.asset.user = :user", AddressInfoP.class);
        q.setParameter("user", user);
        return q;
    }

    public static TypedQuery<in.mycp.domain.AddressInfoP> findAddressInfoPsByCompany(Company company) {
        if (company == null) throw new IllegalArgumentException("The company argument is required");
        EntityManager em = entityManager();
        TypedQuery<AddressInfoP> q = em.createQuery("SELECT o FROM AddressInfoP AS o WHERE o.asset.user.project.department.company = :company", AddressInfoP.class);
        q.setParameter("company", company);
        return q;
    }

    public static Number findAddressInfoCountByCompany(Company company) {
        String queryStr = "SELECT COUNT(i.id) FROM AddressInfoP i  ";
        if (company != null) {
            queryStr = queryStr + "  where i.asset.user.project.department.company = :company";
        }
        Query q = entityManager().createQuery(queryStr);
        //q.setParameter("status", status);
        if (company != null) {
            q.setParameter("company", company);
        }
        return (Number) q.getSingleResult();
    }

    public static TypedQuery<AddressInfoP> findAddressInfoPsByPublicIpEqualsAndCompanyEquals(String publicIp,Company company) {
        if (publicIp == null || publicIp.length() == 0) throw new IllegalArgumentException("The publicIp argument is required");
        EntityManager em = entityManager();
        TypedQuery<AddressInfoP> q = em.createQuery("SELECT o FROM AddressInfoP AS o WHERE o.publicIp = :publicIp " +
        		" and o.asset.user.project.department.company = :company", AddressInfoP.class);
        q.setParameter("publicIp", publicIp);
        q.setParameter("company", company);
        return q;
    }
    
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
