// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package in.mycp.domain;

import in.mycp.domain.Infra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Infra_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Infra.entityManager;
    
    public static final EntityManager Infra.entityManager() {
        EntityManager em = new Infra().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Infra.countInfras() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Infra o", Long.class).getSingleResult();
    }
    
    public static List<Infra> Infra.findAllInfras() {
        return entityManager().createQuery("SELECT o FROM Infra o", Infra.class).getResultList();
    }
    
    public static Infra Infra.findInfra(Integer id) {
        if (id == null) return null;
        return entityManager().find(Infra.class, id);
    }
    
    public static List<Infra> Infra.findInfraEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Infra o", Infra.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Infra.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Infra.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Infra attached = Infra.findInfra(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Infra.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Infra.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Infra Infra.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Infra merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
