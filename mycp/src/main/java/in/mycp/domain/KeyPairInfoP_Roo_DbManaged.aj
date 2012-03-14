// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package in.mycp.domain;

import in.mycp.domain.Asset;
import in.mycp.domain.KeyPairInfoP;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

privileged aspect KeyPairInfoP_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "asset", referencedColumnName = "id")
    private Asset KeyPairInfoP.asset;
    
    @Column(name = "keyName", length = 90)
    private String KeyPairInfoP.keyName;
    
    @Column(name = "keyFingerprint", length = 512)
    private String KeyPairInfoP.keyFingerprint;
    
    @Column(name = "keyMaterial", length = 2048)
    private String KeyPairInfoP.keyMaterial;
    
    @Column(name = "status", length = 45)
    private String KeyPairInfoP.status;
    
    public Asset KeyPairInfoP.getAsset() {
        return asset;
    }
    
    public void KeyPairInfoP.setAsset(Asset asset) {
        this.asset = asset;
    }
    
    public String KeyPairInfoP.getKeyName() {
        return keyName;
    }
    
    public void KeyPairInfoP.setKeyName(String keyName) {
        this.keyName = keyName;
    }
    
    public String KeyPairInfoP.getKeyFingerprint() {
        return keyFingerprint;
    }
    
    public void KeyPairInfoP.setKeyFingerprint(String keyFingerprint) {
        this.keyFingerprint = keyFingerprint;
    }
    
    public String KeyPairInfoP.getKeyMaterial() {
        return keyMaterial;
    }
    
    public void KeyPairInfoP.setKeyMaterial(String keyMaterial) {
        this.keyMaterial = keyMaterial;
    }
    
    public String KeyPairInfoP.getStatus() {
        return status;
    }
    
    public void KeyPairInfoP.setStatus(String status) {
        this.status = status;
    }
    
}
