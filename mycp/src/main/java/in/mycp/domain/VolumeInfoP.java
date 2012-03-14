package in.mycp.domain;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooDbManaged(automaticallyDelete = true)
@RooJpaActiveRecord(versionField = "", table = "volume_info_p", finders = { "findVolumeInfoPsByVolumeIdEquals", "findVolumeInfoPsByAsset" })
public class VolumeInfoP {

    @Transient
    private String details;

    @Transient
    public String product;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public static TypedQuery<in.mycp.domain.VolumeInfoP> findVolumeInfoPsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("The user argument is required");
        EntityManager em = entityManager();
        TypedQuery<VolumeInfoP> q = em.createQuery("SELECT o FROM VolumeInfoP AS o WHERE o.asset.user = :user", VolumeInfoP.class);
        q.setParameter("user", user);
        return q;
    }

    public static TypedQuery<in.mycp.domain.VolumeInfoP> findVolumeInfoPsByCompany(Company company) {
        if (company == null) throw new IllegalArgumentException("The company argument is required");
        EntityManager em = entityManager();
        TypedQuery<VolumeInfoP> q = em.createQuery("SELECT o FROM VolumeInfoP AS o WHERE o.asset.user.project.department.company = :company", VolumeInfoP.class);
        q.setParameter("company", company);
        return q;
    }

    public static Number findVolumeInfoCountByCompany(Company company, String status) {
        String queryStr = "SELECT COUNT(i.id) FROM VolumeInfoP i where i.status = :status ";
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
}
