// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package in.mycp.domain;

import in.mycp.domain.IpAddressP;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect IpAddressP_Roo_Jpa_Entity {
    
    declare @type: IpAddressP: @Entity;
    
    declare @type: IpAddressP: @Table(name = "ip_address_p");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer IpAddressP.id;
    
    public Integer IpAddressP.getId() {
        return this.id;
    }
    
    public void IpAddressP.setId(Integer id) {
        this.id = id;
    }
    
}
