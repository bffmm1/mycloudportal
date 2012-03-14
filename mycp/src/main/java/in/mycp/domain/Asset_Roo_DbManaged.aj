// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package in.mycp.domain;

import in.mycp.domain.AddressInfoP;
import in.mycp.domain.Asset;
import in.mycp.domain.AssetType;
import in.mycp.domain.GroupDescriptionP;
import in.mycp.domain.ImageDescriptionP;
import in.mycp.domain.InstanceP;
import in.mycp.domain.IpAddressP;
import in.mycp.domain.KeyPairInfoP;
import in.mycp.domain.ProductCatalog;
import in.mycp.domain.SnapshotInfoP;
import in.mycp.domain.User;
import in.mycp.domain.VolumeInfoP;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

privileged aspect Asset_Roo_DbManaged {
    
    @OneToMany(mappedBy = "asset")
    private Set<AddressInfoP> Asset.addressInfoPs;
    
    @OneToMany(mappedBy = "asset")
    private Set<GroupDescriptionP> Asset.groupDescriptionPs;
    
    @OneToMany(mappedBy = "asset")
    private Set<ImageDescriptionP> Asset.imageDescriptionPs;
    
    @OneToMany(mappedBy = "asset")
    private Set<InstanceP> Asset.instancePs;
    
    @OneToMany(mappedBy = "asset")
    private Set<IpAddressP> Asset.ipAddressPs;
    
    @OneToMany(mappedBy = "asset")
    private Set<KeyPairInfoP> Asset.keyPairInfoPs;
    
    @OneToMany(mappedBy = "asset")
    private Set<SnapshotInfoP> Asset.snapshotInfoPs;
    
    @OneToMany(mappedBy = "asset")
    private Set<VolumeInfoP> Asset.volumeInfoPs;
    
    @ManyToOne
    @JoinColumn(name = "assetType", referencedColumnName = "id")
    private AssetType Asset.assetType;
    
    @ManyToOne
    @JoinColumn(name = "productCatalog", referencedColumnName = "id")
    private ProductCatalog Asset.productCatalog;
    
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User Asset.user;
    
    @Column(name = "name", length = 45)
    private String Asset.name;
    
    @Column(name = "active")
    private Boolean Asset.active;
    
    @Column(name = "details", length = 255)
    private String Asset.details;
    
    @Column(name = "startTime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date Asset.startTime;
    
    @Column(name = "endTime")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date Asset.endTime;
    
    @Column(name = "startRate")
    private Integer Asset.startRate;
    
    public Set<AddressInfoP> Asset.getAddressInfoPs() {
        return addressInfoPs;
    }
    
    public void Asset.setAddressInfoPs(Set<AddressInfoP> addressInfoPs) {
        this.addressInfoPs = addressInfoPs;
    }
    
    public Set<GroupDescriptionP> Asset.getGroupDescriptionPs() {
        return groupDescriptionPs;
    }
    
    public void Asset.setGroupDescriptionPs(Set<GroupDescriptionP> groupDescriptionPs) {
        this.groupDescriptionPs = groupDescriptionPs;
    }
    
    public Set<ImageDescriptionP> Asset.getImageDescriptionPs() {
        return imageDescriptionPs;
    }
    
    public void Asset.setImageDescriptionPs(Set<ImageDescriptionP> imageDescriptionPs) {
        this.imageDescriptionPs = imageDescriptionPs;
    }
    
    public Set<InstanceP> Asset.getInstancePs() {
        return instancePs;
    }
    
    public void Asset.setInstancePs(Set<InstanceP> instancePs) {
        this.instancePs = instancePs;
    }
    
    public Set<IpAddressP> Asset.getIpAddressPs() {
        return ipAddressPs;
    }
    
    public void Asset.setIpAddressPs(Set<IpAddressP> ipAddressPs) {
        this.ipAddressPs = ipAddressPs;
    }
    
    public Set<KeyPairInfoP> Asset.getKeyPairInfoPs() {
        return keyPairInfoPs;
    }
    
    public void Asset.setKeyPairInfoPs(Set<KeyPairInfoP> keyPairInfoPs) {
        this.keyPairInfoPs = keyPairInfoPs;
    }
    
    public Set<SnapshotInfoP> Asset.getSnapshotInfoPs() {
        return snapshotInfoPs;
    }
    
    public void Asset.setSnapshotInfoPs(Set<SnapshotInfoP> snapshotInfoPs) {
        this.snapshotInfoPs = snapshotInfoPs;
    }
    
    public Set<VolumeInfoP> Asset.getVolumeInfoPs() {
        return volumeInfoPs;
    }
    
    public void Asset.setVolumeInfoPs(Set<VolumeInfoP> volumeInfoPs) {
        this.volumeInfoPs = volumeInfoPs;
    }
    
    public AssetType Asset.getAssetType() {
        return assetType;
    }
    
    public void Asset.setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }
    
    public ProductCatalog Asset.getProductCatalog() {
        return productCatalog;
    }
    
    public void Asset.setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }
    
    public User Asset.getUser() {
        return user;
    }
    
    public void Asset.setUser(User user) {
        this.user = user;
    }
    
    public String Asset.getName() {
        return name;
    }
    
    public void Asset.setName(String name) {
        this.name = name;
    }
    
    public Boolean Asset.getActive() {
        return active;
    }
    
    public void Asset.setActive(Boolean active) {
        this.active = active;
    }
    
    public String Asset.getDetails() {
        return details;
    }
    
    public void Asset.setDetails(String details) {
        this.details = details;
    }
    
    public Date Asset.getStartTime() {
        return startTime;
    }
    
    public void Asset.setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date Asset.getEndTime() {
        return endTime;
    }
    
    public void Asset.setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public Integer Asset.getStartRate() {
        return startRate;
    }
    
    public void Asset.setStartRate(Integer startRate) {
        this.startRate = startRate;
    }
    
}
