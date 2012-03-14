// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package in.mycp.domain;

import in.mycp.domain.KeyPairInfoP;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect KeyPairInfoP_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager KeyPairInfoP.entityManager;
    
    public static final EntityManager KeyPairInfoP.entityManager() {
        EntityManager em = new KeyPairInfoP().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long KeyPairInfoP.countKeyPairInfoPs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM KeyPairInfoP o", Long.class).getSingleResult();
    }
    
    public static List<KeyPairInfoP> KeyPairInfoP.findAllKeyPairInfoPs() {
        return entityManager().createQuery("SELECT o FROM KeyPairInfoP o", KeyPairInfoP.class).getResultList();
    }
    
    public static KeyPairInfoP KeyPairInfoP.findKeyPairInfoP(Integer id) {
        if (id == null) return null;
        return entityManager().find(KeyPairInfoP.class, id);
    }
    
    public static List<KeyPairInfoP> KeyPairInfoP.findKeyPairInfoPEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM KeyPairInfoP o", KeyPairInfoP.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void KeyPairInfoP.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void KeyPairInfoP.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            KeyPairInfoP attached = KeyPairInfoP.findKeyPairInfoP(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void KeyPairInfoP.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void KeyPairInfoP.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public KeyPairInfoP KeyPairInfoP.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        KeyPairInfoP merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
