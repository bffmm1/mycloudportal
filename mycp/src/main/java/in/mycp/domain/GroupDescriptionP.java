package in.mycp.domain;

import java.util.List;
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
@RooJpaActiveRecord(versionField = "", table = "group_description_p", finders = { "findGroupDescriptionPsByNameEquals", "findGroupDescriptionPsByOwnerEquals", "findGroupDescriptionPsByAsset", "findGroupDescriptionPsByDescriptonLike" })
public class GroupDescriptionP {

    @Transient
    public String product;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public static TypedQuery<in.mycp.domain.GroupDescriptionP> findActiveGroupDescriptionPsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("The user argument is required");
        EntityManager em = entityManager();
        TypedQuery<GroupDescriptionP> q = em.createQuery("SELECT o FROM GroupDescriptionP AS o WHERE o.status='active' and o.asset.user = :user", GroupDescriptionP.class);
        q.setParameter("user", user);
        return q;
    }

    public static TypedQuery<in.mycp.domain.GroupDescriptionP> findAllGroupDescriptionPsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("The user argument is required");
        EntityManager em = entityManager();
        TypedQuery<GroupDescriptionP> q = em.createQuery("SELECT o FROM GroupDescriptionP AS o WHERE o.asset.user = :user", GroupDescriptionP.class);
        q.setParameter("user", user);
        return q;
    }

    public static TypedQuery<in.mycp.domain.GroupDescriptionP> findAllGroupDescriptionPsByCompany(Company company) {
        if (company == null) throw new IllegalArgumentException("The company argument is required");
        EntityManager em = entityManager();
        TypedQuery<GroupDescriptionP> q = em.createQuery("SELECT o FROM GroupDescriptionP AS o WHERE o.asset.user.project.department.company = :company", GroupDescriptionP.class);
        q.setParameter("company", company);
        return q;
    }

    public static TypedQuery<in.mycp.domain.GroupDescriptionP> findActiveGroupDescriptionPsByCompany(Company company) {
        if (company == null) throw new IllegalArgumentException("The company argument is required");
        EntityManager em = entityManager();
        TypedQuery<GroupDescriptionP> q = em.createQuery("SELECT o FROM GroupDescriptionP AS o WHERE o.status='active' and o.asset.user.project.department.company = :company", GroupDescriptionP.class);
        q.setParameter("company", company);
        return q;
    }

    public static Number findGroupDescriptionCountByCompany(Company company, String status) {
        String queryStr = "SELECT COUNT(i.id) FROM GroupDescriptionP i where i.status = :status ";
        if (company != null) {
            queryStr = queryStr + "  and i.asset.user.project.department.company = :company";
        }
        Query q = entityManager().createQuery(queryStr);
        q.setParameter("status", status);
        if (company != null) {
            q.setParameter("company", company);
        }
        return (Number) q.getSingleResult();
    }

    public static List<in.mycp.domain.GroupDescriptionP> findAllActiveGroupDescriptionPs() {
        return entityManager().createQuery("SELECT o FROM GroupDescriptionP o where o.status='active'", GroupDescriptionP.class).getResultList();
    }
    
    public static TypedQuery<GroupDescriptionP> findGroupDescriptionPsByNameEqualsAndCompanyEquals(String name,Company company) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = entityManager();
        TypedQuery<GroupDescriptionP> q = em.createQuery("SELECT o FROM GroupDescriptionP AS o WHERE o.name = :name " +
        		" and o.asset.user.project.department.company = :company", GroupDescriptionP.class);
        q.setParameter("name", name);
        q.setParameter("company", company);
        return q;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
