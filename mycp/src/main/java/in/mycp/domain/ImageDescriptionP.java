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
@RooJpaActiveRecord(versionField = "", table = "image_description_p", finders = { "findImageDescriptionPsByImageIdEquals", "findImageDescriptionPsByAsset" })
public class ImageDescriptionP {

    @Transient
    private String instanceIdForImgCreation;

    public String getInstanceIdForImgCreation() {
        return instanceIdForImgCreation;
    }

    public void setInstanceIdForImgCreation(String instanceIdForImgCreation) {
        this.instanceIdForImgCreation = instanceIdForImgCreation;
    }

    public static TypedQuery<in.mycp.domain.ImageDescriptionP> findImageDescriptionPsByUser(User user) {
        if (user == null) throw new IllegalArgumentException("The user argument is required");
        EntityManager em = entityManager();
        TypedQuery<ImageDescriptionP> q = em.createQuery("SELECT o FROM ImageDescriptionP AS o WHERE o.asset.user = :user", ImageDescriptionP.class);
        q.setParameter("user", user);
        return q;
    }

    public static TypedQuery<in.mycp.domain.ImageDescriptionP> findImageDescriptionPsByCompany(Company company) {
        if (company == null) throw new IllegalArgumentException("The company argument is required");
        EntityManager em = entityManager();
        TypedQuery<ImageDescriptionP> q = em.createQuery("SELECT o FROM ImageDescriptionP AS o WHERE o.asset.user.project.department.company = :company", ImageDescriptionP.class);
        q.setParameter("company", company);
        return q;
    }

    public static Number findImageDescriptionCountByCompany(Company company, String status) {
        String queryStr = "SELECT COUNT(i.id) FROM ImageDescriptionP i where i.status = :status ";
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

    public static TypedQuery<ImageDescriptionP> findImageDescriptionPsByImageIdEqualsAndCompanyEquals(String imageId,Company company) {
        if (imageId == null || imageId.length() == 0) throw new IllegalArgumentException("The imageId argument is required");
        EntityManager em = entityManager();
        TypedQuery<ImageDescriptionP> q = em.createQuery("SELECT o FROM ImageDescriptionP AS o WHERE o.imageId = :imageId" +
        		" and o.asset.user.project.department.company = :company", ImageDescriptionP.class);
        q.setParameter("imageId", imageId);
        q.setParameter("company", company);
        return q;
    }
    
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
