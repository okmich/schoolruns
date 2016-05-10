/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.common.entity;


import com.okmich.common.util.api.CommonConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Michael
 */
public class UserInfo extends UserLogin implements UserDetails {

    private List<GrantedAuthority> grantedAuthorities;

    /**
     * default constructor
     */
    public UserInfo() {
        this.grantedAuthorities = new ArrayList<GrantedAuthority>();
    }

    /**
     *
     * @param <G>
     * @param g
     */
    public <G extends GrantedAuthority> void addAuthourity(G g) {
        this.grantedAuthorities.add(g);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return getStatus() != null
                && getStatus().equals(CommonConstants.STATUS_EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return getStatus() != null
                && getStatus().equals(CommonConstants.STATUS_LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return getStatus() != null
                && getStatus().equals(CommonConstants.STATUS_ACTIVE);
    }

    /**
     *
     * @return
     */
    public Integer getSchoolId() {
        return getSchool() == null ? null : getSchool().getSchoolId();
    }

    /**
     *
     * @return
     */
    public String getSchoolname() {
        return getSchool() == null ? null : getSchool().getName();
    }
}
